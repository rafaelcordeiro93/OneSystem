package br.com.onesystem.dao;

import br.com.onesystem.domain.Cobranca;

public class CobrancaDAO extends GenericDAO<Cobranca> {

    public CobrancaDAO() {
        super(Cobranca.class);
        limpar();
    }

    public CobrancaDAO consultaTitulos() {
        query = "select titulo from Titulo titulo";
        where = " where titulo.id != 0";
        return this;
    }
    
     public CobrancaDAO consultaCheques() {
        query = "select cheque from Cheque cheque";
        where = " where cheque.id != 0";
        return this;
    }
     
     public CobrancaDAO orderByNomeMoeda(){
         order = " order by cobranca.cotacao.conta.moeda.nome ";
         return this;
     }

}
