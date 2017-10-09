/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.valueobjects.EspecieDeNotaFiscal;
import br.com.onesystem.valueobjects.ModeloDeNotaFiscal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Rafael Cordeiro
 */
@Entity
@SequenceGenerator(initialValue = 1, allocationSize = 1, name = "SEQ_LOTENOTAFISCAL",
        sequenceName = "SEQ_LOTENOTAFISCAL")
public class LoteNotaFiscal implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_LOTENOTAFISCAL")
    private Long id;
    @NotNull(message = "{nome_not_null}")
    private String nome;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "{modelo_not_null}")
    private ModeloDeNotaFiscal modelo;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "{especie_not_null}")
    private EspecieDeNotaFiscal especie;
    @Column(nullable = false)
    private Long serie;
    @Column(nullable = true)
    private Date dataDeInicio;
    @Column(nullable = true)
    private Date dataDeFim;
    @Column(nullable = true)
    private String observacao;
    @OneToMany(mappedBy = "loteNotaFiscal", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<NumeracaoDeNotaFiscal> numeracaoDeNotaFiscal;
    @OneToMany(mappedBy = "loteNotaFiscal")
    private List<Operacao> operacao;

    public LoteNotaFiscal() {
    }

    public LoteNotaFiscal(Long id, String nome, ModeloDeNotaFiscal modelo, EspecieDeNotaFiscal especie,
            Long serie, Date dataDeInicio, Date dataDeFim, String observacao, List<NumeracaoDeNotaFiscal> numeracaoDeNotaFiscal) throws DadoInvalidoException {
        this.id = id;
        this.nome = nome;
        this.modelo = modelo;
        this.especie = especie;
        this.serie = serie;
        this.dataDeFim = dataDeFim;
        this.dataDeInicio = dataDeInicio;
        this.observacao = observacao;
        this.numeracaoDeNotaFiscal = numeracaoDeNotaFiscal;
    }

    public void adiciona(NumeracaoDeNotaFiscal n) {
        if (numeracaoDeNotaFiscal == null) {
            numeracaoDeNotaFiscal = new ArrayList<>();
        }
        n.setLoteNotaFiscal(this);
        numeracaoDeNotaFiscal.add(n);
    }

    public void atualiza(NumeracaoDeNotaFiscal n) {
        if (numeracaoDeNotaFiscal.contains(n)) {
            numeracaoDeNotaFiscal.set(numeracaoDeNotaFiscal.indexOf(n), n);
        } else {
            n.setLoteNotaFiscal(this);
            numeracaoDeNotaFiscal.add(n);
        }
    }

    public void remove(NumeracaoDeNotaFiscal n) {
        numeracaoDeNotaFiscal.remove(n);
    }

    public void atualizaNumeracao(Filial filial) {
        for (NumeracaoDeNotaFiscal nnf : numeracaoDeNotaFiscal) {
            if (nnf.getFilial() == filial) {
                nnf.interarNumeracao();
            }
        }
    }

    private void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("nome", "dataDeInicio", "observacao", "serie");
        new ValidadorDeCampos<LoteNotaFiscal>().valida(this, campos);
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public ModeloDeNotaFiscal getModelo() {
        return modelo;
    }

    public EspecieDeNotaFiscal getEspecie() {
        return especie;
    }

    public Long getSerie() {
        return serie;
    }

    public Date getDataDeInicio() {
        return dataDeInicio;
    }

    public Date getDataDeFim() {
        return dataDeFim;
    }

    public String getObservacao() {
        return observacao;
    }

    public List<Operacao> getOperacao() {
        return operacao;
    }

    public List<NumeracaoDeNotaFiscal> getNumeracaoDeNotaFiscal() {
        return numeracaoDeNotaFiscal;
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof LoteNotaFiscal)) {
            return false;
        }
        LoteNotaFiscal outro = (LoteNotaFiscal) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

    @Override
    public String toString() {
        return "LoteNotaFiscal{" + "id=" + id + ", nome=" + nome + ", modelo=" + modelo + ", especie=" + especie + ", serie=" + serie + ", dataDeInicio=" + dataDeInicio + ", dataDeFim=" + dataDeFim + '}';
    }

}
