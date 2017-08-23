package br.com.onesystem.valueobjects;

import br.com.onesystem.util.BundleUtil;

public enum TipoContabil {

    CREDITAR(new Long(1), new BundleUtil().getLabel("Creditar")),
    DEBITAR(new Long(2), new BundleUtil().getLabel("Debitar")),
    NAO_CONTABILIZAR(new Long(3), new BundleUtil().getLabel("Nao_Contabilizar"));

    private Long id;
    private String nome;

    private TipoContabil(Long id, String nome) {
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
