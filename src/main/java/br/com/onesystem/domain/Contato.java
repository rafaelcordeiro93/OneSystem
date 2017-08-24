package br.com.onesystem.domain;

import br.com.onesystem.valueobjects.TipoTelefone;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author Usuário
 */
@Entity
@SequenceGenerator(name = "SEQ_CONTATO", sequenceName = "SEQ_CONTATO",
        initialValue = 1, allocationSize = 1)
public class Contato implements Serializable, Comparable<Contato> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CONTATO")
    private Long ID;
    @Column(nullable = false, length = 9)
    private String telefone;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoTelefone tipo;
    @Column(nullable = true, length = 60)
    private String contato;   
    @Column(nullable = true, length = 100)
    private String email;
    @ManyToOne
    private Pessoa pessoa;

    public Contato() {
    }

    public Contato(Long ID, String telefone, Pessoa pessoa, TipoTelefone tipo, String contato, String email) {
        this.ID = ID;
        this.telefone = telefone;
        this.pessoa = pessoa;
        this.contato = contato;
        this.email = email;
        this.tipo = tipo;
    }

    public String getTelefoneFormatado() {
        if (telefone == null) {
            return telefone;
        } else {
            return "(" + telefone.substring(0, 2) + ")" + telefone.substring(2, 6) + "-" + telefone.substring(6, telefone.length());
        }
    }

    public Long getID() {
        return ID;
    }

    public String getTelefone() {
        return telefone;
    }

    public TipoTelefone getTipo() {
        return tipo;
    }

    public String getContato() {
        return contato;
    }

    public String getEmail() {
        return email;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof Contato)) {
            return false;
        }
        Contato outro = (Contato) objeto;
        if (this.ID == null) {
            return false;
        }
        return this.ID.equals(outro.ID);
    }

    @Override
    public int compareTo(Contato outro) {
        if (this.ID < outro.ID) {
            return -1;
        } else if (this.ID > outro.ID) {
            return 1;
        }
        return 0;
    }
}
