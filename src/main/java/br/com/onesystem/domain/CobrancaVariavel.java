/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.util.MoedaFormatter;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.valueobjects.ModalidadeDeCobranca;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import br.com.onesystem.valueobjects.SituacaoDeCobranca;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

/**
 *
 * @author Rafael Fernando Rauber
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "COBRANCAVARIAVEL_CLASSE_NOME")
public abstract class CobrancaVariavel extends Cobranca implements Serializable {

    @ManyToOne
    private Nota nota;

    private Boolean entrada;

    private Integer parcela;

    @OneToMany(mappedBy = "cobrancaVariavel", cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private List<FormaDeCobranca> formasDeCobranca;

    public CobrancaVariavel() {
    }

    public CobrancaVariavel(Long id, Date emissao, Pessoa pessoa, Cotacao cotacao, String historico,
            OperacaoFinanceira operacaoFinanceira, BigDecimal valor, Date vencimento,
            Nota nota, Boolean entrada, SituacaoDeCobranca situacaoDeCobranca, Filial filial, Integer parcela) throws DadoInvalidoException {
        super(id, valor, emissao, pessoa, cotacao, historico, situacaoDeCobranca, filial, vencimento, operacaoFinanceira);
        this.emissao = emissao == null ? new Date() : emissao;
        this.entrada = entrada;
        this.nota = nota;
        this.parcela = parcela;
        ehAbstracaoValida();
    }

    private final void ehAbstracaoValida() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("valor", "emissao", "historico", "cotacao", "operacaoFinanceira", "filial", "pessoa");
        if (!(this instanceof Credito)) {
            campos = Arrays.asList("valor", "emissao", "historico", "cotacao", "operacaoFinanceira", "filial", "vencimento");
        }
        new ValidadorDeCampos<CobrancaVariavel>().valida(this, campos);
    }

    public Integer getParcela() {
        return parcela;
    }

    public Nota getNota() {
        return nota;
    }

    public List<FormaDeCobranca> getFormasDeCobranca() {
        return formasDeCobranca;
    }

    public void geraPara(Nota nota) {
        this.nota = nota;
        this.emissao = nota.getEmissao();
    }

    public String getValorNaMoedaPadraoFormatado() {
        return MoedaFormatter.format(nota.getCotacao().getConta().getMoeda(), getValorNaMoedaPadrao());
    }

    public Boolean getEntrada() {
        return entrada;
    }

    public OperacaoFinanceira getOperacaoFinanceira() {
        return operacaoFinanceira;
    }

    @Override
    public String toString() {
        return "Cobranca{" + "id=" + super.getId() + ", valor=" + valor + ", emissao=" + emissao + ", pessoa=" + pessoa + ", cotacao=" + cotacao + ", historico=" + historico + ", operacaoFinanceira=" + operacaoFinanceira + ", nota=" + nota + ", entrada=" + entrada + '}';
    }

}
