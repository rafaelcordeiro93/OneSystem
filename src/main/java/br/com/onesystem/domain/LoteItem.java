/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.ValidadorDeCampos;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author Rafael Cordeiro
 */
@Entity
@SequenceGenerator(initialValue = 1, allocationSize = 1, name = "SEQ_LOTEITEM",
        sequenceName = "SEQ_LOTEITEM")
public class LoteItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_LOTEITEM")
    private Long id;
    @NotNull(message = "{validade_not_null}")
    @Column(nullable = false)
    private Date dataDeValidade;
    @NotNull(message = "{fabricacao_not_null}")
    @Column(nullable = false)
    private Date dataDeFabricacao;
    @NotNull(message = "{lote_not_null}")
    private String lote;
    @Column(nullable = false)
    private boolean ativo;
    @Length(max = 255, min = 0, message = "{observacao_length}")
    @Column(nullable = true)
    private String observacao;
    @ManyToOne
    private Item item;
    @OneToMany(mappedBy = "loteItem")
    private List<SaldoDeEstoque> saldoDeEstoque;

    public LoteItem() {
    }

    public LoteItem(Long id, Date dataDeValidade, Date dataDeFabricacao,
            String lote, boolean ativo, String observacao, Item item) throws DadoInvalidoException {
        this.id = id;
        this.lote = lote;
        this.ativo = ativo;
        this.dataDeFabricacao = dataDeFabricacao;
        this.dataDeValidade = dataDeValidade;
        this.observacao = observacao;
        this.item = item;
        ehValido();
    }

    private void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("lote", "dataDeFabricacao", "dataDeValidade", "observacao");
        new ValidadorDeCampos<LoteItem>().valida(this, campos);
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Long getId() {
        return id;
    }

    public Date getDataDeValidade() {
        return dataDeValidade;
    }

    public Date getDataDeFabricacao() {
        return dataDeFabricacao;
    }

    public String getLote() {
        return lote;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public String getObservacao() {
        return observacao;
    }

    public Item getItem() {
        return item;
    }

    public String getFabricacaoFormatada() {
        if (dataDeFabricacao != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            return sdf.format(dataDeFabricacao);
        } else {
            return "";
        }
    }

    public String getValidadeFormatada() {
        if (dataDeValidade != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            return sdf.format(dataDeValidade);
        } else {
            return "";
        }
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof LoteItem)) {
            return false;
        }
        LoteItem outro = (LoteItem) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

    @Override
    public String toString() {
        return "LoteItem{" + "id=" + id + ", dataDeValidade=" + dataDeValidade + ", dataDeFabricacao=" + dataDeFabricacao + ", lote=" + lote + ", ativo=" + ativo + ", observacao=" + observacao + ", item=" + item + '}';
    }

}
