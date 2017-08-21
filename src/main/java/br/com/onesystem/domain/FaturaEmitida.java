package br.com.onesystem.domain;

import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.war.service.ConfiguracaoService;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
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
@SequenceGenerator(initialValue = 1, allocationSize = 1, name = "SEQ_FATURAEMITIDA",
        sequenceName = "SEQ_FATURAEMITIDA")
public class FaturaEmitida implements Serializable {

    private static final long serialVersionUID = -1741096381813060074L;

    @Id
    @GeneratedValue(generator = "SEQ_FATURAEMITIDA", strategy = GenerationType.SEQUENCE)
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

    @OneToMany(mappedBy = "faturaEmitida", cascade = {CascadeType.ALL})
    private List<Titulo> titulo;

    @OneToMany(mappedBy = "faturaEmitida", cascade = {CascadeType.ALL})
    private List<NotaEmitida> notaEmitida;

    @OneToMany(mappedBy = "faturaEmitida", cascade = {CascadeType.ALL})
    private List<ValorPorCotacao> valorPorCotacao;

    public FaturaEmitida() {
    }

    public FaturaEmitida(Long id, String codigo, BigDecimal total, Date emissao, Pessoa pessoa, List<Titulo> titulo, List<NotaEmitida> notaEmitida, List<ValorPorCotacao> valorPorCotacao, BigDecimal dinheiro) throws DadoInvalidoException {
        this.id = id;
        this.codigo = codigo;
        this.total = total;
        this.emissao = emissao;
        this.pessoa = pessoa;
        this.titulo = titulo;
        this.notaEmitida = notaEmitida;
        this.valorPorCotacao = valorPorCotacao;
        this.dinheiro = dinheiro;
        ehValido();
    }

    public void adiciona(Titulo t) {
        if (titulo == null) {
            titulo = new ArrayList<>();
        }
        t.setFaturaEmitida(this);
        titulo.add(t);
    }

    public void atualiza(Titulo t) {
        if (titulo.contains(t)) {
            titulo.set(titulo.indexOf(t), t);
        } else {
            t.setFaturaEmitida(this);
            titulo.add(t);
        }
    }

    public void remove(Titulo t) {
        titulo.remove(t);
    }

    public void adiciona(NotaEmitida n) {
        if (notaEmitida == null) {
            notaEmitida = new ArrayList<>();
        }
        n.setFaturaEmitida(this);
        notaEmitida.add(n);
    }

    public void atualiza(NotaEmitida n) {
        if (notaEmitida.contains(n)) {
            notaEmitida.set(notaEmitida.indexOf(n), n);
        } else {
            n.setFaturaEmitida(this);
            notaEmitida.add(n);
        }
    }

    public void remove(NotaEmitida n) {
        notaEmitida.remove(n);
    }

    public void adiciona(ValorPorCotacao v) {
        try {
            if (valorPorCotacao == null) {
                valorPorCotacao = new ArrayList<>();
            }
            v.geraBaixaPor(this);//set a Fatura dentro do ValorPorCotacao e Gera as Baixas
            valorPorCotacao.add(v);
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void atualiza(ValorPorCotacao v) {
        try {
            if (valorPorCotacao.contains(v)) {
                valorPorCotacao.set(valorPorCotacao.indexOf(v), v);
            } else {
                v.geraBaixaPor(this);
                valorPorCotacao.add(v);
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }
//    public void atualiza(ValorPorCotacao valor) {
//        try {
//            for (ValorPorCotacao v : valorPorCotacao) {
//                if (v.getCotacao().getConta().getMoeda().equals(v.getCotacao().getConta().getMoeda())) {
//                    v.atualizaValor(valor.getValor());
//                    v.atualizaValorDeBaixa(valor.getValor());
//                } else {
//                    v.geraBaixaPor(this);
//                    valorPorCotacao.add(v);
//                }
//            }
//        } catch (DadoInvalidoException die) {
//            die.print();
//        }
//    }

    public void remove(ValorPorCotacao v) {
        valorPorCotacao.remove(v);
    }

    private void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("codigo", "total", "emissao", "pessoa");
        new ValidadorDeCampos<FaturaEmitida>().valida(this, campos);
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

    public List<Titulo> getTitulo() {
        return titulo;
    }

    public List<NotaEmitida> getNotaEmitida() {
        return notaEmitida;
    }

    public Moeda getMoedaPadrao() throws EDadoInvalidoException {
        Configuracao cfg = new ConfiguracaoService().buscar();
        return cfg.getMoedaPadrao();
    }

    public BigDecimal getDinheiro() {
        return dinheiro;
    }

    public List<ValorPorCotacao> getValorPorCotacao() {
        return valorPorCotacao;
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof FaturaEmitida)) {
            return false;
        }
        FaturaEmitida outro = (FaturaEmitida) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

    @Override
    public String toString() {
        return "FaturaEmitida{" + "id=" + id + ", codigo=" + codigo + ", total=" + total + ", dinheiro=" + dinheiro + ", emissao=" + emissao + ", pessoa=" + pessoa + ", titulo=" + titulo + ", notaEmitida=" + notaEmitida + ", valorPorCotacao=" + valorPorCotacao + '}';
    }

}
