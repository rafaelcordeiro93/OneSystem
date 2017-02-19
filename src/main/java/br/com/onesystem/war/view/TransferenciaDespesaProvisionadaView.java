package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.ConfiguracaoCambio;
import br.com.onesystem.domain.DespesaProvisionada;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.TransferenciaDespesaProvisionada;
import br.com.onesystem.domain.builder.DespesaProvisionadaBuilder;
import br.com.onesystem.util.FatalMessage;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.war.builder.TransferenciaDespesaProvisionadaBV;
import br.com.onesystem.war.service.TransferenciaDespesaProvisionadaService;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.war.service.ConfiguracaoCambioService;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.SelectEvent;

@ManagedBean
@ViewScoped
public class TransferenciaDespesaProvisionadaView implements Serializable {

    private boolean panel;
    private TransferenciaDespesaProvisionadaBV transferenciaDespesaProvisionada;
    private TransferenciaDespesaProvisionada transferenciaDespesaProvisionadaSelecionada;
    private List<TransferenciaDespesaProvisionada> transferenciaDespesaProvisionadaLista;
    private List<TransferenciaDespesaProvisionada> transferenciaDespesaProvisionadasFiltradas;
    private ConfiguracaoCambio configuracaoCambio;

    @ManagedProperty("#{transferenciaDespesaProvisionadaService}")
    private TransferenciaDespesaProvisionadaService service;

    @ManagedProperty("#{configuracaoCambioService}")
    private ConfiguracaoCambioService configuracaoCambioService;

    @PostConstruct
    public void init() {
        limparJanela();
        panel = false;
        transferenciaDespesaProvisionadaLista = service.buscarTransferenciaDespesaProvisionadas();
        inicializaConfiguracoes();
    }

    private void inicializaConfiguracoes() {
        try {
            configuracaoCambio = configuracaoCambioService.buscar();
        } catch (EDadoInvalidoException ex) {
            ex.print();
        }
    }

