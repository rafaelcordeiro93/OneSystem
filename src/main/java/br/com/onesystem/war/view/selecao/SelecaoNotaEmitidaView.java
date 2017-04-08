package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.NotaEmitidaService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class SelecaoNotaEmitidaView extends BasicCrudMBImpl<NotaEmitida> implements Serializable {

    @ManagedProperty("#{notaEmitidaService}")
    private NotaEmitidaService service;

    @PostConstruct
    public void init() {
        beans = service.buscarNotasEmitidas();
    }

    @Override
    public void abrirDialogo() {
        exibirNaTela("selecaoNotaEmitida");
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

    public NotaEmitidaService getService() {
        return service;
    }

    public void setService(NotaEmitidaService service) {
        this.service = service;
    }
}
