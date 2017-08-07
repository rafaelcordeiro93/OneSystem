package br.com.onesystem.util;

import br.com.onesystem.reportTemplate.*;
import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.Cobranca;
import br.com.onesystem.domain.Coluna;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.exception.impl.FDadoInvalidoException;
import br.com.onesystem.valueobjects.TipoFormatacaoNumero;
import br.com.onesystem.valueobjects.TipoRelatorio;
import br.com.onesystem.valueobjects.Totalizador;
import java.awt.Color;
import java.io.IOException;
import java.lang.reflect.Method;
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
import net.sf.dynamicreports.report.base.expression.AbstractSimpleExpression;
import net.sf.jasperreports.engine.JRException;
import static net.sf.dynamicreports.report.builder.DynamicReports.*;
import net.sf.dynamicreports.report.builder.MarginBuilder;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.component.SubreportBuilder;
import net.sf.dynamicreports.report.builder.datatype.BigDecimalType;
import net.sf.dynamicreports.report.builder.style.PaddingBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.builder.subtotal.AggregationSubtotalBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.constant.VerticalAlignment;
import net.sf.dynamicreports.report.definition.ReportParameters;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * @author Rafael Fernando Rauber
 * @date 01/08/2017
 * @author Rafael Fernando Rauber
 *
 * Classe utilizada para impressão de relatórios dinamicamente.
 */
public class ImpressoraDeRelatorioDinamico {

    private JasperReportBuilder relatorio = report(); // Cria um novo relatório.
    private Map<String, Object> parametros;
    private TipoRelatorio tipoRelatorio;

    public ImpressoraDeRelatorioDinamico() {
    }

    /**
     *
     * @param registros Lista tipada dos registros a serem impressos.
     * @param nome Nome do relatório
     * @param colunas Colunas a serem exibidas.
     * @param caminhoDaMoeda
     * @return Retorna o mesmo para que seja possível escolher entre imprimir na
     * web ou no console.
     * @throws br.com.onesystem.exception.impl.FDadoInvalidoException
     */
    public ImpressoraDeRelatorioDinamico imprimir(List registros, TipoRelatorio tipoRelatorio, List<Coluna> colunas, String caminhoDaMoeda) throws FDadoInvalidoException {
        SubreportBuilder subreport;
        List<Moeda> moedas = new ArrayList<>();
        if (caminhoDaMoeda != null) {
            moedas = getMoedas(registros, caminhoDaMoeda);
            List[] listaDeRegistrosPorMoeda = getRegistrosPorMoeda(moedas, registros, caminhoDaMoeda);
            subreport = cmp.subreport(new SubreportExpression(colunas, moedas))
                    .setDataSource(new SubreportDataSourceExpression(listaDeRegistrosPorMoeda));
        } else {
            subreport = cmp.subreport(new SubreportExpression(colunas, null))
                    .setDataSource(new SubreportDataSourceExpression(registros));
        }

        this.tipoRelatorio = tipoRelatorio; // Recebe o nome do relatório

        margem(); //ajusta as margens
        cabecalhoRodape(); //cria cabecalhoRodapé

        relatorio.noData(Templates.createTitleComponent("NoData"), cmp.text(new BundleUtil().getLabel("Nao_Ha_Registros")));
        relatorio.detail(subreport, cmp.verticalGap(20));
        relatorio.setDataSource(createDataSource(moedas.isEmpty() ? 1 : moedas.size())); //add registros
        relatorio.setLocale(new Locale("pt", "BR"));

        return this;

    }

    private List[] getRegistrosPorMoeda(List<Moeda> moedas, List registros, String caminhoDaMoeda) throws FDadoInvalidoException {
        List[] listaDeRegistrosPorMoeda = new ArrayList[moedas.size()];
        for (int i = 0; i < moedas.size(); i++) {
            listaDeRegistrosPorMoeda[i] = new ArrayList();
            for (Object o : registros) {
                Moeda m = getMoeda(o, caminhoDaMoeda);
                if (m == moedas.get(i)) {
                    listaDeRegistrosPorMoeda[i].add(o);
                }
            }
        }
        return listaDeRegistrosPorMoeda;
    }

    private List<Moeda> getMoedas(List registros, String caminhoDaMoeda) throws FDadoInvalidoException {
        List<Moeda> moedas = new ArrayList<>();
        for (Object o : registros) {
            Moeda m = getMoeda(o, caminhoDaMoeda);
            if (!moedas.contains(m)) {
                moedas.add(m);
            }
        }
        return moedas;
    }

    private JRDataSource createDataSource(Integer dataSource) {
        return new JREmptyDataSource(dataSource);
    }

