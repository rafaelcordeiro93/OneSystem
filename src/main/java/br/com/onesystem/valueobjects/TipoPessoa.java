package br.com.onesystem.valueobjects;

public enum TipoPessoa {

    PESSOA_FISICA(new Long(1), "Pessoa Física"),
    PESSOA_JURIDICA(new Long(2), "Pessoa Jurídica");

    private Long id;
    private String nome;

    private TipoPessoa(Long id, String nome) {
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
