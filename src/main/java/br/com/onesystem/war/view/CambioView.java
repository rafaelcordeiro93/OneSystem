package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.BaixaDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.Cambio;
import br.com.onesystem.domain.ConfiguracaoCambio;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.ContratoDeCambio;
import br.com.onesystem.domain.Despesa;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.FatalMessage;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.util.WarningMessage;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.war.builder.BaixaBV;
import br.com.onesystem.war.builder.CambioBV;
import br.com.onesystem.war.service.CambioService;
import br.com.onesystem.war.service.ConfiguracaoCambioService;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.inject.Named;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class CambioView implements Serializable {

    private boolean panel;
    private CambioBV cambio;
    private Cambio cambioSelecionado;
    private List<Cambio> cambioLista;
    private List<Cambio> cambiosFiltrados;
    private BaixaBV baixa;
    private Baixa baixaSelecionado;
    private List<Baixa> baixaLista = new ArrayList<Baixa>();
    private ConfiguracaoCambio configuracaoCambio;

    @ManagedProperty("#{cambioService}")
    private CambioService service;

    @ManagedProperty("#{configuracaoCambioService}")
    private ConfiguracaoCambioService serviceConfCambio;

    @PostConstruct
    public void init() {
        limparJanela();
        panel = false;
        cambioLista = service.buscarCambios();
        inicializaConfiguracoes();
    }

    private void inicializaConfiguracoes() {
        try {
            configuracaoCambio = serviceConfCambio.buscar();
        } catch (EDadoInvalidoException ex) {
            ex.print();
        }
    }

    public void add() {
        try {
            if (cambio.getPorcentagemDeComissao() == null) {
                cambio.setPorcentagemDeComissao(BigDecimal.ZERO);
            }
            if (cambio.getComissaoCalculada() != null && cambio.getComissaoCalculada().compareTo(BigDecimal.ZERO) == 1
                    && cambio.getPessoaComissionada() == null) {
                throw new EDadoInvalidoException("Quando haver comissão a pessoa comissionada deve ser informada.");
            }
            if (cambio.getComissaoCalculada() == null || cambio.getComissaoCalculada().compareTo(BigDecimal.ZERO) == 0) {
                cambio.setPessoaComissionada(null);
            }
            Cambio novoRegistro = cambio.construir();
            novoRegistro.gerarComissao();
            novoRegistro.baixarContasAPagar();
            novoRegistro = dividirLucros(novoRegistro);
            new AdicionaDAO<Cambio>().adiciona(novoRegistro);
            cambioLista.add(novoRegistro);
            if (baixaLista.size() > 0) {
                for (Baixa baixaNova : baixaLista) {
                    BaixaBV baixaBV = new BaixaBV(baixaNova);
                    baixaBV.setCambio(novoRegistro);
                    new AdicionaDAO<Baixa>().adiciona(baixaBV.construir());
                }
            }
            InfoMessage.print("Cambio '" + novoRegistro.getId() + "' agregado con êxito!");
            limparJanela();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void update() {
        try {
            Cambio cambioExistente = cambio.construirComID();
            if (cambioExistente.getId() != null) {
                new AtualizaDAO<>().atualiza(cambioExistente);
                cambioLista.set(cambioLista.indexOf(cambioExistente),
                        cambioExistente);
                if (baixaLista.size() > 0) {
                    for (Baixa baixaAtualiza : baixaLista) {
                        new AtualizaDAO<>().atualiza(new BaixaBV(baixaAtualiza).construirComID());
                    }
                }
                if (cambiosFiltrados != null && cambiosFiltrados.contains(cambioExistente)) {
                    cambiosFiltrados.set(cambiosFiltrados.indexOf(cambioExistente), cambioExistente);
                }
                InfoMessage.print("Â¡Cambio '" + cambioExistente.getId() + "' cambiado con Ã©xito!");
                limparJanela();
            } else {
                throw new EDadoInvalidoException("!La cambio no se encontra registrada!");
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void delete() {
        try {
            if (cambioLista != null && cambioLista.contains(cambioSelecionado)) {
                new RemoveDAO<>().remove(cambioSelecionado, cambioSelecionado.getId());
                cambioLista.remove(cambioSelecionado);
                if (cambiosFiltrados != null && cambiosFiltrados.contains(cambioSelecionado)) {
                    cambiosFiltrados.remove(cambioSelecionado);
                }
                InfoMessage.print("Cambio '" + this.cambio.getId() + "' removido com sucesso!");
                limparJanela();
            }
        } catch (DadoInvalidoException di) {
            di.print();
        } catch (ConstraintViolationException pe) {
            FatalMessage.print(pe.getMessage(), pe.getCause());
        }
    }

    public void addBaixa() {
        try {
            if (baixa.getValor() != null) {
                if (baixa.getValor().compareTo(BigDecimal.ZERO) == 0) {
                    throw new EDadoInvalidoException("O valor deve ser maior que zero!");
                }
                baixa.setEmissao(new Date());
                baixa.setNaturezaFinanceira(OperacaoFinanceira.SAIDA);
                baixa.setCotacao(cambio.getCotacao());
                baixa.setId(retornarCodigo());
                baixa.setDesconto(BigDecimal.ZERO);
                baixa.setJuros(BigDecimal.ZERO);
                baixa.setMultas(BigDecimal.ZERO);
                Baixa novoRegistro = baixa.construirComID();
                baixaLista.add(novoRegistro);
                cambio.setValorLiquido(cambio.getValorLiquido().subtract(novoRegistro.getValor()));
                InfoMessage.print("!Gasto '" + novoRegistro.getDespesa().getNome() + "' agregado con êxito!");
                limparBaixa();
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void updateBaixa() {
        try {
            if (baixa.getValor() != null) {
                if (baixa.getValor().compareTo(BigDecimal.ZERO) == 0) {
                    throw new EDadoInvalidoException("O valor deve ser maior que zero!");
                }
                Baixa baixaExistente = baixa.construirComID();
                if (baixaExistente.getId() != null) {
                    baixaLista.set(baixaLista.indexOf(baixaSelecionado),
                            baixaExistente);
                    if (baixaSelecionado.getValor().compareTo(baixa.getValor()) == 1) {
                        cambio.setValorLiquido(cambio.getValorLiquido().add(baixaSelecionado.getValor().subtract(baixa.getValor())));
                    } else if (baixaSelecionado.getValor().compareTo(baixa.getValor()) == -1) {
                        cambio.setValorLiquido(cambio.getValorLiquido().subtract(baixa.getValor().subtract(baixaSelecionado.getValor())));
                    }
                    InfoMessage.print("Â¡Despesa '" + baixaExistente.getDespesa().getNome() + "' cambiado con Ã©xito!");
                    limparBaixa();
                } else {
                    throw new EDadoInvalidoException("!La cambio no se encontra registrada!");
                }
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    private Long retornarCodigo() {
        Long id = (long) 1;
        if (!baixaLista.isEmpty()) {
            for (Baixa baixaExistente : baixaLista) {
                if (baixaExistente.getId() >= id) {
                    id = baixaExistente.getId() + 1;
                }
            }
        }
        return id;
    }

    public void deleteBaixa() {
        if (baixaLista != null && baixaLista.contains(baixaSelecionado)) {
            baixaLista.remove(baixaSelecionado);
            cambio.setValorLiquido(cambio.getValorLiquido().add(baixaSelecionado.getValor()));
            InfoMessage.print("!Baixa '" + this.baixaSelecionado.getDespesa().getNome() + "' eliminada con êxito!");
            limparBaixa();
        }
    }

    public void calculaLucroDeTaxaEmPorcentagem() {
        if (cambio.getContrato() != null && cambio.getPorcentualLucroTaxa() != null) {
            BigDecimal taxa = cambio.getPorcentualLucroTaxa().multiply(cambio.getContrato().getTaxaNegociada(), new MathContext(20, RoundingMode.HALF_UP));
            taxa = taxa.divide(new BigDecimal(100));
            if (taxa.compareTo(BigDecimal.ZERO) == 1) {
                taxa = cambio.getContrato().getTaxaNegociada().subtract(taxa);
                cambio.setTaxaEfetivada(taxa);
            } else {
                cambio.setTaxaEfetivada(taxa.multiply(new BigDecimal(-1)));
            }

        }
        calculaValor();
    }

    public void calculaLucroDeTaxa() {
        if (cambio.getContrato() != null && cambio.getTaxaEfetivada() != null
                && cambio.getTaxaEfetivada().compareTo(BigDecimal.ZERO) == 1) {
            BigDecimal porcentagem = cambio.getTaxaEfetivada().divide(cambio.getContrato().getTaxaNegociada(), new MathContext(20, RoundingMode.HALF_UP));
            porcentagem = porcentagem.multiply(new BigDecimal(100));
            porcentagem = porcentagem.subtract(new BigDecimal(100));
            cambio.setPorcentualLucroTaxa(porcentagem.multiply(new BigDecimal(-1)));
        } else if (cambio.getContrato() == null || cambio.getTaxaEfetivada() == null
                || cambio.getTaxaEfetivada().compareTo(BigDecimal.ZERO) == 0) {
            cambio.setPorcentualLucroTaxa(null);
        }
        calculaValor();
    }

    public void calculaValor() {
        if (cambio.getContrato() != null) {
            BigDecimal valorBruto = calculaValorBruto();
            BigDecimal comissao = calculaComissao();
            BigDecimal despesa = calculaDespesas();
            BigDecimal saidas = comissao.add(despesa);
            BigDecimal valorLiquido = cambio.getContrato().getValorCalculado().subtract(valorBruto.add(saidas));

            cambio.setValorLiquido(valorLiquido);
        }
    }

    private Cambio dividirLucros(Cambio novoRegistro) {
        try {
            if (configuracaoCambio != null && configuracaoCambio.isAtivo()) {
                novoRegistro.dividirLucros(configuracaoCambio.getDespesaDivisaoLucro(),
                        serviceConfCambio.buscarPessoas(),
                        configuracaoCambio.getPessoaCaixa());
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
        return novoRegistro;
    }

    public BigDecimal calculaDespesas() {
        BigDecimal despesas = BigDecimal.ZERO;
        for (Baixa despesa : baixaLista) {
            despesas = despesas.add(despesa.getValor());
        }
        return despesas;
    }

    public BigDecimal calculaComissao() {
        BigDecimal comissao = cambio.getPorcentagemDeComissao() == null ? BigDecimal.ZERO : cambio.getPorcentagemDeComissao().multiply(cambio.getValorBruto());
        comissao = comissao.divide(new BigDecimal(100));
        cambio.setComissaoCalculada(comissao);
        return comissao;
    }

    public BigDecimal calculaValorBruto() {
        BigDecimal valorButro = cambio.getContrato().getValorNegociado().multiply(cambio.getTaxaEfetivada() == null ? BigDecimal.ZERO : cambio.getTaxaEfetivada(), new MathContext(3, RoundingMode.HALF_EVEN));
        cambio.setValorBruto(valorButro);
        return valorButro;
    }

    public void limparJanela() {
        cambio = new CambioBV();
        cambioSelecionado = new Cambio();
        baixaLista = new ArrayList<Baixa>();
        limparBaixa();
    }

    private void limparBaixa() {
        baixa = new BaixaBV();
        baixaSelecionado = new Baixa();
    }

    public void selecionaDespesa(SelectEvent event) {
        Despesa despesaSelecionada = (Despesa) event.getObject();
        baixa.setDespesa(despesaSelecionada);
    }

    public void selecionaContrato(SelectEvent event) {
        ContratoDeCambio contratoSelecionado = (ContratoDeCambio) event.getObject();
        cambio.setContrato(contratoSelecionado);
        baixaLista = new ArrayList<Baixa>();
        calculaValor();
        try {
            if (!contratoSelecionado.getOrigem().equals(cambio.getCotacao().getConta().getMoeda())) {
                cambio.setCotacao(null);
            }
        } catch (NullPointerException npe) {
        }
    }

    public void selecionaPessoaComissionada(SelectEvent event) {
        Pessoa pessoaSelecionada = (Pessoa) event.getObject();
        cambio.setPessoaComissionada(pessoaSelecionada);
    }

    public void selecionaConta(SelectEvent event) {
        Conta contaSelecionada = (Conta) event.getObject();
        if (contaSelecionada.getMoeda().equals(cambio.getContrato().getOrigem())) {
//          s  camsetCotacaoonta(contaSelecionada);
        } else {
            WarningMessage.print(new BundleUtil().getMessage("Conta_Mesma_Moeda_Taxa_Negociada"));
        }
    }

    public void abrirEdicao() {
        limparJanela();
        panel = true;
    }

    public void abrirEdicaoComDados() {
        panel = true;
        cambio = new CambioBV(cambioSelecionado);
        baixaLista = new BaixaDAO().buscarBaixasW().eComDespesa().eDeCambio(cambioSelecionado).listaDeResultados();
    }

    public void abrirBaixaEdicaoComDados() {
        baixa = new BaixaBV(baixaSelecionado);
    }

    public void fecharEdicao() {
        panel = false;
    }

    public void desfazer() {
        if (cambioSelecionado != null) {
            cambio = new CambioBV(cambioSelecionado);
        }
    }

    public boolean isPanel() {
        return panel;
    }

    public void setPanel(boolean panel) {
        this.panel = panel;
    }

    public CambioBV getCambio() {
        return cambio;
    }

    public void setCambio(CambioBV cambio) {
        this.cambio = cambio;
    }

    public Cambio getCambioSelecionado() {
        return cambioSelecionado;
    }

    public void setCambioSelecionado(Cambio cambioSelecionado) {
        this.cambioSelecionado = cambioSelecionado;
    }

    public List<Cambio> getCambioLista() {
        return cambioLista;
    }

    public void setCambioLista(List<Cambio> cambioLista) {
        this.cambioLista = cambioLista;
    }

    public List<Cambio> getCambiosFiltrados() {
        return cambiosFiltrados;
    }

    public void setCambiosFiltrados(List<Cambio> cambiosFiltrados) {
        this.cambiosFiltrados = cambiosFiltrados;
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

    public List<Baixa> getBaixaLista() {
        return baixaLista;
    }

    public void setBaixaLista(List<Baixa> baixaLista) {
        this.baixaLista = baixaLista;
    }

    public CambioService getService() {
        return service;
    }

    public void setService(CambioService service) {
        this.service = service;
    }

    public ConfiguracaoCambio getConfiguracaoCambio() {
        return configuracaoCambio;
    }

    public void setConfiguracaoCambio(ConfiguracaoCambio configuracaoCambio) {
        this.configuracaoCambio = configuracaoCambio;
    }

    public ConfiguracaoCambioService getServiceConfCambio() {
        return serviceConfCambio;
    }

    public void setServiceConfCambio(ConfiguracaoCambioService serviceConfCambio) {
        this.serviceConfCambio = serviceConfCambio;
    }

}
