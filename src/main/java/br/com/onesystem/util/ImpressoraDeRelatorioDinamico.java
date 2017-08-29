package br.com.onesystem.util;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.CobrancaVariavel;
import br.com.onesystem.domain.Coluna;
import br.com.onesystem.domain.Filial;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.reportTemplate.CurrencyType;
import br.com.onesystem.exception.impl.FDadoInvalidoException;
import br.com.onesystem.reportTemplate.CurrencyTypeContabil;
import br.com.onesystem.reportTemplate.Templates;
import br.com.onesystem.valueobjects.TipoFormatacaoNumero;
import br.com.onesystem.valueobjects.Totalizador;
import br.com.onesystem.war.service.ConfiguracaoService;
import java.awt.Color;
import java.io.IOException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.base.expression.AbstractSimpleExpression;
import net.sf.jasperreports.engine.JRException;
import static net.sf.dynamicreports.report.builder.DynamicReports.*;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.component.SubreportBuilder;
import net.sf.dynamicreports.report.builder.style.PaddingBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.builder.subtotal.AggregationSubtotalBuilder;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.constant.VerticalTextAlignment;
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
    private Filial filial;
    private Map<String, Object> parametros;
    private String nomeRelatorio;
    private Templates template = new Templates();

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
    public ImpressoraDeRelatorioDinamico imprimir(List registros, String nomeRelatorio, List<Coluna> colunas, String caminhoDaMoeda, Filial filial) throws FDadoInvalidoException {
        this.filial = filial;
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

        this.nomeRelatorio = nomeRelatorio; // Recebe o nome do relatório

        template.margem(relatorio); //ajusta as margens
        relatorio.title(template.cabecalho(filial, nomeRelatorio));
        template.rodape(relatorio);

        relatorio.noData(new Templates().cabecalho(filial, nomeRelatorio), cmp.text(new BundleUtil().getLabel("Nao_Ha_Registros")));
        if (registros != null && !registros.isEmpty()) {
            relatorio.detail(subreport, cmp.verticalGap(5));
            relatorio.setDataSource(createDataSource(moedas.isEmpty() ? 1 : moedas.size())); //add registros
        }
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
                try {
                    Moeda moedaPadrao = new ConfiguracaoService().buscar().getMoedaPadrao();
                    criarColunas(colunas, report, new CurrencyType(moedaPadrao != null ? moedaPadrao.getSigla() : null));
                } catch (DadoInvalidoException die) {
                    die.printStackTrace();
                }
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

    public void naWeb() throws IOException, DRException {
        HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        res.setContentType("application/pdf");
        res.addHeader("Content-disposition", "attachment; filename=" + nomeRelatorio + new Date() + ".pdf");
        ServletOutputStream stream = res.getOutputStream();
        relatorio.toPdf(stream);
        
        stream.flush();
        stream.close();
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
        Iterator<Coluna> it = colunas.iterator();

        colunas.forEach((cl) -> {
            TextColumnBuilder column = col.column(cl.getNome(), cl.getPropriedadeCompleta(), cl.getClasseOriginal());
            column.setWidth(cl.getTamanho());
            column.setHeight(20);
            column.setTitleHeight(20);
            column.setTitleStyle(columnTitleStyle(cl));
            column.setStyle(columnStyle(cl));

            criaPattern(cl, column, currency);
            criaTotalizadores(cl, column, report, currency);
            report.addColumn(column);
        });

        report.setSummaryStyle(stl.style().setTopBorder(stl.pen().setLineColor(Color.BLACK).setLineWidth(new Float(1))));
        report.highlightDetailOddRows();

    }

    private StyleBuilder columnStyle(Coluna coluna) {
        PaddingBuilder padding = stl.padding().setLeft(4).setRight(4);
        StyleBuilder columnStyle;
        if (coluna.getClasseOriginal() == String.class) {
            columnStyle = stl.style().setTextAlignment(HorizontalTextAlignment.LEFT, VerticalTextAlignment.MIDDLE)
                    .setFont(Templates.font10).setPadding(padding);
        } else if (coluna.getClasseOriginal() == BigDecimal.class && coluna.getTipoFormatadorNumero() != null && coluna.getTipoFormatadorNumero() == TipoFormatacaoNumero.MOEDA) {
            columnStyle = stl.style().setTextAlignment(HorizontalTextAlignment.RIGHT, VerticalTextAlignment.MIDDLE)
                    .setFont(Templates.font10).setPadding(padding);
        } else {
            columnStyle = stl.style().setTextAlignment(HorizontalTextAlignment.CENTER, VerticalTextAlignment.MIDDLE)
                    .setFont(Templates.font10).setPadding(padding);
        }
        return columnStyle;
    }

    private StyleBuilder columnTitleStyle(Coluna coluna) {
        //Altera o estilo do Titulo das colunas
        PaddingBuilder padding = stl.padding().setLeft(4).setRight(4);
        StyleBuilder brancoCentralizado;
        if (coluna.getClasseOriginal() == String.class) {
            brancoCentralizado = stl.style().setTextAlignment(HorizontalTextAlignment.LEFT, VerticalTextAlignment.MIDDLE).setForegroundColor(Color.WHITE)
                    .setBackgroundColor(new Color(0, 162, 237)).setBorder(stl.penThin())
                    .setFont(Templates.font10Bold).setPadding(padding);
        } else if (coluna.getClasseOriginal() == BigDecimal.class && coluna.getTipoFormatadorNumero() != null && coluna.getTipoFormatadorNumero() == TipoFormatacaoNumero.MOEDA) {
            brancoCentralizado = stl.style().setTextAlignment(HorizontalTextAlignment.RIGHT, VerticalTextAlignment.MIDDLE).setForegroundColor(Color.WHITE)
                    .setBackgroundColor(new Color(0, 162, 237)).setBorder(stl.penThin())
                    .setFont(Templates.font10Bold).setPadding(padding);
        } else {
            brancoCentralizado = stl.style().setTextAlignment(HorizontalTextAlignment.CENTER, VerticalTextAlignment.MIDDLE).setForegroundColor(Color.WHITE)
                    .setBackgroundColor(new Color(0, 162, 237)).setBorder(stl.penThin())
                    .setFont(Templates.font10Bold).setPadding(padding);
        }
        return brancoCentralizado;
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
            PaddingBuilder padding = stl.padding().setLeft(4).setRight(4);
            HorizontalTextAlignment horizontalAlignment = HorizontalTextAlignment.CENTER;
            if (cl.getTipoFormatadorNumero() != null && cl.getTipoFormatadorNumero() == TipoFormatacaoNumero.MOEDA) {
                horizontalAlignment = HorizontalTextAlignment.RIGHT;
            }

            if (null != cl.getTotalizador()) {
                switch (cl.getTotalizador()) {
                    case AVERAGE:
                        AggregationSubtotalBuilder avg = sbt.avg(cl.getPropriedadeCompleta(), cl.getClasseOriginal(), column);
                        criaPattern(cl, avg, currency);
                        avg.setStyle(stl.style().setTextAlignment(horizontalAlignment, VerticalTextAlignment.MIDDLE).setFont(Templates.font10).setPadding(padding));
                        report.subtotalsAtSummary(avg);
                        break;
                    case COUNT:
                        AggregationSubtotalBuilder count = sbt.count(cl.getPropriedadeCompleta(), cl.getClasseOriginal(), column);
                        criaPattern(cl, count, currency);
                        count.setStyle(stl.style().setTextAlignment(horizontalAlignment, VerticalTextAlignment.MIDDLE).setFont(Templates.font10).setPadding(padding));
                        report.subtotalsAtSummary(count);
                        break;
                    case SUM:
                        AggregationSubtotalBuilder sum = sbt.sum(cl.getPropriedadeCompleta(), cl.getClasseOriginal(), column);
                        criaPattern(cl, sum, currency);
                        sum.setStyle(stl.style().setTextAlignment(horizontalAlignment, VerticalTextAlignment.MIDDLE).setFont(Templates.font10).setPadding(padding));
                        report.subtotalsAtSummary(sum);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public ImpressoraDeRelatorioDinamico comParametros(Map<String, Object> parametros) {
        this.parametros = parametros;
        return this;
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

        List<CobrancaVariavel> registros = new ArmazemDeRegistros<CobrancaVariavel>(CobrancaVariavel.class).listaTodosOsRegistros();
        Filial filial = new ArmazemDeRegistros<Filial>(Filial.class).find(new Long(1));

        List<Coluna> colunas = new ArrayList<>();
        Coluna pessoa = new Coluna("Nome (Pessoa)", "Pessoa", "pessoa", "nome", Pessoa.class, String.class);
        pessoa.setTamanho(30);

        ImpressoraDeRelatorioDinamico impressora = new ImpressoraDeRelatorioDinamico();

        colunas.add(new Coluna("Id", "Cobrança", "id", CobrancaVariavel.class, Long.class));
        colunas.add(pessoa);
        colunas.add(new Coluna("Emissão", "Cobrança", "emissao", CobrancaVariavel.class, Date.class));
        colunas.add(new Coluna("Vencimento", "Cobrança", "vencimento", CobrancaVariavel.class, Date.class));
        colunas.add(new Coluna("Valor", "Cobrança", "valor", CobrancaVariavel.class, BigDecimal.class, TipoFormatacaoNumero.MOEDA, Totalizador.SUM));

        impressora.imprimir(registros, "Relatório de Contas a Pagar Abertas", colunas, null, filial).noConsole();

        System.out.println("Imprimiu");

    }

}
