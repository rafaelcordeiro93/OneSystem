package br.com.onesystem.domain;

import br.com.onesystem.valueobjects.CaseType;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.valueobjects.TipoPessoa;
import br.com.onesystem.services.CharacterType;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.validator.constraints.Length;

@Entity
@DiscriminatorValue("PESSOA_FISICA")
public class PessoaFisica extends Pessoa {
    
    @Column(unique = false)
    private String CI;
    @Temporal(TemporalType.DATE)
    private Date nascimento;
    @Length(min = 0, max = 80, message = "{conjuge_lenght}")
    @CharacterType(value = CaseType.LETTER_SPACE, message = "{conjuge_type_letter_space}")
    @Column(nullable = true, length = 80)
    private String conjuge;
    
    public PessoaFisica() {
    }
    
    public PessoaFisica(String CI, Date nascimento, Long ID, String nome, TipoPessoa tipo, String ruc, boolean ativo, String endereco, String bairro, boolean categoriaCliente, boolean categoriaFornecedor, boolean categoriaVendedor, boolean categoriaTransportado, String conjuge, Double desconto, Date cadastro, String observacao, String fiador, Cep cep, String telefone, String email, String contato, String numero) throws DadoInvalidoException {
        super(ID, nome, tipo, ruc, ativo, endereco, bairro, categoriaCliente, categoriaFornecedor, categoriaVendedor, categoriaTransportado, desconto, cadastro, observacao, fiador, cep, telefone, email, contato, numero);
        this.CI = CI;
        this.nascimento = nascimento;
        this.conjuge = conjuge;
        ehValido();
    }
    
    @Override
    public String getDocumento() {
        return CI;
    }
    
    @Override
    public Date getNascimento() {
        return nascimento;
    }
    
    @Override
    public String getConjuge() {
        return conjuge;
    }
    
    public final void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("nome", "conjuge", "endereco", "bairro", "telefone",
                 "contato", "email", "observacao");
        new ValidadorDeCampos<Pessoa>().valida(this, campos);
    }
}
