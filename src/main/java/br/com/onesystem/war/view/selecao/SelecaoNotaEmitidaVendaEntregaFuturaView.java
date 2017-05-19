package br.com.onesystem.war.view.selecao;

import br.com.onesystem.dao.NotaEmitidaDAO;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.valueobjects.TipoOperacao;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@javax.enterprise.context.RequestScoped
public class SelecaoNotaEmitidaVendaEntregaFuturaView extends BasicCrudMBImpl<NotaEmitida> implements Serializable {

    @Inject
    private NotaEmitidaDAO dao;

    @PostConstruct
    public void init() {
        beans = dao.porTipoOperacao(TipoOperacao.VENDA_ENTREGA_FUTURA).listaDeResultados();
    }

    @Override
    public void abrirDialogo() {
        exibirNaTela("selecaoNotaEmitidaVendaEntregaFutura");
    }

    @Override
    public String abrirEdicao() {
        return "notaEmitida";
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
