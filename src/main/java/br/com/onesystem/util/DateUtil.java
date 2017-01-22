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
     * Utilizado para extrair a diferença em dias entre duas datas.
     * 
     * @param dataMaior a maior das datas para que seja efetuado a subtração
     * @param dataMenor a menor das datas para ser subtraída.
     * @return número de dias entre as datas.
     * @see java.time.LocalDate
     * @see java.time.Instant
     * @see java.time.Period
     * @deprecated Utilizar nova api de Datas do Java 8. Pode ser utilizada a
     * classe Local Date convertendo um date para Instant, e buscando a
     * diferença de dias entre datas através da Classe Period.
     */
    @Deprecated
    public long getDifererencaDeDiasEntreDatas(Date dataMaior, Date dataMenor) {
        final long DIA = 24L * 60L * 60L * 1000L;
        return ((dataMaior.getTime() - dataMenor.getTime()) / DIA);
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
     * @see java.time.LocalDate
     * @see java.time.DayOfWeek
     * @deprecated utilizar classes da nova API java 8 LocalDate e DayOfWeek 
     * para trabalhar buscando o dia da semana.
     */
    @Deprecated
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
