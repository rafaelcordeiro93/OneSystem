package br.com.onesystem.util;

import br.com.onesystem.reportTemplate.*;
import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.Cobranca;
import br.com.onesystem.domain.Coluna;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.valueobjects.Totalizador;
import java.awt.Color;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.base.DRField;
import net.sf.jasperreports.engine.JRException;
import static net.sf.dynamicreports.report.builder.DynamicReports.*;
import net.sf.dynamicreports.report.builder.MarginBuilder;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.datatype.BigDecimalType;
import net.sf.dynamicreports.report.builder.style.PaddingBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.builder.subtotal.AggregationSubtotalBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.constant.VerticalAlignment;
import net.sf.dynamicreports.report.constant.WhenNoDataType;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * @author Rafael Fernando Rauber
 * @date 01/08/2017
 * @author Rafael Fernando Rauber
 *
 * Classe utilizada para impressão de relatórios dinamicamente.
 */
public class ImpressoraDeRelatorioDinamico1 {

    private JasperReportBuilder relatorio = report(); // Cria um novo relatório.
    private String nome;
    private Map<String, Object> parametros;

    public ImpressoraDeRelatorioDinamico1() {
    }

    /**
     *
     * @param registros Lista tipada dos registros a serem impressos.
     * @param nome Nome do relatório
     * @param colunas Colunas a serem exibidas.
     * @return Retorna o mesmo para que seja possível escolher entre imprimir na
     * web ou no console.
     */
    public ImpressoraDeRelatorioDinamico1 imprimir(List registros, String nome, List<Coluna> colunas) {
        this.nome = nome; // Recebe o nome do relatório

        criarColunas(colunas); //cria as colunas
        margem(); //ajusta as margens
        cabecalhoRodape(); //cria cabecalhoRodapé

        relatorio.noData(Templates.createTitleComponent("NoData"), cmp.text(new BundleUtil().getLabel("Nao_Ha_Registros")));
        relatorio.setDataSource(new JRBeanCollectionDataSource(registros)); //add registros
        relatorio.setLocale(new Locale("pt", "BR"));

        return this;

    }

    private void margem() {
        // Para cada 1 de margem, é 0,04 cm - Comparação com Word - Margem Estreita.
        // Top 1,28 cm | Bottom 1,32, Right e Left  1,20 cm 
        MarginBuilder margem = margin().setRight(25).setLeft(25).setTop(32).setBottom(45);
        relatorio.setPageMargin(margem);
    }

    public void naWeb() throws IOException, DRException {
        HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        res.setContentType("application/pdf");
        res.addHeader("Content-disposition", "attachment; filename=" + nome + new Date() + ".pdf");
        relatorio.toPdf(res.getOutputStream());
        FacesContext.getCurrentInstance().responseComplete();
    }

