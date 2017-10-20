package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.services.impl.MetodoInacessivelRelatorio;
import java.io.Serializable;
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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

@Entity
@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "SEQ_FILIAL",
        sequenceName = "SEQ_FILIAL")
public class Filial implements Serializable {

    @Id
    @GeneratedValue(generator = "SEQ_FILIAL", strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotNull(message = "{razao_social_not_null}")
    @Length(min = 2, max = 60, message = "{razao_social_lenght}")
    @Column(length = 60, nullable = false)
    private String razaoSocial;
    @NotNull(message = "{fantasia_not_null}")
    @Length(min = 2, max = 60, message = "{fantasia_lenght}")
    @Column(length = 60, nullable = false)
    private String fantasia;
    @NotNull(message = "{ruc_not_null}")
    @Length(min = 2, max = 60, message = "{ruc_lenght}")
    @Column(length = 60, nullable = false)
    private String ruc;
    @NotNull(message = "{endereco_not_null}")
    @Length(min = 2, max = 60, message = "{endereco_lenght}")
    @Column(length = 60, nullable = false)
    private String endereco;
    @Length(min = 0, max = 40, message = "{numero_lenght}")
    @Column(length = 40, nullable = true)
    private String numero;
    @NotNull(message = "{bairro_not_null}")
    @Length(min = 2, max = 60, message = "{bairro_lenght}")
    @Column(length = 60, nullable = false)
    private String bairro;
    @NotNull(message = "{cep_not_null}")
    @ManyToOne
    private Cep cep;
    @ManyToOne
    private Cidade cidade;
    @NotNull(message = "{telefone_not_null}")
    @Length(min = 2, max = 60, message = "{telefone_lenght}")
    @Column(length = 60, nullable = false)
    private String telefone;
    @Temporal(TemporalType.TIMESTAMP)
    private Date vencimento;
    @Column(nullable = true)
    private String serialKey;
    @org.hibernate.validator.constraints.Email(message = "{email_invalido}")
    @Column(nullable = true, length = 100)
    private String email;
    @Column(nullable = true, length = 60)
    private String contato;
    @ManyToMany(cascade = CascadeType.MERGE)
    private List<Deposito> depositos;

    public Filial() {
    }

    public Filial(Long id, String razaoSocial, String fantasia,
            String ruc, String endereco, String bairro, Cep cep,
            String telefone, Date vencimento, String serialKey, String numero,
            String email, String contato, Cidade cidade) throws DadoInvalidoException {
        this.id = id;
        this.razaoSocial = razaoSocial;
        this.fantasia = fantasia;
        this.ruc = ruc;
        this.cep = cep;
        this.endereco = endereco;
        this.bairro = bairro;
        this.telefone = telefone;
        this.email = email;
        this.contato = contato;
        this.vencimento = vencimento;
        this.serialKey = serialKey;
        this.numero = numero;
        this.cidade = cidade;
        ehValido();
    }

    @MetodoInacessivelRelatorio
    public String getCepCidadeEstadoPaisFormatado() {
        String str = "";
        Cidade cidade = null;
        if (cep != null) {
            str += cep;
            cidade = cep.getCidade();
        } else {
            cidade = getCidade();
        }
        if (cidade != null) {
            if (cidade.getNome() != null) {
                str += " - ";
            }
            str += cidade.getNome();
        }
        if (cidade != null && cidade.getEstado() != null) {
            if (cidade.getEstado().getSigla() != null) {
                str += " - ";
            }
            str += cidade.getEstado().getSigla();
        }
        if (cidade != null && cidade.getEstado() != null && cidade.getEstado().getPais() != null) {
            str += " - ";
            str += cidade.getEstado().getPais().getNome();
        }
        return str;
    }

    public void adiciona(Deposito deposito) {
        if (depositos == null) {
            this.depositos = new ArrayList<Deposito>();
        }
        if (deposito != null && depositos.indexOf(deposito) == -1) {
            this.depositos.add(deposito);
        }
    }

    public void remove(Deposito deposito) {
        if (depositos != null && depositos.indexOf(deposito) != -1) {
            this.depositos.remove(depositos.indexOf(deposito));
        }
    }

    public List<Deposito> getDepositos() {
        return depositos;
    }
    
    public Long getId() {
        return id;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public String getFantasia() {
        return fantasia;
    }

    public String getRuc() {
        return ruc;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getNumero() {
        return numero;
    }

    public String getBairro() {
        return bairro;
    }

    public Cep getCep() {
        return cep;
    }

    public String getEmail() {
        return email;
    }

    public String getContato() {
        return contato;
    }

    public String getTelefone() {
        return telefone;
    }

    public Date getVencimento() {
        return vencimento;
    }

    public String getSerialKey() {
        return serialKey;
    }

    public String getRazaoSocialRuc() {
        return razaoSocial + " - Ruc: " + ruc;
    }

    @MetodoInacessivelRelatorio
    public String getNomeEstado() {
        if (cep != null) {
            return cep.getCidade().getEstado().getNome();
        } else {
            return cidade.getEstado().getNome();
        }
    }

    @MetodoInacessivelRelatorio
    public String getNomeCidade() {
        if (cep != null) {
            return cep.getCidade().getNome();
        } else {
            return cidade.getNome();
        }
    }

    public Cidade getCidade() {
        return cidade;
    }

    @MetodoInacessivelRelatorio
    public String getEnderecoNumeroBairroFormatado() {
        String str = "";
        if (endereco != null) {
            str += endereco;
        }
        if (numero != null) {
            if (endereco != null) {
                str += ", ";
            }
            str += "Nº " + numero;
        }
        if (bairro != null) {
            if (endereco != null || numero != null) {
                str += " - ";
            }
            str += bairro;
        }

        return str;
    }

    @MetodoInacessivelRelatorio
    public String getTelefoneEmailFormatado() {
        String str = "";
        if (telefone != null) {
            str += telefone;
        }
        if (email != null) {
            if (telefone != null) {
                str += ", ";
            }
            str += email;
        }
        return str;
    }

    public final void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("numero", "cep", "razaoSocial", "bairro", "telefone", "ruc", "fantasia");
        new ValidadorDeCampos<Filial>().valida(this, campos);
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof Filial)) {
            return false;
        }
        Filial outro = (Filial) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

    @Override
    public String toString() {
        return "Filial{" + "id=" + id + ", razaoSocial=" + razaoSocial + ", fantasia=" + fantasia + ", ruc=" + ruc + ", endereco=" + endereco + ", numero=" + numero + ", bairro=" + bairro + ", cep=" + cep + ", telefone=" + telefone + ", vencimento=" + vencimento + ", serialKey=" + serialKey + ", email=" + email + ", contato=" + contato + '}';
    }

}
