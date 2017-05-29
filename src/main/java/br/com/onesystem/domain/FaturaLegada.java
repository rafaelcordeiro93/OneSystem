package br.com.onesystem.domain;

import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.war.service.ConfiguracaoService;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

@Entity
@SequenceGenerator(initialValue = 1, allocationSize = 1, name = "SEQ_FATURALEGADA",
        sequenceName = "SEQ_FATURALEGADA")
public class FaturaLegada implements Serializable {

    private static final long serialVersionUID = -1741096381813060074L;

    @Id
    @GeneratedValue(generator = "SEQ_FATURALEGADA", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Length(max = 80, message = "{codigo_lenght}")
    @Column(nullable = true, length = 80)
    private String codigo;

    @Min(value = 0, message = "{total}")
    @Column(nullable = true)
    private BigDecimal total;

    @Temporal(TemporalType.TIMESTAMP)
    private Date emissao;

    @NotNull(message = "{pessoa_not_null}")
    @ManyToOne
    private Pessoa pessoa;

    @OneToMany(mappedBy = "faturaLegada", cascade = {CascadeType.ALL})
    private List<Cobranca> cobranca;

    public FaturaLegada() {
    }

    public FaturaLegada(Long id, String codigo, BigDecimal total, Date emissao, Pessoa pessoa, List<Cobranca> cobranca) throws DadoInvalidoException {
        this.id = id;
        this.codigo = codigo;
        this.total = total;
        this.emissao = emissao;
        this.pessoa = pessoa;
        this.cobranca = cobranca;
        ehValido();
    }

    private void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("codigo", "total", "emissao", "pessoa", "cobranca");
        new ValidadorDeCampos<FaturaLegada>().valida(this, campos);
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

    public Date getEmissao() {
        return emissao;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public List<Cobranca> getCobranca() {
        return cobranca;
    }

    public Moeda getMoedaPadrao() throws EDadoInvalidoException {
        Configuracao cfg = new ConfiguracaoService().buscar();
        return cfg.getMoedaPadrao();
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof FaturaLegada)) {
            return false;
        }
        FaturaLegada outro = (FaturaLegada) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

    @Override
    public String toString() {
        return "FaturaLegada{" + "id=" + id + ", codigo=" + codigo + ", emissao=" + emissao + ", pessoa=" + pessoa + ", cobranca=" + cobranca + '}';
    }

}
