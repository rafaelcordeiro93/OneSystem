package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.Banco;
import br.com.onesystem.domain.Cobranca;
import br.com.onesystem.domain.FaturaLegada;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.exception.DadoInvalidoException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Rafael
 */
public class FaturaLegadaBuilder {

    private Long id;
    private String codigo;
    private BigDecimal total;
    private Date emissao;
    private Pessoa pessoa;
    private List<Cobranca> cobranca;

    public FaturaLegadaBuilder comID(Long ID) {
        this.id = ID;
        return this;
    }

    public FaturaLegadaBuilder comCodigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public FaturaLegadaBuilder comTotal(BigDecimal total) {
        this.total = total;
        return this;
    }

    public FaturaLegadaBuilder comEmissao(Date emissao) {
        this.emissao = emissao;
        return this;
    }

    public FaturaLegadaBuilder comPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
        return this;
    }

    public FaturaLegadaBuilder comCobranca(List<Cobranca> cobranca) {
        this.cobranca = cobranca;
        return this;
    }

    public FaturaLegada construir() throws DadoInvalidoException {
        return new FaturaLegada(id, codigo, total, emissao, pessoa, cobranca);
    }

}
