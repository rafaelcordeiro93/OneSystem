/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.util;

import br.com.onesystem.domain.Pessoa;

import br.com.onesystem.domain.PessoaJuridica;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.TipoPessoa;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Rafael
 */
public class ImportadorRUC {

    public List<Pessoa> Importar() throws DadoInvalidoException {

        List<Pessoa> listaPessoas = null;

        Scanner ler = new Scanner(System.in);

        String nome = "D:\\ruc0.txt";
        try {
            FileReader arq = new FileReader(nome);
            BufferedReader lerArq = new BufferedReader(arq);

            String linha = "";

            while (linha != null) {

                linha = lerArq.readLine();

                if (linha == null) {
                    break;
                }

                int index = linha.indexOf('|');

                String RUC = index == -1 ? linha : linha.substring(0, index);//ruc

                String Nome = index == -1 ? linha : linha.substring(index + 1, linha.lastIndexOf('|'));//nome
                Nome = Nome.substring(0, Nome.indexOf('|'));//nome
//                           System.out.printf("%s\n", RUC);
//              System.out.printf("%s\n", Nome);
                listaPessoas.add(new PessoaJuridica(null, null, Nome, TipoPessoa.PESSOA_JURIDICA, RUC, true, null, null, true, false, false, false, null, null, null, null, null, null, null, null, null));
            
            }
            arq.close();
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n",
                    e.getMessage());
        }
        return listaPessoas;
    }
}
