/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Rafael Fernando Rauber
 */
public abstract class Pagamento {

    private String nome;

    public Pagamento(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return "Pagamento{" + "nome=" + nome + '}';
    }

}
