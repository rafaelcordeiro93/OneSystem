package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.ValorPorCotacao;
import br.com.onesystem.domain.Titulo;
import br.com.onesystem.domain.FaturaEmitida;
import br.com.onesystem.domain.Filial;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.exception.DadoInvalidoException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Rafael
 */
public class FaturaEmitidaBuilder {

    private Long id;
    private String codigo;
    private BigDecimal total;
    private Date emissao;
    private Pessoa pessoa;
    private List<Titulo> titulo;
    private List<NotaEmitida> notaEmitida;
    private List<ValorPorCotacao> valorPorCotacao;
    private BigDecimal dinheiro;
    private Filial filial;

    public FaturaEmitidaBuilder comID(Long ID) {
        this.id = ID;
        return this;
    }

    public FaturaEmitidaBuilder comCodigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public FaturaEmitidaBuilder comTotal(BigDecimal total) {
        this.total = total;
        return this;
    }

    public FaturaEmitidaBuilder comEmissao(Date emissao) {
        this.emissao = emissao;
        return this;
    }

    public FaturaEmitidaBuilder comPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
        return this;
    }

    public FaturaEmitidaBuilder comTitulo(List<Titulo> titulo) {
        this.titulo = titulo;
        return this;
    }

    public FaturaEmitidaBuilder comNotaEmitida(List<NotaEmitida> notaEmitida) {
        this.notaEmitida = notaEmitida;
        return this;
    }

    public FaturaEmitidaBuilder comValorPorCotacaos(List<ValorPorCotacao> valorPorCotacao) {
        this.valorPorCotacao = valorPorCotacao;
        return this;
    }

    public FaturaEmitidaBuilder comDinheiro(BigDecimal dinheiro) {
        this.dinheiro = dinheiro;
        return this;
    }
    
    public FaturaEmitidaBuilder comFilial(Filial filial){
        this.filial = filial;
        return this;
    }

    public FaturaEmitida construir() throws DadoInvalidoException {
        return new FaturaEmitida(id, codigo, total, emissao, pessoa, titulo, notaEmitida, valorPorCotacao, dinheiro, filial);
    }

}
