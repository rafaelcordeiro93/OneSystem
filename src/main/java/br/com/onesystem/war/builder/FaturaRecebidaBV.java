package br.com.onesystem.war.builder;

import br.com.onesystem.domain.ValorPorCotacao;
import br.com.onesystem.domain.Titulo;
import br.com.onesystem.domain.Configuracao;
import br.com.onesystem.domain.FaturaRecebida;
import br.com.onesystem.domain.Filial;
import br.com.onesystem.domain.NotaRecebida;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.builder.FaturaRecebidaBuilder;
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
public class FaturaRecebidaBV implements Serializable, BuilderView<FaturaRecebida> {

    private Long id;
    private String codigo;
    private BigDecimal total;
    private Date emissao = new Date();
    private Pessoa pessoa;
    private List<Titulo> titulo;
    private List<NotaRecebida> notaRecebida;
    private List<ValorPorCotacao> valorPorCotacao;
    private BigDecimal dinheiro;
    private Filial filial;

    public FaturaRecebidaBV(FaturaRecebida faturaRecebidaSelecionada) {
        this.id = faturaRecebidaSelecionada.getId();
        this.codigo = faturaRecebidaSelecionada.getCodigo();
        this.total = faturaRecebidaSelecionada.getTotal();
        this.emissao = faturaRecebidaSelecionada.getEmissao();
        this.pessoa = faturaRecebidaSelecionada.getPessoa();
        this.titulo = faturaRecebidaSelecionada.getTitulo();
        this.notaRecebida = faturaRecebidaSelecionada.getNotaRecebida();
        this.valorPorCotacao = faturaRecebidaSelecionada.getValorPorCotacao();
        this.dinheiro = faturaRecebidaSelecionada.getDinheiro();
        this.filial = faturaRecebidaSelecionada.getFilial();
    }

    public FaturaRecebidaBV() {

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

    public List<NotaRecebida> getNotaRecebida() {
        return notaRecebida;
    }

    public void setNotaRecebida(List<NotaRecebida> notaRecebida) {
        this.notaRecebida = notaRecebida;
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
    
    public FaturaRecebida construirComID() throws DadoInvalidoException {
        return new FaturaRecebidaBuilder().comID(id).comCodigo(codigo).comTotal(total).
                comEmissao(emissao).comPessoa(pessoa).comTitulo(titulo).comNotaRecebida(notaRecebida).
                comValorPorCotacaos(valorPorCotacao).comDinheiro(dinheiro).
                comFilial(filial).construir();
    }

    public FaturaRecebida construir() throws DadoInvalidoException {
        return new FaturaRecebidaBuilder().comCodigo(codigo).comTotal(total).
                comEmissao(emissao).comPessoa(pessoa).comTitulo(titulo).comNotaRecebida(notaRecebida).
                comValorPorCotacaos(valorPorCotacao).comDinheiro(dinheiro).
                comFilial(filial).construir();
    }

}
