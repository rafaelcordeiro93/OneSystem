package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue("FATURA_EMITIDA")
public class FaturaEmitida extends Fatura implements Serializable {

    @OneToMany(mappedBy = "faturaEmitida", cascade = {CascadeType.ALL})
    private List<NotaEmitida> notaEmitida;

    @OneToMany(mappedBy = "faturaEmitida", cascade = {CascadeType.ALL})
    private List<ValorPorCotacao> valorPorCotacao;

    public FaturaEmitida() {
    }

    public FaturaEmitida(Long id, String codigo, BigDecimal total, Date emissao,
            Pessoa pessoa, List<Titulo> titulo, List<NotaEmitida> notaEmitida,
            List<ValorPorCotacao> valorPorCotacao, BigDecimal dinheiro,
            Filial filial) throws DadoInvalidoException {
        super(id, codigo, total, emissao, pessoa, titulo, dinheiro, filial);
        this.notaEmitida = notaEmitida;
        this.valorPorCotacao = valorPorCotacao;
    }

    public void adiciona(NotaEmitida n) {
        if (notaEmitida == null) {
            notaEmitida = new ArrayList<>();
        }
        n.setFaturaEmitida(this);
        notaEmitida.add(n);
    }

    public void atualiza(NotaEmitida n) {
        if (notaEmitida.contains(n)) {
            notaEmitida.set(notaEmitida.indexOf(n), n);
        } else {
            n.setFaturaEmitida(this);
            notaEmitida.add(n);
        }
    }

    public void remove(NotaEmitida n) {
        notaEmitida.remove(n);
    }

    public void adiciona(ValorPorCotacao v) {
        try {
            if (valorPorCotacao == null) {
                valorPorCotacao = new ArrayList<>();
            }
            v.geraBaixaPor(this);//set a Fatura dentro do ValorPorCotacao e Gera as Baixas
            valorPorCotacao.add(v);
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void atualiza(ValorPorCotacao v) {
        try {
            if (valorPorCotacao.contains(v)) {
                valorPorCotacao.set(valorPorCotacao.indexOf(v), v);
            } else {
                v.geraBaixaPor(this);
                valorPorCotacao.add(v);
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void remove(ValorPorCotacao v) {
        valorPorCotacao.remove(v);
    }

    public List<NotaEmitida> getNotaEmitida() {
        return notaEmitida;
    }

    public List<ValorPorCotacao> getValorPorCotacao() {
        return valorPorCotacao;
    }
    
}
