package br.com.onesystem.util;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.valueobjects.TipoLayout;
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

public class ImpressoraDeLayoutConsole {

    private final String diretorio = System.getProperty("user.dir") + "\\src\\main\\resources\\layouts\\";
    private JasperPrint print;
    private Map<String, Object> parametros;
    private TipoLayout tipoLayout;

    public void gerarPDF() throws DadoInvalidoException {
        try {
            JasperExportManager.exportReportToPdfFile(print, diretorio + tipoLayout.getNome() + ".pdf");
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

    public ImpressoraDeLayoutConsole comParametros(Map<String, Object> parametros) {
        this.parametros = parametros;
        return this;
    }

    public ImpressoraDeLayoutConsole imprimir(List<?> lista, TipoLayout tipoLayout) throws JRException {
        this.tipoLayout = tipoLayout;
        JasperReport report = JasperCompileManager.compileReport(diretorio + tipoLayout.getNome() + ".jrxml");
        print = JasperFillManager.fillReport(report, parametros, new JRBeanCollectionDataSource(lista));
        return this;
    }

}
