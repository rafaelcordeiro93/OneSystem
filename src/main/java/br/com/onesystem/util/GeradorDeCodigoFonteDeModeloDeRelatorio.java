/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.util;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.domain.CobrancaFixa;
import br.com.onesystem.domain.Coluna;
import br.com.onesystem.domain.FiltroDeRelatorio;
import br.com.onesystem.domain.ModeloDeRelatorio;
import br.com.onesystem.domain.ParametroDeFiltroDeRelatorio;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.builder.ModeloDeRelatorioBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.valueobjects.SituacaoDeCobranca;
import br.com.onesystem.valueobjects.TipoDeBusca;
import br.com.onesystem.valueobjects.TipoFormatacaoNumero;
import br.com.onesystem.valueobjects.TipoRelatorio;
import br.com.onesystem.valueobjects.Totalizador;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.math.BigDecimal;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.MissingResourceException;

/**
 * Esta classe irá gerar o código fonte dos modelos de relatório. Isso
 * facilitará na hora de criar o modelo quando o mesmo já existe em banco de
 * dados, ou mesmo quando for criá-lo pela aplicação.
 *
 * @date 12/09/2017
 * @author Rafael Fernando Rauber
 */
public class GeradorDeCodigoFonteDeModeloDeRelatorio {

    private StringBuilder bundles = new StringBuilder();

