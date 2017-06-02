package br.com.onesystem.war.builder;

import br.com.onesystem.domain.BoletoDeCartao;
import br.com.onesystem.domain.Cheque;
import br.com.onesystem.domain.Credito;
import br.com.onesystem.domain.FormaDeRecebimento;
import br.com.onesystem.domain.ItemDeNota;
import br.com.onesystem.domain.ListaDePreco;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.domain.NotaRecebida;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.domain.Orcamento;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.Cobranca;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.Titulo;
import br.com.onesystem.domain.ValorPorCotacao;
import br.com.onesystem.domain.builder.NotaRecebidaBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.BuilderView;
import br.com.onesystem.util.MoedaFomatter;
import br.com.onesystem.valueobjects.EstadoDeNota;
import br.com.onesystem.valueobjects.TipoLancamento;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class NotaRecebidaBV implements Serializable, BuilderView<NotaRecebida> {

    private static final long serialVersionUID = 6686124108160060627L;

    private Long id;
    private Pessoa pessoa;
    private Operacao operacao;
    private List<ItemDeNota> itens;
    private List<Cobranca> cobrancas;
    private List<ValorPorCotacao> valorPorCotacao;
    private ListaDePreco listaDePreco;
    private Date emissao = new Date();
    private FormaDeRecebimento formaDeRecebimento;
    private Moeda moedaPadrao;
    private BigDecimal desconto;
    private BigDecimal acrescimo;
    private BigDecimal despesaCobranca;
    private BigDecimal frete;
    private BigDecimal aFaturar;
    private BigDecimal totalEmDinheiro;
    private BigDecimal porcentagemAcrescimo;
    private BigDecimal porcentagemDesconto;
    private Cotacao cotacao;
    private Integer numeroParcelas;
    private EstadoDeNota estado;

    public NotaRecebidaBV(NotaRecebida nota) {
        this.id = nota.getId();
        this.pessoa = nota.getPessoa();
        this.operacao = nota.getOperacao();
        this.cobrancas = nota.getParcelas();
        this.itens = nota.getItens();
        this.listaDePreco = nota.getListaDePreco();
        this.formaDeRecebimento = nota.getFormaDeRecebimento();
        this.emissao = nota.getEmissao();
        this.estado = nota.getEstado();
        this.moedaPadrao = nota.getMoedaPadrao();
    }

    public NotaRecebidaBV() {
        itens = new ArrayList<>();
        cobrancas = new ArrayList<>();
        valorPorCotacao = new ArrayList<>();
    }

    public void adiciona(ItemDeNotaBV item) throws DadoInvalidoException {
        item.setId(getCodigoItem());
        this.itens.add(item.construirComId());
    }

    public void atualiza(ItemDeNota itemSelecionado, ItemDeNota item) throws DadoInvalidoException {
        itens.set(itens.indexOf(itemSelecionado), item);
    }

    public void remove(ItemDeNota item) {
        itens.remove(item);
    }

    private Long getCodigoItem() {
        Long id = (long) 1;
        if (!getItens().isEmpty()) {
            for (ItemDeNota dp : getItens()) {
                if (dp.getId() >= id) {
                    id = dp.getId() + 1;
                }
            }
        }
        return id;
    }

    public void adiciona(ValorPorCotacao valorPorCotacao) {
        this.valorPorCotacao.add(valorPorCotacao);
    }

    public void adiciona(Cobranca cobranca) throws DadoInvalidoException {
        if (cobranca instanceof Cheque) {
            Cheque cheque = (Cheque) cobranca;
            ChequeBV builder = new ChequeBV(cheque);
            builder.setId(getIdCobranca());
            builder.setPessoa(getPessoa());
            builder.setTipoLancamento(TipoLancamento.EMITIDA);
            cobranca = builder.construirComID();
        }
        if (cobranca instanceof BoletoDeCartao) {
            BoletoDeCartaoBV builder = new BoletoDeCartaoBV((BoletoDeCartao) cobranca);
            builder.setId(getIdCobranca());
        }
        if (cobranca instanceof Titulo) {
            TituloBV builder = new TituloBV((Titulo) cobranca);
            builder.setId(getIdCobranca());
        }
        this.cobrancas.add(cobranca);
    }

    public void atualiza(Cobranca cobrancaSelecionada, Cobranca cobranca) {
        cobrancas.set(cobrancas.indexOf(cobrancaSelecionada), cobranca);
    }

    public void remove(Cobranca cobranca) {
        cobrancas.remove(cobranca);
    }

    private Long getIdCobranca() {
        Long id = (long) 1;
        try {
            if (!cobrancas.isEmpty()) {
                for (Cobranca c : cobrancas) {
                    if (c.getId() >= id) {
                        id = c.getId() + 1;
                    }
                }
            }
        } catch (NullPointerException npe) {
            return id;
        }
        return id;
    }

    public List<Cobranca> getChequesDeEntradas() {
        if (cobrancas != null) {
            List<Cobranca> entradas = cobrancas.stream().filter(p -> p.getEntrada() == true).filter(p -> p instanceof Cheque).collect(Collectors.toList());
            entradas.sort(Comparator.comparingLong(Cobranca::getDias));
            return entradas;
        } else {
            return null;
        }
    }

    public BigDecimal getTotalChequeDeEntrada() {
        BigDecimal total = BigDecimal.ZERO;
        try {
            for (Cobranca c : cobrancas) {
                if (c instanceof Cheque && c.getEntrada() != null && c.getEntrada() == true) {
                    if (c.getCotacao() != null && c.getCotacao() != cotacao) {
                        total = total.add(c.getValor().divide(c.getCotacao().getValor(), 2, BigDecimal.ROUND_UP));
                    } else {
                        total = total.add(c.getValor());
                    }
                }
            }
        } catch (NullPointerException npe) {
            return null;
        }
        return total.compareTo(BigDecimal.ZERO) == 0 ? null : total;
    }

    public String getTotalChequeDeEntradaFormatado() {
        return MoedaFomatter.format(moedaPadrao, getTotalChequeDeEntrada());
    }

    public BigDecimal getTotalItens() {
        return itens.stream().map(ItemDeNota::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public String getTotalItensFormatado() {
        return MoedaFomatter.format(moedaPadrao, getTotalItens());
    }

    public BigDecimal getTotalNota() {
        BigDecimal a = acrescimo == null ? BigDecimal.ZERO : acrescimo;
        BigDecimal f = frete == null ? BigDecimal.ZERO : frete;
        BigDecimal c = despesaCobranca == null ? BigDecimal.ZERO : despesaCobranca;
        BigDecimal d = desconto == null ? BigDecimal.ZERO : desconto;
        return getTotalItens().add(a.add(f.add(c))).subtract(d);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public ListaDePreco getListaDePreco() {
        return listaDePreco;
    }

    public void setListaDePreco(ListaDePreco listaDePreco) {
        this.listaDePreco = listaDePreco;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Operacao getOperacao() {
        return operacao;
    }

    public Moeda getMoedaPadrao() {
        return moedaPadrao;
    }

    public void setMoedaPadrao(Moeda moedaPadrao) {
        this.moedaPadrao = moedaPadrao;
    }

    public void setOperacao(Operacao operacao) {
        this.operacao = operacao;
    }

    public List<ItemDeNota> getItens() {
        return itens;
    }

    public void setItens(List<ItemDeNota> itensEmitidos) {
        this.itens = itensEmitidos;
    }

    public FormaDeRecebimento getFormaDeRecebimento() {
        return formaDeRecebimento;
    }

    public void setFormaDeRecebimento(FormaDeRecebimento formaDeRecebimento) {
        this.formaDeRecebimento = formaDeRecebimento;
    }

    public Date getEmissao() {
        return emissao;
    }

    public void setEmissao(Date emissao) {
        this.emissao = emissao;
    }

    public Cotacao getCotacao() {
        return cotacao;
    }

    public void setCotacao(Cotacao cotacao) {
        this.cotacao = cotacao;
    }

    public List<Cobranca> getCobrancas() {
        return cobrancas;
    }

    public void setParcelas(List<Cobranca> parcelas) {
        this.cobrancas = parcelas;
    }

    public List<ValorPorCotacao> getValoresPorCotacao() {
        return valorPorCotacao;
    }

    public void setValoresPorCotacao(List<ValorPorCotacao> valoresPorCotacao) {
        this.valorPorCotacao = valoresPorCotacao;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public Integer getNumeroParcelas() {
        return numeroParcelas;
    }

    public void setNumeroParcelas(Integer numeroParcelas) {
        this.numeroParcelas = numeroParcelas;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }

    public BigDecimal getAcrescimo() {
        return acrescimo;
    }

    public void setAcrescimo(BigDecimal acrescimo) {
        this.acrescimo = acrescimo;
    }

    public BigDecimal getDespesaCobranca() {
        return despesaCobranca;
    }

    public void setDespesaCobranca(BigDecimal despesaCobranca) {
        this.despesaCobranca = despesaCobranca;
    }

    public BigDecimal getFrete() {
        return frete;
    }

    public void setFrete(BigDecimal frete) {
        this.frete = frete;
    }

    public BigDecimal getAFaturar() {
        return aFaturar;
    }

    public void setAFaturar(BigDecimal aFaturar) {
        this.aFaturar = aFaturar;
    }

    public BigDecimal getTotalEmDinheiro() {
        return totalEmDinheiro;
    }

    public void setTotalEmDinheiro(BigDecimal totalEmDinheiro) {
        this.totalEmDinheiro = totalEmDinheiro;
    }

    public BigDecimal getPorcentagemAcrescimo() {
        return porcentagemAcrescimo;
    }

    public void setPorcentagemAcrescimo(BigDecimal porcentagemAcrescimo) {
        this.porcentagemAcrescimo = porcentagemAcrescimo;
    }

    public BigDecimal getPorcentagemDesconto() {
        return porcentagemDesconto;
    }

    public void setPorcentagemDesconto(BigDecimal porcentagemDesconto) {
        this.porcentagemDesconto = porcentagemDesconto;
    }

    public NotaRecebida construir() throws DadoInvalidoException {
        return new NotaRecebidaBuilder().comAFaturar(aFaturar).comAcrescimo(acrescimo)
                .comCobrancas(cobrancas).comDesconto(desconto).comDespesaCobranca(despesaCobranca)
                .comFormaDeRecebimento(formaDeRecebimento).comFrete(frete).comItens(itens).comListaDePreco(listaDePreco)
                .comMoedaPadrao(moedaPadrao).comOperacao(operacao).comEmissao(emissao)
                .comPessoa(pessoa).comTotalEmDinheiro(totalEmDinheiro).comValorPorCotacao(valorPorCotacao).construir();
    }

    public NotaRecebida construirComID() throws DadoInvalidoException {
        return new NotaRecebidaBuilder().comId(id).comAFaturar(aFaturar).comAcrescimo(acrescimo)
                .comCobrancas(cobrancas).comDesconto(desconto).comDespesaCobranca(despesaCobranca)
                .comFormaDeRecebimento(formaDeRecebimento).comFrete(frete).comItens(itens).comListaDePreco(listaDePreco)
                .comMoedaPadrao(moedaPadrao).comOperacao(operacao).comEmissao(emissao)
                .comPessoa(pessoa).comTotalEmDinheiro(totalEmDinheiro).comValorPorCotacao(valorPorCotacao).construir();
    }

}
