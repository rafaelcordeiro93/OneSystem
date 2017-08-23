package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Caixa;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.Usuario;
import br.com.onesystem.domain.builder.CaixaBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.BuilderView;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class CaixaBV implements Serializable, BuilderView<Caixa> {

    private static final long serialVersionUID = -1361062802747818014L;

    private Long id;
    private BigDecimal saldo;
    private Date abertura = new Date();
    private Date fechamento;
    private Usuario usuario;
    private List<Cotacao> cotacao;
    private String descricao;

    public CaixaBV(Caixa caixaSelecionado) {
        if (caixaSelecionado != null) {
            this.id = caixaSelecionado.getId();
            this.saldo = caixaSelecionado.getSaldo();
            this.abertura = caixaSelecionado.getAbertura();
            this.fechamento = caixaSelecionado.getFechamento();
            this.usuario = caixaSelecionado.getUsuario();
            this.cotacao = caixaSelecionado.getCotacao();
            this.descricao = caixaSelecionado.getDescricao();
        }
    }

    public CaixaBV() {
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public Date getAbertura() {
        return abertura;
    }

    public Date getFechamento() {
        return fechamento;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Cotacao> getCotacao() {
        return cotacao;
    }

    public void setCotacao(List<Cotacao> cotacao) {
        this.cotacao = cotacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Caixa construir() throws DadoInvalidoException {
        return new CaixaBuilder().comSaldo(saldo).comUsuario(usuario).comCotacao(cotacao).comDescricao(descricao).construir();
    }

    public Caixa construirComID() throws DadoInvalidoException {
        return new CaixaBuilder().comId(id).comSaldo(saldo).comUsuario(usuario).comDescricao(descricao).comCotacao(cotacao).construir();
    }

}
