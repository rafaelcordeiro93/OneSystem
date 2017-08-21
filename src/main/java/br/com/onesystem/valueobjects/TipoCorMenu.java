package br.com.onesystem.valueobjects;

import br.com.onesystem.util.BundleUtil;

public enum TipoCorMenu {

    BRANCO(1, new BundleUtil().getLabel("Branco")),
    CINZA(2, new BundleUtil().getLabel("Cinza")),
    PRETO(3, new BundleUtil().getLabel("Preto"));

    private Integer id;
    private String nome;
    private TipoCorMenu(Integer id, String nome) {
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
