package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.domain.TipoDespesa;
import br.com.onesystem.domain.DespesaProvisionada;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.ErrorMessage;
import br.com.onesystem.valueobjects.ClassificacaoFinanceira;
import br.com.onesystem.valueobjects.NaturezaFinanceira;
import br.com.onesystem.war.builder.DespesaProvisionadaBV;
import br.com.onesystem.war.service.MoedaService;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class DespesaProvisionadaView extends BasicMBImpl<DespesaProvisionada, DespesaProvisionadaBV> implements Serializable {

    private List<DespesaProvisionadaBV> parcelas;
    private Integer numeroParcelas;
    private Integer intervaloDias;
    private List<Moeda> moedaLista;

    @Inject
    private MoedaService serviceMoeda;

    @PostConstruct
    public void init() {
        limparJanela();
    }

    public void add() {
        try {
            DespesaProvisionada novoRegistro = e.construir();
            new AdicionaDAO<DespesaProvisionada>().adiciona(novoRegistro);

            for (DespesaProvisionadaBV n : parcelas) {
                novoRegistro = n.construir();
                new AdicionaDAO<DespesaProvisionada>().adiciona(novoRegistro);

            }

        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    @Override
    public void limparJanela() {
        e = new DespesaProvisionadaBV();
        intervaloDias = null;
        numeroParcelas = null;
        parcelas = new ArrayList<DespesaProvisionadaBV>();
        moedaLista = serviceMoeda.buscarMoedas();
    }

    @Override
    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        if (obj instanceof DespesaProvisionada) {
            e = new DespesaProvisionadaBV((DespesaProvisionada) obj);
        } else if (obj instanceof Pessoa) {
            e.setPessoa((Pessoa) obj);
        } else if (obj instanceof TipoDespesa) {
            e.setDespesa((TipoDespesa) obj);
        }

    }

    public void gerarMaisParcelas() {
        try {
            parcelas = new ArrayList<DespesaProvisionadaBV>();
            Integer count = new Integer(0);
            for (int i = 0; i < numeroParcelas; i++) {
                count = count + intervaloDias;
                DespesaProvisionadaBV dp
                        = new DespesaProvisionadaBV(retornarCodigo(),
                                e.getPessoa(),
                                e.getDespesa(),
                                e.getValor(),
                                adicionarDiasNa(e.getVencimento(), count),
                                e.getEmissao(),
                                e.getHistorico(),
                                false,
                                e.getCotacao(),
                                null,
                                null,
                                e.getReferencia());
                parcelas.add(dp);
            }
        } catch (Exception e) {
            ErrorMessage.print(new BundleUtil().getMessage("Vencimento_idias_nparcelas_not_null"));
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
            for (DespesaProvisionadaBV dp : parcelas) {
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

    public List<DespesaProvisionadaBV> getParcelas() {
        return parcelas;
    }

    public void setParcelas(List<DespesaProvisionadaBV> parcelas) {
        this.parcelas = parcelas;
    }

    public Integer getNumeroParcelas() {
        return numeroParcelas;
    }

    public void setNumeroParcelas(Integer numeroParcelas) {
        this.numeroParcelas = numeroParcelas;
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

    public void setServiceMoeda(MoedaService serviceMoeda) {
        this.serviceMoeda = serviceMoeda;
    }

}
