package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.ValidadorDeCampos;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

@Entity
@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "SEQ_TRANSFERENCIADESPESAPROVISIONADA",
        sequenceName = "SEQ_TRANSFERENCIADESPESAPROVISIONADA")
public class TransferenciaDespesaProvisionada implements Serializable {

    @Id
    @GeneratedValue(generator = "SEQ_TRANSFERENCIADESPESAPROVISIONADA", strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull(message = "{origem_not_null}")
    @OneToOne
    private DespesaProvisionada origem;

    @NotNull(message = "{destino_not_null}")
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private DespesaProvisionada destino;

    @NotNull(message = "{emissao_not_null}")
    @Temporal(TemporalType.TIMESTAMP)
    private Date emissao;
    
    @NotNull(message = "{motivo_not_null}")
    @Length(min = 10, max = 250, message = "{motivo_lenght}")
    private String motivo;

    public TransferenciaDespesaProvisionada() {
    }

    public TransferenciaDespesaProvisionada(Long id, DespesaProvisionada origem, DespesaProvisionada destino, 
            String motivo, Date emissao) throws DadoInvalidoException {
        this.id = id;
        this.origem = origem;
        this.destino = destino;
        this.emissao = emissao;
        this.motivo = motivo;
        ehValido();
    }

    public Long getId() {
        return id;
    }

    public DespesaProvisionada getOrigem() {
        return origem;
    }

    public DespesaProvisionada getDestino() {
        return destino;
    }

    public Date getEmissao() {
        return emissao;
    }

    public String getMotivo() {
        return motivo;
    }
    
    public final void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("origem", "destino", "motivo", "emissao");
        new ValidadorDeCampos<TransferenciaDespesaProvisionada>().valida(this, campos);
    }
    
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof TransferenciaDespesaProvisionada)) {
            return false;
        }
        TransferenciaDespesaProvisionada outro = (TransferenciaDespesaProvisionada) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

}
