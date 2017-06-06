package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.ConfiguracaoFinanceiro;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.TipoDespesa;
import br.com.onesystem.domain.Transferencia;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.services.BaixaEmissaoComparator;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.ErrorMessage;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.war.builder.BaixaBV;
import br.com.onesystem.war.builder.ExtratoDeContaBV;
import br.com.onesystem.war.builder.TransferenciaBV;
import br.com.onesystem.war.service.BaixaService;
import br.com.onesystem.war.service.ConfiguracaoFinanceiroService;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.inject.Named;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class ExtratoDeContaView implements Serializable {

    private Baixa baixaSelecionada;
    private Date hoje = new Date();
    private ExtratoDeContaBV extrato;
    private TransferenciaBV transferencia;
    private BaixaBV baixa;
    private Baixa baixaSelecionado;
    private List<Baixa> baixas;
    private List<Baixa> tarifasTransferencia = new ArrayList<Baixa>();

    @ManagedProperty("#{baixaService}")
    private BaixaService service;

    @ManagedProperty("#{configuracaoFinanceiroService}")
    private ConfiguracaoFinanceiroService serviceFinanceiro;

    @PostConstruct
    public void init() {
        extrato = new ExtratoDeContaBV();
        transferencia = new TransferenciaBV();
        baixa = new BaixaBV();
        buscarConfiguracaoDeConta();
    }

    private void buscarConfiguracaoDeConta() {
        ConfiguracaoFinanceiro conf = serviceFinanceiro.buscar();
        extrato.setConta(conf == null ? null : conf.getContaPadrao());
        if (extrato.getConta() != null) {
            baixas = service.buscarBaixasPelaData(extrato.getDataInicial(), extrato.getDataFinal(), extrato.getConta());
            Collections.sort(baixas, new BaixaEmissaoComparator());
        }
    }

    public void atualizar() {
        try {
            validaConta();
            Calendar dataAtual = Calendar.getInstance();
            dataAtual.setTime(extrato.getDataInicial() != null ? extrato.getDataInicial() : Calendar.getInstance().getTime());
            dataAtual.set(Calendar.HOUR_OF_DAY, 0);
            dataAtual.set(Calendar.MINUTE, 0);
            dataAtual.set(Calendar.SECOND, 0);
            extrato.setDataInicial(dataAtual.getTime());
            dataAtual.setTime(extrato.getDataFinal() != null ? extrato.getDataFinal() : Calendar.getInstance().getTime());
            dataAtual.set(Calendar.HOUR_OF_DAY, 23);
            dataAtual.set(Calendar.MINUTE, 59);
            dataAtual.set(Calendar.SECOND, 59);
            extrato.setDataFinal(dataAtual.getTime());
            baixas = service.buscarBaixasPelaData(extrato.getDataInicial(), extrato.getDataFinal(), extrato.getConta());
            Collections.sort(baixas, new BaixaEmissaoComparator());
        } catch (EDadoInvalidoException ex) {
            ex.print();
        }
    }

    private void validaConta() throws EDadoInvalidoException {
        if (extrato.getConta() == null) {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("conta_not_null"));
        }
    }

    public void transferir() {
        try {
            Transferencia novaTransferencia = transferencia.construir();
            if (novaTransferencia.getOrigem().equals(novaTransferencia.getDestino())) {
                String message = new BundleUtil().getMessage("Transferencia_Conta_Diferente");
                throw new EDadoInvalidoException(message);
            }
            new AdicionaDAO<Transferencia>().adiciona(novaTransferencia);
            adicionaBaixas(novaTransferencia);
            transferencia = new TransferenciaBV();
            tarifasTransferencia = new ArrayList<Baixa>();
            atualizar();
            InfoMessage.print("Transferência realizada com sucesso!");
        } catch (DadoInvalidoException ex) {
            ex.print();
        } catch (ConstraintViolationException cve) {
            ErrorMessage.print(cve.getConstraintName());
        }
    }

    public void selecionarOrigem(SelectEvent event) {
        Conta conta = (Conta) event.getObject();
        this.transferencia.setOrigem(conta);
        limparTarifas();
    }

    public void selecionarDestino(SelectEvent event) {
        Conta conta = (Conta) event.getObject();
        this.transferencia.setDestino(conta);
    }

    public boolean ehMoedaIgual() {
        if (transferencia.getOrigem() != null && transferencia.getDestino() != null) {
            return transferencia.getOrigem().getMoeda().equals(transferencia.getDestino().getMoeda());
        } else {
            return true;
        }
    }

    public void abrirBaixaEdicaoComDados() {
        System.out.println("sf: " + baixaSelecionado);
        baixa = new BaixaBV(baixaSelecionado);
    }

    private void adicionaBaixas(Transferencia novaTransferencia) throws ConstraintViolationException, DadoInvalidoException {
        BaixaBV origemBV = new BaixaBV();
        origemBV.setValor(transferencia.getValor());
        origemBV.setNaturezaFinanceira(OperacaoFinanceira.SAIDA);
        //origemBV.setConta(novaTransferencia.getOrigem());
        origemBV.setTransferencia(novaTransferencia);
        Baixa origem = origemBV.construir();
        BaixaBV destinoBV = new BaixaBV();
        destinoBV.setValor(transferencia.getOrigem().getMoeda().equals(transferencia.getDestino().getMoeda()) ? transferencia.getValor() : transferencia.getValorConvertido());
        destinoBV.setNaturezaFinanceira(OperacaoFinanceira.ENTRADA);
        //destinoBV.setConta(novaTransferencia.getDestino());
        destinoBV.setTransferencia(novaTransferencia);
        Baixa destino = destinoBV.construir();

        new AdicionaDAO<Baixa>().adiciona(origem);
        new AdicionaDAO<Baixa>().adiciona(destino);

        if (tarifasTransferencia.size() > 0) {
            for (Baixa baixaNova : tarifasTransferencia) {
                BaixaBV baixaBV = new BaixaBV(baixaNova);
                baixaBV.setEmissao(new Date());
                baixaBV.setTransferencia(novaTransferencia);
                new AdicionaDAO<Baixa>().adiciona(baixaBV.construir());
            }
        }
    }

    public void limparTarifas() {
        tarifasTransferencia = new ArrayList<Baixa>();
    }

    public void selecionarConta(SelectEvent event) {
        Conta conta = (Conta) event.getObject();
        this.extrato.setConta(conta);
        atualizar();
    }

    public void addBaixa() {
        try {
            if (baixa.getValor() != null) {
                if (baixa.getValor().compareTo(BigDecimal.ZERO) == 0) {
                    throw new EDadoInvalidoException("O valor deve ser maior que zero!");
                }
                baixa.setEmissao(new Date());
                baixa.setNaturezaFinanceira(OperacaoFinanceira.SAIDA);
                //      baixa.setConta(transferencia.getOrigem());
                baixa.setId(retornarCodigo());
//                baixa.setDesconto(BigDecimal.ZERO);
//                baixa.setJuros(BigDecimal.ZERO);
//                baixa.setMultas(BigDecimal.ZERO);
                Baixa novoRegistro = baixa.construirComID();
                tarifasTransferencia.add(novoRegistro);
                InfoMessage.print("Tarifa '" + novoRegistro.getDespesa().getNome() + "' adicionada com sucesso.");
                limparBaixa();
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void deleteBaixa() {
        if (tarifasTransferencia != null && tarifasTransferencia.contains(baixaSelecionado)) {
            tarifasTransferencia.remove(baixaSelecionado);
            InfoMessage.print("!Baixa '" + this.baixaSelecionado.getDespesa().getNome() + "' eliminada con êxito!");
            limparBaixa();
        }
    }

    public void selecionaDespesa(SelectEvent event) {
        TipoDespesa despesaSelecionada = (TipoDespesa) event.getObject();
        baixa.setDespesa(despesaSelecionada);
    }

    private Long retornarCodigo() {
        Long id = (long) 1;
        if (!tarifasTransferencia.isEmpty()) {
            for (Baixa baixaExistente : tarifasTransferencia) {
                if (baixaExistente.getId() >= id) {
                    id = baixaExistente.getId() + 1;
                }
            }
        }
        return id;
    }

    private void limparBaixa() {
        baixa = new BaixaBV();
        baixaSelecionado = new Baixa();
    }

    public List<Baixa> getBaixas() {
        return baixas;
    }

    public void setBaixas(List<Baixa> baixas) {
        this.baixas = baixas;
    }

    public Baixa getBaixaSelecionada() {
        return baixaSelecionada;
    }

    public void setBaixaSelecionada(Baixa baixaSelecionada) {
        this.baixaSelecionada = baixaSelecionada;
    }

    public BaixaService getService() {
        return service;
    }

    public void setService(BaixaService service) {
        this.service = service;
    }

    public Date getHoje() {
        return hoje;
    }

    public void setHoje(Date hoje) {
        this.hoje = hoje;
    }

    public ExtratoDeContaBV getExtrato() {
        return extrato;
    }

    public void setExtrato(ExtratoDeContaBV extrato) {
        this.extrato = extrato;
    }

    public TransferenciaBV getTransferencia() {
        return transferencia;
    }

    public void setTransferencia(TransferenciaBV transferencia) {
        this.transferencia = transferencia;
    }

    public BaixaBV getBaixa() {
        return baixa;
    }

    public void setBaixa(BaixaBV baixa) {
        this.baixa = baixa;
    }

    public Baixa getBaixaSelecionado() {
        return baixaSelecionado;
    }

    public void setBaixaSelecionado(Baixa baixaSelecionado) {
        this.baixaSelecionado = baixaSelecionado;
    }

    public List<Baixa> getTarifasTransferencia() {
        return tarifasTransferencia;
    }

    public void setTarifasTransferencia(List<Baixa> tarifasTransferencia) {
        this.tarifasTransferencia = tarifasTransferencia;
    }

    public ConfiguracaoFinanceiroService getServiceFinanceiro() {
        return serviceFinanceiro;
    }

    public void setServiceFinanceiro(ConfiguracaoFinanceiroService serviceFinanceiro) {
        this.serviceFinanceiro = serviceFinanceiro;
    }

}
