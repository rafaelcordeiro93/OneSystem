/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.util;

import br.com.onesystem.domain.Configuracao;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.Moeda;
import com.ibm.icu.text.NumberFormat;
import com.sun.org.apache.xml.internal.security.Init;
import java.math.BigDecimal;
import javax.inject.Inject;

/**
 *
 * @author rauber
 */
public class MoedaFormatter {

    public static String format(Moeda moeda, Double valor) {
        Double v = valor == null ? 0 : valor == 0 ? 0 : valor;
        return NumberFormat.getCurrencyInstance(moeda.getBandeira().getLocal()).format(v);
    }

    public static String format(Moeda moeda, BigDecimal valor) {
        BigDecimal v = valor == null ? BigDecimal.ZERO : valor.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : valor;
        return NumberFormat.getCurrencyInstance(moeda.getBandeira().getLocal()).format(v);
    }

    public static String format(BigDecimal valor) {
        BigDecimal v = valor == null ? BigDecimal.ZERO : valor.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : valor;
        return NumberFormat.getCurrencyInstance().format(v);
    }

    public static String format(Double valor) {
        Double v = valor == null ? 0 : valor == 0 ? 0 : valor;
        return NumberFormat.getCurrencyInstance().format(v);
    }
    
    /**
     * O valor a converter deve ser verificado se diferente de nulo e maior que
     * zero, assim também confere se a cotacao foi informada e o seu valor
     * também é maior que zero, se isso for verdadeiro deve dividir o valor a
     * converter pelo valor da cotação. Caso o valor a converter for diferente
     * de nulo e maior que zero e a cotação não foi informada ou for menor ou
     * igual a zero, deve retornar o valor a converter sem alterações. Caso
     * nenhuma dessas afirmações retorne zero.
     *
     * @author Rafael Fernando Rauber
     * @param valorAConverter valor que deve ser convertido
     * @param cotacao cotacao que deve ser informada para a conversão, se a
     * cotacão for a padrão a mesma conterá o valor de 1, que ao realizar a
     * divisão trará o mesmo resultado.
     * @date 31/08/2017
     * @return retorna o valor convertido na moeda padrão
     */
    public static BigDecimal valorConvertidoNaMoedaPadrao(BigDecimal valorAConverter, Cotacao cotacao) {
        if (valorAConverter != null && valorAConverter.compareTo(BigDecimal.ZERO) > 0) {
            if (cotacao != null && cotacao.getValor() != null
                    && cotacao.getValor().compareTo(BigDecimal.ZERO) > 0) {
                return valorAConverter.divide(cotacao.getValor(), 2, BigDecimal.ROUND_UP);
            } else {
                return valorAConverter;
            }
        } else {
            return BigDecimal.ZERO;
        }
    }

