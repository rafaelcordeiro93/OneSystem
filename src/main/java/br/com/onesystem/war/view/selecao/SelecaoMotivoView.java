package br.com.onesystem.war.view.selecao;

import br.com.onesystem.dao.MotivoDAO;
import br.com.onesystem.domain.Motivo;
import br.com.onesystem.util.StringUtils;
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
public class SelecaoMotivoView extends BasicCrudMBImpl<Motivo> implements Serializable {

    @Inject
    private EntityManager manager;

    @Inject
    private MotivoDAO dao;

    @PostConstruct
    public void init() {
        buscarDados();
    }

    public void buscarDados() {
        beans = dao.listaDeResultados(manager);
    }

    @Override
    public void abrirDialogo() {
        exibirNaTela("arquivo/selecao/selecaoMotivo");
    }

    @Override
    public String abrirEdicao() {
        return "/menu/arquivo/motivo";
    }

    @Override
    public List<Motivo> complete(String query) {
        buscarDados();
        List<Motivo> motivosFIltrados = new ArrayList<>();
        for (Motivo b : beans) {
            if (StringUtils.startsWithIgnoreCase(b.getNome(), query)) {
                motivosFIltrados.add(b);
            }
        }
        if (!StringUtils.containsLetter(query)) {
            for (Motivo m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    motivosFIltrados.add(m);
                }
            }
        }
        return motivosFIltrados;
    }

    public String getIcon() {
        return "fa-certificate";
    }
}
