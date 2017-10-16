package br.com.onesystem.util;

import br.com.onesystem.domain.LayoutDeImpressao;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.ADadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.exception.impl.FDadoInvalidoException;
import br.com.onesystem.valueobjects.TipoLayout;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class ImpressoraDeLayoutGrafico {

    private String diretorio;
    private JasperPrint print;
    private Map<String, Object> parametros;
    private LayoutDeImpressao layoutDeImpressao;
    private final List<?> lista;

    public ImpressoraDeLayoutGrafico(List<?> lista, LayoutDeImpressao layoutDeImpressao) {
        this.layoutDeImpressao = layoutDeImpressao;
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
        realPath = realPath + "\\WEB-INF\\classes\\layouts\\";
        this.diretorio = realPath;
    }

    public ImpressoraDeLayoutGrafico addParametro(String key, Object parametro) {
        this.parametros.put(key, parametro);
        return this;
    }

    public void exportarPDF() throws DadoInvalidoException {
        try {
            //Compila o relatorio
            JasperReport report = JasperCompileManager.compileReport(diretorio + layoutDeImpressao.getLayoutGrafico() + ".jrxml");
            print = JasperFillManager.fillReport(report, parametros, new JRBeanCollectionDataSource(lista));

            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.addHeader("Content-disposition", "attachment; filename=" + layoutDeImpressao.getTipoLayout().getNome() + ".pdf");
            ServletOutputStream stream = response.getOutputStream();

            JasperExportManager.exportReportToPdfStream(print, stream);

            stream.flush();
            stream.close();
            FacesContext.getCurrentInstance().responseComplete();
        } catch (JRException ex) {
            throw new ADadoInvalidoException(new BundleUtil().getMessage("Layout_nao_definido_no_gerenciador_de_layouts"));
        } catch (IOException ex) {
            throw new FDadoInvalidoException(new BundleUtil().getMessage("Erro_Exibir_Relatorio") + ": (ImpressoraDeLayout - ExportarPDF)" + ex.getMessage(), "Error");
        }
    }

    public void visualizarPDF() throws DadoInvalidoException {
        try {
            //Compila o relatorio
            JasperReport report = JasperCompileManager.compileReport(diretorio + layoutDeImpressao.getLayoutGrafico() + ".jrxml");
            byte[] bytes = JasperRunManager.runReportToPdf(report, parametros, new JRBeanCollectionDataSource(lista));

            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);

            ServletOutputStream outStream = response.getOutputStream();
            outStream.write(bytes, 0, bytes.length);
            outStream.flush();
            outStream.close();

            FacesContext.getCurrentInstance().responseComplete();
        } catch (JRException ex) {
            Logger.getLogger(ImpressoraDeLayoutGrafico.class.getName()).log(Level.SEVERE, "Erro: " + ex.getMessage(), ex);
            throw new ADadoInvalidoException("Error: " + ex.getMessage()); 
        } catch (IOException ex) {
            throw new FDadoInvalidoException(new BundleUtil().getMessage("Erro_Exibir_Relatorio") + ": (ImpressoraDeLayout - ExportarPDF)" + ex.getMessage(), "Error");
        }
    }

}
