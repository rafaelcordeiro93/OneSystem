/*
 * To change this license nome, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import br.com.onesystem.valueobjects.TipoFormatacaoNumero;
import br.com.onesystem.valueobjects.Totalizador;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "SEQ_COLUNA",
        sequenceName = "SEQ_COLUNA")
public class Coluna implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_COLUNA")
    private Long id;
    private String nome;
    private String tabela;
    private Integer tamanho = 20;
    private Class classeDeDeclaracao;
    private Class classeOriginal;
    private String propriedade;
    private String propriedadeDois;
    private String propriedadeTres;
    private String propriedadeQuatro;
    private Totalizador totalizador;
    private TipoFormatacaoNumero tipoFormatadorNumero;
    @ManyToOne
    private ModeloDeRelatorio modelo;

    public Coluna() {
    }

    public Coluna(String nome, String tabela, String propriedade, Class classeDeDeclaracao, Class classeOriginal) {
        this.nome = nome;
        this.tabela = tabela;
        this.propriedade = propriedade;
        this.classeDeDeclaracao = classeDeDeclaracao;
        this.classeOriginal = classeOriginal;
    }

    public Coluna(String nome, String tabela, String propriedade, Class classeDeDeclaracao, Class classeOriginal, TipoFormatacaoNumero tipoFormatadorNumero) {
        this.nome = nome;
        this.tabela = tabela;
        this.propriedade = propriedade;
        this.classeDeDeclaracao = classeDeDeclaracao;
        this.classeOriginal = classeOriginal;
        this.tipoFormatadorNumero = tipoFormatadorNumero;
    }

    public Coluna(String nome, String tabela, String propriedade, Class classeDeDeclaracao, Class classeOriginal, TipoFormatacaoNumero tipoFormatadorNumero, Totalizador totalizador) {
        this.nome = nome;
        this.tabela = tabela;
        this.propriedade = propriedade;
        this.classeDeDeclaracao = classeDeDeclaracao;
        this.classeOriginal = classeOriginal;
        this.totalizador = totalizador;
        this.tipoFormatadorNumero = tipoFormatadorNumero;
    }

    public Coluna(String nome, String tabela, String propriedade, String propriedadeDois, Class classeDeDeclaracao, Class classeOriginal) {
        this.nome = nome;
        this.tabela = tabela;
        this.propriedade = propriedade;
        this.propriedadeDois = propriedadeDois;
        this.classeDeDeclaracao = classeDeDeclaracao;
        this.classeOriginal = classeOriginal;
    }

    public Coluna(String nome, String tabela, String propriedade, String propriedadeDois, Class classeDeDeclaracao, Class classeOriginal, TipoFormatacaoNumero tipoFormatadorNumero) {
        this.nome = nome;
        this.tabela = tabela;
        this.propriedade = propriedade;
        this.propriedadeDois = propriedadeDois;
        this.classeDeDeclaracao = classeDeDeclaracao;
        this.classeOriginal = classeOriginal;
        this.tipoFormatadorNumero = tipoFormatadorNumero;
    }

    public Coluna(String nome, String tabela, String propriedade, String propriedadeDois, Class classeDeDeclaracao, Class classeOriginal, TipoFormatacaoNumero tipoFormatadorNumero, Totalizador totalizador) {
        this.nome = nome;
        this.tabela = tabela;
        this.propriedade = propriedade;
        this.propriedadeDois = propriedadeDois;
        this.classeDeDeclaracao = classeDeDeclaracao;
        this.classeOriginal = classeOriginal;
        this.totalizador = totalizador;
        this.tipoFormatadorNumero = tipoFormatadorNumero;
    }

    public Coluna(String nome, String tabela, String propriedade, String propriedadeDois, String propriedadeTres, Class classeDeDeclaracao, Class classeOriginal) {
        this.nome = nome;
        this.tabela = tabela;
        this.propriedade = propriedade;
        this.propriedadeDois = propriedadeDois;
        this.propriedadeTres = propriedadeTres;
        this.classeDeDeclaracao = classeDeDeclaracao;
        this.classeOriginal = classeOriginal;
    }

    public Coluna(String nome, String tabela, String propriedade, String propriedadeDois, String propriedadeTres, Class classeDeDeclaracao, Class classeOriginal, TipoFormatacaoNumero tipoFormatadorNumero) {
        this.nome = nome;
        this.tabela = tabela;
        this.propriedade = propriedade;
        this.propriedadeDois = propriedadeDois;
        this.propriedadeTres = propriedadeTres;
        this.classeDeDeclaracao = classeDeDeclaracao;
        this.classeOriginal = classeOriginal;
        this.tipoFormatadorNumero = tipoFormatadorNumero;
    }

    public Coluna(String nome, String tabela, String propriedade, String propriedadeDois, String propriedadeTres, Class classeDeDeclaracao, Class classeOriginal, TipoFormatacaoNumero tipoFormatadorNumero, Totalizador totalizador) {
        this.nome = nome;
        this.tabela = tabela;
        this.propriedade = propriedade;
        this.propriedadeDois = propriedadeDois;
        this.propriedadeTres = propriedadeTres;
        this.classeDeDeclaracao = classeDeDeclaracao;
        this.classeOriginal = classeOriginal;
        this.totalizador = totalizador;
        this.tipoFormatadorNumero = tipoFormatadorNumero;
    }

    public Coluna(String nome, String tabela, String propriedade, String propriedadeDois, String propriedadeTres, String propriedadeQuatro, Class classeDeDeclaracao, Class classeOriginal) {
        this.nome = nome;
        this.tabela = tabela;
        this.propriedade = propriedade;
        this.propriedadeDois = propriedadeDois;
        this.propriedadeTres = propriedadeTres;
        this.propriedadeQuatro = propriedadeQuatro;
        this.classeDeDeclaracao = classeDeDeclaracao;
        this.classeOriginal = classeOriginal;
    }

    public Coluna(String nome, String tabela, String propriedade, String propriedadeDois, String propriedadeTres, String propriedadeQuatro, Class classeDeDeclaracao, Class classeOriginal, TipoFormatacaoNumero tipoFormatadorNumero) {
        this.nome = nome;
        this.tabela = tabela;
        this.propriedade = propriedade;
        this.propriedadeDois = propriedadeDois;
        this.propriedadeTres = propriedadeTres;
        this.propriedadeQuatro = propriedadeQuatro;
        this.classeDeDeclaracao = classeDeDeclaracao;
        this.classeOriginal = classeOriginal;
        this.tipoFormatadorNumero = tipoFormatadorNumero;
    }

    public Coluna(String nome, String tabela, String propriedade, String propriedadeDois, String propriedadeTres, String propriedadeQuatro, Class classeDeDeclaracao, Class classeOriginal, TipoFormatacaoNumero tipoFormatadorNumero, Totalizador totalizador) {
        this.nome = nome;
        this.tabela = tabela;
        this.propriedade = propriedade;
        this.propriedadeDois = propriedadeDois;
        this.propriedadeTres = propriedadeTres;
        this.propriedadeQuatro = propriedadeQuatro;
        this.classeDeDeclaracao = classeDeDeclaracao;
        this.classeOriginal = classeOriginal;
        this.totalizador = totalizador;
        this.tipoFormatadorNumero = tipoFormatadorNumero;
    }

    public Coluna(String nome, String tabela, String propriedade, String propriedadeDois, String propriedadeTres, String propriedadeQuatro, Class classeDeDeclaracao, Class classeOriginal, TipoFormatacaoNumero tipoFormatadorNumero, Totalizador totalizador, Integer tamanho, ModeloDeRelatorio modeloDeRelatorio) {
        this.nome = nome;
        this.tabela = tabela;
        this.propriedade = propriedade;
        this.propriedadeDois = propriedadeDois;
        this.propriedadeTres = propriedadeTres;
        this.propriedadeQuatro = propriedadeQuatro;
        this.classeDeDeclaracao = classeDeDeclaracao;
        this.classeOriginal = classeOriginal;
        this.totalizador = totalizador;
        this.tipoFormatadorNumero = tipoFormatadorNumero;
        this.tamanho = tamanho;
        this.modelo = modeloDeRelatorio;
    }

    public Coluna(Long id, String nome, String tabela, String propriedade, String propriedadeDois, String propriedadeTres, String propriedadeQuatro, Class classeDeDeclaracao, Class classeOriginal, TipoFormatacaoNumero tipoFormatadorNumero, Totalizador totalizador, Integer tamanho, ModeloDeRelatorio modeloDeRelatorio) {
        this.id = id;
        this.nome = nome;
        this.tabela = tabela;
        this.propriedade = propriedade;
        this.propriedadeDois = propriedadeDois;
        this.propriedadeTres = propriedadeTres;
        this.propriedadeQuatro = propriedadeQuatro;
        this.classeDeDeclaracao = classeDeDeclaracao;
        this.classeOriginal = classeOriginal;
        this.totalizador = totalizador;
        this.tipoFormatadorNumero = tipoFormatadorNumero;
        this.tamanho = tamanho;
        this.modelo = modeloDeRelatorio;
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
    
    public String getTamanhoFormatado(){
        return tamanho + "px";
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

    public ModeloDeRelatorio getModelo() {
        return modelo;
    }

    public void setModelo(ModeloDeRelatorio modelo) {
        this.modelo = modelo;
    }
    
    public String getUltimaPropriedade() {
        String ultimaPropriedade = propriedade;
        if (propriedadeDois != null) {
            ultimaPropriedade = propriedadeDois;
        }
        if (propriedadeTres != null) {
            ultimaPropriedade = propriedadeTres;
        }
        if (propriedadeQuatro != null) {
            ultimaPropriedade = propriedadeQuatro;
        }
        return ultimaPropriedade;
    }

    public String getPropriedadeCompleta() {
        String propriedadeCompleta = propriedade;
        if (propriedadeDois != null) {
            propriedadeCompleta += "." + propriedadeDois;
        }
        if (propriedadeTres != null) {
            propriedadeCompleta += "." + propriedadeTres;
        }
        if (propriedadeQuatro != null) {
            propriedadeCompleta += "." + propriedadeQuatro;
        }
        return propriedadeCompleta;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Coluna other = (Coluna) obj;
        if (!Objects.equals(this.getPropriedadeCompleta(), other.getPropriedadeCompleta())) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Coluna{" + "id=" + id + ", nome=" + nome + ", tabela=" + tabela + ", tamanho=" + tamanho + ", classeDeDeclaracao=" + classeDeDeclaracao + ", classeOriginal=" + classeOriginal + ", propriedade=" + propriedade + ", propriedadeDois=" + propriedadeDois + ", propriedadeTres=" + propriedadeTres + ", propriedadeQuatro=" + propriedadeQuatro + ", totalizador=" + totalizador + ", tipoFormatadorNumero=" + tipoFormatadorNumero + ", modelo=" + modelo + '}';
    }

}
