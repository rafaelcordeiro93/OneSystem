package br.com.onesystem.reportTemplate;

import br.com.onesystem.domain.Filial;
import br.com.onesystem.util.BundleUtil;
import static net.sf.dynamicreports.report.builder.DynamicReports.*;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;

import net.sf.dynamicreports.report.builder.HyperLinkBuilder;
import net.sf.dynamicreports.report.builder.MarginBuilder;
import net.sf.dynamicreports.report.builder.ReportTemplateBuilder;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.style.FontBuilder;
import net.sf.dynamicreports.report.builder.style.PaddingBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.builder.tableofcontents.TableOfContentsCustomizerBuilder;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.constant.VerticalTextAlignment;

/**
 *
 */
public class Templates {

    public static final FontBuilder font11Bold;
    public static final FontBuilder font10Bold;
    public static final FontBuilder font10BoldUnderline;
    public static final FontBuilder font10;
    public static final StyleBuilder rootStyle;
    public static final StyleBuilder boldStyle;
    public static final StyleBuilder italicStyle;
    public static final StyleBuilder boldCenteredStyle;
    public static final StyleBuilder bold12CenteredStyle;
    public static final StyleBuilder bold18CenteredStyle;
    public static final StyleBuilder bold22CenteredStyle;
    public static final StyleBuilder columnStyle;
    public static final StyleBuilder columnTitleStyle;
    public static final StyleBuilder groupStyle;
    public static final StyleBuilder subtotalStyle;
    public static final StyleBuilder centeredStyle;

    public static final ReportTemplateBuilder reportTemplate;

    static {
        font11Bold = stl.font("SansSerif", true, false, 11);
        font10Bold = stl.font("SansSerif", true, false, 10);
        font10BoldUnderline = stl.font("SansSerif", true, false, 10).setUnderline(true);
        font10 = stl.font("SansSerif", false, false, 10);
        rootStyle = stl.style().setPadding(2);
        boldStyle = stl.style(rootStyle).bold();
        italicStyle = stl.style(rootStyle).italic();
        centeredStyle = stl.style()
                .setTextAlignment(HorizontalTextAlignment.CENTER, VerticalTextAlignment.MIDDLE);
        boldCenteredStyle = stl.style(boldStyle)
                .setTextAlignment(HorizontalTextAlignment.CENTER, VerticalTextAlignment.MIDDLE);
        bold12CenteredStyle = stl.style(boldCenteredStyle)
                .setFontSize(12);
        bold18CenteredStyle = stl.style(boldCenteredStyle)
                .setFontSize(18);
        bold22CenteredStyle = stl.style(boldCenteredStyle)
                .setFontSize(22);
        columnStyle = stl.style(rootStyle).setVerticalTextAlignment(VerticalTextAlignment.MIDDLE);
        columnTitleStyle = stl.style(columnStyle)
                .setBorder(stl.pen1Point())
                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
                .setBackgroundColor(Color.LIGHT_GRAY)
                .bold();
        groupStyle = stl.style(boldStyle)
                .setHorizontalTextAlignment(HorizontalTextAlignment.LEFT);
        subtotalStyle = stl.style(boldStyle)
                .setTopBorder(stl.pen1Point());

        StyleBuilder crosstabGroupStyle = stl.style(columnTitleStyle);
        StyleBuilder crosstabGroupTotalStyle = stl.style(columnTitleStyle)
                .setBackgroundColor(new Color(170, 170, 170));
        StyleBuilder crosstabGrandTotalStyle = stl.style(columnTitleStyle)
                .setBackgroundColor(new Color(140, 140, 140));
        StyleBuilder crosstabCellStyle = stl.style(columnStyle)
                .setBorder(stl.pen1Point());

        TableOfContentsCustomizerBuilder tableOfContentsCustomizer = tableOfContentsCustomizer()
                .setHeadingStyle(0, stl.style(rootStyle).bold());

        reportTemplate = template()
                .setLocale(Locale.ENGLISH)
                .setColumnStyle(columnStyle)
                .setColumnTitleStyle(columnTitleStyle)
                .setGroupStyle(groupStyle)
                .setGroupTitleStyle(groupStyle)
                .setSubtotalStyle(subtotalStyle)
                .highlightDetailEvenRows()
                .crosstabHighlightEvenRows()
                .setCrosstabGroupStyle(crosstabGroupStyle)
                .setCrosstabGroupTotalStyle(crosstabGroupTotalStyle)
                .setCrosstabGrandTotalStyle(crosstabGrandTotalStyle)
                .setCrosstabCellStyle(crosstabCellStyle)
                .setTableOfContentsCustomizer(tableOfContentsCustomizer);

    }

