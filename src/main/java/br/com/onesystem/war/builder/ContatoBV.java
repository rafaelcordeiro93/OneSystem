package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Contato;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.builder.ContatoBuilder;
import br.com.onesystem.services.CharacterType;
import br.com.onesystem.valueobjects.CaseType;
import br.com.onesystem.valueobjects.TipoTelefone;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public class ContatoBV implements Serializable {

    private Long ID;
    @NotNull(message = "{telefono_not_null}")
    @CharacterType(value = CaseType.DIGIT, message = "{telefono_type_digit}")
    @Length(min = 10, max = 10, message = "{telefono_length}")
    private String telefone;
    private TipoTelefone tipo;
    @CharacterType(value = CaseType.LETTER_SPACE, message = "{contacto_type_letter_space}")
    @Length(min = 4, max = 60, message = "{contacto_lenght}")
    private String contato;
    @org.hibernate.validator.constraints.Email(message = "{email_invalido}")
    private String email;
    private Pessoa pessoa;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public TipoTelefone getTipo() {
        return tipo;
    }

    public void setTipo(TipoTelefone tipo) {
        this.tipo = tipo;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Contato construir() {
        return new ContatoBuilder().comPessoa(pessoa).comEmail(email)
                .comTelefone(telefone).comTipo(tipo).deContato(contato)
                .construir();
    }

    public Contato construirComId() {
        return new ContatoBuilder().comPessoa(pessoa).comEmail(email)
                .comTelefone(telefone).comTipo(tipo).deContato(contato)
                .comID(ID).construir();
    }

}
