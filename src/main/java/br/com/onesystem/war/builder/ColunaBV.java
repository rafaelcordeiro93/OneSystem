package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Coluna;
import br.com.onesystem.domain.ModeloDeRelatorio;
import br.com.onesystem.domain.builder.ColunaBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.TipoFormatacaoNumero;
import br.com.onesystem.valueobjects.Totalizador;
import java.io.Serializable;
import java.util.Objects;

public class ColunaBV implements Serializable {

    private Long id;
    private String nome;
    private String tabela;
    private Integer tamanho;
    private Class classeDeDeclaracao;
    private Class classeOriginal;
    private String propriedade;
    private String propriedadeDois;
    private String propriedadeTres;
    private String propriedadeQuatro;
    private Totalizador totalizador;
    private TipoFormatacaoNumero tipoFormatadorNumero;
    private ModeloDeRelatorio modeloDeRelatorio;

    public ColunaBV() {
    }
    
    public ColunaBV(Coluna c) {
        this.id = c.getId();
        this.nome = c.getNome();
        this.modeloDeRelatorio = c.getModelo();
        this.tabela = c.getTabela();
        this.tamanho = c.getTamanho();
        this.classeDeDeclaracao = c.getClasseDeDeclaracao();
        this.classeOriginal = c.getClasseOriginal();
        this.propriedade = c.getPropriedade();
        this.propriedadeDois = c.getPropriedadeDois();
        this.propriedadeTres = c.getPropriedadeTres();
        this.propriedadeQuatro = c.getPropriedadeQuatro();
        this.totalizador = c.getTotalizador();
        this.tipoFormatadorNumero = c.getTipoFormatadorNumero();

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

    public String getTabela() {
        return tabela;
    }

    public void setTabela(String tabela) {
        this.tabela = tabela;
    }

    public Integer getTamanho() {
        return tamanho;
    }

    public void setTamanho(Integer tamanho) {
        this.tamanho = tamanho;
    }

    public Class getClasseDeDeclaracao() {
        return classeDeDeclaracao;
    }

    public void setClasseDeDeclaracao(Class classeDeDeclaracao) {
        this.classeDeDeclaracao = classeDeDeclaracao;
    }

    public Class getClasseOriginal() {
        return classeOriginal;
    }

    public void setClasseOriginal(Class classeOriginal) {
        this.classeOriginal = classeOriginal;
    }

    public String getPropriedade() {
        return propriedade;
    }

    public void setPropriedade(String propriedade) {
        this.propriedade = propriedade;
    }

    public String getPropriedadeDois() {
        return propriedadeDois;
    }

    public void setPropriedadeDois(String propriedadeDois) {
        this.propriedadeDois = propriedadeDois;
    }

    public String getPropriedadeTres() {
        return propriedadeTres;
    }

    public void setPropriedadeTres(String propriedadeTres) {
        this.propriedadeTres = propriedadeTres;
    }

    public String getPropriedadeQuatro() {
        return propriedadeQuatro;
    }

    public void setPropriedadeQuatro(String propriedadeQuatro) {
        this.propriedadeQuatro = propriedadeQuatro;
    }

    public Totalizador getTotalizador() {
        return totalizador;
    }

    public void setTotalizador(Totalizador totalizador) {
        this.totalizador = totalizador;
    }

    public TipoFormatacaoNumero getTipoFormatadorNumero() {
        return tipoFormatadorNumero;
    }

    public void setTipoFormatadorNumero(TipoFormatacaoNumero tipoFormatadorNumero) {
        this.tipoFormatadorNumero = tipoFormatadorNumero;
    }

    public ModeloDeRelatorio getModeloDeRelatorio() {
        return modeloDeRelatorio;
    }

    public void setModeloDeRelatorio(ModeloDeRelatorio modeloDeRelatorio) {
        this.modeloDeRelatorio = modeloDeRelatorio;
    }

   
    public Coluna construir() {
        return new Coluna(nome, tabela, propriedade, propriedadeDois, propriedadeTres, propriedadeQuatro, classeDeDeclaracao, classeOriginal, tipoFormatadorNumero, totalizador, tamanho, modeloDeRelatorio);
    }

    public Coluna construirComID() throws DadoInvalidoException {
        return new Coluna(id, nome, tabela, propriedade, propriedadeDois, propriedadeTres, propriedadeQuatro, classeDeDeclaracao, classeOriginal, tipoFormatadorNumero, totalizador, tamanho, modeloDeRelatorio);
    }

}
