package br.com.onesystem.domain;

import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.exception.DadoInvalidoException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@SequenceGenerator(initialValue = 1, allocationSize = 1, name = "SEQ_CONTRATODECAMBIO",
        sequenceName = "SEQ_CONTRATODECAMBIO")
public class ContratoDeCambio implements Serializable {

    @Id
    @GeneratedValue(generator = "SEQ_CONTRATODECAMBIO", strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne
    private Pessoa pessoa;
    @NotNull(message = "{valorNegociado_not_null}")
    @Min(value = 0, message = "{valorNegociado_min}")
    @Column(nullable = false)
    private BigDecimal valorNegociado;
    @NotNull(message = "{taxaNegociada_not_null}")
    @Column(nullable = false)
    private BigDecimal taxaNegociada;
    @NotNull(message = "{valorCalculado_not_null}")
    @Column(nullable = false)
    private BigDecimal valorCalculado;
    private boolean status = false;
    @OneToOne(mappedBy = "contrato")
    private Cambio cambio;
    private Date emissao = Calendar.getInstance().getTime();
    @ManyToOne
    private Moeda origem;
    @ManyToOne
    private Moeda destino;

    public ContratoDeCambio() {
    }

    public ContratoDeCambio(Long id, Pessoa pessoa, BigDecimal valorNegociado,
            BigDecimal taxaNegociada, BigDecimal valorCalculado, boolean status,
            Moeda origem, Moeda destino) throws DadoInvalidoException {
        this.id = id;
        this.pessoa = pessoa;
        this.valorNegociado = valorNegociado;
        this.taxaNegociada = taxaNegociada;
        this.valorCalculado = valorCalculado;
        this.status = status;
        this.origem = origem;
        this.destino = destino;
        ehValido();
    }

    public Long getId() {
        return id;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public BigDecimal getValorNegociado() {
        return valorNegociado;
    }

    public BigDecimal getTaxaNegociada() {
        return taxaNegociada;
    }

    public BigDecimal getValorCalculado() {
        return valorCalculado;
    }

    public boolean isStatus() {
        return status;
    }

    public Date getEmissao() {
        return emissao;
    }

    public Cambio getCambio() {
        return cambio;
    }

    public Moeda getOrigem() {
        return origem;
    }

    public Moeda getDestino() {
        return destino;
    }
    
    public String getEmissaoFormatada() {
        SimpleDateFormat emissaoFormatada = new SimpleDateFormat("dd/MM/yyyy");
        return emissaoFormatada.format(getEmissao().getTime());
    }

    private void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("valorNegociado", "taxaNegociada", "valorCalculado");
        new ValidadorDeCampos<ContratoDeCambio>().valida(this, campos);
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof ContratoDeCambio)) {
            return false;
        }
        ContratoDeCambio outro = (ContratoDeCambio) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }
}