    public void noConsole() {
        try {
            relatorio.show();
        } catch (DRException ex) {
            Logger.getLogger(ImpressoraDeRelatorioDinamico1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @author Rafael Fernando Rauber
     * @date 01/08/2017
     *
     * Cria as colunas NOME/CAMPO/TYPE CLASS Altera o estilo do Titulo das
     * colunas Altera o estilo do DETAIL das colunas
     *
     * @param colunas
     */
    public void criarColunas(List<Coluna> colunas) {
        //Gera as colunas
                
        colunas.forEach((cl) -> {
            TextColumnBuilder column = col.column(cl.getNome(), cl.getPropriedadeCompleta() , cl.getClasseOriginal());
            column.setWidth(cl.getTamanho());
            column.setDataType(new CurrencyType());
            relatorio.addColumn(column);
            criaTotalizadores(cl, column);
        });

        //Altera o estilo do Titulo das colunas
        StyleBuilder brancoCentralizado = stl.style().setHorizontalAlignment(HorizontalAlignment.CENTER).setForegroundColor(Color.WHITE)
                .setBackgroundColor(new Color(0, 162, 237)).setBorder(stl.penThin()).setPadding(8);

        PaddingBuilder padding = stl.padding().setBottom(1).setTop(1);
        StyleBuilder columnStyle = stl.style().setHorizontalAlignment(HorizontalAlignment.CENTER).setPadding(padding);

        relatorio.setColumnTitleStyle(brancoCentralizado)
                .setColumnStyle(columnStyle)
                .highlightDetailOddRows();
    }

    private void criaTotalizadores(Coluna cl, TextColumnBuilder column) {
        if (cl.getClasseOriginal() == BigDecimal.class || cl.getClasseOriginal() == Long.class) {

            if (null != cl.getTotalizador()) {
                switch (cl.getTotalizador()) {
                    case AVERAGE:
                        AggregationSubtotalBuilder avg = sbt.avg(cl.getPropriedadeCompleta(), cl.getClasseOriginal(), column);
                        avg.setStyle(stl.style().setHorizontalAlignment(HorizontalAlignment.CENTER));
                        relatorio.subtotalsAtSummary(avg);
                        break;
                    case COUNT:
                        AggregationSubtotalBuilder count = sbt.count(cl.getPropriedadeCompleta(), cl.getClasseOriginal(), column);
                        count.setStyle(stl.style().setHorizontalAlignment(HorizontalAlignment.CENTER));
                        relatorio.subtotalsAtSummary(count);
                        break;
                    case SUM:
                        AggregationSubtotalBuilder sum = sbt.sum(cl.getPropriedadeCompleta(), cl.getClasseOriginal(), column);
                        sum.setStyle(stl.style().setHorizontalAlignment(HorizontalAlignment.CENTER));
                        relatorio.subtotalsAtSummary(sum);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public void cabecalhoRodape() {

        //ESTILOS
        StyleBuilder centeredStyle = stl.style()
                .setHorizontalAlignment(HorizontalAlignment.CENTER);

        StyleBuilder boldCenteredStyle = stl.style(stl.style().bold())
                .setHorizontalAlignment(HorizontalAlignment.CENTER);

        PaddingBuilder padding = stl.padding().setBottom(0).setTop(0);

        //ESTILO DO TITULO
        StyleBuilder titleStyle = stl.style(boldCenteredStyle)
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setPadding(padding).setFontSize(15);

        //ESTILO DO SUBTITULO
        StyleBuilder subTitleStyle = stl.style(centeredStyle)
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setPadding(padding).setFontSize(12);

        //ESTILO DA IMAGEM
        StyleBuilder imagemStyle = stl.style().setVerticalAlignment(VerticalAlignment.MIDDLE).
                setPadding(stl.padding().setTop(9));

        //CABEÇALHO
        relatorio.title(
                cmp.horizontalList()
                        .add(
                                cmp.image(getClass().getClassLoader().getResourceAsStream("logo.png")).setFixedDimension(60, 60).setStyle(imagemStyle),
                                cmp.verticalList().add(cmp.text("Relatório de Contas").setStyle(titleStyle).setHorizontalTextAlignment(HorizontalTextAlignment.LEFT),
                                        cmp.text("RR Minds Soluções de Tecnologia LTDA").setStyle(subTitleStyle).setHorizontalTextAlignment(HorizontalTextAlignment.LEFT)))
                        .newRow()
                        .add(cmp.filler().setStyle(stl.style().setBackgroundColor(new Color(0, 162, 237))).setFixedHeight(10)));
        //RODAPÉ
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");

        relatorio.setPageFooterStyle(stl.style().setBackgroundColor(new Color(200, 200, 200)).setBorder(stl.penThin().setLineColor(new Color(230, 230, 230))));
        relatorio.pageFooter(cmp.horizontalList()
                .add(cmp.text(new BundleUtil().getLabel("Data") + ": " + sdf.format(new Date())).setHorizontalTextAlignment(HorizontalTextAlignment.LEFT))
                .add(cmp.pageXslashY().setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT)));

    }

    public ImpressoraDeRelatorioDinamico1 comParametros(Map<String, Object> parametros) {
        this.parametros = parametros;
        return this;
    }

    public static void main(String[] args) throws JRException, IOException {

        List<Cobranca> registros = new ArmazemDeRegistros<Cobranca>(Cobranca.class).listaTodosOsRegistros();
        List<Coluna> colunas = new ArrayList<>();
        Coluna pessoa = new Coluna("Nome (Pessoa)", "Pessoa", "pessoa", "nome", Pessoa.class, String.class);
        pessoa.setTamanho(30);

        ImpressoraDeRelatorioDinamico1 impressora = new ImpressoraDeRelatorioDinamico1();

        colunas.add(new Coluna("Id", "Cobrança", "id", Cobranca.class, Long.class));
        colunas.add(pessoa);
        colunas.add(new Coluna("Emissão", "Cobrança", "emissao", Cobranca.class, Date.class));
        colunas.add(new Coluna("Vencimento", "Cobrança", "vencimento", Cobranca.class, Date.class));
        colunas.add(new Coluna("Valor", "Cobrança", "valor", Cobranca.class, BigDecimal.class, null, Totalizador.AVERAGE));

        impressora.imprimir(registros, "Report_Model", colunas).noConsole();

        System.out.println("Imprimiu");

    }

    private class CurrencyType extends BigDecimalType {

        private static final long serialVersionUID = 1L;

        @Override
        public String getPattern() {
            return " #,###.00";
        }
    }

}
