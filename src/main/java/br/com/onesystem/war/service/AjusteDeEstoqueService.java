package br.com.onesystem.war.service;

import br.com.onesystem.dao.AjusteDeEstoqueDAO;
import br.com.onesystem.dao.Armazem;
import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.dao.ItemDAO;
import br.com.onesystem.domain.AjusteDeEstoque;
import br.com.onesystem.domain.Estoque;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.OperacaoDeEstoque;
import br.com.onesystem.domain.builder.EstoqueBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.war.view.selecao.SelecaoItemView;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class AjusteDeEstoqueService implements Serializable {

    @Inject
    private ArmazemDeRegistros<AjusteDeEstoque> armazem;

    @Inject
    private AjusteDeEstoqueDAO dao;

    @Inject
    private OperacaoDeEstoqueService operacaoEstoqueService;

    public List<AjusteDeEstoque> buscarAjusteDeEstoques() {
        return armazem.daClasse(AjusteDeEstoque.class).listaTodosOsRegistros();
    }

    public void atualizaEstoque(AjusteDeEstoque ajuste) throws DadoInvalidoException {
        List<OperacaoDeEstoque> listaOpEstoque = operacaoEstoqueService.buscarOperacoesDeEstoquePor(ajuste.getOperacao());
        List<Estoque> adicionar = new ArrayList<>();
        if (ajuste.getEstoque() == null) {
            ajuste.inicializaEstoque();
        }
        for (OperacaoDeEstoque op : listaOpEstoque) {
            boolean encontrou = false;
            for (Estoque e : ajuste.getEstoque()) {
                System.out.println("1");
                if (ajuste.getId() != null) {
                    e.atualizaQuantidade(ajuste.getQuantidade());
                    encontrou = true;
                    break;
                }
            }
            if (!encontrou) {
                adicionar.add(new EstoqueBuilder().comDeposito(ajuste.getDeposito()).comItem(ajuste.getItem()).comEmissao(ajuste.getEmissao()).comQuantidade(ajuste.getQuantidade())
                        .comOperacaoDeEstoque(op).construir());
            }
        }
        for (Estoque a : adicionar) {
            ajuste.adiciona(a);
        }
    }

}
