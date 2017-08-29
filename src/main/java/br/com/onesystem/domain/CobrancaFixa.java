/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.valueobjects.SituacaoDeCobranca;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Rafael Fernando Rauber
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "COBRANCAFIXA_CLASSE_NOME")
public abstract class CobrancaFixa extends Cobranca implements Serializable {

    @NotNull(message = "{referencia_not_null}")
    @Temporal(TemporalType.DATE)
    private Date referencia;

    public CobrancaFixa() {
    }

    public CobrancaFixa(Long id, Date emissao, Pessoa pessoa, Cotacao cotacao, String historico,
            List<Baixa> baixas, OperacaoFinanceira operacaoFinanceira, BigDecimal valor, Date vencimento,
            Date referencia, SituacaoDeCobranca situacaoDeCobranca, Filial filial) throws DadoInvalidoException {
        super(id, valor, emissao, pessoa, cotacao, historico, baixas, situacaoDeCobranca, filial, vencimento, operacaoFinanceira);
        this.referencia = referencia;
        ehAbstracaoValida();
    }

    private final void ehAbstracaoValida() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("valor", "emissao", "historico", "cotacao", "operacaoFinanceira", "referencia", "filial");
        new ValidadorDeCampos<CobrancaFixa>().valida(this, campos);
    }

    public Date getReferencia() {
        return referencia;
    }

}
