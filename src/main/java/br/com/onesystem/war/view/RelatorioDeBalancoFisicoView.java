package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.ItemDAO;
import br.com.onesystem.dao.ModeloDeRelatorioDAO;
import br.com.onesystem.domain.Coluna;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.ModeloDeRelatorio;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.reportTemplate.BalancoFisico;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.war.builder.RelatorioDeBalancoFisicoBV;
import br.com.onesystem.war.service.ItemService;
import br.com.onesystem.war.service.ModeloDeRelatorioService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.SelectEvent;
import br.com.onesystem.reportTemplate.column.BalancoFisicoColumn;
import br.com.onesystem.util.ImpressoraDeRelatorioDinamico;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.valueobjects.TipoRelatorio;
import br.com.onesystem.war.builder.ColunaBV;
import br.com.onesystem.war.builder.ModeloDeRelatorioBV;
import br.com.onesystem.war.service.ColunaService;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.IOException;
import net.sf.jasperreports.engine.JRException;
import org.primefaces.model.DualListModel;

/**
 *
 * @author Rafael
 */
@ManagedBean
@ViewScoped
public class RelatorioDeBalancoFisicoView extends BasicMBImpl implements Serializable {

    private RelatorioDeBalancoFisicoBV relatorio;
    private Coluna coluna;
    private ModeloDeRelatorioBV modelo;
    private ModeloDeRelatorio modeloSelecionado;
    private List<Item> itens;
    private ImpressoraDeRelatorioDinamico impressora;
    private DualListModel<Coluna> campos = new DualListModel<Coluna>();
    private List<Coluna> camposSource = new ArrayList<Coluna>();
    private List<Coluna> camposTarget = new ArrayList<Coluna>();

    @ManagedProperty("#{itemService}")
    private ItemService service;

    @ManagedProperty("#{modeloDeRelatorioService}")
    private ModeloDeRelatorioService modeloService;

    @PostConstruct
    public void construct() {
        limparJanela();
    }

