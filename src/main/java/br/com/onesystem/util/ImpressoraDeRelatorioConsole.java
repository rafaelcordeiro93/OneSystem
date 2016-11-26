package br.com.onesystem.util;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

public class ImpressoraDeRelatorioConsole {

    private final String diretorio = System.getProperty("user.dir") + "\\src\\main\\webapp\\resources\\relatorios\\";
    private JasperPrint print;
    private Map<String, Object> parametros;
   
    public void gerarPDF(String relatorio) throws DadoInvalidoException {
        try {
            JasperExportManager.exportReportToPdfFile(print, diretorio + relatorio + ".pdf");
        } catch (Exception e) {
            throw new EDadoInvalidoException("Erro ao exibir o relatório: " + e.getMessage());
        }
    }

    public void visualizar() throws DadoInvalidoException {
        try {
            JasperViewer.viewReport(print);
        } catch (Exception e) {
            throw new EDadoInvalidoException("Erro ao exibir o relatório: " + e.getMessage());
        }
    }
    
    public ImpressoraDeRelatorioConsole comParametros(Map<String, Object> parametros){
        this.parametros = parametros;
        return this;
    }

    public void imprimir(List<?> lista, String relatorio) throws JRException {
        JasperReport report = JasperCompileManager.compileReport(diretorio + relatorio + ".jrxml");
        print = JasperFillManager.fillReport(report, parametros, new JRBeanCollectionDataSource(lista));
    }

}