    public void add() {
        try {
            DespesaProvisionada despesa = criarDespesa();
            transferenciaDespesaProvisionada.setDestino(despesa);
            TransferenciaDespesaProvisionada novoRegistro = transferenciaDespesaProvisionada.construir();
            if (!validaTransferenciaDespesaProvisionadaExistente(novoRegistro)) {
                new AdicionaDAO<TransferenciaDespesaProvisionada>().adiciona(novoRegistro);
                transferenciaDespesaProvisionadaLista.add(novoRegistro);
                InfoMessage.print("¡TransferenciaDespesaProvisionada '" + novoRegistro.getId() + "' agregado con éxito!");
                limparJanela();
            } else {
                throw new EDadoInvalidoException("¡Ya existe la Transferencia para essa origem!");
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    private DespesaProvisionada criarDespesa() throws DadoInvalidoException {
        try {
            TransferenciaDespesaProvisionadaBV t = transferenciaDespesaProvisionada;
            if (t.getPessoa().equals(transferenciaDespesaProvisionada.getOrigem().getPessoa())) {
                throw new EDadoInvalidoException("A pessoa de origem deve ser diferente da pessoa de destino.");
            }
            if (t.getPessoa().equals(configuracaoCambio.getPessoaCaixa())) {
                return new DespesaProvisionadaBuilder().comPessoa(t.getPessoa()).comValor(t.getOrigem().getValor())
                        .comDespesa(configuracaoCambio.getDespesaDivisaoLucro()).comCambio(t.getOrigem().getCambio()).comDivisaoLucroCambioCaixa(true)
                        .comEmissao(new Date()).comHistorico("Divisão de Lucros").construir();
            } else {
                return new DespesaProvisionadaBuilder().comPessoa(t.getPessoa()).comValor(t.getOrigem().getValor())
                        .comDespesa(configuracaoCambio.getDespesaDivisaoLucro()).comCambio(t.getOrigem().getCambio()).comDivisaoLucroCambioCaixa(false)
                        .comEmissao(new Date()).comHistorico("Divisão de Lucros").construir();

            }
        } catch (NullPointerException npe) {
            throw new EDadoInvalidoException("A origem deve ser informada!");
        }
    }

    public void update() {
        try {
            TransferenciaDespesaProvisionada transferenciaDespesaProvisionadaExistente = transferenciaDespesaProvisionada.construirComID();
            if (transferenciaDespesaProvisionadaExistente.getId() != null) {
                new AtualizaDAO<TransferenciaDespesaProvisionada>(TransferenciaDespesaProvisionada.class).atualiza(transferenciaDespesaProvisionadaExistente);
                transferenciaDespesaProvisionadaLista.set(transferenciaDespesaProvisionadaLista.indexOf(transferenciaDespesaProvisionadaExistente),
                        transferenciaDespesaProvisionadaExistente);
                if (transferenciaDespesaProvisionadasFiltradas != null && transferenciaDespesaProvisionadasFiltradas.contains(transferenciaDespesaProvisionadaExistente)) {
                    transferenciaDespesaProvisionadasFiltradas.set(transferenciaDespesaProvisionadasFiltradas.indexOf(transferenciaDespesaProvisionadaExistente), transferenciaDespesaProvisionadaExistente);
                }
                InfoMessage.print("¡TransferenciaDespesaProvisionada '" + transferenciaDespesaProvisionadaExistente.getId() + "' cambiado con éxito!");
                limparJanela();
            } else {
                throw new EDadoInvalidoException("!El transferenciaDespesaProvisionada no se encontra registrada!");
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void selecionaDespesaProvisionada(SelectEvent event) {
        DespesaProvisionada dp = (DespesaProvisionada) event.getObject();
        transferenciaDespesaProvisionada.setOrigem(dp);
    }

    public void selecionaPessoa(SelectEvent event) {
        Pessoa pessoa = (Pessoa) event.getObject();
        transferenciaDespesaProvisionada.setPessoa(pessoa);
    }

    private boolean validaTransferenciaDespesaProvisionadaExistente(TransferenciaDespesaProvisionada novoRegistro) {
        for (TransferenciaDespesaProvisionada novaTransferenciaDespesaProvisionada : transferenciaDespesaProvisionadaLista) {
            if (novoRegistro.getOrigem().equals(novaTransferenciaDespesaProvisionada.getOrigem())) {
                return true;
            }
        }
        return false;
    }

    public void limparJanela() {
        transferenciaDespesaProvisionada = new TransferenciaDespesaProvisionadaBV();
        transferenciaDespesaProvisionadaSelecionada = new TransferenciaDespesaProvisionada();
    }

    public void abrirEdicao() {
        limparJanela();
        panel = true;
    }

    public void abrirEdicaoComDados() {
        panel = true;
        transferenciaDespesaProvisionada = new TransferenciaDespesaProvisionadaBV(transferenciaDespesaProvisionadaSelecionada);
    }

    public void fecharEdicao() {
        panel = false;
    }

    public void desfazer() {
        if (transferenciaDespesaProvisionadaSelecionada != null) {
            transferenciaDespesaProvisionada = new TransferenciaDespesaProvisionadaBV(transferenciaDespesaProvisionadaSelecionada);
        }
    }

    public TransferenciaDespesaProvisionadaBV getTransferenciaDespesaProvisionada() {
        return transferenciaDespesaProvisionada;
    }

    public void setTransferenciaDespesaProvisionada(TransferenciaDespesaProvisionadaBV transferenciaDespesaProvisionada) {
        this.transferenciaDespesaProvisionada = transferenciaDespesaProvisionada;
    }

    public TransferenciaDespesaProvisionada getTransferenciaDespesaProvisionadaSelecionada() {
        return transferenciaDespesaProvisionadaSelecionada;
    }

    public void setTransferenciaDespesaProvisionadaSelecionada(TransferenciaDespesaProvisionada transferenciaDespesaProvisionadaSelecionada) {
        this.transferenciaDespesaProvisionadaSelecionada = transferenciaDespesaProvisionadaSelecionada;
    }

    public List<TransferenciaDespesaProvisionada> getTransferenciaDespesaProvisionadaLista() {
        return transferenciaDespesaProvisionadaLista;
    }

    public void setTransferenciaDespesaProvisionadaLista(List<TransferenciaDespesaProvisionada> transferenciaDespesaProvisionadaLista) {
        this.transferenciaDespesaProvisionadaLista = transferenciaDespesaProvisionadaLista;
    }

    public List<TransferenciaDespesaProvisionada> getTransferenciaDespesaProvisionadasFiltradas() {
        return transferenciaDespesaProvisionadasFiltradas;
    }

    public void setTransferenciaDespesaProvisionadasFiltradas(List<TransferenciaDespesaProvisionada> transferenciaDespesaProvisionadasFiltradas) {
        this.transferenciaDespesaProvisionadasFiltradas = transferenciaDespesaProvisionadasFiltradas;
    }

    public boolean isPanel() {
        return panel;
    }

    public void setPanel(boolean panel) {
        this.panel = panel;
    }

    public TransferenciaDespesaProvisionadaService getService() {
        return service;
    }

    public void setService(TransferenciaDespesaProvisionadaService service) {
        this.service = service;
    }

    public ConfiguracaoCambio getConfiguracaoCambio() {
        return configuracaoCambio;
    }

    public void setConfiguracaoCambio(ConfiguracaoCambio configuracaoCambio) {
        this.configuracaoCambio = configuracaoCambio;
    }

    public ConfiguracaoCambioService getConfiguracaoCambioService() {
        return configuracaoCambioService;
    }

    public void setConfiguracaoCambioService(ConfiguracaoCambioService configuracaoCambioService) {
        this.configuracaoCambioService = configuracaoCambioService;
    }

}
