package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.PessoaJuridica;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.ImportadorRUC;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.war.service.PessoaService;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import org.apache.commons.io.FileUtils;

import org.primefaces.event.FileUploadEvent;

/**
 *
 * @author Rafael
 */
@ManagedBean
public class ImportarPessoaView implements Serializable {

    ImportadorRUC importador = new ImportadorRUC();

    @ManagedProperty("#{pessoaService}")
    private PessoaService pessoaLista;

    public ImportarPessoaView() {
    }

    public void upload(FileUploadEvent event) throws DadoInvalidoException, IOException {
        try {
            File destFile = new File("rucimport.txt");//cria um arquivo txt
            FileUtils.copyInputStreamToFile(event.getFile().getInputstream(), destFile);//adiciona o conteudo o UploadedFile no arquivo novo criado
            add(importador.Importar(destFile));
        } catch (EDadoInvalidoException e) {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("erro_ao_importar") + e.getMessage());
        }

    }

    public void add(List<PessoaJuridica> lista) throws DadoInvalidoException {
        for (PessoaJuridica pessoa : lista) {
            try {
                if (pessoaExiste(pessoa) == false) {
                    new AdicionaDAO<Pessoa>().adiciona(pessoa);
                }
            } catch (NullPointerException npe) {
            }
        }
        InfoMessage.adicionado();
    }

    public Boolean pessoaExiste(Pessoa pessoa) throws DadoInvalidoException {
        String documento = pessoa.getRuc();
        List<Pessoa> buscarPessoas = pessoaLista.buscarPessoas();
        try {
            for (Pessoa personaDaLista : buscarPessoas) {
                if (personaDaLista.getRuc().equals(documento)) {
                    return true;
                }
            }
        } catch (NullPointerException npe) {
        }
        return false;
    }

    public ImportadorRUC getImportador() {
        return importador;
    }

    public void setImportador(ImportadorRUC importador) {
        this.importador = importador;
    }

    public PessoaService getPessoaLista() {
        return pessoaLista;
    }

    public void setPessoaLista(PessoaService pessoaLista) {
        this.pessoaLista = pessoaLista;
    }

}
