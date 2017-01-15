package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.domain.Receita;
import br.com.onesystem.domain.ConhecimentoDeFrete;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.Titulo;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.ErrorMessage;
import br.com.onesystem.util.FatalMessage;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.valueobjects.ClassificacaoFinanceira;
import br.com.onesystem.valueobjects.NaturezaFinanceira;
import br.com.onesystem.war.builder.BaixaBV;
import br.com.onesystem.war.builder.ConhecimentoDeFreteBV;
import br.com.onesystem.war.builder.TituloBV;
import br.com.onesystem.war.service.MoedaService;
import br.com.onesystem.war.service.ConhecimentoDeFreteService;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.SelectEvent;

@ManagedBean
@ViewScoped
public class ConhecimentoDeFreteView implements Serializable {

    private boolean panel;
    private ConhecimentoDeFreteBV conhecimentoDeFrete;
    private ConhecimentoDeFrete conhecimentoDeFreteSelecionado;
    private List<ConhecimentoDeFrete> conhecimentoDeFreteLista;
    private List<ConhecimentoDeFrete> gruposFinanceirosFiltrados;
    private TituloBV titulos;
    private List<TituloBV> parcelas;
    private BaixaBV baixa;
   
    private BigDecimal total;
    private BigDecimal numeroParcela;
    private Integer intervaloDias;

    
    @ManagedProperty("#{conhecimentoDeFreteService}")
    private ConhecimentoDeFreteService service;

    @ManagedProperty("#{moedaService}")
    private MoedaService serviceMoeda;

    private List<Moeda> moedaLista;
    
    @PostConstruct
    public void init() {
        limparJanela();
        panel = false;
        conhecimentoDeFrete = new ConhecimentoDeFreteBV();
        conhecimentoDeFreteLista = service.buscarConhecimentoDeFreteService();
        moedaLista = serviceMoeda.buscarMoedas();
    }

