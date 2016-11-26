package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.ValidadorDeCampos;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@SequenceGenerator(initialValue = 1, allocationSize = 1, name = "SEQ_TRANSFERENCIA",
        sequenceName = "SEQ_TRANSFERENCIA")
public class Transferencia implements Serializable {

    @Id
    @GeneratedValue(generator = "SEQ_TRANSFERENCIA", strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull(message = "{origem_not_null}")
    @ManyToOne(optional = false)
    private Conta origem;

    @NotNull(message = "{destino_not_null}")
    @ManyToOne(optional = false)
    private Conta destino;

    @NotNull(message = "{valor_not_null}")
    @Min(value = 0, message = "{valor_min}")
    private BigDecimal valor;
    
    @Min(value = 0, message = "{valorConvertido_min}")
    private BigDecimal valorConvertido;

    @OneToMany(mappedBy = "transferencia")
    private List<Baixa> baixas;
        
    public Transferencia() {
    }
    
    public Transferencia(Long id, Conta origem, Conta destino, BigDecimal valor, BigDecimal valorConvertido) throws DadoInvalidoException {
        this.id = id;
        this.origem = origem;
        this.destino = destino;
        this.valor = valor;
        this.valorConvertido = valorConvertido;
        ehValido();
    }

    public Long getId() {
        return id;
    }

    public Conta getOrigem() {
        return origem;
    }

    public Conta getDestino() {
        return destino;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public List<Baixa> getBaixas() {
        return baixas;
    }

    public void setBaixas(List<Baixa> baixas) {
        this.baixas = baixas;
    }

    public BigDecimal getValorConvertido() {
        return valorConvertido;
    }
    
    private void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("origem", "destino", "valor", "valorConvertido");
        new ValidadorDeCampos<Transferencia>().valida(this, campos);
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof Transferencia)) {
            return false;
        }
        Transferencia outro = (Transferencia) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

}
