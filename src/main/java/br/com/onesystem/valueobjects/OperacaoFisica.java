package br.com.onesystem.valueobjects;

import br.com.onesystem.util.BundleUtil;

public enum OperacaoFisica {

    ENTRADA(new Long(1), new BundleUtil().getLabel("Entrada")),
    SAIDA(new Long(2), new BundleUtil().getLabel("Saida")),
    SEM_ALTERACAO(new Long(3), new BundleUtil().getLabel("Sem_Alteracao"));
    
    private Long id;
    private String nome;

    private OperacaoFisica(Long id, String nome) {
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