    public static String valorPorExtenso(Moeda moeda, BigDecimal valor) {
        double vlr = valor.doubleValue();
        BundleUtil b = new BundleUtil();
        if (vlr == 0) {
            return b.getLabel("zero");
        }

        long inteiro = (long) Math.abs(vlr); // parte inteira do valor
        double resto = vlr - inteiro;       // parte fracionária do valor

        String vlrS = String.valueOf(inteiro);
        if (vlrS.length() > 15) {
            return ("Erro: valor superior a 999 trilhões.");
        }

        String s = "", saux, vlrP;
        String centavos = String.valueOf((int) Math.round(resto * 100));

        String[] unidade = {"", b.getLabel("um"), b.getLabel("dois"), b.getLabel("tres"), b.getLabel("quatro"), b.getLabel("cinco"),
            b.getLabel("seis"), b.getLabel("sete"), b.getLabel("oito"), b.getLabel("nove"), b.getLabel("dez"), b.getLabel("onze"),
            b.getLabel("doze"), b.getLabel("treze"), b.getLabel("quatorze"), b.getLabel("quinze"), b.getLabel("dezesseis"),
            b.getLabel("dezessete"), b.getLabel("dezoito"), b.getLabel("dezenove")};
        String[] centena = {"", b.getLabel("cento"), b.getLabel("duzentos"), b.getLabel("trezentos"),
            b.getLabel("quatrocentos"), b.getLabel("quinhentos"), b.getLabel("seiscentos"),
            b.getLabel("setecentos"), b.getLabel("oitocentos"), b.getLabel("novecentos")};
        String[] dezena = {"", "", b.getLabel("vinte"), b.getLabel("trinta"), b.getLabel("quarenta"), b.getLabel("cinquenta"),
            b.getLabel("sessenta"), b.getLabel("setenta"), b.getLabel("oitenta"), b.getLabel("noventa")};
        String[] qualificaS = {"", b.getLabel("mil"), b.getLabel("milhao"), b.getLabel("bilhao"), b.getLabel("trilhao")};
        String[] qualificaP = {"", b.getLabel("mil"), b.getLabel("milhoes"), b.getLabel("bilhoes"), b.getLabel("trilhoes")};

        // definindo o extenso da parte inteira do valor
        int n, unid, dez, cent, tam, i = 0;
        boolean umReal = false, tem = false;
        while (!vlrS.equals("0")) {
            tam = vlrS.length();
            // retira do valor a 1a. parte, 2a. parte, por exemplo, para 123456789:
            // 1a. parte = 789 (centena)
            // 2a. parte = 456 (mil)
            // 3a. parte = 123 (milhões)
            if (tam > 3) {
                vlrP = vlrS.substring(tam - 3, tam);
                vlrS = vlrS.substring(0, tam - 3);
            } else { // última parte do valor
                vlrP = vlrS;
                vlrS = "0";
            }
            if (!vlrP.equals("000")) {
                saux = "";
                if (vlrP.equals("100")) {
                    saux = b.getLabel("cem");
                } else {
                    n = Integer.parseInt(vlrP, 10);  // para n = 371, tem-se:
                    cent = n / 100;                  // cent = 3 (centena trezentos)
                    dez = (n % 100) / 10;            // dez  = 7 (dezena setenta)
                    unid = (n % 100) % 10;           // unid = 1 (unidade um)
                    if (cent != 0) {
                        saux = centena[cent];
                    }
                    if ((n % 100) <= 19) {
                        if (saux.length() != 0) {
                            saux = saux + " " + b.getLabel("e") + " " + unidade[n % 100];
                        } else {
                            saux = unidade[n % 100];
                        }
                    } else {
                        if (saux.length() != 0) {
                            saux = saux + " " + b.getLabel("e") + " " + dezena[dez];
                        } else {
                            saux = dezena[dez];
                        }
                        if (unid != 0) {
                            if (saux.length() != 0) {
                                saux = saux + " " + b.getLabel("e") + " " + unidade[unid];
                            } else {
                                saux = unidade[unid];
                            }
                        }
                    }
                }
                if (vlrP.equals("1") || vlrP.equals("001")) {
                    if (i == 0) // 1a. parte do valor (um real)
                    {
                        umReal = true;
                    } else {
                        saux = saux + " " + qualificaS[i];
                    }
                } else if (i != 0) {
                    saux = saux + " " + qualificaP[i];
                }
                if (s.length() != 0) {
                    s = saux + ", " + s;
                } else {
                    s = saux;
                }
            }
            if (((i == 0) || (i == 1)) && s.length() != 0) {
                tem = true; // tem centena ou mil no valor
            }
            i = i + 1; // próximo qualificador: 1- mil, 2- milhão, 3- bilhão, ...
        }

        if (s.length() != 0) {
            if (umReal) {
                s = s + " " + moeda.getNome();
            } else if (tem) {
                s = s + " " + moeda.getNomePlural();
            } else {
                s = s + " " + b.getLabel("de") + moeda.getNomePlural();
            }
        }

        // definindo o extenso dos centavos do valor
        if (!centavos.equals("0")) { // valor com centavos
            if (s.length() != 0) // se não é valor somente com centavos
            {
                s = s + " " + b.getLabel("e") + " ";
            }
            if (centavos.equals("1")) {
                s = s + b.getLabel("um") + " " + moeda.getNomeCasasDecimais();
            } else {
                n = Integer.parseInt(centavos, 10);
                if (n <= 19) {
                    s = s + unidade[n];
                } else {             // para n = 37, tem-se:
                    unid = n % 10;   // unid = 37 % 10 = 7 (unidade sete)
                    dez = n / 10;    // dez  = 37 / 10 = 3 (dezena trinta)
                    s = s + dezena[dez];
                    if (unid != 0) {
                        s = s + " " + b.getLabel("e") + " " + unidade[unid];
                    }
                }
                s = s + " " + moeda.getNomeCasasDecimaisPlural();
            }
        }
        return (s);
    }

}
