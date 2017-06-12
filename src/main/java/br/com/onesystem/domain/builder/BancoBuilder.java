package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.Banco;
import br.com.onesystem.exception.DadoInvalidoException;

/**
 *
 * @author Rafael
 */
public class BancoBuilder {

    private Long id;
    private String nome;
    private String site;
    private String codigo;

    public BancoBuilder comID(Long ID) {
        this.id = ID;
        return this;
    }

    public BancoBuilder comNome(String nombre) {
        this.nome = nombre;
        return this;
    }
    
    public BancoBuilder comSite(String site){
        this.site = site;
        return this;
    }

    public BancoBuilder comCodigo(String codigo){
        this.codigo = codigo;
        return this;
    }
  
    public Banco construir() throws DadoInvalidoException {
        return new Banco(id, codigo, nome, site);
    }

}
