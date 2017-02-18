package br.com.onesystem.valueobjects;

import br.com.onesystem.util.BundleUtil;

public enum TipoOperacao {

    VENDA(1, new BundleUtil().getLabel("VENDA")),
    CAMBIO(2, new BundleUtil().getLabel("CAMBIO")),
    DEVOLUCAO_CLIENTE(3, new BundleUtil().getLabel("DEVOLUCAO_CLIENTE")),
    SIMPLES_REMESSA(4, new BundleUtil().getLabel("SIMPLES_REMESSA")),
    DEVOLUCAO_FORNECEDOR(5, new BundleUtil().getLabel("DEVOLUCAO_FORNECEDOR")),
    VENDA_ENTREGA_FUTURA(6, new BundleUtil().getLabel("VENDA_ENTREGA_FUTURA")),
    ENTREGA_MERCADORIA_VENDIDA(7, new BundleUtil().getLabel("ENTREGA_MERCADORIA_VENDIDA")),
    OUTRAS_SAIDA(8, new BundleUtil().getLabel("OUTRAS_SAIDA")),
    OUTRAS_ENTRADA(9, new BundleUtil().getLabel("OUTRAS_ENTRADA")),
    TRASNFERENCIA(10, new BundleUtil().getLabel("TRASNFERENCIA")),
    REMESSA_PARA_CONSERTO(11, new BundleUtil().getLabel("REMESSA_PARA_CONSERTO")),
    DEVOLUCAO_ENTREGA_VENDA(12, new BundleUtil().getLabel("DEVOLUCAO_ENTREGA_VENDA")),
    DEVOLUCAO_VENDA_FUTURA(13, new BundleUtil().getLabel("DEVOLUCAO_VENDA_FUTURA")),
    SAIDA_POR_PERDAS(14, new BundleUtil().getLabel("SAIDA_POR_PERDAS")),
    COMPRA_NORMAL(15, new BundleUtil().getLabel("COMPRA_NORMAL")),
    COMPRA_CONSIGNACAO(16, new BundleUtil().getLabel("COMPRA_CONSIGNACAO")),
    FATURAMENTO_COMPRA_CONSIGNACAO(17, new BundleUtil().getLabel("FATURAMENTO_COMPRA_CONSIGNACAO")),
    COMPRA_IMOBILIZADO(18, new BundleUtil().getLabel("COMPRA_IMOBILIZADO")),
    VENDA_IMOBILIZADO(19, new BundleUtil().getLabel("VENDA_IMOBILIZADO")),
    CORRECAO(20, new BundleUtil().getLabel("CORRECAO")),
    RECEPCAO(21, new BundleUtil().getLabel("RECEPCAO")),
    DESPESA(22, new BundleUtil().getLabel("DESPESA")),
    RECEITA(23, new BundleUtil().getLabel("RECEITA")),
    TITULO(24, new BundleUtil().getLabel("TITULO")),
    AVULSO(25, new BundleUtil().getLabel("AVULSO")),
    TODOS(26, new BundleUtil().getLabel("VENDA")),
    DESPESA_PROVISIONADA(27, new BundleUtil().getLabel("DESPESA_PROVISIONADA")),
    RECEITA_PROVISIONADA(28, new BundleUtil().getLabel("RECEITA_PROVISIONADA")),
    BONIFICACAO(29, new BundleUtil().getLabel("BONIFICACAO")),
    RETORNO_DE_CONSERTO(30, new BundleUtil().getLabel("RETORNO_DE_CONSERTO")),
    DEVOLUCAO_DE_CONSIGNACAO(31, new BundleUtil().getLabel("DEVOLUCAO_DE_CONSIGNACAO")),
    OUTRAS(32, new BundleUtil().getLabel("OUTRAS"));

    private Integer id;
    private String nome;

    private TipoOperacao(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

}
