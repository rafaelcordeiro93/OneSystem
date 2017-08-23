package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.ContratoDeCambio;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.util.FatalMessage;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.war.builder.ContratoDeCambioBV;
import br.com.onesystem.war.service.ContratoDeCambioService;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.ADadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.reportTemplate.SomaSaldoDeTituloPorMoedaReportTemplate;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.WarningMessage;
import br.com.onesystem.war.service.MoedaService;
import br.com.onesystem.war.service.RecepcaoService;
import br.com.onesystem.war.service.TituloService;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.inject.Named;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class ContratoDeCambioView implements Serializable {

    private boolean panel;
    private ContratoDeCambioBV contratoDeCambio;
    private ContratoDeCambio contratoDeCambioSelecionado;
    private List<ContratoDeCambio> contratoDeCambioLista;
    private List<ContratoDeCambio> contratoDeCambioListaDeHoje;
    private List<ContratoDeCambio> contratosDeCambioFiltrados;
    private String statusButton = "RedButton";
    private List<SomaSaldoDeTituloPorMoedaReportTemplate> listaDeSoma;
    private String dataUltimoRecebimento = "Não existe.";
    private List<Moeda> moedaLista;

    @ManagedProperty("#{contratoDeCambioService}")
    private ContratoDeCambioService service;

    @ManagedProperty("#{tituloService}")
    private TituloService serviceTitulo;

    @ManagedProperty("#{recepcaoService}")
    private RecepcaoService serviceRecepcao;

    @ManagedProperty("#{moedaService}")
    private MoedaService serviceMoeda;

    @PostConstruct
    public void init() {
        moedaLista = serviceMoeda.buscarMoedas();
        limparJanela();
        panel = false;
        contratoDeCambioLista = service.buscarContratosDeCambio();
        contratoDeCambioListaDeHoje = service.buscarContratosDeHoje();
    }

    public void add() {
        try {
            if (contratoDeCambio.getPessoa() != null) {
                validaMoedasIguais();
                verificaSaldoDeMoeda();
                ContratoDeCambio novoRegistro = contratoDeCambio.construir();
                new AdicionaDAO<ContratoDeCambio>().adiciona(novoRegistro);
                contratoDeCambioLista.add(novoRegistro);
                contratoDeCambioListaDeHoje.add(novoRegistro);
                InfoMessage.print("¡ContratoDeCambio '" + novoRegistro.getId() + "' agregado con éxito!");
                limparJanela();
            } else {
                throw new ADadoInvalidoException(new BundleUtil().getMessage("pessoa_not_null"));
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    private void verificaSaldoDeMoeda() throws EDadoInvalidoException {
        SomaSaldoDeTituloPorMoedaReportTemplate somaMoeda = null;
        boolean origem = false;
        for (SomaSaldoDeTituloPorMoedaReportTemplate soma : listaDeSoma) {
            if (soma.getMoeda().equals(contratoDeCambio.getOrigem())) {
                origem = true;
                somaMoeda = soma;
            }
        }
        if (contratoDeCambio.getValorCalculado() == null) {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("ValorNeg_TaxaNeg_Not_Null"));
        }
        if (!origem || somaMoeda == null || somaMoeda.getValor().compareTo(contratoDeCambio.getValorCalculado()) == -1) {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("Saldo_Indisponivel_Moeda"));
        }
    }

    private void validaMoedasIguais() throws EDadoInvalidoException {
        if (contratoDeCambio.getOrigem().equals(contratoDeCambio.getDestino())) {
            throw new EDadoInvalidoException("As moedas devem ser diferentes!");
        }
    }

    public void update() {
        try {
            if (contratoDeCambio.getCambio() != null) {
                throw new EDadoInvalidoException("O contrato não pode ser atualizado, pois já possui um cambio efetivado!");
            }
            validaMoedasIguais();
            verificaSaldoDeMoeda();
            ContratoDeCambio contratoDeCambioExistente = contratoDeCambio.construirComID();
            if (contratoDeCambioExistente.getId() != null) {
                new AtualizaDAO<ContratoDeCambio>().atualiza(contratoDeCambioExistente);
                contratoDeCambioLista.set(contratoDeCambioLista.indexOf(contratoDeCambioExistente),
                        contratoDeCambioExistente);
                if (contratosDeCambioFiltrados != null && contratosDeCambioFiltrados.contains(contratoDeCambioExistente)) {
                    contratosDeCambioFiltrados.set(contratosDeCambioFiltrados.indexOf(contratoDeCambioExistente), contratoDeCambioExistente);
                }
                InfoMessage.print("¡ContratoDeCambio '" + contratoDeCambioExistente.getId() + "' cambiado con éxito!");
                limparJanela();
            } else {
                throw new EDadoInvalidoException("!La contratoDeCambio no se encontra registrada!");
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void delete() {
        try {
            if (contratoDeCambioLista != null && contratoDeCambioLista.contains(contratoDeCambioSelecionado)) {
                new RemoveDAO<ContratoDeCambio>().remove(contratoDeCambioSelecionado, contratoDeCambioSelecionado.getId());
                contratoDeCambioLista.remove(contratoDeCambioSelecionado);
                if (contratosDeCambioFiltrados != null && contratosDeCambioFiltrados.contains(contratoDeCambioSelecionado)) {
                    contratosDeCambioFiltrados.remove(contratoDeCambioSelecionado);
                }
                InfoMessage.print("ContratoDeCambio '" + this.contratoDeCambio.getId() + "' eliminada con éxito!");
                limparJanela();
            }
        } catch (DadoInvalidoException di) {
            di.print();
        } catch (ConstraintViolationException pe) {
            FatalMessage.print(pe.getMessage(), pe.getCause());
        }
    }

    public void limparJanela() {
        listaDeSoma = new ArrayList<SomaSaldoDeTituloPorMoedaReportTemplate>();
        dataUltimoRecebimento = "Não existe.";
        contratoDeCambio = new ContratoDeCambioBV();
        contratoDeCambioSelecionado = new ContratoDeCambio();
        atuStatusButton();
        atualizaMoeda();
    }

    private void atualizaMoeda() {
        if (moedaLista.size() > 1) {
            contratoDeCambio.setOrigem(moedaLista.get(0));
            contratoDeCambio.setDestino(moedaLista.get(1));
        } else {
            WarningMessage.print(new BundleUtil().getMessage("Deve_Possuir_Duas_Moedas_Contrato_Cambio"));
        }
    }

    public void selecionaPessoa(SelectEvent event) {
        Pessoa pessoaSelecionada = (Pessoa) event.getObject();
        contratoDeCambio.setPessoa(pessoaSelecionada);
        listaDeSoma = serviceTitulo.buscarSaldoDeTitulosAPagarDeRecepcaoPara(pessoaSelecionada);
        dataUltimoRecebimento = serviceRecepcao.buscarUltimaRecepcaoDa(pessoaSelecionada);
    }

    public void abrirEdicao() {
        if (moedaLista.size() > 1) {
            limparJanela();
            panel = true;
        } else {
            WarningMessage.print(new BundleUtil().getMessage("Deve_Possuir_Duas_Moedas_Contrato_Cambio"));
        }
    }

    public void calculaValor() {
        if (contratoDeCambio.getValorNegociado() == null) {
            contratoDeCambio.setValorNegociado(BigDecimal.ZERO);
        }
        if (contratoDeCambio.getTaxaNegociada() == null) {
            contratoDeCambio.setTaxaNegociada(BigDecimal.ZERO);
        }
        contratoDeCambio.setValorCalculado(contratoDeCambio.getValorNegociado()
                .multiply(contratoDeCambio.getTaxaNegociada()));
    }

    public void abrirEdicaoComDados() {
        panel = true;
        selecionaRegistro();
    }

    public void selecionaRegistro() {
        contratoDeCambio = new ContratoDeCambioBV(contratoDeCambioSelecionado);
        listaDeSoma = serviceTitulo.buscarSaldoDeTitulosAPagarDeRecepcaoPara(contratoDeCambioSelecionado.getPessoa());
        dataUltimoRecebimento = serviceRecepcao.buscarUltimaRecepcaoDa(contratoDeCambioSelecionado.getPessoa());
        atuStatusButton();
    }

    public void fecharEdicao() {
        panel = false;
    }

    public void atuStatusButton() {
        if (contratoDeCambio.isStatus()) {
            statusButton = "GreenButton";
        } else {
            statusButton = "RedButton";
        }
        RequestContext.getCurrentInstance().update("conteudo:statusButton");
    }

    public void selecionarOrigemDeMoeda(SelectEvent event) {
        Moeda moeda = (Moeda) event.getObject();
        contratoDeCambio.setOrigem(moeda);
    }

    public void selecionarDestinoDeMoeda(SelectEvent event) {
        Moeda moeda = (Moeda) event.getObject();
        contratoDeCambio.setDestino(moeda);
    }

    public void desfazer() {
        if (contratoDeCambioSelecionado != null) {
            contratoDeCambio = new ContratoDeCambioBV(contratoDeCambioSelecionado);
        }
    }

    public boolean isPanel() {
        return panel;
    }

    public void setPanel(boolean panel) {
        this.panel = panel;
    }

    public ContratoDeCambioBV getContratoDeCambio() {
        return contratoDeCambio;
    }

    public void setContratoDeCambio(ContratoDeCambioBV contratoDeCambio) {
        this.contratoDeCambio = contratoDeCambio;
    }

    public ContratoDeCambio getContratoDeCambioSelecionado() {
        return contratoDeCambioSelecionado;
    }

    public void setContratoDeCambioSelecionado(ContratoDeCambio contratoDeCambioSelecionado) {
        this.contratoDeCambioSelecionado = contratoDeCambioSelecionado;
    }

    public List<ContratoDeCambio> getContratoDeCambioLista() {
        return contratoDeCambioLista;
    }

    public void setContratoDeCambioLista(List<ContratoDeCambio> contratoDeCambioLista) {
        this.contratoDeCambioLista = contratoDeCambioLista;
    }

    public List<ContratoDeCambio> getContratosDeCambioFiltrados() {
        return contratosDeCambioFiltrados;
    }

    public void setContratosDeCambioFiltrados(List<ContratoDeCambio> contratosDeCambioFiltrados) {
        this.contratosDeCambioFiltrados = contratosDeCambioFiltrados;
    }

    public String getStatusButton() {
        return statusButton;
    }

    public void setStatusButton(String statusButton) {
        this.statusButton = statusButton;
    }

    public ContratoDeCambioService getService() {
        return service;
    }

    public void setService(ContratoDeCambioService service) {
        this.service = service;
    }

    public List<ContratoDeCambio> getContratoDeCambioListaDeHoje() {
        return contratoDeCambioListaDeHoje;
    }

    public void setContratoDeCambioListaDeHoje(List<ContratoDeCambio> contratoDeCambioListaDeHoje) {
        this.contratoDeCambioListaDeHoje = contratoDeCambioListaDeHoje;
    }

    public List<SomaSaldoDeTituloPorMoedaReportTemplate> getListaDeSoma() {
        return listaDeSoma;
    }

    public void setListaDeSoma(List<SomaSaldoDeTituloPorMoedaReportTemplate> listaDeSoma) {
        this.listaDeSoma = listaDeSoma;
    }

    public TituloService getServiceTitulo() {
        return serviceTitulo;
    }

    public void setServiceTitulo(TituloService serviceTitulo) {
        this.serviceTitulo = serviceTitulo;
    }

    public RecepcaoService getServiceRecepcao() {
        return serviceRecepcao;
    }

    public void setServiceRecepcao(RecepcaoService serviceRecepcao) {
        this.serviceRecepcao = serviceRecepcao;
    }

    public MoedaService getServiceMoeda() {
        return serviceMoeda;
    }

    public void setServiceMoeda(MoedaService serviceMoeda) {
        this.serviceMoeda = serviceMoeda;
    }

    public String getDataUltimoRecebimento() {
        return dataUltimoRecebimento;
    }

    public void setDataUltimoRecebimento(String dataUltimoRecebimento) {
        this.dataUltimoRecebimento = dataUltimoRecebimento;
    }

    public List<Moeda> getMoedaLista() {
        return moedaLista;
    }

    public void setMoedaLista(List<Moeda> moedaLista) {
        this.moedaLista = moedaLista;
    }

}
