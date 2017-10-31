package br.com.onesystem.war.service;

import br.com.onesystem.dao.CaixaDAO;
import br.com.onesystem.domain.Caixa;
import br.com.onesystem.domain.Configuracao;
import br.com.onesystem.domain.Usuario;
import br.com.onesystem.util.MoedaFormatter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;

public class CaixaService implements Serializable {

    @Inject
    private CaixaDAO dao;

    @Inject
    private ConfiguracaoService cfgService;
    
    @Inject
    private EntityManager manager;

    public List<Caixa> buscarCaixas() {
        return dao.listaDeResultados(manager);
    }

    public Caixa getCaixaAbertoDo(Usuario usuario) {
        List<Caixa> lista = dao.porUsuario(usuario).emAberto().listaDeResultados(manager);
        if (lista.size() == 1) {
            return lista.get(0);
        } else {
            return null;
        }
    }

    public List<Caixa> getListaDeCaixaAbertoDo(Usuario usuario) {
        List<Caixa> lista = dao.porUsuario(usuario).emAberto().listaDeResultados(manager);
        if (lista.size() > 0) {
            return lista;
        } else {
            return new ArrayList<>();
        }
    }

    public String getSaldoFormatado(Caixa caixa) {
        Configuracao cfg = cfgService.buscar();
        return MoedaFormatter.format(cfg.getMoedaPadrao(), caixa.getSaldo());
    }
}
