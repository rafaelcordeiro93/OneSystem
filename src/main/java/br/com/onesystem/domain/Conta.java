package br.com.onesystem.domain;

import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.exception.DadoInvalidoException;
import java.io.Serializable;
import java.util.Arrays;
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

@Entity
@SequenceGenerator(initialValue = 1, allocationSize = 1, name = "SEQ_CONTA",
        sequenceName = "SEQ_CONTA")
public class Conta implements Serializable {

    @Id
    @GeneratedValue(generator = "SEQ_CONTA", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, length = 80)
    @NotNull(message = "{nome_not_null}")
    @Length(min = 4, max = 80, message = "{nome_lenght}")
    private String nome;

    @ManyToOne(optional = true)
    private Banco banco;

    @OneToMany(mappedBy = "origem")
    private List<Transferencia> origemDeTransferencias;

    @OneToMany(mappedBy = "destino")
    private List<Transferencia> destinoDeTransferencias;
    
    @OneToMany(mappedBy = "conta")
    private List<TipoDeCobranca> tipoDeCobrancas;

    @NotNull(message = "moeda_not_null")
    @ManyToOne(optional = false)
    private Moeda moeda;
    @OneToMany(mappedBy = "conta")
    private List<Cotacao> cotacoes;

    public Conta() {
    }

    public Conta(Long id, String nome, Banco banco, Moeda moeda) throws DadoInvalidoException {
        this.id = id;
        this.nome = nome;
        this.banco = banco;
        this.moeda = moeda;
        ehValido();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Banco getBanco() {
        return banco;
    }

    public List<Transferencia> getOrigemDeTransferencias() {
        return origemDeTransferencias;
    }

    public List<Transferencia> getDestinoDeTransferencias() {
        return destinoDeTransferencias;
    }

    public String getMoedaNomeESiglaMoeda() {
        return nome + " - " + moeda.getSigla();
    }

    public Moeda getMoeda() {
        return moeda;
    }

    private void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("nome", "moeda");
        new ValidadorDeCampos<Conta>().valida(this, campos);
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof Conta)) {
            return false;
        }
        Conta outro = (Conta) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

    @Override
    public String toString() {
        return "Conta{" + "id=" + id + ", nome=" + nome + ", banco=" + banco + ", origemDeTransferencias=" + origemDeTransferencias + ", destinoDeTransferencias=" + destinoDeTransferencias + ", moeda=" + moeda + ", cotacoes=" + cotacoes + '}';
    }

}
