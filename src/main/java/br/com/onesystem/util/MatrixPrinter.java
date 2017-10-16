package br.com.onesystem.util;

public class MatrixPrinter {

    private String impressora;

    public MatrixPrinter(String impressora) {
        
        this.impressora = impressora;
    }

    public void imprimir(String texto[]) {
        float linha = (float) 0.01;

        ESCPrinter escp = new ESCPrinter(impressora, false); //create ESCPrinter on network location \\computer\sharename, 9pin printer
        if (escp.initialize()) {
            escp.setCharacterSet(ESCPrinter.BRAZIL);
            escp.select15CPI(); //15 characters per inch printing
            escp.bold(false);
            
            for (int i = 0; i < texto.length; i++) {
                escp.print(texto[i].toString());
                escp.advanceVertical(linha); //go down 5cm
            }

            escp.formFeed(); //eject paper
            escp.close(); //close stream
        } else {
            System.out.println("Couldn't open stream to printer");
        }
    }
}
