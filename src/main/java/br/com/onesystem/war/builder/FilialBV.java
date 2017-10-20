package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Cep;
import br.com.onesystem.domain.Cidade;
import br.com.onesystem.domain.Filial;
import br.com.onesystem.domain.builder.FilialBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.BuilderView;
import java.io.Serializable;
import java.util.Date;

public class FilialBV implements Serializable, BuilderView<Filial> {

    private Long id;
    private String razaoSocial;
    private String fantasia;
    private String ruc;
    private String endereco;
    private String bairro;
    private Cep cep;
    private String telefone;
    private Date vencimento;
    private String serialKey;
    private String numero;
    private String contato;
    private String email;
    private Cidade cidade;

    public FilialBV(Filial f) {
        this.id = f.getId();
        this.razaoSocial = f.getRazaoSocial();
        this.fantasia = f.getFantasia();
        this.ruc = f.getRuc();
        this.endereco = f.getEndereco();
        this.bairro = f.getBairro();
        this.cep = f.getCep();
        this.telefone = f.getTelefone();
        this.vencimento = f.getVencimento();
        this.serialKey = f.getSerialKey();
        this.numero = f.getNumero();
        this.email = f.getEmail();
        this.contato = f.getEmail();
        this.cidade = f.getCidade();
    }

    public FilialBV() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getFantasia() {
        return fantasia;
    }

    public void setFantasia(String fantasia) {
        this.fantasia = fantasia;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public Cep getCep() {
        return cep;
    }

    public void setCep(Cep cep) {
        this.cep = cep;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Date getVencimento() {
        return vencimento;
    }

    public void setVencimento(Date vencimento) {
        this.vencimento = vencimento;
    }

    public String getSerialKey() {
        return serialKey;
    }

    public void setSerialKey(String serialKey) {
        this.serialKey = serialKey;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public Filial construir() throws DadoInvalidoException {
        return new FilialBuilder().comRazaoSocial(razaoSocial).comFantasia(fantasia).comRuc(ruc).comNumero(numero).comEmail(email).comContato(contato).comCidade(cidade)
                .comEndereco(endereco).comBairro(bairro).comCep(cep).comTelefone(telefone).comVencimento(vencimento).comSerialKey(serialKey).construir();
    }

    public Filial construirComID() throws DadoInvalidoException {
        return new FilialBuilder().comID(id).comRazaoSocial(razaoSocial).comFantasia(fantasia).comRuc(ruc).comNumero(numero).comEmail(email).comContato(contato).comCidade(cidade)
                .comEndereco(endereco).comBairro(bairro).comCep(cep).comTelefone(telefone).comVencimento(vencimento).comSerialKey(serialKey).construir();
    }
}
