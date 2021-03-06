package br.com.onesystem.domain;

import br.com.onesystem.valueobjects.CaseType;
import br.com.onesystem.valueobjects.TipoPessoa;
import br.com.onesystem.services.CharacterType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
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
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import br.com.onesystem.services.impl.MetodoInacessivelRelatorio;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "PESSOA_CLASSE_NOME")
public abstract class Pessoa implements Serializable {

    @Id
    @SequenceGenerator(name = "SEQ_PESSOA", sequenceName = "SEQ_PESSOA",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "SEQ_PESSOA", strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(nullable = false, length = 80)
    @NotNull(message = "{nome_not_null}")
    @CharacterType(value = CaseType.LETTER_SPACE, message = "{nome_type_letter_space}")
    @Length(min = 4, max = 80, message = "{nome_lenght}")
    private String nome;
    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Contato> contatos;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoPessoa tipo;
    @Column(nullable = true)
    private String ruc;
    @Length(min = 10, max = 16, message = "{telefono_length}")
    @Column(nullable = true, length = 20)
    private String telefone;
    @org.hibernate.validator.constraints.Email(message = "{email_invalido}")
    @Column(nullable = true, length = 100)
    private String email;
    @CharacterType(value = CaseType.LETTER_SPACE, message = "{contacto_type_letter_space}")
    @Length(min = 4, max = 60, message = "{contacto_lenght}")
    @Column(nullable = true, length = 60)
    private String contato;
    @Column(nullable = false)
    private boolean ativo;
    @Length(min = 4, max = 120, message = "{direcao_lenght}")
    @Column(nullable = true, length = 120)
    private String endereco;
    @Length(min = 0, max = 40, message = "{numero_lenght}")
    @Column(length = 40, nullable = true)
    private String numero;
    @Length(min = 4, max = 60, message = "{bairro_lenght}")
    @Column(nullable = true, length = 80)
    private String bairro;
    @Column(nullable = true)
    @Temporal(TemporalType.DATE)
    private Date nascimento;
    private boolean categoriaCliente;
    @Column(nullable = false)
    private boolean categoriaFornecedor;
    @Column(nullable = false)
    private boolean categoriaVendedor;
    @Column(nullable = false)
    private boolean categoriaTransportador;
    @Column(nullable = true)
    private Double desconto;
    @Temporal(TemporalType.DATE)
    private Date cadastro;
    @Length(min = 4, max = 255, message = "{observacao_lenght}")
    @Column(nullable = true, length = 255)
    private String observacao;
    @CharacterType(value = CaseType.LETTER_SPACE, message = "{fiador_type_letter_space}")
    @Length(min = 4, max = 80, message = "{fiador_lenght}")
    @Column(nullable = true, length = 80)
    private String fiador;
    @ManyToOne
    private Cep cep;
    @ManyToOne
    private Cidade cidade;
    @OneToMany(mappedBy = "pessoa")
    private List<Recepcao> recepcoes;
    @OneToMany(mappedBy = "pessoa")
    private List<ContratoDeCambio> contrato;
    @OneToMany(mappedBy = "pessoa")
    private List<Baixa> listaDeBaixas;
    @OneToMany(mappedBy = "pessoaComissionada")
    private List<Cambio> listaDeCambiosPessoaComissionada;
    @ManyToOne
    private ConfiguracaoCambio configuracaoCambio;
    @OneToMany(mappedBy = "pessoa")
    private List<Cobranca> cobrancas;
    @OneToMany(mappedBy = "pessoa")
    private List<Nota> notas;
    @OneToMany(mappedBy = "pessoa")
    private List<Orcamento> orcamentos;
    @OneToMany(mappedBy = "pessoa")
    private List<Pedido> pedido;

    public Pessoa() {
    }

    public Pessoa(Long id, String nome, TipoPessoa tipo, String ruc, boolean ativo,
            String endereco, String bairro, boolean categoriaCliente, boolean categoriaFornecedor,
            boolean categoriaVendedor, boolean categoriaTransportador, Double desconto,
            Date cadastro, String observacao, String fiador, Cep cep, String telefone, String email,
            String contato, String numero, Cidade cidade, Date nascimento) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.ruc = ruc;
        this.ativo = ativo;
        this.endereco = endereco;
        this.numero = numero;
        this.bairro = bairro;
        this.categoriaCliente = categoriaCliente;
        this.categoriaFornecedor = categoriaFornecedor;
        this.categoriaVendedor = categoriaVendedor;
        this.categoriaTransportador = categoriaTransportador;
        this.desconto = desconto;
        this.cadastro = cadastro;
        this.observacao = observacao;
        this.fiador = fiador;
        this.cep = cep;
        this.cidade = cidade;
        this.telefone = telefone;
        this.email = email;
        this.contato = contato;
        this.nascimento = nascimento;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    @MetodoInacessivelRelatorio
    public String getPrimeiroNomeEComecoDoSobrenome() {
        if (nome.contains(" ")) {
            return nome.substring(0, nome.indexOf(' ')) + (nome.substring(nome.lastIndexOf(' '), nome.lastIndexOf(' ') + 2) + '.');
        }
        return nome;
    }

    @MetodoInacessivelRelatorio
    public String getNomeRuc() {
        return nome + " - Ruc: " + ruc == null ? "" : ruc;
    }

    public List<Contato> getContatos() {
        return contatos;
    }

    public TipoPessoa getTipo() {
        return tipo;
    }

    public String getNumero() {
        return numero;
    }

    public String getRuc() {
        return ruc;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getBairro() {
        return bairro;
    }

    public boolean isCategoriaCliente() {
        return categoriaCliente;
    }

    public boolean isCategoriaFornecedor() {
        return categoriaFornecedor;
    }

    public boolean isCategoriaVendedor() {
        return categoriaVendedor;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    public Date getNascimento() {
        return nascimento;
    }

    public String getContato() {
        return contato;
    }

    public boolean isCategoriaTransportador() {
        return categoriaTransportador;
    }

    public Double getDesconto() {
        return desconto;
    }

    public Date getCadastro() {
        return cadastro;
    }

    public String getObservacao() {
        return observacao;
    }

    public String getFiador() {
        return fiador;
    }

    public Cep getCep() {
        return cep;
    }

    public void instanciaContactoList() {
        contatos = new ArrayList<Contato>();
    }

    public void setConfiguracaoCambio(ConfiguracaoCambio configuracaoCambio) {
        this.configuracaoCambio = configuracaoCambio;
    }

    @MetodoInacessivelRelatorio
    public String getIdFormatado() {
        return "(" + id + ")";
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
    public String getEnderecoNumeroBairroCidadeFormatado() {
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
        if (cep != null && cep.getCidade() != null && cep.getCidade().getNome() != null) {
            str += " - ";
            str += cep.getCidade().getNome();
        }
        return str;
    }

    public Cidade getCidade() {
        if (cep != null) {
            return cep.getCidade();
        } else {
            return cidade;
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

    @MetodoInacessivelRelatorio
    public String getNomeEstado() {
        if (cep != null) {
            return cep.getCidade().getEstado().getNome();
        } else {
            return cidade.getEstado().getNome();
        }
    }

    @MetodoInacessivelRelatorio
    public String getNumeroCep() {
        return cep.getCep();
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

    public abstract String getDocumento();

    @MetodoInacessivelRelatorio
    public String getFirstNameLastName() {
        String firstName = nome;
        String lastName = "";
        if (nome.contains(" ")) {
            firstName = nome.substring(0, nome.indexOf(" "));
            if (nome.indexOf(" ") != nome.lastIndexOf(" ")) {
                lastName = nome.substring(nome.lastIndexOf(" "), nome.length());
            }
        }
        return firstName + lastName;
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof Pessoa)) {
            return false;
        }
        Pessoa outro = (Pessoa) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

    @Override
    public String toString() {
        return "Pessoa{" + "id=" + id + ", nome=" + nome + '}';
    }

}
