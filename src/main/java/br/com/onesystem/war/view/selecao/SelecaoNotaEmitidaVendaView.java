package br.com.onesystem.war.view.selecao;

import br.com.onesystem.dao.NotaEmitidaDAO;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.valueobjects.TipoOperacao;
import br.com.onesystem.war.service.NotaEmitidaService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@javax.enterprise.context.RequestScoped
public class SelecaoNotaEmitidaVendaView extends BasicCrudMBImpl<NotaEmitida> implements Serializable {

    @Inject
    private NotaEmitidaDAO dao;

    @PostConstruct
    public void init() {
        beans = dao.porTipoOperacao(TipoOperacao.VENDA).listaDeResultados();
    }

    @Override
    public void abrirDialogo() {
        exibirNaTela("selecaoNotaEmitidaVenda");
    }

    @Override
    public String abrirEdicao() {
        return "/menu/vendas/notaEmitida";
    }

    @Override
    public List<NotaEmitida> complete(String query) {
        List<NotaEmitida> contasFIltradas = new ArrayList<>();

        if (!StringUtils.containsLetter(query)) {
            for (NotaEmitida c : beans) {
                if (StringUtils.startsWithIgnoreCase(c.getId().toString(), query)) {
                    contasFIltradas.add(c);
                }
            }
        }
        return contasFIltradas;
    }

    public NotaEmitidaDAO getDao() {
        return dao;
    }

    public void setDao(NotaEmitidaDAO dao) {
        this.dao = dao;
    }

}
