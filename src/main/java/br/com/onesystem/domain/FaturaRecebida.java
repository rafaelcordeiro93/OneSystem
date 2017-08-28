package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.war.service.ConfiguracaoService;
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
@DiscriminatorValue("FATURA_RECEBIDA")
public class FaturaRecebida extends Fatura implements Serializable {

    @OneToMany(mappedBy = "faturaRecebida", cascade = {CascadeType.ALL})
    private List<NotaRecebida> notaRecebida;

    @OneToMany(mappedBy = "faturaRecebida", cascade = {CascadeType.ALL})
    private List<ValorPorCotacao> valorPorCotacao;

    public FaturaRecebida() {
    }

    public FaturaRecebida(Long id, String codigo, BigDecimal total, Date emissao,
            Pessoa pessoa, List<Titulo> titulo, List<NotaRecebida> notaRecebida,
            List<ValorPorCotacao> valorPorCotacao, BigDecimal dinheiro, Filial filial) throws DadoInvalidoException {
        super(id, codigo, total, emissao, pessoa, titulo, dinheiro, filial);
        this.notaRecebida = notaRecebida;
        this.valorPorCotacao = valorPorCotacao;
    }

    public void adiciona(NotaRecebida n) {
        if (notaRecebida == null) {
            notaRecebida = new ArrayList<>();
        }
        n.setFaturaRecebida(this);
        notaRecebida.add(n);
    }

    public void atualiza(NotaRecebida n) {
        if (notaRecebida.contains(n)) {
            notaRecebida.set(notaRecebida.indexOf(n), n);
        } else {
            n.setFaturaRecebida(this);
            notaRecebida.add(n);
        }
    }

    public void remove(NotaRecebida n) {
        notaRecebida.remove(n);
    }

    public void adiciona(ValorPorCotacao b) {
        try {
            if (valorPorCotacao == null) {
                valorPorCotacao = new ArrayList<>();
            }
            b.geraBaixaPor(this);
            valorPorCotacao.add(b);
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void atualiza(ValorPorCotacao b) {
        try {
            if (valorPorCotacao.contains(b)) {
                valorPorCotacao.set(valorPorCotacao.indexOf(b), b);
            } else {
                b.geraBaixaPor(this);
                valorPorCotacao.add(b);
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void remove(ValorPorCotacao b) {
        valorPorCotacao.remove(b);
    }

    public List<NotaRecebida> getNotaRecebida() {
        return notaRecebida;
    }

    public Moeda getMoedaPadrao() throws EDadoInvalidoException {
        Configuracao cfg = new ConfiguracaoService().buscar();
        return cfg.getMoedaPadrao();
    }

    public List<ValorPorCotacao> getValorPorCotacao() {
        return valorPorCotacao;
    }

}
