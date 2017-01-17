package br.com.onesystem.util;

import br.com.onesystem.valueobjects.TipoPeriodicidade;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {

    public static Date getCurrentDateTime() {
        Calendar calendar = new GregorianCalendar();
        SimpleDateFormat out = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        calendar.setTime(date);
        return date;
    }

    /**
     * @param data usado como base para calcular próxima data.
     * @param tipoPeriodicidade define o tipo de periodicidade "DIARIO" ou
     * "MENSAL"
     * @param peridiocidade quantidade de dias ou meses para calculo de
     * periodicidade
     * @return Retorna data futura calculada a partir da periodicidade.
     */
    public Date getPeriodicidadeCalculada(Date data, TipoPeriodicidade tipoPeriodicidade, Integer peridiocidade) {
        com.ibm.icu.util.Calendar c = com.ibm.icu.util.Calendar.getInstance();
        if (tipoPeriodicidade == TipoPeriodicidade.DIARIO) {
            c.setTime(data);
            c.add(com.ibm.icu.util.Calendar.DATE, +peridiocidade);
        } else {
            c.setTime(data);
            c.add(com.ibm.icu.util.Calendar.MONTH, +peridiocidade);
        }
        return c.getTime();
    }

    /**
     * @param data deve ser enviado uma data para buscar o dia da semana
     * @return retorna o dia da semana
     */
    public String getDiaDaSemana(Date data) {
        com.ibm.icu.util.Calendar c = new com.ibm.icu.util.GregorianCalendar();
        c.setTime(data);
        String nome = "";
        int dia = c.get(c.DAY_OF_WEEK);
        switch (dia) {
            case com.ibm.icu.util.Calendar.SUNDAY:
                nome = "Domingo";
                break;
            case com.ibm.icu.util.Calendar.MONDAY:
                nome = "Segunda";
                break;
            case com.ibm.icu.util.Calendar.TUESDAY:
                nome = "Terça";
                break;
            case com.ibm.icu.util.Calendar.WEDNESDAY:
                nome = "Quarta";
                break;
            case com.ibm.icu.util.Calendar.THURSDAY:
                nome = "Quinta";
                break;
            case com.ibm.icu.util.Calendar.FRIDAY:
                nome = "Sexta";
                break;
            case com.ibm.icu.util.Calendar.SATURDAY:
                nome = "Sábado";
                break;
        }
        return nome;
    }

}