    public void addTemplateRelatorios() {
        try {
            modelo.setTipoRelatorio(TipoRelatorio.BALANCO_FISICO);
            ModeloDeRelatorio novoRegistro = modelo.construir();
            addColunas(novoRegistro);

            if (validaTemplateRelatoriosExistente(novoRegistro)) {
                new AdicionaDAO<ModeloDeRelatorio>().adiciona(novoRegistro);
                InfoMessage.adicionado();
                limparJanela();
            } else {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("contaDeEstoque_ja_registrada"));
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    private void addColunas(ModeloDeRelatorio novoRegistro) throws DadoInvalidoException {
        List<Coluna> colunasSelecionadas = new ArrayList<>();
        for (Coluna cl : campos.getTarget()) {
            ColunaBV colunaBV = new ColunaBV(cl);
            colunaBV.setModeloDeRelatorio(novoRegistro);
            colunasSelecionadas.add(colunaBV.construir());
        }

        novoRegistro.setColunas(colunasSelecionadas);
    }

    private Long retornarCodigo() {
        return null;
    }

    public void imprimir() throws ClassNotFoundException, JRException, IOException {
        try {
            List<BalancoFisico> lista = new ArrayList<BalancoFisico>();
            itens = service.buscarItemsRelatorio(relatorio.getItem());

            for (Item item : itens) {
                BalancoFisico balanco = new BalancoFisico(item, service.custoMedio(item), relatorio.getEmissao());
                lista.add(balanco);
            }

            if (lista.isEmpty()) {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("Nao_existem_dados"));
            }

            impressora.imprimir(lista, new BundleUtil().getLabel("Relatorio_Balanco_Fisico"),
                    new BalancoFisicoColumn().getColunas(), campos.getTarget());

        } catch (DadoInvalidoException die) {
            die.print();
        }

    }

    private boolean validaTemplateRelatoriosExistente(ModeloDeRelatorio novoRegistro) {
        List<ModeloDeRelatorio> lista = new ModeloDeRelatorioDAO().buscarModeloDeRelatorio().porNome(novoRegistro).listaDeResultados();
        return lista.isEmpty();
    }

//    public void updateTemplateRelatorios() {
//        try {
//            if (operacaoDeEstoqueSelecionado != null) {
//
//                validaOperacaoDeEstoqueExistente(true);
//                listaOperacoesDeEstoqueBV.set(listaOperacoesDeEstoqueBV.indexOf(operacaoDeEstoqueSelecionado),
//                        operacaoDeEstoque);
//                limparOperacao();
//            }
//        } catch (DadoInvalidoException ex) {
//            ex.print();
//        }
//    }
//
//    public void deleteTemplateRelatorios() throws DadoInvalidoException {
//        if (operacaoDeEstoqueSelecionado != null) {
//            listaOperacoesDeEstoqueBV.remove(operacaoDeEstoqueSelecionado);
//            limparOperacao();
//        }
//    }
    public void limparJanela() {
        relatorio = new RelatorioDeBalancoFisicoBV();
        modelo = new ModeloDeRelatorioBV();
        modeloSelecionado = null;
        impressora = new ImpressoraDeRelatorioDinamico();
        criarCampos();
    }

    private void criarCampos() {
        try {
            camposSource = new ArrayList<>();
            camposTarget = new ArrayList<>();
            camposSource.add(new Coluna(null, "Id", null));
            camposSource.add(new Coluna(null, "Nome", null));
            camposSource.add(new Coluna(null, "Saldo", null));
            camposSource.add(new Coluna(null, "Custo_Medio", null));
            camposSource.add(new Coluna(null, "Custo_Total", null));
            camposSource.add(new Coluna(null, "Marca", null));
            camposSource.add(new Coluna(null, "Grupo", null));
            camposSource.add(new Coluna(null, "Grupo_Fiscal", null));
            campos = new DualListModel<Coluna>(camposSource, camposTarget);
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    @Override
    public void selecionar(SelectEvent event) {
        if (event.getObject() instanceof Item) {
            Item item = (Item) event.getObject();
            relatorio.setItem(item);
        }
        if (event.getObject() instanceof ModeloDeRelatorio) {
            ModeloDeRelatorio mr = (ModeloDeRelatorio) event.getObject();
            modelo = new ModeloDeRelatorioBV(mr);
            atualizaCampos(mr);
        }
    }

    private void atualizaCampos(ModeloDeRelatorio mr) {
        criarCampos();
        ModeloDeRelatorioDAO dao = new ModeloDeRelatorioDAO();
        List<Coluna> lista = new ArrayList<>();
        lista = dao.buscarModeloDeRelatorio().porId(mr.getId()).porTipoRelatorio(TipoRelatorio.BALANCO_FISICO).listaDeColunas();
        for (Coluna cl : lista) {
            campos.getTarget().add(cl);
            campos.getSource().remove(cl);
        }
    }

    @Override
    public void buscaPorId() {
        Long id = relatorio.getId();
        if (id != null) {
            try {
                ItemDAO dao = new ItemDAO();
                Item i = dao.buscarItems().porId(id).resultado();
                relatorio.setItem(i);
            } catch (DadoInvalidoException die) {
                relatorio = new RelatorioDeBalancoFisicoBV();
                die.print();
            }
        }
    }

    public void removerItem() {
        relatorio = new RelatorioDeBalancoFisicoBV();
    }

    public List<Coluna> getCamposSource() {
        return camposSource;
    }

    public void setCamposSource(List<Coluna> camposSource) {
        this.camposSource = camposSource;
    }

    public List<Coluna> getCamposTarget() {
        return camposTarget;
    }

    public void setCamposTarget(List<Coluna> camposTarget) {
        this.camposTarget = camposTarget;
    }

    public ImpressoraDeRelatorioDinamico getImpressora() {
        return impressora;
    }

    public void setImpressora(ImpressoraDeRelatorioDinamico impressora) {
        this.impressora = impressora;
    }

    public RelatorioDeBalancoFisicoBV getRelatorio() {
        return relatorio;
    }

    public void setRelatorio(RelatorioDeBalancoFisicoBV relatorio) {
        this.relatorio = relatorio;
    }

    public List<Item> getItens() {
        return itens;
    }

    public void setItens(List<Item> itens) {
        this.itens = itens;
    }

    public ItemService getService() {
        return service;
    }

    public void setService(ItemService service) {
        this.service = service;
    }

    public DualListModel<Coluna> getCampos() {
        return campos;
    }

    public void setCampos(DualListModel<Coluna> campos) {
        this.campos = campos;
    }

    public ModeloDeRelatorioBV getModelo() {
        return modelo;
    }

    public void setModelo(ModeloDeRelatorioBV modelo) {
        this.modelo = modelo;
    }

    public List<ModeloDeRelatorio> getModelosDeRelatorio() {
        return modeloService.buscarModeloDeRelatorio();
    }

    public ModeloDeRelatorioService getModeloService() {
        return modeloService;
    }

    public void setModeloService(ModeloDeRelatorioService modeloService) {
        this.modeloService = modeloService;
    }

    public ModeloDeRelatorio getModeloSelecionado() {
        return modeloSelecionado;
    }

    public void setModeloSelecionado(ModeloDeRelatorio modeloSelecionado) {
        this.modeloSelecionado = modeloSelecionado;
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

}
