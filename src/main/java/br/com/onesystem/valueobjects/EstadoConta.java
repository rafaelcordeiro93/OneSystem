package br.com.onesystem.valueobjects;

public enum EstadoConta {

    ABERTO(1,"ABERTO"),
    PAGO(2,"PAGO"),
    RECEBIDO(3,"RECEBIDO");
    
    private Integer id;
    private String nome;

    private EstadoConta(Integer id, String nome) {
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
