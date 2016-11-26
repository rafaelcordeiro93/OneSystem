package br.com.onesystem.war.builder;

import br.com.onesystem.domain.GrupoDePrivilegio;
import br.com.onesystem.domain.builder.GrupoDePrivilegioBuilder;
import br.com.onesystem.valueobjects.CaseType;
import br.com.onesystem.services.CharacterType;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public class GrupoDePrivilegioBV {

    private Long id;
    @NotNull(message = "{nome_not_null}")
    @Length(min = 3, max = 40, message = "{nome_lenght}")
    @CharacterType(value = CaseType.LETTER_SPACE, message = "{nome_type_letter_space}")
    private String nome;

    public GrupoDePrivilegioBV() {
    }

    public GrupoDePrivilegioBV(GrupoDePrivilegio grupo) {
        this.id = grupo.getId();
        this.nome = grupo.getNome();
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public GrupoDePrivilegio construir() {
        return new GrupoDePrivilegioBuilder().comNome(nome).contruir();
    }

    public GrupoDePrivilegio construirComId() {
        return new GrupoDePrivilegioBuilder().comId(id).comNome(nome).contruir();
    }

}
