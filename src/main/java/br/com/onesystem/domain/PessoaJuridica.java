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

    public PessoaJuridica(String fantasia, Long ID, String nome, TipoPessoa tipo, String ruc, boolean ativo, String direcao, String bairro, boolean categoriaCliente, boolean categoriaFornecedor, boolean categoriaVendedor, boolean categoriaTransportador, Double desconto, Date cadastro, String observacao, String fiador, Cidade cidade, String telefone, String email, String contato) throws DadoInvalidoException {
        super(ID, nome, tipo, ruc, ativo, direcao, bairro, categoriaCliente, categoriaFornecedor, categoriaVendedor, categoriaTransportador, desconto, cadastro, observacao, fiador, cidade, telefone, email, contato);
        this.fantasia = fantasia;
        ehValido();
    }

    @Override
    public String getDocumento() {
        return fantasia;
    }

    public final void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("nome", "direcao", "bairro", "fiador", "observacao", "fantasia");
        new ValidadorDeCampos<Pessoa>().valida(this, campos);
    }

}
