package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Cobranca;
import br.com.onesystem.domain.Configuracao;
import br.com.onesystem.domain.FaturaLegada;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.builder.FaturaLegadaBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.services.BuilderView;
import br.com.onesystem.war.service.ConfiguracaoService;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Rafael
 */
public class FaturaLegadaBV implements Serializable, BuilderView<FaturaLegada> {

    private Long id;
    private String codigo;
    private BigDecimal total;
    private Date emissao;
    private Pessoa pessoa;
    private List<Cobranca> cobranca;

    public FaturaLegadaBV(FaturaLegada faturaLegadaSelecionada) {
        this.id = faturaLegadaSelecionada.getId();
        this.codigo = faturaLegadaSelecionada.getCodigo();
        this.total = faturaLegadaSelecionada.getTotal();
        this.emissao = faturaLegadaSelecionada.getEmissao();
        this.pessoa = faturaLegadaSelecionada.getPessoa();
        this.cobranca = faturaLegadaSelecionada.getCobranca();

    }

    public FaturaLegadaBV() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Date getEmissao() {
        return emissao;
    }

    public void setEmissao(Date emissao) {
        this.emissao = emissao;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public List<Cobranca> getCobranca() {
        return cobranca;
    }
    
     public String getMoedaPadrao() throws EDadoInvalidoException {
        Configuracao cfg = new ConfiguracaoService().buscar();
        return cfg.getMoedaPadrao().getSigla();
    }

    public void setCobranca(List<Cobranca> cobranca) {
        this.cobranca = cobranca;
    }

    public FaturaLegada construirComID() throws DadoInvalidoException {
        return new FaturaLegadaBuilder().comID(id).comCodigo(codigo).comTotal(total).comEmissao(emissao).comPessoa(pessoa).comCobranca(cobranca).construir();
    }

    public FaturaLegada construir() throws DadoInvalidoException {
        return new FaturaLegadaBuilder().comCodigo(codigo).comTotal(total).comEmissao(emissao).comPessoa(pessoa).comCobranca(cobranca).construir();
    }

}
