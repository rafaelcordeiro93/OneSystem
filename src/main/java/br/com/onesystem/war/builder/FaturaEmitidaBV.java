package br.com.onesystem.war.builder;

import br.com.onesystem.domain.ValorPorCotacao;
import br.com.onesystem.domain.Titulo;
import br.com.onesystem.domain.Configuracao;
import br.com.onesystem.domain.FaturaEmitida;
import br.com.onesystem.domain.Filial;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.builder.FaturaEmitidaBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.services.BuilderView;
import br.com.onesystem.war.service.ConfiguracaoService;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Rafael
 */
public class FaturaEmitidaBV implements Serializable, BuilderView<FaturaEmitida> {

    private Long id;
    private String codigo;
    private BigDecimal total;
    private Date emissao = new Date();
    private Pessoa pessoa;
    private List<Titulo> titulo = new ArrayList<>();
    private List<NotaEmitida> notaEmitida = new ArrayList<>();
    private List<ValorPorCotacao> valorPorCotacao = new ArrayList<>();
    private BigDecimal dinheiro;
    private Filial filial;

    public FaturaEmitidaBV(FaturaEmitida faturaEmitidaSelecionada) {
        this.id = faturaEmitidaSelecionada.getId();
        this.codigo = faturaEmitidaSelecionada.getCodigo();
        this.total = faturaEmitidaSelecionada.getTotal();
        this.emissao = faturaEmitidaSelecionada.getEmissao();
        this.pessoa = faturaEmitidaSelecionada.getPessoa();
        this.titulo = faturaEmitidaSelecionada.getTitulo();
        this.notaEmitida = faturaEmitidaSelecionada.getNotaEmitida();
        this.valorPorCotacao = faturaEmitidaSelecionada.getValorPorCotacao();
        this.dinheiro = faturaEmitidaSelecionada.getDinheiro();
        this.filial = faturaEmitidaSelecionada.getFilial();
    }

    public FaturaEmitidaBV() {

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

    public List<Titulo> getTitulo() {
        return titulo;
    }

    public void setTitulo(List<Titulo> titulo) {
        this.titulo = titulo;
    }

    public List<NotaEmitida> getNotaEmitida() {
        return notaEmitida;
    }

    public void setNotaEmitida(List<NotaEmitida> notaEmitida) {
        this.notaEmitida = notaEmitida;
    }

    public List<ValorPorCotacao> getValorPorCotacao() {
        return valorPorCotacao;
    }

    public void setValorPorCotacao(List<ValorPorCotacao> valorPorCotacao) {
        this.valorPorCotacao = valorPorCotacao;
    }

    public BigDecimal getDinheiro() {
        return dinheiro;
    }

    public void setDinheiro(BigDecimal dinheiro) {
        this.dinheiro = dinheiro;
    }

    public String getMoedaPadrao() throws EDadoInvalidoException {
        Configuracao cfg = new ConfiguracaoService().buscar();
        return cfg.getMoedaPadrao().getSigla();
    }

    public Filial getFilial() {
        return filial;
    }

    public void setFilial(Filial filial) {
        this.filial = filial;
    }
    
    public FaturaEmitida construirComID() throws DadoInvalidoException {
        return new FaturaEmitidaBuilder().comID(id).comCodigo(codigo).comTotal(total).comEmissao(emissao).
                comPessoa(pessoa).comTitulo(titulo).comNotaEmitida(notaEmitida).comValorPorCotacaos(valorPorCotacao).
                comDinheiro(dinheiro).comFilial(filial).construir();
    }

    public FaturaEmitida construir() throws DadoInvalidoException {
        return new FaturaEmitidaBuilder().comCodigo(codigo).comTotal(total).comEmissao(emissao).
                comPessoa(pessoa).comTitulo(titulo).comNotaEmitida(notaEmitida).comValorPorCotacaos(valorPorCotacao).
                comDinheiro(dinheiro).comFilial(filial).construir();
    }

}
