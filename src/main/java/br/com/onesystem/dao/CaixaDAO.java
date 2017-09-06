package br.com.onesystem.dao;

import br.com.onesystem.domain.Caixa;
import br.com.onesystem.domain.Usuario;
import java.util.Calendar;
import java.util.Date;

public class CaixaDAO extends GenericDAO<Caixa> {

    public CaixaDAO() {
        super(Caixa.class);
        limpar();
    }

    public CaixaDAO porEmailDeUsuario(String usuario) {
        where += " and caixa.usuario.pessoa.email = :cEmailDeUsuario ";
        parametros.put("cEmailDeUsuario", usuario);
        return this;
    }

    public CaixaDAO porUsuario(Usuario usuario) {
        where += " and caixa.usuario = :cUsuario ";
        parametros.put("cUsuario", usuario);
        return this;
    }

    public CaixaDAO emAberto() {
        where += " and caixa.fechamento = null ";
        return this;
    }

    public CaixaDAO porUltimoAberto() {
        where += " and caixa.abertura in (select max(ct.abertura) from Caixa ct where ct.fechamento = null) ";
        return this;
    }

    private Date getDataComHoraZerada(Date data) {
        Calendar c = Calendar.getInstance();
        c.setTime(data);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    private Date getDataComHoraFimDoDia(Date data) {
        Calendar c = Calendar.getInstance();
        c.setTime(data);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        return c.getTime();
    }

    public CaixaDAO porId(Long id) {
        where += " and caixa.id = :cId ";
        parametros.put("cId", id);
        return this;
    }

}
