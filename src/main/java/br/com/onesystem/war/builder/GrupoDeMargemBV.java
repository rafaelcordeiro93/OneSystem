package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Margem;
import br.com.onesystem.domain.builder.GrupoBuilder;
import br.com.onesystem.domain.builder.GrupoDeMargemBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.BuilderView;
import java.io.Serializable;
import java.math.BigDecimal;

public class GrupoDeMargemBV implements Serializable, BuilderView<Margem> {
    
    private Long id;
    private String nome;    
    private BigDecimal margem;
    private BigDecimal custoFixo;
    private BigDecimal frete;
    private BigDecimal outrosCustos;
    private BigDecimal embalagem;
    
    public GrupoDeMargemBV(Margem grupoDeMargem) {
        this.id = grupoDeMargem.getId();
        this.nome = grupoDeMargem.getNome();
        this.margem = grupoDeMargem.getMargem();
        this.custoFixo = grupoDeMargem.getCustoFixo();
        this.frete = grupoDeMargem.getFrete();
        this.outrosCustos = grupoDeMargem.getOutrosCustos();
        this.embalagem = grupoDeMargem.getEmbalagem();
    }
    
    public GrupoDeMargemBV() {
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
    
    public BigDecimal getMargem() {
        return margem;
    }
    
    public void setMargem(BigDecimal margem) {
        this.margem = margem;
    }
    
    public BigDecimal getCustoFixo() {
        return custoFixo;
    }
    
    public void setCustoFixo(BigDecimal custoFixo) {
        this.custoFixo = custoFixo;
    }
    
    public BigDecimal getFrete() {
        return frete;
    }
    
    public void setFrete(BigDecimal frete) {
        this.frete = frete;
    }
    
    public BigDecimal getOutrosCustos() {
        return outrosCustos;
    }
    
    public void setOutrosCustos(BigDecimal outrosCustos) {
        this.outrosCustos = outrosCustos;
    }
    
    public BigDecimal getEmbalagem() {
        return embalagem;
    }
    
    public void setEmbalagem(BigDecimal embalagem) {
        this.embalagem = embalagem;
    }
    
    public Margem construir() throws DadoInvalidoException {
        return new GrupoDeMargemBuilder().comNome(nome).comMargem(margem)
                .comCustoFixo(custoFixo).comEmbalagem(embalagem).comFrete(frete)
                .comOutrosCustos(outrosCustos).construir();
    }
    
    public Margem construirComID() throws DadoInvalidoException {
        return new GrupoDeMargemBuilder().comID(id).comNome(nome).comMargem(margem)
                .comCustoFixo(custoFixo).comEmbalagem(embalagem).comFrete(frete)
                .comOutrosCustos(outrosCustos).construir();
    }
}
