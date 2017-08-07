package br.com.onesystem.valueobjects;

public enum ClassificacaoFinanceira {

    OPERACIONAIS("Operacionais", "OPERACIONAIS"),
    NAO_OPERACIONAIS("Não Operacionais", "NAO_OPERACIONAIS"),
    OUTRAS_OPERACOES("Outras Operações", "OUTRAS_OPERACOES"),
    FINANCEIRAS("Financeiras", "FINANCEIRAS"),
    DEDUCOES_DE_RECEITA_BRUTA("Deduções de Receita Bruta", "DEDUCOES_DE_RECEITA_BRUTA"),
    CUSTOS_OPERACIONAIS("Custos Operacionais", "CUSTOS_OPERACIONAIS"),
    IMOBILIZADO("Imobilizado", "IMOBILIZADO"),
    USO_CONSUMO("Uso Consumo", "USO_CONSUMO"),
    OUTRAS("Outras", "OUTRAS");

    private final String classificacao;
    private final String nome;

    private ClassificacaoFinanceira(String classificacao, String nome) {
        this.classificacao = classificacao;
        this.nome = nome;
    }

    public String getClassificacao() {
        return classificacao;
    }

    public String getNome() {
        return nome;
    }

}
