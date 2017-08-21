package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Cep;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.PessoaFisica;
import br.com.onesystem.domain.PessoaJuridica;
import br.com.onesystem.valueobjects.TipoPessoa;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.BuilderView;
import java.io.Serializable;
import java.util.Date;

public class PessoaBV implements Serializable, BuilderView<Pessoa> {
    
    private Long id;
    private String nome;
    private String ruc;
    private boolean ativo = true;
    private String endereco;
    private String numero;
    private String bairro;
    private boolean categoriaCliente = true;
    private boolean categoriaFornecedor = false;
    private boolean categoriaVendedor = false;
    private boolean categoriaTransportador = false;
    private Double desconto;
    private Date cadastro = new Date();
    private String observacao;
    private String fiador;
    private Cep cep;
    private String telefone;
    private String contato;
    private String email;
    private boolean fisicaJuridica = false;
    private Date nascimento;
    private String conjuge;
    private String fantasiaCI;
    private String fantasiaCILabel = "C.I.";
    
    public PessoaBV() {
    }
    
    public PessoaBV(Pessoa pessoaSelecionada) {
        this.id = pessoaSelecionada.getId();
        this.nome = pessoaSelecionada.getNome();
        this.ruc = pessoaSelecionada.getRuc();
        this.ativo = pessoaSelecionada.isAtivo();
        this.endereco = pessoaSelecionada.getEndereco();
        this.numero = pessoaSelecionada.getNumero();
        this.bairro = pessoaSelecionada.getBairro();
        this.categoriaCliente = pessoaSelecionada.isCategoriaCliente();
        this.categoriaFornecedor = pessoaSelecionada.isCategoriaFornecedor();
        this.categoriaTransportador = pessoaSelecionada.isCategoriaTransportador();
        this.categoriaVendedor = pessoaSelecionada.isCategoriaVendedor();
        this.desconto = pessoaSelecionada.getDesconto();
        this.cadastro = pessoaSelecionada.getCadastro();
        this.observacao = pessoaSelecionada.getObservacao();
        this.fiador = pessoaSelecionada.getFiador();
        this.cep = pessoaSelecionada.getCep();
        this.fisicaJuridica = pessoaSelecionada.getTipo() != TipoPessoa.PESSOA_FISICA;
        this.nascimento = pessoaSelecionada.getNascimento();
        this.conjuge = pessoaSelecionada.getConjuge();
        this.fantasiaCI = pessoaSelecionada.getDocumento();
        this.telefone = pessoaSelecionada.getTelefone();
        this.email = pessoaSelecionada.getEmail();
        this.contato = pessoaSelecionada.getContato();
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getRuc() {
        return ruc;
    }
    
    public void setRuc(String ruc) {
        this.ruc = ruc;
    }
    
    public boolean isAtivo() {
        return ativo;
    }
    
    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
    
    public String getBairro() {
        return bairro;
    }
    
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
    
    public boolean isCategoriaCliente() {
        return categoriaCliente;
    }
    
    public void setCategoriaCliente(boolean categoriaCliente) {
        this.categoriaCliente = categoriaCliente;
    }
    
    public Date getNascimento() {
        return nascimento;
    }
    
    public void setNascimento(Date nascimento) {
        this.nascimento = nascimento;
    }
    
    public String getConjuge() {
        return conjuge;
    }
    
    public void setConjuge(String conjuge) {
        this.conjuge = conjuge;
    }
    
    public String getFantasiaCI() {
        return fantasiaCI;
    }
    
    public void setFantasiaCI(String fantasiaCI) {
        this.fantasiaCI = fantasiaCI;
    }
    
    public String getFantasiaCILabel() {
        return fantasiaCILabel;
    }
    
    public void setFantasiaCILabel(String fantasiaCILabel) {
        this.fantasiaCILabel = fantasiaCILabel;
    }
    
    public boolean isCategoriaFornecedor() {
        return categoriaFornecedor;
    }
    
    public void setCategoriaFornecedor(boolean categoriaFornecedor) {
        this.categoriaFornecedor = categoriaFornecedor;
    }
    
    public boolean isCategoriaVendedor() {
        return categoriaVendedor;
    }
    
    public void setCategoriaVendedor(boolean categoriaVendedor) {
        this.categoriaVendedor = categoriaVendedor;
    }
    
    public boolean isCategoriaTransportador() {
        return categoriaTransportador;
    }
    
    public void setCategoriaTransportador(boolean categoriaTransportador) {
        this.categoriaTransportador = categoriaTransportador;
    }
    
    public Double getDesconto() {
        return desconto;
    }
    
    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }
    
    public Date getCadastro() {
        return cadastro;
    }
    
    public void setCadastro(Date cadastro) {
        this.cadastro = cadastro;
    }
    
    public String getObservacao() {
        return observacao;
    }
    
    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
    
    public String getFiador() {
        return fiador;
    }
    
    public boolean isFisicaJuridica() {
        return fisicaJuridica;
    }
    
    public void setFisicaJuridica(boolean fisicaJuridica) {
        this.fisicaJuridica = fisicaJuridica;
    }
    
    public void setFiador(String fiador) {
        this.fiador = fiador;
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
    
    public Pessoa construir() throws DadoInvalidoException {
        telefone = telefone == null ? null : telefone.replaceAll("\\(", "").replaceAll("\\)", "");
        return fisicaJuridica == false
                ? new PessoaFisica(fantasiaCI, nascimento, null, nome, fisicaJuridica == false ? TipoPessoa.PESSOA_FISICA : TipoPessoa.PESSOA_JURIDICA,
                        ruc, ativo, endereco, bairro, categoriaCliente, categoriaFornecedor, categoriaVendedor, categoriaTransportador,
                        conjuge, desconto, cadastro, observacao, fiador, cep, telefone, email, contato, numero)
                : new PessoaJuridica(fantasiaCI, null, nome, fisicaJuridica == false ? TipoPessoa.PESSOA_FISICA : TipoPessoa.PESSOA_JURIDICA,
                        ruc, ativo, endereco, bairro, categoriaCliente, categoriaFornecedor, categoriaVendedor, categoriaTransportador,
                        desconto, cadastro, observacao, fiador, cep, telefone, email, contato, numero);
    }
    
    public Pessoa construirComID() throws DadoInvalidoException {
        telefone = telefone == null ? null : telefone.replaceAll("\\(", "").replaceAll("\\)", "");
        return fisicaJuridica == false
                ? new PessoaFisica(fantasiaCI, nascimento, id, nome, fisicaJuridica == false ? TipoPessoa.PESSOA_FISICA : TipoPessoa.PESSOA_JURIDICA,
                        ruc, ativo, endereco, bairro, categoriaCliente, categoriaFornecedor, categoriaVendedor, categoriaTransportador,
                        conjuge, desconto, cadastro, observacao, fiador, cep, telefone, email, contato, numero)
                : new PessoaJuridica(fantasiaCI, id, nome, fisicaJuridica == false ? TipoPessoa.PESSOA_FISICA : TipoPessoa.PESSOA_JURIDICA,
                        ruc, ativo, endereco, bairro, categoriaCliente, categoriaFornecedor, categoriaVendedor, categoriaTransportador,
                        desconto, cadastro, observacao, fiador, cep, telefone, email, contato, numero);
    }
    
}