    public String gerarCodigo(ModeloDeRelatorio modeloDeRelatorio) throws DadoInvalidoException {
        StringBuilder sb = new StringBuilder();

        //Cria nome relatório, retira acentos e troca espaços por underline "_"
        String nomeRelatorio = Normalizer.normalize(modeloDeRelatorio.getNome(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").replaceAll(" ", "_");
        testaBundle(nomeRelatorio);

        //Cria nome modelo, retira acentos
        String retiraAcento = Normalizer.normalize(modeloDeRelatorio.getNome(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").trim();
        String[] split = retiraAcento.split(" ");
        //Formata nomeModelo
        String nomeModelo = retiraAcento.contains(" ") ? formataSplit(split) : retiraAcento;

        //Cria o Modelo
        sb.append(" //").append(modeloDeRelatorio.getNome()).append("\n").append(" //Cria o Modelo\n");
        sb.append("BundleUtil bundle = new BundleUtil();\n");
        sb.append("ModeloDeRelatorio ").append(nomeModelo).append(" = new ModeloDeRelatorioBuilder()");
        sb.append(".comNome(bundle.getLabel(\"").append(nomeRelatorio).append("\"))");
        sb.append(".comTipoRelatorio(TipoRelatorio.").append(modeloDeRelatorio.getTipoRelatorio()).append(")");
        sb.append(".construir();\n\n");

        //Cria os Filtros
        if (modeloDeRelatorio.getFiltroDeRelatorio() != null) {
            sb.append(" //Cria os Filtros\n");
            for (FiltroDeRelatorio filtro : modeloDeRelatorio.getFiltroDeRelatorio()) {
                //Cria a coluna do Filtro
                String nomeColuna = criaColuna(filtro.getColuna(), sb);

                //Cria o Filtro
                String nomeFiltro = "filtro" + nomeColuna.substring(0, 1).toUpperCase() + nomeColuna.substring(1);
                sb.append("FiltroDeRelatorio ").append(nomeFiltro).append(" = new FiltroDeRelatorio(null, ");
                sb.append(nomeColuna).append(", ").append("TipoDeBusca.").append(filtro.getTipoDaBusca()).append(");\n");

                if (filtro.getFiltroDeData() != null) {
                    sb.append(nomeFiltro).append(".setFiltroDeData(").append(filtro.getFiltroDeData()).append(");");
                } else {
                    for (ParametroDeFiltroDeRelatorio p : filtro.getParametros()) {
                        sb.append(nomeFiltro).append(".add(");
                        if (p.getParametroBigDecimal() != null) {
                            sb.append(p.getParametroBigDecimal());
                        } else if (p.getParametroEnum() != null) {
                            sb.append(filtro.getEnum(p.getParametroEnum()).getClass().getSimpleName())
                                    .append(".").append(p.getParametroEnum());
                        } else if (p.getParametroInteger() != null) {
                            sb.append(p.getParametroInteger());
                        } else if (p.getParametroString() != null) {
                            sb.append(p.getParametroString());
                        } else if (p.getParametroLong() != null) {
                            sb.append(p.getParametroLong());
                        }
                        sb.append(");\n");
                    }
                }
                sb.append(nomeModelo).append(".addFiltro(").append(nomeFiltro).append(");\n\n");
            }
        }

        //Cria as Colunas Exibidas
        if (modeloDeRelatorio.getColunasExibidas() != null) {
            sb.append(" //Cria as Colunas Exibidas\n");
            List<String> colunas = new ArrayList<>(); // Para melhor formatação
            for (Coluna c : modeloDeRelatorio.getColunasExibidas()) {
                String nomeColuna = criaColuna(c, sb);
                colunas.add(nomeColuna);
            }

            //Adiciona colunas exibidas no modelo
            sb.append("\n //Adiciona colunas exibidas no modelo\n");
            colunas.forEach(n -> sb.append(nomeModelo).append(".addColunaExibida(").append(n).append(");\n"));
        }

        //Adiciona no Banco
        sb.append("\n //Adiciona no Banco\n");
        sb.append("new AdicionaDAO<>().adiciona(").append(nomeModelo).append(");");

        return sb.toString();
    }

    private String criaColuna(Coluna coluna, StringBuilder sb) {
        //Nome da coluna
        String nomeColuna = coluna.getPropriedadeCompleta().contains(".")
                ? formataSplit(coluna.getPropriedadeCompleta().split("\\.")) : coluna.getPropriedadeCompleta();

        //Bundle criado
        String bundle = coluna.getUltimaPropriedade().substring(0, 1).toUpperCase() + coluna.getUltimaPropriedade().substring(1);
        testaBundle(bundle);

        sb.append("Coluna ").append(nomeColuna).append(" = new Coluna(");
        sb.append("bundle.getLabel(\"").append(bundle).append("\"), ");
        sb.append("\"").append(coluna.getClasseDeDeclaracao().getSimpleName()).append("\", ");
        sb.append("\"").append(coluna.getPropriedade()).append("\", ");

        appendPropriedade(coluna.getPropriedadeDois(), sb);
        appendPropriedade(coluna.getPropriedadeTres(), sb);
        appendPropriedade(coluna.getPropriedadeQuatro(), sb);

        sb.append(coluna.getClasseDeDeclaracao().getSimpleName()).append(".class, ");
        sb.append(coluna.getClasseOriginal().getSimpleName()).append(".class, ");

        formataTipoFormatacaoNumero(coluna.getTipoFormatadorNumero(), sb);
        formataTotalizador(coluna.getTotalizador(), sb);

        sb.append(coluna.getTamanho()).append(", ");
        sb.append("null);\n");

        return nomeColuna;
    }

    private void testaBundle(String bundle) {
        //Testa bundle
        try {
            new BundleUtil().getLabel(bundle);
        } catch (MissingResourceException mre) {
            bundles.append(bundle).append("\n");
        }
    }

    private void formataTipoFormatacaoNumero(TipoFormatacaoNumero tipoFormatacaoNumero, StringBuilder sb) {
        if (tipoFormatacaoNumero != null) {
            sb.append("TipoFormatacaoNumero.").append(tipoFormatacaoNumero).append(", ");
        } else {
            sb.append("null, ");
        }
    }

    private void formataTotalizador(Totalizador totalizador, StringBuilder sb) {
        if (totalizador != null) {
            sb.append("Totalizador.").append(totalizador).append(", ");
        } else {
            sb.append("null, ");
        }
    }

    private void appendPropriedade(String str, StringBuilder sb) {
        if (str != null) {
            sb.append("\"").append(str).append("\", ");
        } else {
            sb.append(str).append(", ");
        }
    }

    private String formataSplit(String[] split) {
        String str = split[0].substring(0, 1).toLowerCase() + split[0].substring(1);
        for (int i = 1; i < split.length; i++) {
            split[i] = split[i].substring(0, 1).toUpperCase() + split[i].substring(1);
            str += split[i];
        }
        return str;
    }

    public static void main(String[] args) throws DadoInvalidoException {
        BundleUtil bundle = new BundleUtil();

        ModeloDeRelatorio relatorioDeContasFixasAPagar = new ModeloDeRelatorioBuilder().
                comNome("Relatório De Contas Fixas a Pagar")
                .comTipoRelatorio(TipoRelatorio.COBRANCA_FIXA)
                .construir();

        //Filtros
        Coluna colOperacaoFinanceiraCFP = new Coluna(bundle.getLabel("Operacao_Financeira"), "Cobranca", "operacaoFinanceira", CobrancaFixa.class, OperacaoFinanceira.class);
        FiltroDeRelatorio filtroOFCFP = new FiltroDeRelatorio(null, colOperacaoFinanceiraCFP, TipoDeBusca.IGUAL_A);
        filtroOFCFP.add(OperacaoFinanceira.SAIDA);

        Coluna colSituacaoDeCobrancaCFP = new Coluna(bundle.getLabel("Situacao_de_Cobranca"), "Cobranca", "situacaoDeCobranca", CobrancaFixa.class, SituacaoDeCobranca.class);
        FiltroDeRelatorio filtroSDCCFP = new FiltroDeRelatorio(null, colSituacaoDeCobrancaCFP, TipoDeBusca.IGUAL_A);
        filtroSDCCFP.add(SituacaoDeCobranca.ABERTO);

        relatorioDeContasFixasAPagar.addFiltro(filtroOFCFP);
        relatorioDeContasFixasAPagar.addFiltro(filtroSDCCFP);

        //Colunas Exibidas
        Coluna cfpPessoa = new Coluna(bundle.getLabel("Nome") + "(" + bundle.getLabel("Pessoa") + ")", bundle.getLabel("Pessoa"), "pessoa", "nome", Pessoa.class, String.class);
        cfpPessoa.setTamanho(30);

        relatorioDeContasFixasAPagar.addColunaExibida(new Coluna(bundle.getLabel("Id"), bundle.getLabel("Cobranca"), "id", CobrancaFixa.class, Long.class));
        relatorioDeContasFixasAPagar.addColunaExibida(cfpPessoa);
        relatorioDeContasFixasAPagar.addColunaExibida(new Coluna(bundle.getLabel("Emissao"), bundle.getLabel("Cobranca"), "emissao", CobrancaFixa.class, Date.class));
        relatorioDeContasFixasAPagar.addColunaExibida(new Coluna(bundle.getLabel("Vencimento"), bundle.getLabel("Cobranca"), "vencimento", CobrancaFixa.class, Date.class));
        relatorioDeContasFixasAPagar.addColunaExibida(new Coluna(bundle.getLabel("Valor"), bundle.getLabel("Cobranca"), "valor", CobrancaFixa.class, BigDecimal.class, TipoFormatacaoNumero.MOEDA, Totalizador.SUM));

        String gerarCodigo = new GeradorDeCodigoFonteDeModeloDeRelatorio().gerarCodigo(relatorioDeContasFixasAPagar);

        StringSelection selection = new StringSelection(gerarCodigo);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
    }

    public String getBundles() {
        if (bundles == null) {
            return null;
        } else {
            return bundles.toString();
        }
    }
}
