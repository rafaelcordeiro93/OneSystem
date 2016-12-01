package br.com.onesystem.valueobjects;

public enum TipoOperacao {

    VENDA(1, "Venda"),
    CAMBIO(2, "Câmbio"),
    DEVOLUCAO_CLIENTE(3, "Devolução de Cliente"),
    SIMPLES_REMESSA(4, "Simples Remessa"),
    DEVOLUCAO_FORNECEDOR(5, "Devolução a Fornecedor"),
    VENDA_ENTREGA_FUTURA(6, "Venda para Entrega Futura"),
    ENTREGA_MERCADORIA_VENDIDA(7, "Entrega de Mercadoria Vendida"),
    OUTRAS_SAIDA(8, "Outra Saída"),
    OUTRAS_ENTRADA(9, "Outra Entrada"),
    TRASNFERENCIA(10, "Transferência"),
    REMESSA_PARA_CONSERTO(11, "Remessa para conserto"),
    DEVOLUCAO_ENTREGA_VENDA(12, "Devolução de entrega de venda"),
    DEVOLUCAO_VENDA_FUTURA(13, "Devolução de venda de entrega futura"),
    SAIDA_POR_PERDAS(14, "Saídas por perdas"),
    COMPRA_NORMAL(15, "Compra normal"),
    COMPRA_CONSIGNACAO(16, "Compra em consignação"),
    FATURAMENTO_COMPRA_CONSIGNACAO(17, "Faturamento de compra em consignação"),
    COMPRA_IMOBILIZADO(18, "Compra de Imobilizado"),
    VENDA_IMOBILIZADO(19, "Venda de Imobilizado"),
    CORRECAO(20, "Correção"),
    RECEPCAO(21, "Recepção"),
    DESPESA(22, "Despesa"),
    RECEITA(23, "Receita"),
    TITULO(24, "Título"),
    AVULSO(25, "Avulso"),
    TODOS(26, "Todos"),
    DESPESA_PROVISIONADA(27, "Despesa_Provisionada"),
    RECEITA_PROVISIONADA(28, "Receita_Provisionada"),
    BONIFICACAO(29, "Bonificação"),
    RETORNO_DE_CONSERTO(30,"Retorno de Conserto"),
    DEVOLUCAO_DE_CONSIGNACAO(31,"Devolução de Consignação"),
    OUTRAS(32,"Outras");
    
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
