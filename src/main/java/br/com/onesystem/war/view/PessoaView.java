package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.PessoaDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.Cidade;
import br.com.onesystem.domain.Contato;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.util.ErrorMessage;
import br.com.onesystem.util.FatalMessage;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.war.builder.ContatoBV;
import br.com.onesystem.war.builder.PessoaBV;
import br.com.onesystem.war.service.CidadeService;
import br.com.onesystem.war.service.PessoaService;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.exception.impl.IDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.SelectEvent;

@ManagedBean
@ViewScoped
public class PessoaView extends BasicMBImpl<Pessoa> implements Serializable {

    private Pessoa pessoaSelecionada;
    private Contato contatoSelecionado;
    private PessoaBV pessoa;
    private ContatoBV contato;

    @ManagedProperty("#{cidadeService}")
    private CidadeService serviceCidade;

    @ManagedProperty("#{pessoaService}")
    private PessoaService pessoaLista;

    @PostConstruct
    public void init() {
        limparJanela();
    }

    public void add() {
        try {
            Pessoa novaPessoa = pessoa.construir();
            if (validaPessoaExistente(novaPessoa)) {
                new AdicionaDAO<Pessoa>().adiciona(novaPessoa);
                InfoMessage.adicionado();
                limparJanela();
            } else {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("ruc_existe"));
            }
        } catch (DadoInvalidoException die) {
            die.print();
        } catch (ConstraintViolationException ex) {
            ex.getMessage();
        }
    }

    public void update() {
        try {
            Pessoa pessoaExistente = pessoa.construirComID();
            if (pessoaExistente.getId() != null) {
                if (!validaPessoaExistente(pessoaExistente)) {
                    new AtualizaDAO<Pessoa>(Pessoa.class).atualiza(pessoaExistente);
                    InfoMessage.atualizado();
                    limparJanela();
                } else {
                    throw new EDadoInvalidoException(new BundleUtil().getMessage("ruc_existe"));
                }
            }
        } catch (DadoInvalidoException di) {
            di.print();
        } catch (ConstraintViolationException ex) {
            ex.getMessage();
        }
    }

    public void delete() {
        try {
            if (pessoaSelecionada != null) {
                new RemoveDAO<Pessoa>(Pessoa.class).remove(pessoaSelecionada, pessoaSelecionada.getId());
                InfoMessage.removido();
                limparJanela();
            }
        } catch (DadoInvalidoException di) {
            di.print();
        } catch (ConstraintViolationException pe) {
            pe.getMessage();
        }
    }

    public void addContato() {
        try {
            contatoExiste(false);
            this.contato.setPessoa(pessoaSelecionada);
            Contato novoContato = this.contato.construir();
            if (pessoaSelecionada.getContatos() == null || pessoaSelecionada.getContatos().isEmpty()) {
                pessoaSelecionada.instanciaContactoList();
            }
            new AdicionaDAO<Contato>().adiciona(novoContato);
            pessoaSelecionada.getContatos().add(novoContato);
            InfoMessage.print("¡Contacto '" + contato.getTelefone() + "' agregado!");
            limparContato();
        } catch (DadoInvalidoException di) {
            di.print();
        }
    }

    public void updateContato() {
        try {
            contatoExiste(true);
            Contato contatoExistente = this.contato.construir();
            new AtualizaDAO<Contato>(Contato.class).atualiza(contatoExistente);
            pessoaSelecionada.getContatos().set(pessoaSelecionada.getContatos().indexOf(contatoSelecionado),
                    contatoExistente);
            InfoMessage.print("¡Telefono '" + contatoExistente.getTelefone() + "' actualizado con éxito!");
            limparContato();
        } catch (DadoInvalidoException di) {

            di.print();
        }
    }

    public void deleteContato() {
        try {
            if (contatoSelecionado != null) {
                new RemoveDAO<Contato>(Contato.class).remove(contatoSelecionado, contatoSelecionado.getID());
                pessoaSelecionada.getContatos().remove(contatoSelecionado);
                InfoMessage.print("¡Telefono '" + contatoSelecionado.getTelefone() + "' eliminado con éxito!");
                limparContato();
            } else {
                ErrorMessage.print("!Seleccione un registro para eliminar!");
            }
        } catch (DadoInvalidoException di) {
            di.print();
        } catch (ConstraintViolationException pe) {
            FatalMessage.print(pe.getMessage(), pe.getCause());
        }
    }

    private boolean validaPessoaExistente(Pessoa novoRegistro) {
        List<Pessoa> lista = new PessoaDAO().buscarPessoas().porRuc(novoRegistro).listaDeResultados();
        return lista.isEmpty();
    }

    @Override
    public void selecionar(SelectEvent e) {
        if (pessoa == null) {
            limparJanela();
        }
        Object obj = e.getObject();
        if (obj instanceof Pessoa) {
            Pessoa c = (Pessoa) e.getObject();
            pessoa = new PessoaBV(c);
            pessoaSelecionada = c;
        } else if (obj instanceof Cidade) {
            Cidade cidade = (Cidade) obj;
            pessoa.setCidade(cidade);
        }
    }

    @Override
    public void buscaPorId() {
        Long id = pessoa.getId();
        if (id != null) {
            try {
                PessoaDAO dao = new PessoaDAO();
                Pessoa c = dao.buscarPessoas().porId(id).resultado();
                pessoaSelecionada = c;
                pessoa = new PessoaBV(pessoaSelecionada);
            } catch (DadoInvalidoException die) {
                limparJanela();
                pessoa.setId(id);
                die.print();
            }
        }
    }

    public void contatoExiste(Boolean contatoExiste) throws DadoInvalidoException {
        if (pessoaSelecionada.getContatos() == null || pessoaSelecionada.getContatos().isEmpty()) {
            return;
        }
        if (contatoExiste) {
            for (Contato contatoDaLista : pessoaSelecionada.getContatos()) {
                if (contatoDaLista.getTelefone().equals(this.contato.getTelefone())
                        && !(contatoDaLista.getID().equals(this.contato.getID()))) {
                    throw new IDadoInvalidoException("¡Número de teléfono existente!");
                }
            }
        } else {
            for (Contato contatoDaLista : pessoaSelecionada.getContatos()) {

                if (contatoDaLista.getTelefone().equals(this.contato.getTelefone())) {
                    throw new IDadoInvalidoException("¡Número de teléfono existente!");
                }
            }
        }
    }

    public void selecionaCidade(SelectEvent e) {

    }

    public void desfazer() {
        if (pessoaSelecionada != null) {
            pessoa = new PessoaBV(pessoaSelecionada);
        }
    }

    public void limparContato() {
        contato = new ContatoBV();
        this.contato.setPessoa(pessoaSelecionada);
        this.contatoSelecionado = new Contato();
    }

    public void limparJanela() {
        pessoa = new PessoaBV();
        contato = new ContatoBV();
        pessoaSelecionada = null;

    }

    public void fisicaJuridicaOnSelect() {
        if (this.pessoa.isFisicaJuridica()) {
            this.pessoa.setFantasiaCILabel("Fantasia");
            this.pessoa.setFantasiaCI("");
            this.pessoa.setNascimento(null);
            this.pessoa.setConjuge("");
        } else {
            this.pessoa.setFantasiaCILabel("C.I.");
            this.pessoa.setFantasiaCI("");
        }
    }

    public void abrirEdicao() {
        limparJanela();
    }

    public Pessoa getPessoaSelecionada() {
        return pessoaSelecionada;
    }

    public void setPessoaSelecionada(Pessoa pessoaSelecionada) {
        this.pessoaSelecionada = pessoaSelecionada;
    }

    public Contato getContatoSelecionado() {
        return contatoSelecionado;
    }

    public void setContatoSelecionado(Contato contatoSelecionado) {
        this.contatoSelecionado = contatoSelecionado;
    }

    public PessoaBV getPessoa() {
        return pessoa;
    }

    public void setPessoa(PessoaBV pessoa) {
        this.pessoa = pessoa;
    }

    public ContatoBV getContato() {
        return contato;
    }

    public void setContato(ContatoBV contato) {
        this.contato = contato;
    }

    public CidadeService getServiceCidade() {
        return serviceCidade;
    }

    public void setServiceCidade(CidadeService serviceCidade) {
        this.serviceCidade = serviceCidade;
    }

    public PessoaService getPessoaLista() {
        return pessoaLista;
    }

    public void setPessoaLista(PessoaService pessoaLista) {
        this.pessoaLista = pessoaLista;
    }

}
