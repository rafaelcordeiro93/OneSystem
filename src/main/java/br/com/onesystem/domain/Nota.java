/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author rauber
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "NOTA_CLASSE_NOME")
public class Nota implements Serializable {
    
    @Id
    @SequenceGenerator(name = "SEQ_NOTA", sequenceName = "SEQ_NOTA",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "SEQ_NOTA", strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotNull(message = "{pessoa_not_null}")
    @ManyToOne
    private Pessoa pessoa;
    @NotNull(message = "{operacao_not_null}")
    @ManyToOne
    private Operacao operacao;
    @NotNull(message = "{forma_recebimento_not_null}")
    @ManyToOne
    private FormaDeRecebimento formaDeRecebimento;
    @ManyToOne
    private ListaDePreco listaDePreco;
    @Temporal(TemporalType.TIMESTAMP)
    private Date emissao;
    private boolean cancelada;
    @OneToOne(mappedBy = "notaEmitida", cascade = {CascadeType.ALL})
    private ValoresAVista valoresAVista;
    @OneToMany(mappedBy = "notaEmitida", cascade = {CascadeType.ALL})
    private List<Baixa> baixaDinheiro;
    @OneToMany(mappedBy = "notaEmitida", cascade = {CascadeType.ALL})
    private List<ItemEmitido> itensEmitidos;
    @OneToOne(mappedBy = "notaEmitida", cascade = {CascadeType.ALL})
    private Credito credito;
    @OneToMany(mappedBy = "notaEmitida", cascade = {CascadeType.ALL})
    private List<Parcela> parcelas;
    @NotNull(message = "{moeda_padrao_not_null}")
    @ManyToOne(optional = false)
    private Moeda moedaPadrao;
}
