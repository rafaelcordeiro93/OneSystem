package br.com.onesystem.dao;

import br.com.onesystem.domain.GrupoDePrivilegio;
import br.com.onesystem.domain.Modulo;
import br.com.onesystem.domain.Privilegio;

public class PrivilegioDAO extends GenericDAO<Privilegio> {

    public PrivilegioDAO() {
        super(Privilegio.class);
    }

    public PrivilegioDAO ePorGrupoDePrivilegio(GrupoDePrivilegio grupoDePrivilegio) {
        where += " and privilegio.grupoPrivilegio = :pGrupoPrivilegio ";
        parametros.put("pGrupoPrivilegio", grupoDePrivilegio);
        return this;
    }

    public PrivilegioDAO ePorModulo(Modulo modulo) {
        where += " and privilegio.modulo = :pModulo ";
        parametros.put("pModulo", modulo);
        return this;
    }

    public PrivilegioDAO ePorEmailDeUsuario(String email) {
        where += " and privilegio.usuario.email = :pEmail ";
        parametros.put("pEmail", email);
        return this;
    }

    public PrivilegioDAO ePorJanela(String janela) {
        where += " and privilegio.janela.endereco = :pJanela ";
        parametros.put("pJanela", janela);
        return this;
    }

}
