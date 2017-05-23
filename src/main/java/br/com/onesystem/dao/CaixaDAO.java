package br.com.onesystem.dao;

import br.com.onesystem.domain.Caixa;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;

public class CaixaDAO {

    private String consulta;
    private BundleUtil msg;
    private Map<String, Object> parametros;

    public CaixaDAO() {
        limpar();
    }

    private void limpar() {
        consulta = "";
        msg = new BundleUtil();
        parametros = new HashMap<String, Object>();
    }

    public CaixaDAO buscarCaixas() {
        consulta += "select c from Caixa c where c.id > 0 ";
        return this;
    }

    public CaixaDAO porUsuario(String usuario) {
        consulta += " and c.usuario.pessoa.email = :cUsuario ";
        parametros.put("cUsuario", usuario);
        return this;
    }

    public CaixaDAO porEmAberto() {
        consulta += " and c.fechamento = null ";
        return this;
    }

    public CaixaDAO porUltimoAberto() {
        consulta += " and c.abertura in (select max(ct.abertura) from Caixa ct where ct.fechamento = null) ";
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
        consulta += " and c.id = :cId ";
        parametros.put("cId", id);
        return this;
    }

    public List<Caixa> listaDeResultados() {
        List<Caixa> resultado = new ArmazemDeRegistros<Caixa>(Caixa.class)
                .listaRegistrosDaConsulta(consulta, parametros);
        limpar();
        return resultado;
    }

    public Caixa resultado() throws DadoInvalidoException {
        try {
            Caixa resultado = new ArmazemDeRegistros<Caixa>(Caixa.class)
                    .resultadoUnicoDaConsulta(consulta, parametros);
            limpar();
            return resultado;
        } catch (NoResultException nre) {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("registro_nao_encontrado"));
        }
    }
}
