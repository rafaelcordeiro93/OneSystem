package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Pessoa;
import java.io.Serializable;
import java.util.Date;

public class RelatorioDeContratoDeCambioBV implements Serializable{

    private Date dataInicial;
    private Date dataFinal;
    private Pessoa pessoa;
    private Boolean status;

    public Date getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(Date dataInicial) {
        this.dataInicial = dataInicial;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
    
}
