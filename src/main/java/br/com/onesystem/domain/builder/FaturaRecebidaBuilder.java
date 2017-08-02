package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.ValorPorCotacao;
import br.com.onesystem.domain.Titulo;
import br.com.onesystem.domain.FaturaRecebida;
import br.com.onesystem.domain.NotaRecebida;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.exception.DadoInvalidoException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Rafael
 */
public class FaturaRecebidaBuilder {

    private Long id;
    private String codigo;
    private BigDecimal total;
    private Date emissao;
    private Pessoa pessoa;
    private List<Titulo> titulo;
    private List<NotaRecebida> notaRecebida;
    private List<ValorPorCotacao> valorPorCotacao;
    private BigDecimal dinheiro;

    public FaturaRecebidaBuilder comID(Long ID) {
        this.id = ID;
        return this;
    }

    public FaturaRecebidaBuilder comCodigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public FaturaRecebidaBuilder comTotal(BigDecimal total) {
        this.total = total;
        return this;
    }

    public FaturaRecebidaBuilder comEmissao(Date emissao) {
        this.emissao = emissao;
        return this;
    }

    public FaturaRecebidaBuilder comPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
        return this;
    }

    public FaturaRecebidaBuilder comTitulo(List<Titulo> titulo) {
        this.titulo = titulo;
        return this;
    }

    public FaturaRecebidaBuilder comNotaRecebida(List<NotaRecebida> notaRecebida) {
        this.notaRecebida = notaRecebida;
        return this;
    }

    public FaturaRecebidaBuilder comValorPorCotacaos(List<ValorPorCotacao> valorPorCotacao) {
        this.valorPorCotacao = valorPorCotacao;
        return this;
    }

    public FaturaRecebidaBuilder comDinheiro(BigDecimal dinheiro) {
        this.dinheiro = dinheiro;
        return this;
    }

    public FaturaRecebida construir() throws DadoInvalidoException {
        return new FaturaRecebida(id, codigo, total, emissao, pessoa, titulo, notaRecebida, valorPorCotacao, dinheiro);
    }

}
