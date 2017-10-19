package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.ArmazemDeRegistrosNaMemoria;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.DepositoDAO;
import br.com.onesystem.domain.Deposito;
import br.com.onesystem.domain.Filial;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.ErrorMessage;
import br.com.onesystem.util.Model;
import br.com.onesystem.util.ModelList;
import br.com.onesystem.war.builder.DepositoBV;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import br.com.onesystem.war.view.selecao.SelecaoDepositoView;
import br.com.onesystem.war.view.selecao.SelecaoFilialView;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class DepositoView extends BasicMBImpl<Deposito, DepositoBV> implements Serializable {

    @Inject
    private DepositoDAO dao;
    private ModelList<Filial> filiais;
    private Filial filial;
    private Model<Filial> modelFilial;

    @Inject
    private AtualizaDAO<Filial> filialDAO;
    
    @PostConstruct
    public void init() {
        limparJanela();
    }

    public void add() {
        try {
            Deposito novoRegistro = e.construir();
            filiais.getList().forEach(f -> novoRegistro.adiciona(f));
            addNoBanco(novoRegistro);
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void update() {
        try {
            Deposito depositoExistente = e.construirComID();
            filiais.getRemovidos().forEach(f -> depositoExistente.remove((Filial) f.getObject()));
            filiais.getList().forEach(f -> depositoExistente.adiciona(f));

            for(Model f : filiais.getRemovidos()){
                Filial fil = (Filial) new ArmazemDeRegistrosNaMemoria<SelecaoFilialView>().initialize(((Filial) f.getObject()), SelecaoFilialView.class, "getDepositos");
                fil.remove(depositoExistente);
            }
            
            for(Filial f : filiais.getList()){
                f = (Filial) new ArmazemDeRegistrosNaMemoria<SelecaoFilialView>().initialize(f, SelecaoFilialView.class, "getDepositos");
                f.adiciona(depositoExistente);
                filialDAO.atualiza(f);
            }

            if (depositoExistente.getId() != null) {
                updateNoBanco(depositoExistente);
            } else {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("deposito_nao_cadastrado"));
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    @Override
    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        if (obj instanceof Deposito) {
            e = new DepositoBV((Deposito) obj);
        } else if (obj instanceof Filial) {
            filial = (Filial) obj;
        }
    }

    public void inicializaFiliais() throws DadoInvalidoException {
        t = (Deposito) new ArmazemDeRegistrosNaMemoria<SelecaoDepositoView>().initialize(e.construirComID(), SelecaoDepositoView.class, "getFiliais");
        e = new DepositoBV(t);
        filiais = new ModelList<>(t.getFiliais());
    }

    public void limparJanela() {
        t = null;
        e = new DepositoBV();
        filiais = new ModelList<>();
        limpaFilial();
    }

    public void limpaFilial() {
        filial = null;
        modelFilial = null;
    }

    public void selecionaFilial(SelectEvent event) {
        if (modelFilial != null) {
            filial = (Filial) modelFilial.getObject();
        }
    }

    public void addFilial() {
        if (filial != null) {
            filiais.add(filial);
            limpaFilial();
        } else {
            ErrorMessage.print(new BundleUtil().getLabel("Filial_deve_ser_selecionada"));
        }
    }

    public void removeFilial() {
        if (modelFilial != null) {
            filiais.remove(modelFilial);
            limpaFilial();
        } else {
            ErrorMessage.print(new BundleUtil().getLabel("Selecione_Uma_Filial_Na_Lista_Para_Remover"));
        }
    }

    public Filial getFilial() {
        return filial;
    }

    public void setFilial(Filial filial) {
        this.filial = filial;
    }

    public ModelList<Filial> getFiliais() {
        return filiais;
    }

    public void setFiliais(ModelList<Filial> filiais) {
        this.filiais = filiais;
    }

    public Model<Filial> getModelFilial() {
        return modelFilial;
    }

    public void setModelFilial(Model<Filial> modelFilial) {
        this.modelFilial = modelFilial;
    }

}
