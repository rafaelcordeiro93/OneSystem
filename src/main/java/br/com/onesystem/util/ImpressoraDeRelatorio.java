package br.com.onesystem.util;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class ImpressoraDeRelatorio {

    private final String dir = FacesContext.getCurrentInstance().getExternalContext().getRealPath("") + "\\resources\\relatorios\\";
    private JasperPrint print;
    private Map<String, Object> parametros;

    public void imprimir(String relatorio) throws EDadoInvalidoException {
        try {
            //Compila o relatorio
            JasperReport report = JasperCompileManager.compileReport(dir + relatorio + ".jrxml");
            HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            res.setContentType("application/pdf");
            res.addHeader("Content-disposition", "attachment; filename=" + relatorio + ".pdf");

            print = JasperFillManager.fillReport(report, null, new ConnectionFactory().getConnection());

            JasperExportManager.exportReportToPdfStream(print, res.getOutputStream());
            FacesContext.getCurrentInstance().responseComplete();

        } catch (Exception e) {
            throw new EDadoInvalidoException("Erro ao exibir o relatório: " + e.getMessage());
        }
    }

    public ImpressoraDeRelatorio comParametros(Map<String, Object> parametros) {
        this.parametros = parametros;
        return this;
    }

    public void imprimir(List<?> lista, String relatorio) throws DadoInvalidoException {

        try {
            //Compila o relatorio
            JasperReport report = JasperCompileManager.compileReport(dir + relatorio + ".jrxml");
            HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            res.setContentType("application/pdf");
            res.addHeader("Content-disposition", "attachment; filename=" + relatorio + ".pdf");

            print = JasperFillManager.fillReport(report, parametros, new JRBeanCollectionDataSource(lista));

            JasperExportManager.exportReportToPdfStream(print, res.getOutputStream());
            FacesContext.getCurrentInstance().responseComplete();

        } catch (Exception e) {
            throw new EDadoInvalidoException("Erro ao exibir o relatório: " + e.getMessage());
        }
    }

}
