package br.com.onesystem.util;

//import java.awt.Color;
//import java.awt.GradientPaint;
//import java.awt.Graphics2D;
//import java.awt.RenderingHints;
//import java.awt.geom.Rectangle2D;
//import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import net.sf.dynamicreports.report.constant.BreakType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
//
//import com.sun.image.codec.jpeg.JPEGCodec;
//import com.sun.image.codec.jpeg.JPEGEncodeParam;
//import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ImpressoraDeTexto {

    public String impressora;
    private String page[][];

    public ImpressoraDeTexto() {
        try {
            File f = new File("conf.ini");
            if (f.exists()) {
                Properties confBanco = new Properties();
                confBanco.load(new FileInputStream("conf.ini"));
                impressora = confBanco.getProperty("impressora");

                System.out.print("IMP --->" + impressora);
            }
        } catch (Exception e) {
            System.out.println("ERRO DE IMPRESSORA ------> " + e);
        }
    }

    public void carregaLayout(JSONArray layout) {
        int linha = 1;
        for (Object o : layout) {
            JSONObject j = (JSONObject) o;
            while (j.get("linha" + Integer.toString(linha)) != null) {
                insertTextoNaLinhaColuna(linha, 1, (String) j.get("linha" + Integer.toString(linha)));
                linha++;
            }
        }
    }
    
    public void insertTextoNaLinhaColuna(int lin, int colunaIn, String text) {
        int colunaEnd = page[0].length;
        int charPoss = 0;
        for (int col = 0; col < colunaEnd; col++) {
            if (col >= colunaIn - 1 && charPoss < text.length() && col <= colunaEnd) {
                page[lin - 1][col] = Character.toString(text.charAt(charPoss));
                charPoss++;
            }
        }
    }

    

    public List<GenericLayout> criaListaGenericLayout(JSONArray dados) {
        List<GenericLayout> listaLayout = new ArrayList<>();
        for (Object o : dados) {
            JSONObject j = (JSONObject) o;
            String tabela = (String) j.get("tabela");
            String coluna = (String) j.get("coluna");
            Integer left = ((Long) j.get("left")).intValue();
            Integer top = ((Long) j.get("top")).intValue();

            GenericLayout g = new GenericLayout(tabela, coluna, left, top);
            listaLayout.add(g);
        }
        return listaLayout;
    }

    public void setOutSize(int lin, int col) {
        page = new String[lin][col];
    }

    public void printCharAtCol(int lin, int colunaIn, int colunaEnd, String text) {
        for (int i = 0; i < colunaEnd; i++) {
            if (i >= colunaIn - 1 && i <= colunaEnd - 1) {
                page[lin - 1][i] = text;
            }
        }

    }

    public void printCharAtLin(int linhaIn, int linhaEnd, int coluna, String text) {
        for (int i = 0; i < linhaEnd; i++) {
            if (i >= linhaIn - 1 && i <= linhaEnd - 1) {
                page[i][coluna - 1] = text;
            }
        }

    }

    public String printTextLin(int linha, String text) {
        text = RemoveAcentos(text);
        String newText = "";
        for (int i = 0; i < linha; i++) {
            newText = newText + " ";
        }
        return newText + text;
    }

    public void printTextLinCol(int lin, int coluna, String text) {
        // text = RemoveAcentos(text);
        // for (int i = 0; i < coluna; i++) {
        // if (i == coluna - 1 && i <= 70) {
        page[lin - 1][coluna - 1] = text;
        // }
        //   }

    }
    
    public void printCharAtLinCol(int linIn, int linEnd, int colunaIn, int colunaEnd, String text) {
        for (int i = 0; i < linEnd; i++) {
            if (i < linIn - 1 || i > linEnd - 1) {
                continue;
            }
            for (int c = 0; c < colunaEnd; c++) {
                if (c >= colunaIn - 1 && c <= colunaEnd - 1) {
                    page[i][c] = text;
                }
            }

        }

    }

    public void printTextWrap(int linI, int linE, int colI, int colE, String text) {
        int textSize = text.length();
        int limitH = linE - linI;
        int limitV = colE - colI;
        int wrap = 0;
        if (textSize > limitV) {
            wrap = textSize / limitV;
            if (textSize % limitV > 0) {
                wrap++;
            }
        } else {
            wrap = 1;
        }
        System.out.println("textSize:" + textSize);
        System.out.println("limitH:" + limitH);
        System.out.println("limitV:" + limitV);
        System.out.println("wrap:" + wrap);
        String wraped[] = new String[wrap];
        int end = 0;
        int init = 0;
        for (int i = 1; i <= wrap; i++) {
            end = i * limitV;
            if (end > text.length()) {
                end = text.length();
            }
            wraped[i - 1] = text.substring(init, end);
            System.out.println("wraped[" + (i - 1) + "]:" + wraped[i - 1]);
            init = end;
        }

        for (int b = 0; b < wraped.length; b++) {
            if (b <= linE) {
                page[linI][colI] = wraped[b];
                System.out.println("page[" + linI + "][" + colI + "]:" + page[linI][colI]);
                linI++;
            }
        }

    }

    public void show() {
        for (int i = 0; i < page.length; i++) {
            for (int b = 0; b < page[i].length;) {
                String tmp = page[i][b];
                if (tmp != null && !tmp.equals("")) {
                    int size = tmp.length();
                    b += size;
                    System.out.print(tmp);
                } else {
                    System.out.print(" ");
                    b++;
                }
            }

            System.out.println();
        }

    }

    public void toFile(String fileName) {
        try {
            FileOutputStream fo = new FileOutputStream(fileName);
            for (int i = 0; i < page.length; i++) {
                for (int b = 0; b < page[i].length;) {
                    String tmp = page[i][b];
                    if (tmp != null && !tmp.equals("")) {
                        int size = tmp.length();
                        b += size;
                        fo.write(tmp.getBytes());
                    } else {
                        fo.write(" ".getBytes());
                        b++;
                    }
                }

                fo.write("\n".getBytes());
            }

            fo.flush();
            fo.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void toPrinter(String printerName) {
        try {
            FileOutputStream fo = new FileOutputStream(printerName);
            for (int i = 0; i < page.length; i++) {
                for (int b = 0; b < page[i].length;) {
                    String tmp = page[i][b];
                    if (tmp != null && !tmp.equals("")) {
                        int size = tmp.length();
                        b += size;
                        fo.write(tmp.getBytes());
                    } else {
                        fo.write(" ".getBytes());
                        b++;
                    }
                }

                fo.write("\n".getBytes());
            }

            fo.flush();
            fo.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void toPrinterMatricial() {
        try {
            FileOutputStream fo = new FileOutputStream("LPT1");
            for (int i = 0; i < page.length; i++) {
                for (int b = 0; b < page[i].length;) {
                    String tmp = page[i][b];
                    if (tmp != null && !tmp.equals("")) {
                        int size = tmp.length();
                        b += size;
                        fo.write(tmp.getBytes());
                    } else {
                        fo.write(" ".getBytes());
                        b++;
                    }
                }

                fo.write("\n".getBytes());
            }
            PrintStream ps = new PrintStream(fo);
            ps.print(fo);
            //fo.flush();
            ps.close();
            fo.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void toPrinter(String printerName, byte escCommands[]) {
        try {
            FileOutputStream fo = new FileOutputStream(printerName);
            fo.write(escCommands);
            for (int i = 0; i < page.length; i++) {
                for (int b = 0; b < page[i].length;) {
                    String tmp = page[i][b];
                    if (tmp != null && !tmp.equals("")) {
                        int size = tmp.length();
                        b += size;
                        fo.write(tmp.getBytes());
                    } else {
                        fo.write(" ".getBytes());
                        b++;
                    }
                }

                fo.write("\n".getBytes());
            }

            fo.flush();
            fo.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void toPrintStream(PrintStream out) {
        try {
            for (int i = 0; i < page.length; i++) {
                for (int b = 0; b < page[i].length;) {
                    String tmp = page[i][b];
                    if (tmp != null && !tmp.equals("")) {
                        int size = tmp.length();
                        b += size;
                        out.print(tmp);
                    } else {
                        out.print(" ");
                        b++;
                    }
                }

                out.println("");
            }

            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void toPrinterServer(String ipServer, int port) {
        try {
            Socket socket = new Socket(ipServer, port);
            System.out.println("Porta:" + socket.getPort());
            System.out.println("Host IP......:" + socket.getInetAddress().getHostAddress());
            System.out.println("Host Name......:" + socket.getInetAddress().getHostName());
            OutputStream fo = socket.getOutputStream();
            StringBuffer saida = new StringBuffer();
            for (int i = 0; i < page.length; i++) {
                for (int b = 0; b < page[i].length;) {
                    String tmp = page[i][b];
                    if (tmp != null && !tmp.equals("")) {
                        int size = tmp.length();
                        b += size;
                        saida.append(tmp);
                    } else {
                        saida.append(" ");
                        b++;
                    }
                }

                saida.append("\n");
            }

            fo.write(saida.toString().getBytes());
            fo.flush();
            fo.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String alinharADireita(int tamCampo, String text) {
        int spaces = tamCampo - text.length();
        String newText = "";
        for (int i = 0; i < spaces; i++) {
            newText = newText + " ";
        }

        return newText + text;
    }

    public String alinharAEsquerda(int tamCampo, String text) {
        int spaces = tamCampo - text.length();
        String newText = "";
        for (int i = 0; i < spaces; i++) {
            newText = newText + " ";
        }

        return text + newText;
    }

    public String centralizar(int tamCampo, String text) {
        int spaces = (tamCampo - text.length()) / 2;
        String newText = "";
        for (int i = 0; i < spaces; i++) {
            newText = newText + " ";
        }
        return newText + text + newText;
    }

    public String centralizarcomcaractere(int tamCampo, String text, String caractere) {
        int spaces = (tamCampo - text.length()) / 2;
        String newText = "";
        for (int i = 0; i < spaces; i++) {
            newText = newText + caractere;
        }
        return newText + text + newText;
    }

    public void mapearDocumento(int linha, int coluna, String printer) {
        setOutSize(linha, coluna);
        for (int i = 1; i <= coluna; i++) {
            String print = i + "";
            if (i >= 10 && i <= 99) {
                print = print.substring(1, 2);
            }
            if (i >= 100 && i <= coluna) {
                print = print.substring(2, 3);
            }
            printTextLinCol(1, i, print);
        }

        for (int i = 1; i <= linha; i++) {
            String print = "" + i;
            printTextLinCol(i, 1, print);
        }

        toPrinter(printer);
    }

    public void mapearDocumento(int linha, int coluna, String printer, byte escCommands[]) {
        setOutSize(linha, coluna);
        for (int i = 1; i <= coluna; i++) {
            String print = i + "";
            if (i >= 10 && i <= 99) {
                print = print.substring(1, 2);
            }
            if (i >= 100 && i <= coluna) {
                print = print.substring(2, 3);
            }
            printTextLinCol(1, i, print);
        }

        for (int i = 1; i <= linha; i++) {
            String print = "" + i;
            printTextLinCol(i, 1, print);
        }

        toPrinter(printer, escCommands);
    }

    public void mapearDocumentoImageFile(int linha, int coluna, String fileName) {
        setOutSize(linha, coluna);
        for (int i = 1; i <= coluna; i++) {
            String print = i + "";
            if (i >= 10 && i <= 99) {
                print = print.substring(1, 2);
            }
            if (i >= 100 && i <= coluna) {
                print = print.substring(2, 3);
            }
            printTextLinCol(1, i, print);
        }

        for (int i = 1; i <= linha; i++) {
            String print = "" + i;
            printTextLinCol(i, 1, print);
        }

        // toImageFile(fileName);
    }

//    public void toImageFile(String fileName) {
//        int width = page[0].length * 10;
//        int height = page.length * 10;
//        BufferedImage image = new BufferedImage(width, height, 1);
//        Graphics2D g = image.createGraphics();
//        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        g.setPaint(Color.white);
//        Rectangle2D rectangle = new java.awt.geom.Rectangle2D.Double(0.0D, 0.0D, width, height);
//        g.fill(rectangle);
//        GradientPaint gp = new GradientPaint(0.0F, 0.0F, Color.black, 400F, 100F, Color.black);
//        g.setPaint(gp);
//        try {
//            for (int i = 0; i < page.length; i++) {
//                for (int b = 0; b < page[i].length;) {
//                    String tmp = page[i][b];
//                    if (tmp != null && !tmp.equals("")) {
//                        int size = tmp.length();
//                        g.drawString(tmp, b * 10, (i + 1) * 10);
//                        b += size;
//                    } else {
//                        g.drawString(" ", b * 10, (i + 1) * 10);
//                        b++;
//                    }
//                }
//
//            }
//
//            FileOutputStream file = new FileOutputStream(fileName);
//            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(file);
//            JPEGEncodeParam jpegParams = encoder.getDefaultJPEGEncodeParam(image);
//            jpegParams.setQuality(1.0F, false);
//            encoder.setJPEGEncodeParam(jpegParams);
//            encoder.encode(image);
//            file.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    public String RemoveAcentos(String str) {
        char[] acentuados = new char[]{'ç', 'á', 'à', 'ã', 'â', 'ä', 'é', 'è', 'ê', 'ë', 'í', 'ì', 'î', 'ï', 'ó', 'ò', 'õ', 'ô', 'ö', 'ú', 'ù', 'û', 'ü'};

        char[] naoAcentuados = new char[]{'c', 'a', 'a', 'a', 'a', 'a', 'e', 'e', 'e', 'e', 'i', 'i', 'i', 'i', 'o', 'o', 'o', 'o', 'o', 'u', 'u', 'u', 'u'};

        for (int i = 0; i < acentuados.length; i++) {
            str = str.replace(acentuados[i], naoAcentuados[i]);
            str = str.replace(Character.toUpperCase(acentuados[i]), Character.toUpperCase(naoAcentuados[i]));
        }
        return str;
    }

    
//
//    public static void main(String args[]) {
//        System.out.print("");
//        ImpressoraDeTexto t = new ImpressoraDeTexto();
//
//        //t.p
//        //          linha/coluna
//        t.setOutSize(15, 250);
//        t.insertTextoNaLinhaColuna(2, 1, "NOME.....:");
//        t.insertTextoNaLinhaColuna(3, 1, "ENDEREÇO.:");
//        t.insertTextoNaLinhaColuna(4, 1, "CIDADE...:");
//        t.insertTextoNaLinhaColuna(5, 1, "ATIVIDADE:");
//        t.insertTextoNaLinhaColuna(2, 11, "Rafael Cordeiro");
//        t.insertTextoNaLinhaColuna(3, 11, "TESTE PAH ");
//        t.insertTextoNaLinhaColuna(4, 11, "ASSIS SÃO PAULO ");
//        t.insertTextoNaLinhaColuna(5, 11, "ATIVIDADE ATESTEAKLJ");
//        // t.printTextLinCol(6, 1, t.centralizar(70, "TEXTO CENTRALIZAdo"));
//         t.insertTextoNaLinhaColuna(2, 1, "TTTT");
//         t.insertTextoNaLinhaColuna(5, 1, "     ");
//        t.insertTextoNaLinhaColuna(5, 12, "Coco");
//        //t.toFile("impresso.txt");
//
//        t.show();
//        //  t.toPrinterMatricial();
//
//        // t.toImageFile("printermatrix.jpg");
//    }

}