    private class SubreportExpression extends AbstractSimpleExpression<JasperReportBuilder> {

        private static final long serialVersionUID = 1L;

        private List<Moeda> moedas;
        private List<Coluna> colunas;

        public SubreportExpression(List<Coluna> colunas, List<Moeda> moedas) {
            this.moedas = moedas;
            this.colunas = colunas;
        }

        @Override
        public JasperReportBuilder evaluate(ReportParameters reportParameters) {
            int masterRowNumber = reportParameters.getReportRowNumber();
            JasperReportBuilder report = report();
//            report
//                    .setTemplate(Templates.reportTemplate)
//                    .title(cmp.text(moedas.get(masterRowNumber - 1).getNome()).setStyle(Templates.bold12CenteredStyle));

            if (moedas == null) {
                criarColunas(colunas, report, null);
            } else {
                criarColunas(colunas, report, new CurrencyType(moedas.get(masterRowNumber - 1).getSigla()));
            }
            return report;
        }
    }

    private class SubreportDataSourceExpression extends AbstractSimpleExpression<JRDataSource> {

        private static final long serialVersionUID = 1L;
        private final List[] listaRegistros;
        private List registros;

        public SubreportDataSourceExpression(List[] listaRegistros) {
            this.listaRegistros = listaRegistros;
        }

        public SubreportDataSourceExpression(List registros) {
            this.registros = registros;
            listaRegistros = new ArrayList[0];
        }

        @Override
        public JRDataSource evaluate(ReportParameters reportParameters) {
            int masterRowNumber = reportParameters.getReportRowNumber();
            if (registros != null) {
                return new JRBeanCollectionDataSource(registros);
            } else {
                return new JRBeanCollectionDataSource(listaRegistros[masterRowNumber - 1]);
            }
        }
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
        res.addHeader("Content-disposition", "attachment; filename=" + tipoRelatorio.getNome() + new Date() + ".pdf");
        relatorio.toPdf(res.getOutputStream());
        FacesContext.getCurrentInstance().responseComplete();
    }

