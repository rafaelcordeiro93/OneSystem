/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterJob;

/**
 *
 * @author Rafael
 */
public class MatricialPrintable implements Printable {

    //--- Private instances declarations
    // The number of CMs per Inch
    public static final double CM_PER_INCH = 0.393700787d;
    private final double INCH = 72;
    private PrinterJob pJob;
    private PageFormat pageformat;

    public MatricialPrintable(int width, int height) throws Exception {

        System.out.println("MatricialPrintable - Iniciando Job");

        //--- Create a printerJob object
        pJob = PrinterJob.getPrinterJob();
        pageformat = pJob.defaultPage();

        System.out.println("MatricialPrintable - setPrintable");
        //--- Set the printable class to this one since we
        //--- are implementing the Printable interface
        setupPageFormat(width, height);

        System.out.println("MatricialPrintable - printDialog");
        //--- Show a print dialog to the user. If the user
        //--- click the print button, then print otherwise
        //--- cancel the print job

        try {
            pJob.print();
        } catch (Exception printException) {
            System.out.println(printException.getMessage());
            throw new Exception(printException);
        }

    }

    /**
     * Method: print
     * <p>
     *
     * This class is responsible for rendering a page using the provided
     * parameters. The result will be a grid where each cell will be half an
     * inch by half an inch.
     *
     * @param g a value of type Graphics
     * @param format a value of type PageFormat
     * @param page a value of type int
     * @return a value of type int
     */
    @Override
    public int print(Graphics g, PageFormat format, int page) {

        if (page != 0) {
            return NO_SUCH_PAGE;
        }
//        Graphics2D gr = (Graphics2D) g;
        //posiciona o objeto graphics no começo da área util da pág  
//        gr.translate(100, 100);

//        Rectangle2D.Double border = new Rectangle2D.Double(0, 0, 100, 100);
//        gr.draw(border);
        //adiocionando uma linha na impressao  
//        Font titleFont = new Font("helvetica", Font.PLAIN, 12);
//        gr.setFont(titleFont);
        //gr.drawString("Teste", 10, 10);
        return PAGE_EXISTS;

//        //--- Create the Graphics2D object
//        Graphics2D g2d = (Graphics2D) g;
//
//        //--- Translate the origin to 0,0 for the top left corner
//        g2d.translate(pageFormat.getImageableX(), pageFormat
//                .getImageableY());
//
//        //--- Set the default drawing color to black
//        g2d.setPaint(Color.black);
//
////        Rectangle2D.Double border = new Rectangle2D.Double(0, 0, pageFormat
////                .getImageableWidth(), pageFormat.getImageableHeight());
////        g2d.draw(border);
//
//        //--- Print the title
//        String titleText = "Imprimindo primeira página para teste de impressora";
//        Font titleFont = new Font("helvetica", Font.PLAIN, 12);
//        g2d.setFont(titleFont);
//
//        //--- Compute the horizontal center of the page
//        FontMetrics fontMetrics = g2d.getFontMetrics();
////        double titleX = (pageFormat.getImageableWidth() / 2)
////                - (fontMetrics.stringWidth(titleText) / 2);
////        double titleY = 3 * INCH;
//        g2d.drawString(titleText, 0, 0);
//
//        return (PAGE_EXISTS);
    }

    public boolean setupPageFormat(int width, int height) {
        Paper paper = pageformat.getPaper();
        //(double) width / 12, (double) height / 12
        paper.setSize((double) width * 12, (double) height * 12);
        paper.setImageableArea(
                cmsToPixel(0.1, 72),
                cmsToPixel(0.1, 72),
                width - cmsToPixel(0.1, 72),
                height - cmsToPixel(0.1, 72));

        this.pageformat.setPaper(paper);
        PageFormat validatePage = pJob.validatePage(pageformat);
        pJob.setPrintable(this, this.pageformat);
        return true;
    }

    public PageFormat getPageFormat() {
        return this.pageformat;
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

}
