package br.com.onesystem.dao;

import br.com.onesystem.domain.Motivo;

public class MotivoDAO extends GenericDAO<Motivo> {

    public MotivoDAO() {
        super(Motivo.class);
        limpar();
    }

    public MotivoDAO porId(Long id) {
        if (id != null) {
            where += " and motivo.id = :bId ";
            parametros.put("bId", id);
        }
        return this;
    }

    public MotivoDAO porNome(String nome) {
        if (nome != null) {
            where += " and motivo.nome = :bNome ";
            parametros.put("bNome", nome);
        }
        return this;
    }

}
