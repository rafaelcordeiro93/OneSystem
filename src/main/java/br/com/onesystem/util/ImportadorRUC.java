package br.com.onesystem.util;

import br.com.onesystem.domain.PessoaJuridica;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.valueobjects.TipoPessoa;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Rafael
 */
public class ImportadorRUC {

    public List<PessoaJuridica> Importar(File file) throws DadoInvalidoException {

        List<PessoaJuridica> listaPessoas = new ArrayList<>();

        Scanner ler = new Scanner(System.in);

        try {
            FileReader arq = new FileReader(file);
            BufferedReader lerArq = new BufferedReader(arq);
            String linha = "";

            while (linha != null) {

                linha = lerArq.readLine();

                if (linha == null) {
                    break;
                }

                try {
                    int index = linha.indexOf('|');

                    String ruc = index == -1 ? linha : linha.substring(0, index);//ruc

                    String nome = index == -1 ? linha : linha.substring(index + 1, linha.lastIndexOf('|'));//nome
                    nome = nome.substring(0, nome.indexOf('|'));//nome

                    listaPessoas.add(new PessoaJuridica(nome, null, nome, TipoPessoa.PESSOA_JURIDICA, ruc, true, null, null, true, false, false, false, null, new Date(), null, null, null, null, null, null,"", null, null));
                    
                } catch (EDadoInvalidoException e) {
                    throw new EDadoInvalidoException(new BundleUtil().getMessage("erro_ao_importar") + e.getMessage());
                }

            }
            arq.close();
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n",
                    e.getMessage());
        }
        return listaPessoas;
    }
}
