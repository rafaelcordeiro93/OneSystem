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
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.AttributedString;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.MediaSizeName;

public class MatrixPrinter implements Printable {

    // The number of CMs per Inch
    public static final double CM_PER_INCH = 0.393700787d;
// The number of Inches per CMs
    public static final double INCH_PER_CM = 2.545d;
// The number of Inches per mm's
    public static final double INCH_PER_MM = 25.45d;
    private final static int POINTS_PER_INCH = 72;
    private String layout[][];
    private int imprimiuVezes = 0;

    public MatrixPrinter(String[][] layout) {
        this.layout = layout;
        PrinterJob pj = PrinterJob.getPrinterJob();

        PrintRequestAttributeSet printRequestAttrSet = new HashPrintRequestAttributeSet();
        printRequestAttrSet.add(MediaSizeName.ISO_A5);
        printRequestAttrSet.add(new MediaPrintableArea((float) 0.0, (float) 0.0, 100, 100, MediaPrintableArea.MM));
        
        PageFormat pf = pj.getPageFormat(printRequestAttrSet);
//        pf.setOrientation(pf.LANDSCAPE);
//        PageFormat pf = new PageFormat();

        pj.setPrintable(this, pf);

        try {
            pj.print();
        } catch (PrinterException e) {
            System.out.println(e);
        }
    }

    public int print(Graphics g, PageFormat pf, int pageIndex) {
        if (pageIndex != 0) {
            return NO_SUCH_PAGE;
        }
//        if (imprimiuVezes == 0) {
            System.out.println("page: " + pageIndex);

//            double width = cmsToPixel(10, 72);
//            double height = cmsToPixel(15, 72);
//            System.out.println(" - pfW: " + width + "pfH: " + height);
//            Paper paper = pf.getPaper();
//            paper.setSize(width, height);
//            paper.setImageableArea(
//                    cmsToPixel(0.1, 72),
//                    cmsToPixel(0.1, 72),
//                    width - cmsToPixel(0.1, 72),
//                    height - cmsToPixel(0.1, 72));
//            pf.setPaper(paper);

            //Area utilizavel para escrita... 
            //Largura Inicio: 10 - Fim: 575 - Definição de largura de caracter no espaçamento
            //Altura Inicio: 25 - Fim: 370 - Cada linha consome 10.
            System.out.println("WIM: " + pf.getImageableWidth());
            System.out.println("WIMX: " + pf.getImageableX());

            System.out.println("HIM: " + pf.getImageableHeight());
            System.out.println("HIMY: " + pf.getImageableY());

//============================================================================================================================================
            Graphics2D g2 = (Graphics2D) g;
            g2.setFont(new Font("Serif", Font.PLAIN, 9));
            g2.setPaint(Color.black);

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
            String text = "AAAAAAAAA_AAAAAAAAA_AAAAAAAAA_AAAAAAAAA_AAAAAAAAA_AAAAAAAAA_AAAAAAAAA_AAAAAAAAA_AAAAAAAAA_AAAAAAAAA_AAAAAAAAA_AAAAAAAAA_AAAAAAAAA_AAAAAAAAA_AAAAAAAAA_AAAAAAAAA";

            for (int i = 0; i < text.length(); i++) {
                String c = String.valueOf(text.charAt(i));
                int math = 50 + (int) Math.floor(5.5 * i);
                g2.drawString(c, math, 100);
            }
//=============================================Bordas===================================
//            Rectangle2D outline = new Rectangle2D.Double(pf.getImageableX(), pf
//                    .getImageableY(), pf.getImageableWidth(), pf
//                    .getImageableHeight());
//            g2.draw(outline);
            imprimiuVezes++;
            return PAGE_EXISTS;
//        }
//        return NO_SUCH_PAGE;
    }

    /**
     * Converts the given pixels to cm's based on the supplied DPI
     *
     * @param pixels
     * @param dpi
     * @return
     */
    public static double pixelsToCms(double pixels, double dpi) {
        return inchesToCms(pixels / dpi);
    }

    /**
     * Converts the given cm's to pixels based on the supplied DPI
     *
     * @param cms
     * @param dpi
     * @return
     */
    public static double cmsToPixel(double cms, double dpi) {
        return cmToInches(cms) * dpi;
    }

    /**
     * Converts the given cm's to inches
     *
     * @param cms
     * @return
     */
    public static double cmToInches(double cms) {
        return cms * CM_PER_INCH;
    }

    /**
     * Converts the given inches to cm's
     *
     * @param inch
     * @return
     */
    public static double inchesToCms(double inch) {
        return inch * INCH_PER_CM;
    }

}
