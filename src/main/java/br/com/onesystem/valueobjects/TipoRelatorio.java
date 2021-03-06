package br.com.onesystem.valueobjects;

import br.com.onesystem.util.BundleUtil;

public enum TipoRelatorio {

    ITEM(new Long(1), new BundleUtil().getLabel("Relatorio_de_Item")),
    COBRANCA(new Long(2), new BundleUtil().getLabel("Relatorio_De_Contas")),
    BAIXA(new Long(3), new BundleUtil().getLabel("Relatorio_De_Baixas")),
    COBRANCA_FIXA(new Long(3), new BundleUtil().getLabel("Relatorio_De_Contas_Fixas")),
    AJUSTE_DE_ESTOQUE(new Long(4), new BundleUtil().getLabel("Relatorio_De_Ajuste_De_Estoque")),
    CHEQUES(new Long(5), new BundleUtil().getLabel("Relatorio_De_Cheques")),
    DESPESAS_PROVISIONADAS(new Long(6), new BundleUtil().getLabel("Relatorio_De_Despesa_Provisionada")),
    RECEITAS_PROVISIONADAS(new Long(7), new BundleUtil().getLabel("Relatorio_De_Receita_Provisionada")),
    NOTAS_EMITIDAS(new Long(8), new BundleUtil().getLabel("Relatorio_de_Notas_Emitidas")),
    NOTAS_RECEBIDAS(new Long(9), new BundleUtil().getLabel("Relatorio_de_Notas_Recebidas")),
    PESSOAS(new Long(10), new BundleUtil().getLabel("Relatorio_de_Pessoas")),
    ITEM_RECEBIDO(new Long(11), new BundleUtil().getLabel("Relatorio_de_Item_Emitido")),
    ITEM_EMITIDO(new Long(12), new BundleUtil().getLabel("Relatorio_de_Item_Emitido")),
    CONDICIONAL(new Long(13), new BundleUtil().getLabel("Relatorio_de_Condicional")),
    COMANDA(new Long(14), new BundleUtil().getLabel("Relatorio_de_Comanda")),
    ITEM_COMANDA(new Long(15), new BundleUtil().getLabel("Relatorio_de_Item_Comanda")),
    ORCAMENTO(new Long(16), new BundleUtil().getLabel("Relatorio_de_Orcamento")),
    ITEM_ORCADO(new Long(17), new BundleUtil().getLabel("Relatorio_de_Item_Orcado")),
    ITEM_CONDICIONAL(new Long(18), new BundleUtil().getLabel("Relatorio_de_Item_Condicional")),
    ESTOQUE(new Long(19), new BundleUtil().getLabel("Relatorio_de_Estoque")),
    CONHECIMENTO_DE_FRETE(new Long(20), new BundleUtil().getLabel("Relatorio_de_Conhecimento_de_Frete"));


    private Long id;
    private String nome;

    private TipoRelatorio(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

}
