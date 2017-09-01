package br.com.onesystem.valueobjects;

import br.com.onesystem.util.BundleUtil;

public enum ClassificacaoFinanceira {

    OPERACIONAIS(new Long(1), new BundleUtil().getLabel("Operacionais")),
    NAO_OPERACIONAIS(new Long(2), new BundleUtil().getLabel("Nao_Operacionais")),
    OUTRAS_OPERACOES(new Long(3), new BundleUtil().getLabel("Outras_Operacoes")),
    FINANCEIRAS(new Long(4), new BundleUtil().getLabel("Financeiras")),
    DEDUCOES_DE_RECEITA_BRUTA(new Long(5), new BundleUtil().getLabel("Deducoes_de_Receita_Bruta")),
    CUSTOS_OPERACIONAIS(new Long(6), new BundleUtil().getLabel("Custos_Operacionais")),
    IMOBILIZADO(new Long(7), new BundleUtil().getLabel("Imobilizado")),
    USO_CONSUMO(new Long(8), new BundleUtil().getLabel("Uso_Consumo")),
    OUTRAS(new Long(9), new BundleUtil().getLabel("Outras")),
    OUTRAS_OPERACIONAIS(new Long(10), new BundleUtil().getLabel("Outras_Operacionais"));

    private final Long id;
    private final String nome;

    private ClassificacaoFinanceira(Long id, String nome) {
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