    public void noConsole() {
        try {
            relatorio.show();
        } catch (DRException ex) {
            Logger.getLogger(ImpressoraDeRelatorioDinamico.class.getName()).log(Level.SEVERE, null, ex);
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
    public void criarColunas(List<Coluna> colunas, JasperReportBuilder report, CurrencyType currency) {
        //Gera as colunas

        colunas.forEach((cl) -> {
            TextColumnBuilder column = col.column(cl.getNome(), cl.getPropriedadeCompleta(), cl.getClasseOriginal());
            column.setWidth(cl.getTamanho());
            criaPattern(cl, column, currency);
            criaTotalizadores(cl, column, report, currency);
            report.addColumn(column);
        });

        //Altera o estilo do Titulo das colunas
        StyleBuilder brancoCentralizado = stl.style().setHorizontalAlignment(HorizontalAlignment.CENTER).setForegroundColor(Color.WHITE)
                .setBackgroundColor(new Color(0, 162, 237)).setBorder(stl.penThin()).setPadding(5);

        PaddingBuilder padding = stl.padding().setBottom(1).setTop(1);
        StyleBuilder columnStyle = stl.style().setHorizontalAlignment(HorizontalAlignment.CENTER).setPadding(padding);

        report.setColumnTitleStyle(brancoCentralizado)
                .setColumnStyle(columnStyle)
                .highlightDetailOddRows();
    }

    private void criaPattern(Coluna cl, TextColumnBuilder column, CurrencyType currency) {
        if (cl.getClasseOriginal() == BigDecimal.class && cl.getTipoFormatadorNumero() == TipoFormatacaoNumero.MOEDA && currency != null) {
            column.setDataType(currency);
        } else if (cl.getClasseOriginal() == BigDecimal.class && (cl.getTipoFormatadorNumero() == TipoFormatacaoNumero.CONTABIL || currency == null)) {
            column.setDataType(new CurrencyTypeContabil());
        }
    }

    private void criaPattern(Coluna cl, AggregationSubtotalBuilder subTotal, CurrencyType currency) {
        if (cl.getTotalizador() != Totalizador.COUNT) {
            if (cl.getClasseOriginal() == BigDecimal.class && cl.getTipoFormatadorNumero() == TipoFormatacaoNumero.MOEDA && currency != null) {
                subTotal.setDataType(currency);
            } else if (cl.getClasseOriginal() == BigDecimal.class && (cl.getTipoFormatadorNumero() == TipoFormatacaoNumero.CONTABIL || currency == null)) {
                subTotal.setDataType(new CurrencyTypeContabil());
            }
        }
    }

    private void criaTotalizadores(Coluna cl, TextColumnBuilder column, JasperReportBuilder report, CurrencyType currency) {
        if (cl.getClasseOriginal() == BigDecimal.class || cl.getClasseOriginal() == Long.class) {

            if (null != cl.getTotalizador()) {
                switch (cl.getTotalizador()) {
                    case AVERAGE:
                        AggregationSubtotalBuilder avg = sbt.avg(cl.getPropriedadeCompleta(), cl.getClasseOriginal(), column);
                        criaPattern(cl, avg, currency);
                        avg.setStyle(stl.style().setHorizontalAlignment(HorizontalAlignment.CENTER));
                        report.subtotalsAtSummary(avg);
                        break;
                    case COUNT:
                        AggregationSubtotalBuilder count = sbt.count(cl.getPropriedadeCompleta(), cl.getClasseOriginal(), column);
                        criaPattern(cl, count, currency);
                        count.setStyle(stl.style().setHorizontalAlignment(HorizontalAlignment.CENTER));
                        report.subtotalsAtSummary(count);
                        break;
                    case SUM:
                        AggregationSubtotalBuilder sum = sbt.sum(cl.getPropriedadeCompleta(), cl.getClasseOriginal(), column);
                        criaPattern(cl, sum, currency);
                        sum.setStyle(stl.style().setHorizontalAlignment(HorizontalAlignment.CENTER));
                        report.subtotalsAtSummary(sum);
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
                                cmp.verticalList().add(cmp.text(tipoRelatorio.getNome()).setStyle(titleStyle).setHorizontalTextAlignment(HorizontalTextAlignment.LEFT),
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

    public ImpressoraDeRelatorioDinamico comParametros(Map<String, Object> parametros) {
        this.parametros = parametros;
        return this;
    }

    private class CurrencyType extends BigDecimalType {

        private static final long serialVersionUID = 1L;
        private final String sigla;

        public CurrencyType(String sigla) {
            this.sigla = sigla;
        }

        @Override
        public String getPattern() {
            return sigla + " #,###.00";
        }
    }

    private class CurrencyTypeContabil extends BigDecimalType {

        private static final long serialVersionUID = 1L;

        @Override
        public String getPattern() {
            return "#,###.00";
        }
    }

    /**
     * Este método busca a moeda dentro do objeto, sendo necessário passar
     *
     * @param obj objeto que contém a moeda
     * @param caminhoDaMoeda endereço necessário para buscar a moeda
     * @return Retorna a moeda do objeto.
     */
    private Moeda getMoeda(Object obj, String caminhoDaMoeda) throws FDadoInvalidoException {
        try {
            String[] split = caminhoDaMoeda.split("\\.");

            Class classePadrao = obj.getClass();

            Method m = classePadrao.getMethod("get" + split[0].substring(0, 1).toUpperCase() + split[0].substring(1), null);
            Object o = null;
            o = m.invoke(obj, null);
            for (int i = 1; i < split.length; i++) {
                Class<?> clazz = o.getClass();
                Method n = clazz.getMethod("get" + split[i].substring(0, 1).toUpperCase() + split[i].substring(1), null);
                o = n.invoke(o, null);
            }
            return (Moeda) o;
        } catch (Exception ex) {
            throw new FDadoInvalidoException(ex.getMessage());
        }
    }

    public static void main(String[] args) throws JRException, IOException, FDadoInvalidoException {

        List<Cobranca> registros = new ArmazemDeRegistros<Cobranca>(Cobranca.class).listaTodosOsRegistros();

        List<Coluna> colunas = new ArrayList<>();
        Coluna pessoa = new Coluna("Nome (Pessoa)", "Pessoa", "pessoa", "nome", Pessoa.class, String.class);
        pessoa.setTamanho(30);

        ImpressoraDeRelatorioDinamico impressora = new ImpressoraDeRelatorioDinamico();

        colunas.add(new Coluna("Id", "Cobrança", "id", Cobranca.class, Long.class));
        colunas.add(pessoa);
        colunas.add(new Coluna("Emissão", "Cobrança", "emissao", Cobranca.class, Date.class));
        colunas.add(new Coluna("Vencimento", "Cobrança", "vencimento", Cobranca.class, Date.class));
        colunas.add(new Coluna("Valor", "Cobrança", "valor", Cobranca.class, BigDecimal.class, TipoFormatacaoNumero.MOEDA, Totalizador.SUM));

        impressora.imprimir(registros, TipoRelatorio.CONTAS, colunas, "cotacao.conta.moeda").noConsole();

        System.out.println("Imprimiu");

    }

}
