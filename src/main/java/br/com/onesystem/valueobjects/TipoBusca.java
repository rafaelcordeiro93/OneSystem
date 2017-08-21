package br.com.onesystem.valueobjects;

public enum TipoBusca {

    EMISSAO(1, "EMISSAO"),
    PAGAMENTO(2, "PAGAMENTO"),
    VENCIMENTO(3, "VENCIMENTO"),
    RECEBIMENTO(4, "RECEBIMENTO");

    private Integer id;
    private String nome;

    private TipoBusca(Integer id, String nome) {
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
