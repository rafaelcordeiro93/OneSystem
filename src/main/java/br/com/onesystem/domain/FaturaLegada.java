package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("FATURA_LEGADA")
public class FaturaLegada extends Fatura implements Serializable {

    public FaturaLegada() {
    }

    public FaturaLegada(Long id, String codigo, BigDecimal total, Date emissao, Pessoa pessoa, List<Titulo> titulo, Filial filial) throws DadoInvalidoException {
        super(id, codigo, total, emissao, pessoa, titulo, total, filial);
    }

}
