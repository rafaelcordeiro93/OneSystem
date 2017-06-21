package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.TipoReceita;
import br.com.onesystem.domain.ReceitaProvisionada;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.ErrorMessage;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.valueobjects.ClassificacaoFinanceira;
import br.com.onesystem.valueobjects.NaturezaFinanceira;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.war.builder.ReceitaProvisionadaBV;
import br.com.onesystem.war.service.CotacaoService;
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
public class ReceitaProvisionadaView extends BasicMBImpl<ReceitaProvisionada, ReceitaProvisionadaBV> implements Serializable {

    private List<ReceitaProvisionadaBV> parcelas;
    private Integer numeroParcelas;
    private Integer intervaloDias;
    private List<Cotacao> listaCotacao;

    @Inject
    private CotacaoService serviceCotacao;

    @PostConstruct
    public void init() {
        limparJanela();
    }

    public void add() {
        try {
            e.setOperacaoFinanceira(OperacaoFinanceira.SAIDA);
            if (e.getId() == null) {
                ReceitaProvisionada dp = e.construir();
                new AdicionaDAO<ReceitaProvisionada>().adiciona(dp);
            }
            ReceitaProvisionada novoRegistro = e.construir();
            for (ReceitaProvisionadaBV n : parcelas) {
                novoRegistro = n.construir();
                new AdicionaDAO<ReceitaProvisionada>().adiciona(novoRegistro);
            }
            InfoMessage.adicionado();
            limparJanela();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    @Override
    public void limparJanela() {
        e = new ReceitaProvisionadaBV();
        intervaloDias = null;
        numeroParcelas = null;
        parcelas = new ArrayList<ReceitaProvisionadaBV>();
        listaCotacao = serviceCotacao.buscarTodasCotacoesDoDiaAtual();
    }

    @Override
    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        if (obj instanceof ReceitaProvisionada) {
            e = new ReceitaProvisionadaBV((ReceitaProvisionada) obj);
        } else if (obj instanceof Pessoa) {
            e.setPessoa((Pessoa) obj);
        } else if (obj instanceof TipoReceita) {
            e.setReceita((TipoReceita) obj);
        }
    }

    public void gerarMaisParcelas() {
        try {
            parcelas = new ArrayList<ReceitaProvisionadaBV>();
            Integer count = new Integer(0);
            for (int i = 0; i < numeroParcelas; i++) {
                count = count + intervaloDias;
                ReceitaProvisionadaBV dp
                        = new ReceitaProvisionadaBV(retornarCodigo(),
                                e.getPessoa(),
                                e.getReceita(),
                                e.getValor(),
                                adicionarDiasNa(e.getVencimento(), count),
                                e.getEmissao(),
                                e.getHistorico(),
                                e.getCotacao(),
                                adicionarMesNa(e.getReferencia(), i + 1),
                                OperacaoFinanceira.SAIDA);
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

    private Date adicionarMesNa(Date data, Integer dias) {
        Calendar c = Calendar.getInstance();
        c.setTime(data);
        c.add(Calendar.MONTH, dias);
        return c.getTime();
    }

    private Long retornarCodigo() {
        Long id = (long) 1;
        if (!parcelas.isEmpty()) {
            for (ReceitaProvisionadaBV dp : parcelas) {
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

    public List<ReceitaProvisionadaBV> getParcelas() {
        return parcelas;
    }

    public void setParcelas(List<ReceitaProvisionadaBV> parcelas) {
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

    public List<Cotacao> getListaCotacao() {
        return listaCotacao;
    }

    public void setListaCotacao(List<Cotacao> listaCotacao) {
        this.listaCotacao = listaCotacao;
    }

    public CotacaoService getServiceCotacao() {
        return serviceCotacao;
    }

    public void setServiceCotacao(CotacaoService serviceCotacao) {
        this.serviceCotacao = serviceCotacao;
    }

}
