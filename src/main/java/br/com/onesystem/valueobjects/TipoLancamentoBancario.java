package br.com.onesystem.valueobjects;

import br.com.onesystem.util.BundleUtil;

public enum TipoLancamentoBancario {

    LANCAMENTO(new Long(1), new BundleUtil().getLabel("Lancamento")),
    ESTORNO(new Long(2), new BundleUtil().getLabel("Estorno"));
   
    private Long id;
    private String nome;

    private TipoLancamentoBancario(Long id, String nome) {
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
