package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Cfop;
import br.com.onesystem.domain.builder.CfopBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.BuilderView;
import br.com.onesystem.valueobjects.OperacaoFisica;
import java.io.Serializable;

public class CfopBV implements Serializable, BuilderView<Cfop> {

    private Long id;
    private Long cfop;
    private String nome;
    private OperacaoFisica operacao;
    private String texto;

    public CfopBV() {
    }

    public CfopBV(Cfop c) {
        this.id = c.getId();
        this.cfop = c.getCfop();
        this.nome = c.getNome();
        this.operacao = c.getOperacaoFisica();
        this.texto = c.getTexto();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCfop() {
        return cfop;
    }

    public void setCfop(Long cfop) {
        this.cfop = cfop;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public OperacaoFisica getOperacao() {
        return operacao;
    }

    public void setOperacao(OperacaoFisica operacao) {
        this.operacao = operacao;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Cfop construir() throws DadoInvalidoException {
        return new CfopBuilder().comNome(nome).comTexto(texto).
                comOperacaoFisica(operacao).comCfop(cfop).construir();
    }

    public Cfop construirComID() throws DadoInvalidoException {
        return new CfopBuilder().comId(id).comNome(nome).comTexto(texto).
                comOperacaoFisica(operacao).comCfop(cfop).construir();
    }
}
