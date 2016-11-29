/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.valueobjects.TipoContabil;
import br.com.onesystem.valueobjects.TipoNota;
import br.com.onesystem.valueobjects.TipoOperacao;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author Rafael
 */
@Entity
@SequenceGenerator(sequenceName = "SEQ_OPERACAO", initialValue = 1, allocationSize = 1,
        name = "SEQ_OPERACAO")
public class Operacao implements Serializable {

    @Id
    @GeneratedValue(generator = "SEQ_OPERACAO", strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotNull(message = "{nome_not_null}")
    @Length(min = 4, max = 80, message = "{nome_lenght}")
    private String nome;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "{operacao_financeira_not_null}")
    private OperacaoFinanceira operacaoFinanceira;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "{tipo_nota_not_null}")
    private TipoNota tipoNota;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "{tipo_operacao_not_null}")
    private TipoOperacao tipoOperacao;
    @NotNull(message = "{receita_venda_vista_not_null}")
    @ManyToOne
    private Receita vendaAVista;
    @NotNull(message = "{receita_venda_prazo_not_null}")
    @ManyToOne
    private Receita vendaAPrazo;
    @NotNull(message = "{receita_servico_vista_not_null}")
    @ManyToOne
    private Receita servicoAVista;
    @NotNull(message = "{receita_servico_prazo_not_null}")
    @ManyToOne
    private Receita servicoAPrazo;
    @NotNull(message = "{receita_frete_not_null}")
    @ManyToOne
    private Receita receitaFrete;
    @NotNull(message = "{despesa_CMV_not_null}")
    @ManyToOne
    private Despesa despesaCMV;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "{contabilizar_CMV_not_null}")
    private TipoContabil contabilizarCMV;
    @NotNull(message = "{compra_vista_not_null}")
    @ManyToOne
    private Despesa compraAVista;
    @NotNull(message = "{compra_prazo_not_null}")
    @ManyToOne
    private Despesa compraAPrazo;

    public Operacao() {
    }

}
