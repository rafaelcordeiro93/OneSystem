package br.com.onesystem.util;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.valueobjects.TipoLayout;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class ImpressoraDeLayout {

    private String diretorio;
    private JasperPrint print;
    private Map<String, Object> parametros;
    private TipoLayout tipoLayout;
    private final List<?> lista;

    public ImpressoraDeLayout(List<?> lista, TipoLayout tipoLayout) {
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

    public ImpressoraDeLayout addParametro(String key, Object parametro) {
        this.parametros.put(key, parametro);
        return this;
    }

    public void exportarPDF() throws DadoInvalidoException {
        try {
            //Compila o relatorio
            JasperReport report = JasperCompileManager.compileReport(diretorio + tipoLayout.getNome() + ".jrxml");
            print = JasperFillManager.fillReport(report, parametros, new JRBeanCollectionDataSource(lista));

            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.addHeader("Content-disposition", "attachment; filename=" + tipoLayout.getNome() + ".pdf");
            ServletOutputStream stream = response.getOutputStream();

            JasperExportManager.exportReportToPdfStream(print, stream);

            stream.flush();
            stream.close();
            FacesContext.getCurrentInstance().responseComplete();
        } catch (Exception e) {
            throw new EDadoInvalidoException("Erro ao exibir o relatório: " + e.getMessage());
        }
    }

    public void visualizarPDF() throws DadoInvalidoException {
        try {
            //Compila o relatorio
            JasperReport report = JasperCompileManager.compileReport(diretorio + tipoLayout.getNome() + ".jrxml");
            byte[] bytes = JasperRunManager.runReportToPdf(report, parametros, new JRBeanCollectionDataSource(lista));

            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);

            ServletOutputStream outStream = response.getOutputStream();
            outStream.write(bytes, 0, bytes.length);
            outStream.flush();
            outStream.close();

            FacesContext.getCurrentInstance().responseComplete();
        } catch (Exception e) {
            throw new EDadoInvalidoException("Erro ao exibir o relatório: " + e.getMessage());
        }
    }

}
