package br.com.onesystem.war.view.selecao;

import br.com.onesystem.dao.PedidoAFornecedoresDAO;
import br.com.onesystem.domain.PedidoAFornecedores;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.valueobjects.EstadoDePedido;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

@Named
@ViewScoped
public class SelecaoPedidoAFornecedoresEmDefinicaoView extends BasicCrudMBImpl<PedidoAFornecedores> implements Serializable {

    @Inject
    private EntityManager manager;

    @Inject
    private PedidoAFornecedoresDAO dao;

    @PostConstruct
    public void init() {
        buscarDados();
    }

    public void buscarDados() {
        beans = dao.porEstado(EstadoDePedido.EM_DEFINICAO).listaDeResultados(manager);
    }

    @Override
    public void abrirDialogo() {
        exibirNaTela("estoque/selecao/selecaoPedidoAFornecedoresEmDefinicao");
    }

    @Override
    public String abrirEdicao() {
        return "/menu/estoque/pedidoAFornecedores";
    }

    @Override
    public List<PedidoAFornecedores> complete(String query) {
        buscarDados();
        List<PedidoAFornecedores> contasFIltradas = new ArrayList<>();

        if (!StringUtils.containsLetter(query)) {
            for (PedidoAFornecedores c : beans) {
                if (StringUtils.startsWithIgnoreCase(c.getId().toString(), query)) {
                    contasFIltradas.add(c);
                }
            }
        }
        return contasFIltradas;
    }

}
