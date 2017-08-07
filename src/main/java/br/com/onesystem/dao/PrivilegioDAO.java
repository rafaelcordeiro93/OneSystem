package br.com.onesystem.dao;

import br.com.onesystem.domain.GrupoDePrivilegio;
import br.com.onesystem.domain.Modulo;
import br.com.onesystem.domain.Privilegio;
import br.com.onesystem.util.BundleUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrivilegioDAO {

    private String consulta;
    private BundleUtil msg;
    private Map<String, Object> parametros;

    public PrivilegioDAO() {
        limpar();
    }

    private void limpar() {
        consulta = "";
        msg = new BundleUtil();
        parametros = new HashMap<String, Object>();
    }

    public PrivilegioDAO buscarPrivilegiosW() {
        consulta += "select p from Privilegio p where id != null ";
        return this;
    }

    public PrivilegioDAO ePorGrupoDePrivilegio(GrupoDePrivilegio grupoDePrivilegio){
        consulta += " and p.grupoPrivilegio = :pGrupoPrivilegio ";
        parametros.put("pGrupoPrivilegio", grupoDePrivilegio);
        return this;
    }
    
    public PrivilegioDAO ePorModulo(Modulo modulo){
        consulta += " and p.modulo = :pModulo ";
        parametros.put("pModulo", modulo);
        return this;
    }
    
    public PrivilegioDAO ePorEmailDeUsuario(String email) {
        consulta += " and p.usuario.email = :pEmail ";
        parametros.put("pEmail", email);
        return this;
    }

    public PrivilegioDAO ePorJanela(String janela) {
        consulta += " and p.janela.endereco = :pJanela ";
        parametros.put("pJanela", janela);
        return this;
    }
    
    public Privilegio resultadoUnico() {
        Privilegio resultado = new ArmazemDeRegistros<Privilegio>(Privilegio.class)
                .resultadoUnicoDaConsulta(consulta, parametros);
        limpar();
        return resultado;
    }

    public List<Privilegio> listaDeResultados() {
        List<Privilegio> resultado = new ArmazemDeRegistros<Privilegio>(Privilegio.class)
                .listaRegistrosDaConsulta(consulta, parametros);
        limpar();
        return resultado;
    }

}
