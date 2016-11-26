package br.com.onesystem.war.builder;

import br.com.onesystem.domain.DespesaProvisionada;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.TransferenciaDespesaProvisionada;
import br.com.onesystem.exception.DadoInvalidoException;
import java.io.Serializable;
import java.util.Date;

public class TransferenciaDespesaProvisionadaBV implements Serializable {

    private Long id;
    private String motivo;  
    private Date emissao = new Date();
    private DespesaProvisionada destino;
    private DespesaProvisionada origem;
    private Pessoa pessoa;

    public TransferenciaDespesaProvisionadaBV(TransferenciaDespesaProvisionada transferenciaDespesaProvisionadaSelecionada) {
        this.id = transferenciaDespesaProvisionadaSelecionada.getId();
        this.origem = transferenciaDespesaProvisionadaSelecionada.getOrigem();
        this.destino = transferenciaDespesaProvisionadaSelecionada.getDestino();
        this.emissao = transferenciaDespesaProvisionadaSelecionada.getEmissao();
        this.pessoa = transferenciaDespesaProvisionadaSelecionada.getDestino().getPessoa();
        this.motivo = transferenciaDespesaProvisionadaSelecionada.getMotivo();
    }

    public TransferenciaDespesaProvisionadaBV() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Date getEmissao() {
        return emissao;
    }

    public void setEmissao(Date emissao) {
        this.emissao = emissao;
    }

    public DespesaProvisionada getDestino() {
        return destino;
    }

    public void setDestino(DespesaProvisionada destino) {
        this.destino = destino;
    }

    public DespesaProvisionada getOrigem() {
        return origem;
    }

    public void setOrigem(DespesaProvisionada origem) {
        this.origem = origem;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public TransferenciaDespesaProvisionada construir() throws DadoInvalidoException {
        return new TransferenciaDespesaProvisionada(null, origem, destino, motivo, emissao);
    }

    public TransferenciaDespesaProvisionada construirComID() throws DadoInvalidoException {
        return new TransferenciaDespesaProvisionada(id, origem, destino, motivo, emissao);
    }
}
