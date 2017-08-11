
import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.CobrancaDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.Cidade;
import br.com.onesystem.domain.Cobranca;
import br.com.onesystem.domain.Coluna;
import br.com.onesystem.domain.FiltroDeRelatorio;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.Marca;
import br.com.onesystem.domain.ModeloDeRelatorio;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.builder.ModeloDeRelatorioBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.valueobjects.SituacaoDeCobranca;
import br.com.onesystem.valueobjects.TipoDeBusca;
import br.com.onesystem.valueobjects.TipoFormatacaoNumero;
import br.com.onesystem.valueobjects.TipoRelatorio;
import br.com.onesystem.valueobjects.Totalizador;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
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

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, DadoInvalidoException {

        BundleUtil bundle = new BundleUtil();
        AdicionaDAO<ModeloDeRelatorio> modeloDeRelatorioDAO = new AdicionaDAO<ModeloDeRelatorio>();

        //Relatório de Balanço Físico
//        //========================================================
        ModeloDeRelatorio relatorioDeAniversariantes = new ModeloDeRelatorioBuilder()
                .comNome(new BundleUtil().getLabel("Relatorio_de_Aniversariantes"))
                .comTipoRelatorio(TipoRelatorio.PESSOAS)
                .construir();

        //Colunas Exibidas
        String pessoaStr = bundle.getLabel("Pessoa");
        Coluna pessoaId = new Coluna(bundle.getLabel("Id"), pessoaStr, "id", Pessoa.class, Long.class);
        Coluna pessoaNome = new Coluna(bundle.getLabel("Nome"), pessoaStr, "nome", Pessoa.class, String.class);
        Coluna pessoaNascimento = new Coluna(bundle.getLabel("Nascimento"), pessoaStr, "nascimento", Pessoa.class, Date.class);
        Coluna pessoaTelefone = new Coluna(bundle.getLabel("Telefone"), pessoaStr, "telefone", Pessoa.class, String.class);
        Coluna pessoaCidadeNome = new Coluna(bundle.getLabel("Nome") + "(" + bundle.getLabel("Cidade") + ")", "Cidade", "cidade", "nome", Cidade.class, String.class);

        //Alterar Tamanho das Colunas
        pessoaNome.setTamanho(40);
        pessoaNascimento.setTamanho(15);

        relatorioDeAniversariantes.addColunaExibida(pessoaId);
        relatorioDeAniversariantes.addColunaExibida(pessoaNome);
        relatorioDeAniversariantes.addColunaExibida(pessoaNascimento);
        relatorioDeAniversariantes.addColunaExibida(pessoaTelefone);
        relatorioDeAniversariantes.addColunaExibida(pessoaCidadeNome);

        modeloDeRelatorioDAO.adiciona(relatorioDeAniversariantes);

    }
}
