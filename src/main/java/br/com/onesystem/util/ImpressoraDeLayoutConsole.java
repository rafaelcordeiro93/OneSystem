package br.com.onesystem.util;

import br.com.onesystem.domain.LayoutDeImpressao;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.valueobjects.TipoLayout;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

public class ImpressoraDeLayoutConsole {

    private final String diretorio = System.getProperty("user.dir") + "\\src\\main\\resources\\layouts\\";
    private JasperPrint print;
    private Map<String, Object> parametros;
    private LayoutDeImpressao layoutDeImpressao;
    private List<?> lista;

    public ImpressoraDeLayoutConsole(List<?> lista, LayoutDeImpressao layoutDeImpressao) {
        this.layoutDeImpressao = layoutDeImpressao;
        this.lista = lista;
        initParametros();
    }

    private void initParametros() {
        parametros = new HashMap<>();
        parametros.put("REPORT_RESOURCE_BUNDLE", new BundleUtil().getBUNDLE_LABEL());
    }

    public void gerarPDF() throws DadoInvalidoException {
        try {
            imprimir();
            JasperExportManager.exportReportToPdfFile(print, diretorio + layoutDeImpressao.getLayoutGrafico() + ".pdf");
        } catch (Exception e) {
            throw new EDadoInvalidoException("Erro ao exibir o relatório: " + e.getMessage());
        }
    }

    public void visualizar() throws DadoInvalidoException {
        try {
            imprimir();
            JasperViewer.viewReport(print);
        } catch (Exception e) {
            throw new EDadoInvalidoException("Erro ao exibir o relatório: " + e.getMessage());
        }
    }

    public ImpressoraDeLayoutConsole comParametros(Map<String, Object> parametros) {
        this.parametros = parametros;
        return this;
    }

    private ImpressoraDeLayoutConsole imprimir() throws JRException {
        JasperReport report = JasperCompileManager.compileReport(diretorio + layoutDeImpressao.getLayoutGrafico() + ".jrxml");
        print = JasperFillManager.fillReport(report, parametros, new JRBeanCollectionDataSource(lista));
        return this;
    }

}
