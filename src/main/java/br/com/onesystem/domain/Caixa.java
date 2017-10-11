package br.com.onesystem.domain;

import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.DateUtil;
import br.com.onesystem.util.MoedaFormatter;
import br.com.onesystem.util.Money;
import br.com.onesystem.war.service.ConfiguracaoService;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javafx.scene.input.DataFormat;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

@Entity
@SequenceGenerator(initialValue = 1, allocationSize = 1, name = "SEQ_CAIXA",
        sequenceName = "SEQ_CAIXA")
public class Caixa implements Serializable {

    private static final long serialVersionUID = -1741096381813060074L;

    @Id
    @GeneratedValue(generator = "SEQ_CAIXA", strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull(message = "{valor_not_null}")
    @Column(nullable = false)
    private BigDecimal saldo;

    @Length(max = 250, message = "{descricao_length}")
    private String descricao;

    @Temporal(TemporalType.TIMESTAMP)
    private Date abertura = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechamento;

    @ManyToOne
    private Usuario usuario;

    @ManyToMany
    private List<Cotacao> cotacao;

    public Caixa() {
    }

    public Caixa(Long id, BigDecimal saldo, Usuario usuario, List<Cotacao> cotacao, String descricao) throws DadoInvalidoException {
        this.id = id;
        this.usuario = usuario;
        this.abertura = new Date();
        this.saldo = saldo;
        this.cotacao = cotacao;
        this.descricao = descricao;
        ehValido();
    }

    public void fecharCaixa() throws EDadoInvalidoException {
        this.fechamento = new Date();
    }

    public Long getId() {
        return id;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Date getAbertura() {
        return abertura;
    }

    public Date getFechamento() {
        return fechamento;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getAberturaFormatado() {
        DateTimeFormatter formatadorBarra = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return getAbertura().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(formatadorBarra).toString();
    }

    public String getFechamentoFormatado() {
        if (getFechamento() != null) {
            DateTimeFormatter formatadorBarra = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return getFechamento().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(formatadorBarra).toString();
        } else {
            return null;
        }
    }

    public List<Cotacao> getCotacao() {
        return cotacao;
    }

    private void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("usuario", "abertura", "fechamento", "saldo", "cotacao", "descricao");
        new ValidadorDeCampos<Caixa>().valida(this, campos);
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof Caixa)) {
            return false;
        }
        Caixa outro = (Caixa) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

    @Override
    public String toString() {
        return "Caixa{" + "id=" + id + ", usuario=" + usuario + ", abertura=" + abertura + ", fechamento=" + fechamento + ", saldo=" + saldo + ", cotacao=" + cotacao + '}';
    }

}
