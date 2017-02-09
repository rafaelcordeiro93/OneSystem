package br.com.onesystem.war.view;

import br.com.onesystem.dao.ItemDAO;
import br.com.onesystem.domain.Item;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.reportTemplate.BalancoFisico;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.war.builder.RelatorioDeBalancoFisicoBV;
import br.com.onesystem.war.service.ItemService;
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
public class RelatorioDeBalancoFisicoView extends BasicMBImpl<Item> implements Serializable {
    
    private RelatorioDeBalancoFisicoBV relatorio;
    private List<Item> itens;
    private ImpressoraDeRelatorioDinamico impressora;
    private DualListModel<String> campos;
    private List<String> camposSource = new ArrayList<String>();
    private List<String> camposTarget = new ArrayList<String>();
    
    @ManagedProperty("#{itemService}")
    private ItemService service;
    
    @PostConstruct
    public void construct() {
        relatorio = new RelatorioDeBalancoFisicoBV();
        impressora = new ImpressoraDeRelatorioDinamico();
        criarCampos();
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
    
    private void criarCampos() {
        camposSource.add(new BundleUtil().getMessage("id"));
        camposSource.add(new BundleUtil().getMessage("nome"));
        camposSource.add(new BundleUtil().getMessage("saldo"));
        camposSource.add(new BundleUtil().getMessage("custo_medio"));
        camposSource.add(new BundleUtil().getMessage("custo_total"));
        camposSource.add(new BundleUtil().getMessage("marca"));
        camposSource.add(new BundleUtil().getMessage("grupo"));
        camposSource.add(new BundleUtil().getMessage("grupo_fiscal"));
//        camposSource.add(new BundleUtil().getMessage("deposito"));
//        camposSource.add(new BundleUtil().getMessage("saldo_por_deposito"));
        campos = new DualListModel<String>(camposSource, camposTarget);
    }
    
    public List<String> getCamposSource() {
        return camposSource;
    }
    
    public void setCamposSource(List<String> camposSource) {
        this.camposSource = camposSource;
    }
    
    public List<String> getCamposTarget() {
        return camposTarget;
    }
    
    public void setCamposTarget(List<String> camposTarget) {
        this.camposTarget = camposTarget;
    }
    
    @Override
    public void selecionar(SelectEvent event) {
        if (event.getObject() instanceof Item) {
            Item item = (Item) event.getObject();
            relatorio.setId(item.getId());
            relatorio.setItem(item);
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
    
    public DualListModel<String> getCampos() {
        return campos;
    }
    
    public void setCampos(DualListModel<String> campos) {
        this.campos = campos;
    }
    
}
