/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.view;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.ItemImagem;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.valueobjects.TipoLayout;
import com.lowagie.text.pdf.PdfWriter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.inject.Named;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Rafael
 */
@Named
@SessionScoped
public class PDFStreamedView implements Serializable {

    private Map<String, Object> parametros;
    private final TipoLayout tipoLayout;
    private final List<?> lista;
    private String diretorio;
    private StreamedContent pdf;

    public void abrirDialogo() {
        geraPDF();
        exibeNaTela();
    }

    private void exibeNaTela() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("resizable", false);
        opcoes.put("width", "80%");
        opcoes.put("draggable", false);
        opcoes.put("height", 300);
        opcoes.put("closable", true);
        opcoes.put("contentWidth", "100%");
        opcoes.put("contentHeight", "100%");
        opcoes.put("headerElement", "customheader");

        RequestContext.getCurrentInstance().openDialog("/dialogo/dialogoLayout", opcoes, null);
    }
    
    public PDFStreamedView(List<?> lista, TipoLayout tipoLayout) {
        this.tipoLayout = tipoLayout;
        this.lista = lista;
        initDiretorio();
        initParametros();
    }

    private void initParametros() {
        parametros = new HashMap<>();
        parametros.put("REPORT_RESOURCE_BUNDLE", new BundleUtil().getBUNDLE_LABEL());
    }

    private void initDiretorio() {
        String realPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("");
        realPath = realPath.substring(0, realPath.lastIndexOf("\\"));
        realPath = realPath + "\\classes\\layouts\\";
        this.diretorio = realPath;
    }

    public PDFStreamedView addParametro(String key, Object parametro) {
        this.parametros.put(key, parametro);
        return this;
    }

    private void geraPDF(){
        try {
            JasperReport report = JasperCompileManager.compileReport(diretorio + tipoLayout.getNome() + ".jrxml");
            byte[] bytes = JasperRunManager.runReportToPdf(report, parametros, new JRBeanCollectionDataSource(lista));

            pdf = new DefaultStreamedContent(new ByteArrayInputStream(bytes), "application/pdf");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public StreamedContent getPdf() {
        return pdf;
    }

    public void setPdf(StreamedContent pdf) {
        this.pdf = pdf;
    }
    
    
}
