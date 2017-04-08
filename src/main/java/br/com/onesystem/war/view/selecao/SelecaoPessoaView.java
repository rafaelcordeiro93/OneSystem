package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.PessoaService;
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
public class SelecaoPessoaView extends BasicCrudMBImpl<Pessoa> implements Serializable {

    @ManagedProperty("#{pessoaService}")
    private PessoaService service;

    @PostConstruct
    public void init() {
        beans = service.buscarPessoas();
    }

    @Override
    public void abrirDialogo() {
        exibirNaTela("selecaoPessoa");
    }
    
    @Override
    public String abrirEdicao() {
        return "pessoa";
    }
    
    @Override
    public List<Pessoa> complete(String query) {
        List<Pessoa> listaFIltrada = new ArrayList<>();
        for (Pessoa b : beans) {
            if (StringUtils.startsWithIgnoreCase(b.getNome(), query)) {
                listaFIltrada.add(b);
            }
        }
        if (!StringUtils.containsLetter(query)) {
            for (Pessoa m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    listaFIltrada.add(m);
                }
            }
        }
        return listaFIltrada;
    }

    public PessoaService getService() {
        return service;
    }

    public void setService(PessoaService service) {
        this.service = service;
    }

}