    public void margem(JasperReportBuilder report) {
        // Para cada 1 de margem, é 0,04 cm - Comparação com Word - Margem Estreita.
        // Top 1,28 cm | Bottom 1,8 cm, Right e Left 1 cm 
//        MarginBuilder margem = margin().setRight(25).setLeft(25).setTop(32).setBottom(45);
        MarginBuilder margem = margin().setRight(20).setLeft(20).setTop(25).setBottom(45);
        report.setPageMargin(margem);
    }

    public ComponentBuilder<?, ?> cabecalho(Filial filial, String nomeRelatorio) {

        //ESTILOS
        PaddingBuilder padding = stl.padding().setBottom(2).setTop(0);

        //ESTILO DO TITULO
        StyleBuilder titleStyle = stl.style().setTextAlignment(HorizontalTextAlignment.LEFT, VerticalTextAlignment.MIDDLE)
                .setPadding(padding).setFont(font11Bold);
        
        StyleBuilder titleStyleRight = stl.style().setTextAlignment(HorizontalTextAlignment.RIGHT, VerticalTextAlignment.MIDDLE)
                .setPadding(stl.padding().setTop(4)).setFont(font10BoldUnderline);

        //ESTILO DO SUBTITULO
        StyleBuilder subTitleStyle = stl.style().setTextAlignment(HorizontalTextAlignment.LEFT, VerticalTextAlignment.MIDDLE)
                .setPadding(padding).setFont(font10);

        //ESTILO DA IMAGEM
        StyleBuilder imagemStyle = stl.style(centeredStyle).
                setPadding(stl.padding().setTop(10));

        String endereço = filial.getEndereco() + ", Nº " + filial.getNumero();
        String bairro = filial.getBairro() + " - " + filial.getCep().getCep() + " - "
                + filial.getCep().getCidade().getNome() + " - " + filial.getCep().getCidade().getEstado().getSigla()
                + "/" + filial.getCep().getCidade().getEstado().getPais().getNome();
        String contato = filial.getTelefone() + ", " + filial.getEmail();

        //CABEÇALHO
        return cmp.horizontalList()
                .add(
                        cmp.image(getClass().getClassLoader().getResourceAsStream("logo.png")).setFixedDimension(60, 60).setStyle(imagemStyle),
                        cmp.verticalList().add(cmp.horizontalList().add(cmp.text(filial.getFantasia()).setStyle(titleStyle).setHorizontalTextAlignment(HorizontalTextAlignment.LEFT),
                                cmp.text(nomeRelatorio).setStyle(titleStyleRight).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT)),
                                cmp.text(filial.getRazaoSocial()).setStyle(titleStyle),
                                cmp.text(endereço).setStyle(subTitleStyle),
                                cmp.text(bairro).setStyle(subTitleStyle),
                                cmp.text(contato).setStyle(subTitleStyle)))
                .newRow()
                .add(cmp.line())
                .newRow()
                .add(cmp.verticalGap(1));
        //.add(cmp.filler().setStyle(stl.style().setBackgroundColor(new Color(0, 162, 237))).setFixedHeight(10));
    }

    public void rodape(JasperReportBuilder report) {

        //RODAPÉ
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");

        report.setPageFooterStyle(stl.style().setBackgroundColor(new Color(200, 200, 200)).setBorder(stl.penThin().setLineColor(new Color(230, 230, 230))));
        report.pageFooter(cmp.horizontalList()
                .add(cmp.text(new BundleUtil().getLabel("Data") + ": " + sdf.format(new Date())).setHorizontalTextAlignment(HorizontalTextAlignment.LEFT))
                .add(cmp.pageXslashY().setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT)));

    }

}
