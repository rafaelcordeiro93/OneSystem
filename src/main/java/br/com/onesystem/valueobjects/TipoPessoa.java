package br.com.onesystem.valueobjects;

public enum TipoPessoa {

    PESSOA_FISICA("Pessoa Física"),
    PESSOA_JURIDICA("Pessoa Jurídica");
    private String tipoPessoa;

    private TipoPessoa(String tipoPersona) {
        this.tipoPessoa = tipoPersona;
    }

    public String getTipoPessoa() {
        return tipoPessoa;
    }
}
