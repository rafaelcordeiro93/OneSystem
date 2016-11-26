package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Moeda;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.Receita;
import br.com.onesystem.domain.ReceitaProvisionada;
import br.com.onesystem.exception.DadoInvalidoException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReceitaProvisionadaBV implements Serializable {

    private Long id;
    private Pessoa pessoa;
    private Receita receita;
    private BigDecimal valor;
    private Date vencimento;
    private Date emissao;
    private String historico;
    private Moeda moeda;

    public ReceitaProvisionadaBV(ReceitaProvisionada receitaProvisionadaSelecionada) {
        this.id = receitaProvisionadaSelecionada.getId();
        this.pessoa = receitaProvisionadaSelecionada.getPessoa();
        this.receita = receitaProvisionadaSelecionada.getReceita();
        this.valor = receitaProvisionadaSelecionada.getValor();
        this.vencimento = receitaProvisionadaSelecionada.getVencimento();
        this.emissao = receitaProvisionadaSelecionada.getEmissao();
        this.historico = receitaProvisionadaSelecionada.getHistorico();
        this.moeda = receitaProvisionadaSelecionada.getMoeda();
    }

    public ReceitaProvisionadaBV(Long id, Pessoa pessoa, Receita receita, BigDecimal valor, 
            Date vencimento, Date emissao, String historico, Moeda moeda) {
        this.id = id;
        this.pessoa = pessoa;
        this.receita = receita;
        this.valor = valor;
        this.vencimento = vencimento;
        this.emissao = emissao;
        this.historico = historico;
        this.moeda = moeda;
    }
    
    public ReceitaProvisionadaBV() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Receita getReceita() {
        return receita;
    }

    public void setReceita(Receita receita) {
        this.receita = receita;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Date getVencimento() {
        return vencimento;
    }

    public String getVencimentoFormatado() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(vencimento);
    }
    
    public void setVencimento(Date vencimento) {
        this.vencimento = vencimento;
    }

    public Date getEmissao() {
        return emissao;
    }

    public void setEmissao(Date emissao) {
        this.emissao = emissao;
    }

    public String getHistorico() {
        return historico;
    }

    public void setHistorico(String historico) {
        this.historico = historico;
    }

    public Moeda getMoeda() {
        return moeda;
    }

    public void setMoeda(Moeda moeda) {
        this.moeda = moeda;
    }
    
    public ReceitaProvisionada construir() throws DadoInvalidoException {
        return new ReceitaProvisionada(null, pessoa, receita, valor, vencimento, historico, moeda);
    }

    public ReceitaProvisionada construirComID() throws DadoInvalidoException {
        return new ReceitaProvisionada(id, pessoa, receita, valor, vencimento, historico, moeda);
    }
}