    public void add() {
        try {
            ConhecimentoDeFrete novoRegistro = conhecimentoDeFrete.construir();
            Titulo novoTitulo = titulos.construir();
            new AdicionaDAO<ConhecimentoDeFrete>().adiciona(novoRegistro);
            conhecimentoDeFreteLista.add(novoRegistro);
            for(TituloBV n : parcelas){
                novoTitulo = n.construir();
               }            
            InfoMessage.print("Receita Provisionada agregado con éxito!");
            limparJanela();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void update() {
        try {
            ConhecimentoDeFrete conhecimentoDeFreteExistente = conhecimentoDeFrete.construirComID();
            if (conhecimentoDeFreteExistente.getId() != null) {
                new AtualizaDAO<ConhecimentoDeFrete>(ConhecimentoDeFrete.class).atualiza(conhecimentoDeFreteExistente);
                conhecimentoDeFreteLista.set(conhecimentoDeFreteLista.indexOf(conhecimentoDeFreteExistente),
                        conhecimentoDeFreteExistente);
                if (gruposFinanceirosFiltrados != null && gruposFinanceirosFiltrados.contains(conhecimentoDeFreteExistente)) {
                    gruposFinanceirosFiltrados.set(gruposFinanceirosFiltrados.indexOf(conhecimentoDeFreteExistente), conhecimentoDeFreteExistente);
                }
                InfoMessage.print("Receita Provisionada cambiado con éxito!");
                limparJanela();
            } else {
                throw new EDadoInvalidoException("!La conhecimentoDeFrete no se encontra registrada!");
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void delete() {
        try {
            if (conhecimentoDeFreteLista != null && conhecimentoDeFreteLista.contains(conhecimentoDeFrete.construirComID())) {
                new RemoveDAO<ConhecimentoDeFrete>(ConhecimentoDeFrete.class).remove(conhecimentoDeFrete.construirComID(), conhecimentoDeFrete.construirComID().getId());
                conhecimentoDeFreteLista.remove(conhecimentoDeFrete.construirComID());
                if (gruposFinanceirosFiltrados != null && gruposFinanceirosFiltrados.contains(conhecimentoDeFrete.construirComID())) {
                    gruposFinanceirosFiltrados.remove(conhecimentoDeFrete.construirComID());
                }
                InfoMessage.print("ConhecimentoDeFrete '" + this.conhecimentoDeFrete.getId() + "' eliminada con éxito!");
                limparJanela();
            }
        } catch (DadoInvalidoException di) {
            di.print();
        } catch (ConstraintViolationException pe) {
            FatalMessage.print(pe.getMessage(), pe.getCause());
        }
    }

    
 

    public void CarnePagamento(BigDecimal numeroParcela, BigDecimal total) {
        parcelas = new ArrayList<TituloBV>();
        Integer count = new Integer(0);
        
        this.numeroParcela = numeroParcela;
        this.total = total;
        
        BigDecimal parcela = total.divide(numeroParcela);
     
        for (int i = 1; i <= numeroParcela.intValue(); i++) {
            count = count + intervaloDias;
           TituloBV dp = new TituloBV(null , conhecimentoDeFrete.getPessoa(),null,parcela, null,null,
                        conhecimentoDeFrete.getEmissao(),null, null, null, null ,conhecimentoDeFrete.getMoeda(), conhecimentoDeFreteSelecionado);
            parcelas.add(dp);
        }
    }
 
    private Date adicionarDiasNa(Date data, Integer dias) {
        Calendar c = Calendar.getInstance();
        c.setTime(data);
        c.add(Calendar.DATE, dias);
        return c.getTime();
    }

    private Long retornarCodigo() {
        Long id = (long) 1;
        if (!parcelas.isEmpty()) {
            for (TituloBV dp : parcelas) {
                if (dp.getId() >= id) {
                    id = dp.getId() + 1;
                }
            }
        }
        return id;
    }

    public List<NaturezaFinanceira> getNaturezasFinanceiras() {
        return Arrays.asList(NaturezaFinanceira.values());
    }

    public List<ClassificacaoFinanceira> getClassificacaoFinanceira() {
        return Arrays.asList(ClassificacaoFinanceira.values());
    }

    public void limparJanela() {
        conhecimentoDeFrete = new ConhecimentoDeFreteBV();
        conhecimentoDeFreteSelecionado = new ConhecimentoDeFrete();
        intervaloDias = null;
        numeroParcela = null;
        //parcelas = new ArrayList<TituloBV>();
    }

    public void abrirEdicao() {
        limparJanela();
        panel = true;
    }

    public void abrirEdicaoComDados() {
        panel = true;
        conhecimentoDeFrete = new ConhecimentoDeFreteBV(conhecimentoDeFreteSelecionado);
    }

    public void fecharEdicao() {
        panel = false;
    }

    public void desfazer() {
        if (conhecimentoDeFreteSelecionado != null) {
            conhecimentoDeFrete = new ConhecimentoDeFreteBV(conhecimentoDeFreteSelecionado);
        }
    }

    
    public ConhecimentoDeFreteBV getConhecimentoDeFrete() {
        return conhecimentoDeFrete;
    }

    public void setConhecimentoDeFrete(ConhecimentoDeFreteBV conhecimentoDeFrete) {
        this.conhecimentoDeFrete = conhecimentoDeFrete;
    }

    public ConhecimentoDeFrete getConhecimentoDeFreteSelecionado() {
        return conhecimentoDeFreteSelecionado;
    }

    public void setConhecimentoDeFreteSelecionado(ConhecimentoDeFrete conhecimentoDeFreteSelecionado) {
        this.conhecimentoDeFreteSelecionado = conhecimentoDeFreteSelecionado;
    }

    public List<ConhecimentoDeFrete> getConhecimentoDeFreteLista() {
        return conhecimentoDeFreteLista;
    }

    public void setConhecimentoDeFreteLista(List<ConhecimentoDeFrete> conhecimentoDeFreteLista) {
        this.conhecimentoDeFreteLista = conhecimentoDeFreteLista;
    }

    public List<ConhecimentoDeFrete> getGruposFinanceirosFiltrados() {
        return gruposFinanceirosFiltrados;
    }

    public void setGruposFinanceirosFiltrados(List<ConhecimentoDeFrete> gruposFinanceirosFiltrados) {
        this.gruposFinanceirosFiltrados = gruposFinanceirosFiltrados;
    }

    public boolean isPanel() {
        return panel;
    }

    public void setPanel(boolean panel) {
        this.panel = panel;
    }

    public ConhecimentoDeFreteService getService() {
        return service;
    }

    public void setService(ConhecimentoDeFreteService service) {
        this.service = service;
    }
    
    
    public void selecionarMoeda(SelectEvent event) {
        Moeda moeda = (Moeda) event.getObject();
        conhecimentoDeFrete.setMoeda(moeda);
    }

    public void selecionaPessoa(SelectEvent event) {
        Pessoa pessoaSelecionada = (Pessoa) event.getObject();
        conhecimentoDeFrete.setPessoa(pessoaSelecionada);
    }

    public void selecionaOperacao(SelectEvent event) {
        Operacao operacaoSelecionada = (Operacao) event.getObject();
        conhecimentoDeFrete.setOperacao(operacaoSelecionada);
    }

    public TituloBV getTitulos() {
        return titulos;
    }

    public void setTitulos(TituloBV titulos) {
        this.titulos = titulos;
    }

    public List<TituloBV> getParcelas() {
        return parcelas;
    }

    public void setParcelas(List<TituloBV> parcelas) {
        this.parcelas = parcelas;
    }

    public BigDecimal getNumeroParcela() {
        return numeroParcela;
    }

    public void setNumeroParcela(BigDecimal numeroParcela) {
        this.numeroParcela = numeroParcela;
    }


    public Integer getIntervaloDias() {
        return intervaloDias;
    }

    public void setIntervaloDias(Integer intervaloDias) {
        this.intervaloDias = intervaloDias;
    }
    
      public List<Moeda> getMoedaLista() {
        return moedaLista;
    }

    public void setMoedaLista(List<Moeda> moedaLista) {
        this.moedaLista = moedaLista;
    }

    public MoedaService getServiceMoeda() {
        return serviceMoeda;
    }

    public BaixaBV getBaixa() {
        return baixa;
    }

    public void setBaixa(BaixaBV baixa) {
        this.baixa = baixa;
    }

    public void setServiceMoeda(MoedaService serviceMoeda) {
        this.serviceMoeda = serviceMoeda;
    }


}
