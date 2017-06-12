package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.Cidade;
import br.com.onesystem.domain.Filial;
import br.com.onesystem.exception.DadoInvalidoException;

/**
 *
 * @author Rafael
 */
public class FilialBuilder {

    private Long id;
    private String nome;
    private String razao_social;
    private String fantasia;
    private String ruc;
    private String endereco;
    private String bairro;
    private Cidade cidade;
    private String telefone;

    public FilialBuilder comID(Long ID) {
        this.id = ID;
        return this;
    }

    public FilialBuilder comNome(String nome) {
        this.nome = nome;
        return this;
    }

    public FilialBuilder comRazaoSocial(String razao_social) {
        this.razao_social = razao_social;
        return this;
    }

    public FilialBuilder comFantasia(String fantasia) {
        this.fantasia = fantasia;
        return this;
    }

    public FilialBuilder comRuc(String ruc) {
        this.ruc = ruc;
        return this;
    }

    public FilialBuilder comEndereco(String endereco) {
        this.endereco = endereco;
        return this;
    }

    public FilialBuilder comBairro(String bairro) {
        this.bairro = bairro;
        return this;
    }

    public FilialBuilder comCidade(Cidade cidade) {
        this.cidade = cidade;
        return this;
    }

    public FilialBuilder comTelefone(String telefone) {
        this.telefone = telefone;
        return this;
    }

    public Filial construir() throws DadoInvalidoException {
        return new Filial(id, nome, razao_social, fantasia, ruc, endereco, bairro, cidade, telefone);
    }

}
