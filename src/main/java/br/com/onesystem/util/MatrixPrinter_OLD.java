/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.util;

/**
 *
 * @author Rafael
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.print.PrinterAttributes;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttribute;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;

public class MatrixPrinter_OLD implements Printable {

    private String layout[][];

    public PrintRequestAttributeSet getAttribute() {
        PrintRequestAttributeSet printRequestAttrSet = new HashPrintRequestAttributeSet();
        printRequestAttrSet.add(MediaSizeName.ISO_A6);
        printRequestAttrSet.add(OrientationRequested.LANDSCAPE);
        return printRequestAttrSet;
    }

    public MatrixPrinter_OLD(String[][] layout) {
        this.layout = layout;
        try {

            PrinterJob job = PrinterJob.getPrinterJob();
            job.setPrintService(getPrinterService());

//            419.5275573730469
            double width = 419.5275573730469;
            double height = 297.6377868652344;
            PageFormat pf = new PageFormat();
            pf.setOrientation(pf.LANDSCAPE);
            Paper paper = pf.getPaper();
            paper.setSize(height, width);
            paper.setImageableArea(9, 12, height - 18, width - 24);
            pf.setPaper(paper);
            job.setPrintable(this, pf);

            job.print();
        } catch (PrinterException ex) {
            System.out.println(ex.getMessage());

        }
    }

    public int print(Graphics g, PageFormat pf, int pageIndex) {

        System.out.println("W: " + pf.getWidth() + " - " + pf.getImageableWidth() + " - " + pf.getImageableX());
        System.out.println("H: " + pf.getHeight() + " - " + pf.getImageableHeight() + " - " + pf.getImageableY());

        if (pageIndex != 0) {
            return NO_SUCH_PAGE;
        }
        //Area utilizavel para escrita... 
        //Largura Inicio: 10 - Fim: 575 - Definição de largura de caracter no espaçamento
        //Altura Inicio: 25 - Fim: 370 - Cada linha consome 10.

//============================================================================================================================================
        Graphics2D g2 = (Graphics2D) g;
        g2.setFont(new Font("Serif", Font.PLAIN, 10));
        g2.setPaint(Color.black);

        String text = "AAAAAAAAA_AAAAAAAAA_AAAAAAAAA_AAAAAAAAA_AAAAAAAAA_AAAAAAAAA_AAAAAAAAA_AAAAAAAAA_AAAAAAAAA_AAAAAAAAA_AAAAAAAAA_AAAAAAAAA_AAAAAAAAA_AAAAAAAAA_AAAAAAAAA_AAAAAAAAA";
        g2.rotate(-Math.PI / 2, 0, 0);
        g2.scale(0.5, 1.6);
        g2.drawString(text, 0, 0);
//        for (int i = 0; i < text.length(); i++) {
//            String c = String.valueOf(text.charAt(i));
//            int math = 10 + (int) Math.floor(16 * i);
//            g2.drawString(c, math, 15);
//        }
//==========================================Gera Impressão====================================================================================        
//
//        double espacamento = 6.1;
//        int margemInicialDaPagina = 10;
//        for (int i = 0; i < layout.length; i++) {
//            int linha = 25 + (i * 10);
//            for (int b = 0; b < layout[i].length; b++) {
//                String tmp = layout[i][b];
//                int colunaDoCaracter = (int) Math.floor(espacamento * b);
//                int coluna = margemInicialDaPagina + colunaDoCaracter;
//                if (tmp != null && !tmp.equals("")) {
//                    g2.drawString(tmp, coluna, linha);
//                } else {
//                    g2.drawString(" ", coluna, linha);
//                }
//            }
//        }
        //============================================Teste de Impressao========================
//        String text = "AAAAAAAAA_AAAAAAAAA_AAAAAAAAA_AAAAAAAAA_AAAAAAAAA_AAAAAAAAA_AAAAAAAAA_AAAAAAAAA_AAAAAAAAA_AAAAAAAAA_AAAAAAAAA_AAAAAAAAA_AAAAAAAAA_AAAAAAAAA_AAAAAAAAA_AAAAAAAAA";
//        String text = "AAAA";
//
//        for (int i = 0; i < text.length(); i++) {
//            String c = String.valueOf(text.charAt(i));
//            int math = 50 + (int) Math.floor(5.5 * i);
//            g2.drawString(c, math, 90);
//        }
        return PAGE_EXISTS;
    }

    private PrintService getPrinterService() {
        String nomeImpressora = "Epson";
        PrintService[] lokup = PrinterJob.lookupPrintServices();
        PrintService impressora = null;
        for (PrintService s : lokup) {
            if (s.getName().equals(nomeImpressora)) {
                impressora = s;
            }
        }
        return impressora;
    }
}
