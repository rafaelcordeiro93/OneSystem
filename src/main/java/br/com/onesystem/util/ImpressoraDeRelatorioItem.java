package br.com.onesystem.util;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.DJCalculation;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Transparency;
import ar.com.fdvs.dj.domain.constants.VerticalAlign;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import java.awt.Color;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class ImpressoraDeRelatorioItem {

    public void Imprimir(List<?> lista, String siglaMoeda, List<?> campos) throws ColumnBuilderException, ClassNotFoundException, JRException, IOException, EDadoInvalidoException {
        FastReportBuilder drb = new FastReportBuilder();

        drb.setTitle("Relatorio de Balanco Fisico")
                //.setSubtitle("Este relatorio foi gerado em " + new Date())
                .setMargins(0, 0, 0, 0)
                .setPrintBackgroundOnOddRows(true)
                .setUseFullPageWidth(true)
                .setReportName("Relatorio")
                .setAllowDetailSplit(true)
                .setGrandTotalLegend("Total")
                //nao esta funcionando o style de subtitle, quando adiciona um style o campo fica em branco
                .setDefaultStyles(getTitleStyle(), null, getColumnHeaderStyle(), getColumnDetailsStyle());

        AbstractColumn colunaId = ColumnBuilder.getNew()
                .setColumnProperty("item.id", Long.class.getName())
                .setTitle("ID").setWidth(new Integer(75))
                .build();

        AbstractColumn colunaNome = ColumnBuilder.getNew()
                .setColumnProperty("item.nome", String.class.getName())
                .setTitle("Nome").setWidth(new Integer(100))
                .build();

        AbstractColumn colunaSaldo = ColumnBuilder.getNew()
                .setColumnProperty("saldo", BigDecimal.class.getName()).setPattern("0.00")
                .setTitle("Saldo").setWidth(new Integer(75))
                .build();

        AbstractColumn colunaCustoMedio = ColumnBuilder.getNew()
                .setColumnProperty("custoMedio", BigDecimal.class.getName())
                .setTitle("Custo Medio").setWidth(new Integer(75)).setPattern(siglaMoeda + " 0.00")
                .build();

        AbstractColumn colunaCustoTotal = ColumnBuilder.getNew()
                .setColumnProperty("custoTotal", BigDecimal.class.getName())
                .setTitle("Custo Total").setWidth(new Integer(75)).setPattern(siglaMoeda + " 0.00")
                .build();

        for (Object a : campos) {
            if (a.equals("ID")) {
                drb.addColumn(colunaId);
            }
            if (a.equals("Nome")) {
                drb.addColumn(colunaNome);
            }
            if (a.equals("Saldo")) {
                drb.addColumn(colunaSaldo);
            }
            if (a.equals("Custo Médio")) {
                drb.addColumn(colunaCustoMedio);
            }
            if (a.equals("Custo Total")) {
                drb.addColumn(colunaCustoTotal);
                drb.addGlobalFooterVariable(colunaCustoTotal, DJCalculation.SUM);
            }
        }

        DynamicReport dr = drb.build();
        JRDataSource ds = new JRBeanCollectionDataSource(lista);
        JasperPrint jp = DynamicJasperHelper.generateJasperPrint(dr, new ClassicLayoutManager(), ds);
        HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        res.setContentType("application/pdf");
        res.addHeader("Content-disposition", "attachment; filename=" + "Relatorio" + ".pdf");
        JasperExportManager.exportReportToPdfStream(jp, res.getOutputStream());
        FacesContext.getCurrentInstance().responseComplete();

    }

    Font font = new Font(30, "Courier New", true);

    private Style getTitleStyle() {

        Style headerStyle = new Style();
        headerStyle.setBackgroundColor(new Color(35, 98, 201));
        headerStyle.setPaddingBottom(new Integer(30));
        headerStyle.setPaddingTop(new Integer(30));
        headerStyle.setPaddingRight(new Integer(25));
        headerStyle.setPaddingLeft(new Integer(25));
        headerStyle.setHorizontalAlign(HorizontalAlign.LEFT);
        headerStyle.setFont(font);
        headerStyle.setTextColor(Color.WHITE);
        headerStyle.setTransparency(Transparency.OPAQUE);
        return headerStyle;
    }

    private Style getSubTitleStyle() {
        Style subtitleStyle = new Style();
        subtitleStyle.setBackgroundColor(new Color(35, 98, 201));
        subtitleStyle.setTextColor(Color.WHITE);
        subtitleStyle.setHorizontalAlign(HorizontalAlign.RIGHT);
        subtitleStyle.setFont(Font.ARIAL_SMALL);
        subtitleStyle.setTransparency(Transparency.OPAQUE);
        return subtitleStyle;
    }

    private Style getColumnHeaderStyle() {
        Style hStyle = new Style();
        // hStyle.setBorder(Border.THIN());
        hStyle.setTransparent(false);
        hStyle.setBackgroundColor(new Color(35, 98, 201));
        hStyle.setTextColor(Color.WHITE);
        hStyle.setBorderColor(Color.WHITE);
        hStyle.setBorderBottom(Border.THIN());
        hStyle.setBorderLeft(Border.THIN());
        hStyle.setBorderRight(Border.THIN());
        hStyle.setBorderTop(Border.THIN());
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
