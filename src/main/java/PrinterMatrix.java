
//import java.awt.Color;
//import java.awt.GradientPaint;
//import java.awt.Graphics2D;
//import java.awt.RenderingHints;
//import java.awt.geom.Rectangle2D;
//import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.net.Socket;
import java.text.ParseException;
import java.util.Properties;

//
//import com.sun.image.codec.jpeg.JPEGCodec;
//import com.sun.image.codec.jpeg.JPEGEncodeParam;
//import com.sun.image.codec.jpeg.JPEGImageEncoder;
public class PrinterMatrix {

    public String impressora;

    public PrinterMatrix() {
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

    public void printTextLinCol(int lin, int coluna, String text) {
        text = RemoveAcentos(text);
        for (int i = 0; i < coluna; i++) {
            if (i == coluna - 1 && i <= 70) {
                page[lin - 1][i] = text;
            }
        }

    }

    public String printTextLin(int coluna, String text) {
        text = RemoveAcentos(text);
        String newText = "";
        for (int i = 0; i < coluna; i++) {
            newText = newText + " ";
        }
        return newText + text;
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

    public static void main(String args[]) throws FileNotFoundException, IOException {
        System.out.print("");
        PrinterMatrix t = new PrinterMatrix();
        //t.p
        t.setOutSize(15, 70);
        t.printCharAtCol(1, 1, 70, "-");
        t.printTextLinCol(2, 1, "NOME.....:");
        t.printTextLinCol(3, 1, "ENDEREÇO.:");
        t.printTextLinCol(4, 1, "CIDADE...:");
        t.printTextLinCol(5, 1, "ATIVIDADE:");
        t.printTextLinCol(2, 11, "Nome TEste");
        t.printTextLinCol(3, 11, "TESTE PAH");
        t.printTextLinCol(4, 11, "ASSIS SÃO PAULO");
        t.printTextLinCol(5, 11, "ATIVIDADE ATESTEAKLJ ÇLSAKJFAÇSLKDJFASLFJKASaaaaaa");
        t.printTextLinCol(6, 1, t.centralizar(70, "CENTRALIZAR"));
        //t.toFile("impresso.txt");
        //  t.show();
        //  t.toPrinterMatricial();

        // t.toImageFile("printermatrix.jpg");
//        
//        
////        
//        Reader reader = new FileReader(new File(ClassLoader.getSystemResource("ParametrosMatricial.json").getFile()));
//
//        Gson gson = new Gson();
//
//        String pedido = gson.fromJson(reader, String.class);
//
//        System.out.print("Pedido: " + pedido);;
//
////                + "\nId Cliente: " + pedido.getCliente().getId()
////                + "\nNome: " + pedido.getCliente().getNome()
////                + "\nItens: ");
//
//        for (Item item : pedido.getItens()) {
//
//            System.out.println("\n---------------------------------------"
//                    + "\nid item: " + item.getId()
//                    + "\nnome item: " + item.getNome()
//                    + "\nqtde: " + item.getQtde());
//
//        }

    }

    private String page[][];
}
