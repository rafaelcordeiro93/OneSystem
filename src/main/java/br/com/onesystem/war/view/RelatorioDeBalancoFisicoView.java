package br.com.onesystem.war.view;

import br.com.onesystem.domain.Item;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.reportTemplate.BalancoFisico;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.ErrorMessage;
import br.com.onesystem.war.builder.RelatorioDeBalancoFisicoBV;
import br.com.onesystem.war.service.ConfiguracaoService;
import br.com.onesystem.war.service.ItemService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.SelectEvent;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import br.com.onesystem.util.ImpressoraDeRelatorioItem;
import java.io.IOException;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import org.primefaces.model.DualListModel;

/**
 *
 * @author Rafael
 */
@ManagedBean
@ViewScoped
public class RelatorioDeBalancoFisicoView implements Serializable {

    private RelatorioDeBalancoFisicoBV relatorio;
    private List<Item> itens;
    private ImpressoraDeRelatorioItem impressora;
    private DualListModel<String> campos;
    private List<String> camposSource = new ArrayList<String>();
    private List<String> camposTarget = new ArrayList<String>();

    @ManagedProperty("#{itemService}")
    private ItemService service;

    @ManagedProperty("#{configuracaoService}")
    private ConfiguracaoService serviceConfigurcao;

    @PostConstruct
    public void construct() {
        relatorio = new RelatorioDeBalancoFisicoBV();
        impressora = new ImpressoraDeRelatorioItem();
        criarCampos();
    }

    public void imprimir() throws ClassNotFoundException, JRException, IOException {
        try {
            List<BalancoFisico> lista = new ArrayList<BalancoFisico>();
            String sigla = serviceConfigurcao.buscar().getMoedaPadrao().getSigla();
            itens = service.buscarItemsRelatorio(relatorio.getItem());

            for (Item item : itens) {
                BalancoFisico balanco = new BalancoFisico(item, serviceConfigurcao.buscar().getMoedaPadrao(), service.custoMedio(item), relatorio.getEmissao());
                lista.add(balanco);
            }

            if (lista.isEmpty()) {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("Nao_existem_dados"));
            }

            impressora.Imprimir(lista, sigla, campos.getTarget());

        } catch (DadoInvalidoException die) {
            ErrorMessage.print("Erro ao gerar o relat�rio: " + die.getMessage());
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

    public void selecionaItem(SelectEvent event) {
        Item item = (Item) event.getObject();
        relatorio.setItem(item);
    }

    public void removerItem() {
        relatorio.setItem(null);
    }

    public ImpressoraDeRelatorioItem getImpressora() {
        return impressora;
    }

    public void setImpressora(ImpressoraDeRelatorioItem impressora) {
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

    public ConfiguracaoService getServiceConfigurcao() {
        return serviceConfigurcao;
    }

    public void setServiceConfigurcao(ConfiguracaoService serviceConfigurcao) {
        this.serviceConfigurcao = serviceConfigurcao;
    }

    public DualListModel<String> getCampos() {
        return campos;
    }

    public void setCampos(DualListModel<String> campos) {
        this.campos = campos;
    }

}
