/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import br.com.onesystem.valueobjects.TipoTransacao;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Rauber
 */
@Entity
@SequenceGenerator(name = "SEQ_LOG", initialValue = 1, allocationSize = 1,
sequenceName = "SEQ_LOG")
public class Log {

    @Id
    @GeneratedValue(generator = "SEQ_LOG", strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(nullable = false, length = 10000)
    private String mensagem;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoTransacao tipoDeOperacao;
    @Temporal(TemporalType.TIMESTAMP)
    private Date data = Calendar.getInstance().getTime();

    public Log() {
    }

    public Log(String mensagem, TipoTransacao tipoDeOperacao) {
        this.mensagem = mensagem;
        this.tipoDeOperacao = tipoDeOperacao;
    }

    public long getId() {
        return id;
    }

    public String getMensagem() {
        return mensagem;
    }

    public TipoTransacao getTipoDeOperacao() {
        return tipoDeOperacao;
    }

    public Date getData() {
        return data;
    }
}
