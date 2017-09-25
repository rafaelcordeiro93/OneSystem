package br.com.onesystem.domain;

import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.MoedaFormatter;
import br.com.onesystem.war.service.ConfiguracaoService;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "FATURA_CLASSE_NOME")
public abstract class Fatura implements Serializable {

    private static final long serialVersionUID = -1741096381813060074L;

    @Id
    @SequenceGenerator(initialValue = 1, allocationSize = 1, name = "SEQ_FATURA",
            sequenceName = "SEQ_FATURA")
    @GeneratedValue(generator = "SEQ_FATURA", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Length(max = 80, message = "{codigo_lenght}")
    @Column(nullable = true, length = 80)
    private String codigo;

    @Min(value = 0, message = "{total}")
    @Column(nullable = true)
    private BigDecimal total;

    @Min(value = 0, message = "{dinheiro}")
    @Column(nullable = true)
    private BigDecimal dinheiro;

    @Temporal(TemporalType.TIMESTAMP)
    private Date emissao;

    @NotNull(message = "{pessoa_not_null}")
    @ManyToOne
    private Pessoa pessoa;

    @OneToMany(mappedBy = "fatura", cascade = {CascadeType.ALL})
    private List<Titulo> titulo;

    @NotNull(message = "{filial_not_null}")
    @ManyToOne(optional = false)
    private Filial filial;
    
    @Inject
    private Configuracao cfg;

    public Fatura() {
    }

    public Fatura(Long id, String codigo, BigDecimal total, Date emissao,
            Pessoa pessoa, List<Titulo> titulo, BigDecimal dinheiro,
            Filial filial) throws DadoInvalidoException {
        this.id = id;
        this.codigo = codigo;
        this.total = total;
        this.emissao = emissao;
        this.pessoa = pessoa;
        this.titulo = titulo;
        this.dinheiro = dinheiro;
        this.filial = filial;
        ehValido();
    }

    public void adiciona(Titulo t) {
        if (titulo == null) {
            titulo = new ArrayList<>();
        }
        t.setFatura(this);
        titulo.add(t);
    }

    public void atualiza(Titulo t) {
        if (titulo.contains(t)) {
            titulo.set(titulo.indexOf(t), t);
        } else {
            t.setFatura(this);
            titulo.add(t);
        }
    }

    public void remove(Titulo t) {
        titulo.remove(t);
    }

    public Filial getFilial() {
        return filial;
    }

    private void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("codigo", "total", "emissao", "pessoa");
        new ValidadorDeCampos<Fatura>().valida(this, campos);
    }

    public Long getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public String getTotalFormatado() throws DadoInvalidoException {
        return MoedaFormatter.format(getMoedaPadrao(), getTotal());
    }

    public Date getEmissao() {
        return emissao;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public List<Titulo> getTitulo() {
        return titulo;
    }

    public Moeda getMoedaPadrao() throws EDadoInvalidoException {
        return cfg.getMoedaPadrao();
    }

    public BigDecimal getDinheiro() {
        return dinheiro;
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof Fatura)) {
            return false;
        }
        Fatura outro = (Fatura) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

    @Override
    public String toString() {
        return "Fatura{" + "id=" + id + ", codigo=" + codigo + ", total=" + total + ", dinheiro=" + dinheiro + ", emissao=" + emissao + ", pessoa=" + pessoa + ", titulo=" + titulo + '}';
    }

}
