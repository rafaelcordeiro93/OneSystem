package br.com.onesystem.domain;

import br.com.onesystem.valueobjects.CaseType;
import br.com.onesystem.valueobjects.TipoPessoa;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.CharacterType;
import br.com.onesystem.services.ValidadorDeCampos;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import org.hibernate.validator.constraints.Length;

@Entity
@DiscriminatorValue("PESSOA_JURIDICA")
public class PessoaJuridica extends Pessoa {

    @CharacterType(value = CaseType.LETTER_SPACE, message = "{fantasia_type_letter_space}")
    @Length(min = 4, max = 80, message = "{fantasia_lenght}")
    @Column(nullable = false, length = 80)
    private String fantasia;

    public PessoaJuridica() {
    }

    public PessoaJuridica(String fantasia, Long ID, String nome, TipoPessoa tipo, String ruc, boolean ativo, String endereco, String bairro, boolean categoriaCliente, boolean categoriaFornecedor, boolean categoriaVendedor, boolean categoriaTransportador, Double desconto, Date cadastro, String observacao, String fiador, Cep cep, String telefone, String email, String contato, String numero, Cidade cidade, Date nascimento) throws DadoInvalidoException {
        super(ID, nome, tipo, ruc, ativo, endereco, bairro, categoriaCliente, categoriaFornecedor, categoriaVendedor, categoriaTransportador, desconto, cadastro, observacao, fiador, cep, telefone, email, contato, numero, cidade, nascimento);
        this.fantasia = fantasia;
        ehValido();
    }

    @Override
    public String getDocumento() {
        return fantasia;
    }

    public final void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("nome", "endereco", "bairro", "fiador", "observacao", "fantasia");
        new ValidadorDeCampos<Pessoa>().valida(this, campos);
    }

}
