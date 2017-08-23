package br.com.onesystem.domain;

import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.exception.DadoInvalidoException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@SequenceGenerator(sequenceName = "SEQ_PARCELADEPEDIDO", initialValue = 1, allocationSize = 1,
        name = "SEQ_PARCELADEPEDIDO")
public class ParcelaDePedido implements Serializable {

    @Id
    @GeneratedValue(generator = "SEQ_PARCELADEPEDIDO", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private BigDecimal valor;

    @Temporal(TemporalType.TIMESTAMP)
    private Date vencimento;

    @ManyToOne
    private Pedido pedido;

    public ParcelaDePedido() {
    }

    public ParcelaDePedido(Long id, BigDecimal valor, Date vencimento, Pedido pedido) throws DadoInvalidoException {
        this.id = id;
        this.valor = valor;
        this.vencimento = vencimento;
        this.pedido = pedido;
        ehValido();
    }

    private void ehValido() throws DadoInvalidoException {
        List<String> camposTitulo = Arrays.asList("valor");
        new ValidadorDeCampos<>().valida(this, camposTitulo);
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Date getVencimento() {
        return vencimento;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public String getValorFormatado() {
        NumberFormat numeroFormatado = NumberFormat.getCurrencyInstance();
        return numeroFormatado.format(getValor());
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
        final ParcelaDePedido other = (ParcelaDePedido) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ParcelaDePedido{" + "id=" + id + ", valor=" + valor + ", vencimento=" + vencimento + ", pedido=" + pedido + '}';
    }

}
