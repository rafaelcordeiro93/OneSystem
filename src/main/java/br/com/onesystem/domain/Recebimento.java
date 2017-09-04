/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.EstadoDeLancamento;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 *
 * @author Rafael Fernando Rauber
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "RECEBIMENTO_CLASSE_NOME")
public class Recebimento extends Movimento implements Serializable {

    public Recebimento() {
        super();
    }

    public Recebimento(Long id, List<TipoDeCobranca> tipoDeCobranca, List<FormaDeCobranca> formasDeCobranca,
            Cotacao cotacaoPadrao, Date emissao, BigDecimal totalEmDinheiro, EstadoDeLancamento estado, Caixa caixa,
            Filial filial, List<ValorPorCotacao> valorPorCotacao) throws DadoInvalidoException {
        super(id, tipoDeCobranca, formasDeCobranca, cotacaoPadrao, emissao, estado, caixa, filial, valorPorCotacao, totalEmDinheiro);
    }

}
