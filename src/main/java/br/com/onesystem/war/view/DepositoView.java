package br.com.onesystem.war.view;

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
import br.com.onesystem.war.service.DepositoService;
import br.com.onesystem.war.service.FilialService;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
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

    @Inject
    private DepositoService depositoService;

    @PostConstruct
    public void init() {
        limparJanela();
    }

    public void add() {
        try {
            Deposito novoRegistro = e.construir();
            novoRegistro.setFiliais(filiais.getList());
            addNoBanco(novoRegistro);
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void update() {
        try {
            Deposito depositoExistente = e.construirComID();
            depositoExistente.setFiliais(filiais.getList());
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
        try {
            Object obj = event.getObject();
            if (obj instanceof Deposito) {
                e = new DepositoBV((Deposito) obj);
                inicializaFiliais();
            } else if (obj instanceof Filial) {
                filial = (Filial) obj;
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void inicializaFiliais() throws DadoInvalidoException {
            Deposito deposito = depositoService.getDeposito(e.getId());
            e = new DepositoBV(deposito);
            e.setFiliais(deposito.getFiliais());
            filiais = new ModelList<>(deposito.getFiliais());
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
            boolean present = filiais.getList().stream().filter(f -> f.getId().equals(filial.getId())).findAny().isPresent();
            if (!present) {
                filiais.add(filial);
                limpaFilial();
            } else {
                ErrorMessage.print(new BundleUtil().getLabel("Filial_ja_selecionada_para_deposito"));
            }
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
