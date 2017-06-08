package br.com.onesystem.valueobjects;

import br.com.onesystem.util.BundleUtil;

public enum TipoLancamento {

    EMITIDA(new Long(1), new BundleUtil().getLabel("Emitida")),
    RECEBIDA(new Long(2), new BundleUtil().getLabel("Recebida")),
    OUTROS(new Long(3), new BundleUtil().getLabel("Outros"));

    private Long id;
    private String nome;

    private TipoLancamento(Long id, String nome) {
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
