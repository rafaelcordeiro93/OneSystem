package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.Cep;
import br.com.onesystem.domain.Cidade;
import br.com.onesystem.domain.Filial;
import br.com.onesystem.exception.DadoInvalidoException;
import java.util.Date;

/**
 *
 * @author Rafael
 */
public class FilialBuilder {

    private Long id;
    private String razaoSocial;
    private String fantasia;
    private String ruc;
    private String endereco;
    private String numero;
    private String bairro;
    private Cep cep;
    private String telefone;
    private Date vencimento;
    private String serialKey;
    private String email;
    private String contato;
    private Cidade cidade;

    public FilialBuilder comID(Long ID) {
        this.id = ID;
        return this;
    }

    public FilialBuilder comRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
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

    public FilialBuilder comNumero(String numero) {
        this.numero = numero;
        return this;
    }

    public FilialBuilder comEmail(String email) {
        this.email = email;
        return this;
    }

    public FilialBuilder comContato(String contato) {
        this.contato = contato;
        return this;
    }

    public FilialBuilder comBairro(String bairro) {
        this.bairro = bairro;
        return this;
    }

    public FilialBuilder comCep(Cep cep) {
        this.cep = cep;
        return this;
    }

    public FilialBuilder comTelefone(String telefone) {
        this.telefone = telefone;
        return this;
    }

    public FilialBuilder comVencimento(Date vencimento) {
        this.vencimento = vencimento;
        return this;
    }

    public FilialBuilder comSerialKey(String serialKey) {
        this.serialKey = serialKey;
        return this;
    }

    public FilialBuilder comCidade(Cidade cidade) {
        this.cidade = cidade;
        return this;
    }

    public Filial construir() throws DadoInvalidoException {
        return new Filial(id, razaoSocial, fantasia, ruc, endereco, bairro, cep, telefone, vencimento, serialKey, numero, email, contato, cidade);
    }

}
