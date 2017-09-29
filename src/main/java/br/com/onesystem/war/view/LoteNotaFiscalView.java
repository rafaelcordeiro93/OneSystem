package br.com.onesystem.war.view;

import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.Filial;
import br.com.onesystem.domain.LoteNotaFiscal;
import br.com.onesystem.domain.NumeracaoDeNotaFiscal;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.ADadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.Model;
import br.com.onesystem.util.ModelList;
import br.com.onesystem.valueobjects.EspecieDeNotaFiscal;
import br.com.onesystem.valueobjects.ModeloDeNotaFiscal;
import br.com.onesystem.war.builder.LoteNotaFiscalBV;
import br.com.onesystem.war.builder.NumeracaoDeNotaFiscalBV;
import br.com.onesystem.war.service.NumeracaoDeNotaFiscalService;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class LoteNotaFiscalView extends BasicMBImpl<LoteNotaFiscal, LoteNotaFiscalBV> implements Serializable {

    private NumeracaoDeNotaFiscalBV numeracaoNF;
    private Model<NumeracaoDeNotaFiscal> numeracaoSelecionado;
    private ModelList<NumeracaoDeNotaFiscal> listaNumeracao;

    @Inject
    private RemoveDAO<NumeracaoDeNotaFiscal> removeNumeracaoDeNotaFiscalDAO;

    @Inject
    private BundleUtil bundle;

    @Inject
    private NumeracaoDeNotaFiscalService numeracaoService;

    @PostConstruct
    public void init() {
        limparJanela();
    }

    public void limparJanela() {
        e = new LoteNotaFiscalBV();
        t = null;
        numeracaoNF = new NumeracaoDeNotaFiscalBV();
        numeracaoSelecionado = null;
        listaNumeracao = new ModelList<>();
    }

    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        if (obj instanceof LoteNotaFiscal) {
            e = new LoteNotaFiscalBV((LoteNotaFiscal) event.getObject());
            t = (LoteNotaFiscal) event.getObject();
            selecionaNumeracao();
        } else if (obj instanceof Filial) {
            numeracaoNF.setFilial((Filial) obj);
        }
    }

    public void selecionaNumeracao() {
        if (t == null) {
            listaNumeracao = new ModelList<NumeracaoDeNotaFiscal>();
        } else {
            List<NumeracaoDeNotaFiscal> listaNumeracaoNoBanco = numeracaoService.buscaNumeracaoPorLote(t);
            if (listaNumeracaoNoBanco == null) {
                listaNumeracao = new ModelList<NumeracaoDeNotaFiscal>();
            } else {
                listaNumeracao = new ModelList<>(listaNumeracaoNoBanco);
                //gambi -- Lazy Loading
                e.setNumeracaoDeNotaFiscal(listaNumeracaoNoBanco); // busca lista do BD, para nao dar lazy loading.
            }
        }

    }

    public void add() {
        try {
            LoteNotaFiscal f = e.construir();
            listaNumeracao.getList().forEach((n) -> {
                f.adiciona(n);
            });
            addNoBanco(f);
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    @Override
    public void update() {
        try {
            LoteNotaFiscal f = e.construirComID();
            List<NumeracaoDeNotaFiscal> removidos = listaNumeracao.getRemovidos().stream().filter(m -> ((NumeracaoDeNotaFiscal) m.getObject()).getId() != null).map(m -> (NumeracaoDeNotaFiscal) m.getObject()).collect(Collectors.toList());
            removidos.forEach(c -> f.remove(c));
            listaNumeracao.getList().forEach((n) -> {
                f.atualiza(n);
            });
            for (NumeracaoDeNotaFiscal c : removidos) {
                removeNumeracaoDeNotaFiscalDAO.remove(c, c.getId());
            }
            updateNoBanco(f);

        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void addNumeracao() {
        try {
            validaNumeracaoFilial();
            listaNumeracao.add(numeracaoNF.construir());
            limparNumeracao();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    private void validaNumeracaoFilial() throws ADadoInvalidoException {
        for (NumeracaoDeNotaFiscal n : listaNumeracao.getList()) {
            if (!numeracaoNF.getFilial().equals(n.getFilial())) {
                continue;
            } else {
                throw new ADadoInvalidoException(bundle.getMessage("esta_filial_ja_possui_numeracao"));
            }
        }
    }

    public void updateNumeracao() {
        try {
            if (numeracaoNF.getId() == null) {
                numeracaoSelecionado.setObject(numeracaoNF.construir());
            } else {
                numeracaoSelecionado.setObject(numeracaoNF.construirComID());
            }
            listaNumeracao.set(numeracaoSelecionado);
            limparNumeracao();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void removeNumeracao() {
        listaNumeracao.remove(numeracaoSelecionado);
        numeracaoSelecionado = null;
    }

    public void selecionaNumeracaoEdicao(SelectEvent event) {
        numeracaoSelecionado = (Model<NumeracaoDeNotaFiscal>) event.getObject();
        numeracaoNF = new NumeracaoDeNotaFiscalBV(numeracaoSelecionado.getObject());;
    }

    public void limparNumeracao() {
        numeracaoNF = new NumeracaoDeNotaFiscalBV();
        numeracaoSelecionado = null;
    }

    public List<ModeloDeNotaFiscal> getModelosDeNotaFiscal() {
        return Arrays.asList(ModeloDeNotaFiscal.values());
    }

    public List<EspecieDeNotaFiscal> getEspeciesDeNotaFiscal() {
        return Arrays.asList(EspecieDeNotaFiscal.values());
    }

    public NumeracaoDeNotaFiscalBV getNumeracaoNF() {
        return numeracaoNF;
    }

    public void setNumeracaoNF(NumeracaoDeNotaFiscalBV numeracaoNF) {
        this.numeracaoNF = numeracaoNF;
    }

    public Model<NumeracaoDeNotaFiscal> getNumeracaoSelecionado() {
        return numeracaoSelecionado;
    }

    public void setNumeracaoSelecionado(Model<NumeracaoDeNotaFiscal> numeracaoSelecionado) {
        this.numeracaoSelecionado = numeracaoSelecionado;
    }

    public ModelList<NumeracaoDeNotaFiscal> getListaNumeracao() {
        return listaNumeracao;
    }

    public void setListaNumeracao(ModelList<NumeracaoDeNotaFiscal> listaNumeracao) {
        this.listaNumeracao = listaNumeracao;
    }

}
