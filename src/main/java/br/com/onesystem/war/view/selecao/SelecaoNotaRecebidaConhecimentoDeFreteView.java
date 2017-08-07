package br.com.onesystem.war.view.selecao;

import br.com.onesystem.dao.NotaRecebidaDAO;
import br.com.onesystem.domain.NotaRecebida;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@javax.enterprise.context.RequestScoped
public class SelecaoNotaRecebidaConhecimentoDeFreteView extends BasicCrudMBImpl<NotaRecebida> implements Serializable {

    @Inject
    private NotaRecebidaDAO dao;

    @PostConstruct
    public void init() {
       
        beans = dao.porConhecimentoDeFreteNaoInformado().listaDeResultados();
    }

     @Override
    public void abrirDialogo() {
        exibirNaTela("selecaoNotaRecebidaConhecimentoDeFrete");
    }

    @Override
    public String abrirEdicao() {
        return "notaRecebida";
    }

    @Override
    public List<NotaRecebida> complete(String query) {
        List<NotaRecebida> contasFIltradas = new ArrayList<>();

        if (!StringUtils.containsLetter(query)) {
            for (NotaRecebida c : beans) {
                if (StringUtils.startsWithIgnoreCase(c.getId().toString(), query)) {
                    contasFIltradas.add(c);
                }
            }
        }
        return contasFIltradas;
    }

    public NotaRecebidaDAO getDao() {
        return dao;
    }

    public void setDao(NotaRecebidaDAO dao) {
        this.dao = dao;
    }
}
