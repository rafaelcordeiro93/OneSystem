package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Credito;
import br.com.onesystem.domain.Nota;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.TipoContabil;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CreditoBV implements Serializable {

    private Long id;
    private Date emissao;
    private Pessoa pessoa;
    private BigDecimal valor;
    private Nota nota;
    private TipoContabil tipoContabil;

    public CreditoBV(Credito creditoSelecionado) {
        this.id = creditoSelecionado.getId();
        this.emissao = creditoSelecionado.getEmissao();
        this.pessoa = creditoSelecionado.getPessoa();
        this.valor = creditoSelecionado.getValor();
        this.nota = creditoSelecionado.getNota();
        this.tipoContabil = creditoSelecionado.getTipoContabil();
    }

    public CreditoBV() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getEmissao() {
        return emissao;
    }

    public void setEmissao(Date emissao) {
        this.emissao = emissao;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Nota getNota() {
        return nota;
    }

    public void setNota(Nota nota) {
        this.nota = nota;
    }

    public TipoContabil getTipoContabil() {
        return tipoContabil;
    }

    public void setTipoContabil(TipoContabil tipoContabil) {
        this.tipoContabil = tipoContabil;
    }

    public Credito construir() throws DadoInvalidoException {
        return new Credito(null, emissao, pessoa, valor, nota, tipoContabil);
    }

    public Credito construirComID() throws DadoInvalidoException {
        return new Credito(id, emissao, pessoa, valor, nota, tipoContabil);
    }
}
