package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Cfop;
import br.com.onesystem.domain.Estado;
import br.com.onesystem.domain.GrupoFiscal;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.domain.SituacaoFiscal;
import br.com.onesystem.domain.TabelaDeTributacao;
import br.com.onesystem.domain.builder.SituacaoFiscalBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.BuilderView;
import br.com.onesystem.valueobjects.TipoPessoa;
import java.io.Serializable;

public class SituacaoFiscalBV implements BuilderView<SituacaoFiscal>, Serializable {

    private Long id;
    private Integer sequencia;
    private Operacao operacao;
    private GrupoFiscal grupoFiscal;
    private TabelaDeTributacao tabelaDeTributacao;
    private Estado estado;
    private TipoPessoa tipoPessoa;
    private Cfop cfop;
    private String observacao;

    public SituacaoFiscalBV() {
    }

    public SituacaoFiscalBV(SituacaoFiscal s) {
        this.id = s.getId();
        this.sequencia = s.getSequencia();
        this.operacao = s.getOperacao();
        this.grupoFiscal = s.getGrupoFiscal();
        this.tabelaDeTributacao = s.getTabelaDeTributacao();
        this.estado = s.getEstado();
        this.tipoPessoa = s.getTipoPessoa();
        this.cfop = s.getCfop();
        this.observacao = s.getObservacao();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSequencia() {
        return sequencia;
    }

    public void setSequencia(Integer sequencia) {
        this.sequencia = sequencia;
    }

    public Operacao getOperacao() {
        return operacao;
    }

    public void setOperacao(Operacao operacao) {
        this.operacao = operacao;
    }

    public GrupoFiscal getGrupoFiscal() {
        return grupoFiscal;
    }

    public void setGrupoFiscal(GrupoFiscal grupoFiscal) {
        this.grupoFiscal = grupoFiscal;
    }

    public TabelaDeTributacao getTabelaDeTributacao() {
        return tabelaDeTributacao;
    }

    public void setTabelaDeTributacao(TabelaDeTributacao tabelaDeTributacao) {
        this.tabelaDeTributacao = tabelaDeTributacao;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public TipoPessoa getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(TipoPessoa tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public Cfop getCfop() {
        return cfop;
    }

    public void setCfop(Cfop cfop) {
        this.cfop = cfop;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
    
    public SituacaoFiscal construir() throws DadoInvalidoException {
        return new SituacaoFiscalBuilder().comSequencia(sequencia).comOperacao(operacao).comObservacao(observacao)
                .comGrupoFiscal(grupoFiscal).comTabelaDeTributacao(tabelaDeTributacao).comEstado(estado)
                .comTipoPessoa(tipoPessoa).comCFOP(cfop).construir();
    }

    @Override
    public SituacaoFiscal construirComID() throws DadoInvalidoException {
                return new SituacaoFiscalBuilder().comId(id).comSequencia(sequencia).comOperacao(operacao)
                .comGrupoFiscal(grupoFiscal).comTabelaDeTributacao(tabelaDeTributacao).comEstado(estado)
                .comTipoPessoa(tipoPessoa).comCFOP(cfop).comObservacao(observacao).construir();
    }

    @Override
    public String toString() {
        return "SituacaoFiscalBV{" + "id=" + id + ", sequencia=" + sequencia + ", operacao=" + operacao + ", grupoFiscal=" + grupoFiscal + ", tabelaDeTributacao=" + tabelaDeTributacao + ", estado=" + estado + ", tipoPessoa=" + tipoPessoa + ", cfop=" + cfop + ", observacao=" + observacao + '}';
    }
    
}
