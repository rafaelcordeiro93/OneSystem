/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.util;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Transparency;
import ar.com.fdvs.dj.domain.constants.VerticalAlign;
import br.com.onesystem.reportTemplate.ColunaRepository;
import java.awt.Color;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class ImpressoraDeRelatorioDinamico {

    private FastReportBuilder builder = new FastReportBuilder();

    public ImpressoraDeRelatorioDinamico() {
    }

    public void imprimir(List<?> lista, String nome, List<ColunaRepository> colunas, List<?> campos) throws JRException, IOException {
        //Cria propriedades e colunas
        propriedades(nome);
        construirCampos(colunas, campos);

        //Cria o relatório dinamico
        DynamicReport dr = builder.build();
        JRDataSource ds = new JRBeanCollectionDataSource(lista);
        JasperPrint jp = DynamicJasperHelper.generateJasperPrint(dr, new ClassicLayoutManager(), ds);
        HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        res.setContentType("application/pdf");
        res.addHeader("Content-disposition", "attachment; filename=" + nome + new Date()  + ".pdf");
        JasperExportManager.exportReportToPdfStream(jp, res.getOutputStream());
        FacesContext.getCurrentInstance().responseComplete();
    }

    private void construirCampos(List<ColunaRepository> colunas, List<?> campos) {
        for (Object c : campos) {
            for (ColunaRepository cr : colunas) {
                if (c.equals(cr.getColuna().getTitle())) {
                    builder.addColumn(cr.getColuna());
                    if (cr.getTotalizador() != null) {
                        builder.addGlobalFooterVariable(cr.getColuna(), cr.getTotalizador());
                    }
                }
            }
        }
    }

    private void propriedades(String nome) {
        builder.setTitle(nome)
                .setMargins(0, 0, 0, 0)
                .setPrintBackgroundOnOddRows(true)
                .setUseFullPageWidth(true)
                .setReportName(nome)
                .setAllowDetailSplit(true)
                .setGrandTotalLegend(new BundleUtil().getMessage("total"))
                .setGrandTotalLegendStyle(getSubTitleStyle())
                .setDefaultStyles(getTitleStyle(), null, getColumnHeaderStyle(), getColumnDetailsStyle());
    }

    private Style getTitleStyle() {

        Style headerStyle = new Style();
        headerStyle.setBackgroundColor(new Color(35, 98, 201));
        headerStyle.setPaddingBottom(new Integer(30));
        headerStyle.setPaddingTop(new Integer(30));
        headerStyle.setPaddingRight(new Integer(25));
        headerStyle.setPaddingLeft(new Integer(25));
        headerStyle.setHorizontalAlign(HorizontalAlign.LEFT);
        headerStyle.setFont(new Font(30, "Open Sans", true));
        headerStyle.setTextColor(Color.WHITE);
        headerStyle.setTransparency(Transparency.OPAQUE);
        return headerStyle;
    }

    private Style getSubTitleStyle() {
        Style subtitleStyle = new Style();
        subtitleStyle.setBackgroundColor(new Color(35, 98, 201));
        subtitleStyle.setPadding(new Integer(3));
        subtitleStyle.setBorder(Border.THIN());
        subtitleStyle.setTextColor(Color.WHITE);
        subtitleStyle.setHorizontalAlign(HorizontalAlign.RIGHT);
        subtitleStyle.setVerticalAlign(VerticalAlign.MIDDLE);
        subtitleStyle.setFont(Font.ARIAL_SMALL);
        subtitleStyle.setTransparency(Transparency.OPAQUE);
        return subtitleStyle;
    }

    private Style getColumnHeaderStyle() {
        Style hStyle = new Style();
        hStyle.setBorder(Border.THIN());
        hStyle.setTransparent(false);
        hStyle.setBackgroundColor(new Color(35, 98, 201));
        hStyle.setTextColor(Color.WHITE);
        hStyle.setBorderColor(Color.WHITE);
        hStyle.setPadding(3);
        hStyle.setHorizontalAlign(HorizontalAlign.CENTER);
        hStyle.setVerticalAlign(VerticalAlign.MIDDLE);
        hStyle.setFont(Font.ARIAL_SMALL);

        return hStyle;
    }

    private Style getColumnDetailsStyle() {
        Style cStyle = new Style();
        cStyle.setBorderLeft(Border.THIN());
        cStyle.setBorderRight(Border.THIN());
        cStyle.setFont(Font.ARIAL_SMALL);
        cStyle.setHorizontalAlign(HorizontalAlign.CENTER);
        cStyle.setVerticalAlign(VerticalAlign.TOP);
        cStyle.setTextColor(Color.BLACK);
        return cStyle;
    }

}
