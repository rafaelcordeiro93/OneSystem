package br.com.onesystem.valueobjects;

import br.com.onesystem.util.BundleUtil;

public enum TipoRelatorio {

    ITEM(new Long(1), new BundleUtil().getLabel("Relatorio_de_Item")),
    CONTAS(new Long(2), new BundleUtil().getLabel("Relatorio_De_Contas")),
    AJUSTE_DE_ESTOQUE(new Long(3), new BundleUtil().getLabel("Relatorio_De_Ajuste_De_Estoque")),
    CHEQUES(new Long(4), new BundleUtil().getLabel("Relatorio_De_Cheques")),
    DESPESAS_PROVISIONADAS(new Long(5), new BundleUtil().getLabel("Relatorio_De_Despesa_Provisionada")),
    RECEITAS_PROVISIONADAS(new Long(6), new BundleUtil().getLabel("Relatorio_De_Receita_Provisionada")),
    NOTAS_EMITIDAS(new Long(7), new BundleUtil().getLabel("Relatorio_de_Notas_Emitidas")),
    NOTAS_RECEBIDAS(new Long(8), new BundleUtil().getLabel("Relatorio_de_Notas_Recebidas")),
    PESSOAS(new Long(9), new BundleUtil().getLabel("Relatorio_de_Pessoas"));

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
