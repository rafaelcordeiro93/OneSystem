package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.PedidoAFornecedores;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.PedidoAFornecedoresService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@javax.enterprise.context.RequestScoped
public class SelecaoPedidoAFornecedoresEmDefinicaoView extends BasicCrudMBImpl<PedidoAFornecedores> implements Serializable {

    @Inject
    private PedidoAFornecedoresService service;

    @PostConstruct
    public void init() {
        beans = service.buscarPedidosAFornecedoresEmDefinicao();
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

    public PedidoAFornecedoresService getService() {
        return service;
    }

    public void setService(PedidoAFornecedoresService service) {
        this.service = service;
    }
}
