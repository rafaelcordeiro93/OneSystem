package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Comissao;
import br.com.onesystem.domain.builder.ComissaoBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.BuilderView;
import java.io.Serializable;
import java.math.BigDecimal;

public class ComissaoBV implements Serializable,BuilderView<Comissao> {

    private Long id;
    private String nome;   
    private BigDecimal comissaoVendedor;
    private BigDecimal comissaoRepresentante;

    public ComissaoBV(Comissao comissaoSelecionada) {
        this.id = comissaoSelecionada.getId();
        this.nome = comissaoSelecionada.getNome();
        this.comissaoVendedor = comissaoSelecionada.getComissaoVendedor();
        this.comissaoRepresentante = comissaoSelecionada.getComissaoRepresentante();
    }

    public ComissaoBV() {
    }

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

    public BigDecimal getComissaoVendedor() {
        return comissaoVendedor;
    }

    public void setComissaoVendedor(BigDecimal comissaoVendedor) {
        this.comissaoVendedor = comissaoVendedor;
    }

    public BigDecimal getComissaoRepresentante() {
        return comissaoRepresentante;
    }

    public void setComissaoRepresentante(BigDecimal comissaoRepresentante) {
        this.comissaoRepresentante = comissaoRepresentante;
    }


    public Comissao construir() throws DadoInvalidoException {
        return new ComissaoBuilder().comNome(nome).comComissaoVendedor(comissaoVendedor).comComissaoRepresentante(comissaoRepresentante).construir();
    }

    public Comissao construirComID() throws DadoInvalidoException {
        return new ComissaoBuilder().comID(id).comNome(nome).comComissaoVendedor(comissaoVendedor).comComissaoRepresentante(comissaoRepresentante).construir();
    }
}
