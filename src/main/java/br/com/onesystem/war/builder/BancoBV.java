package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Banco;
import br.com.onesystem.domain.builder.BancoBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.BuilderView;
import java.io.Serializable;

public class BancoBV implements Serializable, BuilderView<Banco> {

    private static final long serialVersionUID = -1361062802747818014L;

    private Long id;
    private String nome;
    private String site;
    private String codigo;

    public BancoBV(Banco bancoSelecionado) {
        this.id = bancoSelecionado.getId();
        this.nome = bancoSelecionado.getNome();
        this.codigo = bancoSelecionado.getCodigo();
        this.site = bancoSelecionado.getSite();
    }

    public BancoBV() {
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getSite() {
        return site;
    }

    public String getCodigo() {
        return codigo;
    }

    public Banco construir() throws DadoInvalidoException {
        return new BancoBuilder().comNome(nome).comSite(site).comCodigo(codigo).construir();
    }

    public Banco construirComID() throws DadoInvalidoException {
        return new BancoBuilder().comID(id).comNome(nome).comSite(site).comCodigo(codigo).construir();
    }

    @Override
    public String toString() {
        return "BancoBV{" + "id=" + id + ", nome=" + nome + ", site=" + site + ", codigo=" + codigo + '}';
    }
}
