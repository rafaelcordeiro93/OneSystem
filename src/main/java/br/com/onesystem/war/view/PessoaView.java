package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
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
import br.com.onesystem.exception.impl.IDadoInvalidoException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.hibernate.exception.ConstraintViolationException;

@ManagedBean
@ViewScoped
public class PessoaView implements Serializable {

    private boolean panel;
    private Pessoa pessoaSelecionada;
    private Contato contatoSelecionado;
    private PessoaBV pessoa;
    private ContatoBV contato;
    private Cidade cidadeSelecionada;
    private List<Pessoa> pessoaLista;
    private List<Cidade> cidadeLista;
    private List<Pessoa> pessoasFiltradas;
    private List<Cidade> cidadesFiltradas;

    @ManagedProperty("#{pessoaService}")
    private PessoaService servicePessoa;

    @ManagedProperty("#{cidadeService}")
    private CidadeService serviceCidade;

    @PostConstruct
    public void init() {
        limparJanela();
        pessoaLista = servicePessoa.buscarPessoas();
        cidadeLista = serviceCidade.buscarCidades();
    }

    public void add() {
        try {
            pessoaExiste(false);
            Pessoa novaPessoa = pessoa.construir();
            System.out.println(novaPessoa);
            new AdicionaDAO<Pessoa>().adiciona(novaPessoa);
            pessoaLista.add(novaPessoa);
            InfoMessage.print("¡Persona '" + novaPessoa.getNome() + "' agregada con éxito!");
            limparJanela();
        } catch (DadoInvalidoException die) {
            die.print();
        } catch (ConstraintViolationException ex) {
            FatalMessage.print("Erro Desconhecido: ", ex.getCause());
        }
    }

    public void update() {
        try {
            if (pessoa.getId() != null) {
                Pessoa personaActualizada = pessoa.construirComID();
                System.out.println("p: " + personaActualizada);
                pessoaExiste(true);
                new AtualizaDAO<Pessoa>(Pessoa.class).atualiza(personaActualizada);
                pessoaLista.set(pessoaLista.indexOf(pessoaSelecionada),
                        personaActualizada);
                InfoMessage.print("¡Persona '" + personaActualizada.getNome() + "' actualizada con éxito!");
                limparJanela();
            }
        } catch (DadoInvalidoException di) {
            di.print();
        } catch (ConstraintViolationException ex) {
            FatalMessage.print(ex.getMessage(), ex.getCause());
        }
    }

    public void delete() {
        try {
            if (pessoaSelecionada != null) {
                new RemoveDAO<Pessoa>(Pessoa.class).remove(pessoaSelecionada, pessoaSelecionada.getId());
                pessoaLista.remove(pessoaSelecionada);
                InfoMessage.print("!Persona '" + pessoaSelecionada.getNome() + "' eliminada con éxito!");
                limparJanela();
            } else {
                ErrorMessage.print("!Seleccione un registro para eliminar!");
            }
        } catch (DadoInvalidoException di) {
            di.print();
        } catch (ConstraintViolationException pe) {
            FatalMessage.print(pe.getMessage(), pe.getCause());
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

    public void pessoaExiste(Boolean personaExiste) throws DadoInvalidoException {
        String documento = pessoa.getRuc();
        try {
            if (documento != null && !documento.trim().equals("")) {
                if (personaExiste) {
                    for (Pessoa personaDaLista : pessoaLista) {
                        if (personaDaLista.getDocumento().equals(documento)
                                && personaDaLista.getId() != pessoa.getId()) {
                            throw new IDadoInvalidoException("¡Persona ya existe!");
                        }
                    }
                } else {
                    for (Pessoa personaDaLista : pessoaLista) {
                        if (personaDaLista.getRuc().equals(documento)) {
                            throw new IDadoInvalidoException("¡Persona ya existe!");
                        }
                    }
                }
            }
        } catch (NullPointerException npe) {
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
                System.out.println(contatoDaLista.getTelefone());
                System.out.println(this.contato.getTelefone());
                if (contatoDaLista.getTelefone().equals(this.contato.getTelefone())) {
                    throw new IDadoInvalidoException("¡Número de teléfono existente!");
                }
            }
        }
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
        cidadeSelecionada = new Cidade();
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
        panel = true;
    }

    public void abrirEdicaoComDados() {
        panel = true;
        pessoa = new PessoaBV(pessoaSelecionada);
    }

    public void fecharEdicao() {
        panel = false;
    }

    public void selecionaCidade() {
        pessoa.setCidade(cidadeSelecionada);
    }

    public boolean isPanel() {
        return panel;
    }

    public void setPanel(boolean panel) {
        this.panel = panel;
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

    public Cidade getCidadeSelecionada() {
        return cidadeSelecionada;
    }

    public void setCidadeSelecionada(Cidade cidadeSelecionada) {
        this.cidadeSelecionada = cidadeSelecionada;
    }

    public List<Pessoa> getPessoaLista() {
        return pessoaLista;
    }

    public void setPessoaLista(List<Pessoa> pessoaLista) {
        this.pessoaLista = pessoaLista;
    }

    public List<Cidade> getCidadeLista() {
        return cidadeLista;
    }

    public void setCidadeLista(List<Cidade> cidadeLista) {
        this.cidadeLista = cidadeLista;
    }

    public List<Pessoa> getPessoasFiltradas() {
        return pessoasFiltradas;
    }

    public void setPessoasFiltradas(List<Pessoa> pessoasFiltradas) {
        this.pessoasFiltradas = pessoasFiltradas;
    }

    public List<Cidade> getCidadesFiltradas() {
        return cidadesFiltradas;
    }

    public void setCidadesFiltradas(List<Cidade> cidadesFiltradas) {
        this.cidadesFiltradas = cidadesFiltradas;
    }

    public PessoaService getServicePessoa() {
        return servicePessoa;
    }

    public void setServicePessoa(PessoaService servicePessoa) {
        this.servicePessoa = servicePessoa;
    }

    public CidadeService getServiceCidade() {
        return serviceCidade;
    }

    public void setServiceCidade(CidadeService serviceCidade) {
        this.serviceCidade = serviceCidade;
    }

}
