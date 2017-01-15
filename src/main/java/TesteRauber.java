
import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.AjusteDeEstoque;
import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.FormaDeRecebimento;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.PessoaJuridica;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.TipoPessoa;
import br.com.onesystem.war.service.AjusteDeEstoqueService;
import br.com.onesystem.war.view.selecao.SelecaoFormaDeRecebimentoAtivaView;
import java.io.File;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Rafael Fernando Rauber
 */
public class TesteRauber {

    public static void main(String[] args) {

        BigDecimal bd = new BigDecimal(239003293);
        System.out.println(NumberFormat.getCurrencyInstance(Locale.FRANCE).format(bd));
        
    }

}
