/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Rafael
 */
@WebServlet(name = "RelatorioServlet", urlPatterns = {"/RelatorioServlet"})
public class RelatorioServlet extends HttpServlet {                 
    public static final String IMAGES_DIR = "/br/requisitebuilder/relatorios/";  
    public static final String IMAGE_LOGO = "logo.png";         
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("application/pdf", "Content-Type");  
        response.setContentType("application/pdf");
        //Para download
        //   response.setContentType("application/vnd.ms-excel");  
        //response.setHeader("Content-Disposition","attachment;filename=relatorioExcel.xls"); 
        InputStream caminho = null;
        ServletOutputStream ouputStream = null;               
        try{                         
             List listagem = new ArrayList();
             HttpSession session = request.getSession(true);                          
             Object attribute = session.getAttribute("listagem");
             String relatorio = session.getAttribute("relatorio").toString();           
             System.out.println(attribute);
             System.out.println(relatorio);
             if (attribute instanceof List);             
                listagem = (List)attribute;
             if (listagem == null)
                 listagem = new ArrayList();
             String logoPath = IMAGES_DIR + IMAGE_LOGO;
//             caminho = Report.class.getResourceAsStream(relatorio);
             System.out.println(caminho);
             HashMap parametros = new HashMap();
             parametros.put("IMAGE_TOPO", logoPath );
             JRDataSource jrds = new JRBeanCollectionDataSource(listagem);  
             JasperPrint preencher = JasperFillManager.fillReport(caminho, parametros, jrds);
             byte[] bytes = JasperExportManager.exportReportToPdf(preencher);
             response.setContentLength(bytes.length);                   
             ouputStream = response.getOutputStream();  
             ouputStream.write(bytes, 0, bytes.length);               
             ouputStream.flush();
         }catch(Exception e){
             e.printStackTrace();         
         }finally {
            if (caminho != null)
                caminho.close();
            if (ouputStream != null)
                ouputStream.close();
         }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}