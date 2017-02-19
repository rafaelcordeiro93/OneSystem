/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Rafael Fernando Rauber
 */
public class Banco extends Pagamento {

    private String banco;

    public Banco(String nome, String banco) {
        super(nome);
        this.banco = banco;
    }

    public String getBanco() {
        return banco;
    }

    @Override
    public String toString() {
        return "Banco{" + "banco=" + banco + "Nome: " + super.getNome() + '}';
    }

}
