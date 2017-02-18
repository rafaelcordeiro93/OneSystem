/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.reportTemplate.column;

import ar.com.fdvs.dj.domain.DJCalculation;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import br.com.onesystem.domain.Configuracao;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.reportTemplate.ColunaRepository;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.war.service.ConfiguracaoService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class BalancoFisicoColumn {

    public List<ColunaRepository> getColunas() throws DadoInvalidoException {
        ConfiguracaoService configuracaoService = new ConfiguracaoService();
        Configuracao conf = configuracaoService.buscar();

        List<ColunaRepository> colunas = new ArrayList<ColunaRepository>();

        // Adiciona Id
        AbstractColumn colunaId = ColumnBuilder.getNew()
                .setColumnProperty("item.id", Long.class.getName())
                .setTitle(new BundleUtil().getLabel("Id")).setWidth(new Integer(75))
                .build();
        colunas.add(new ColunaRepository(colunaId, null));

        // Adiciona Nome
        AbstractColumn colunaNome = ColumnBuilder.getNew()
                .setColumnProperty("item.nome", String.class.getName())
                .setTitle(new BundleUtil().getLabel("Nome")).setWidth(new Integer(100))
                .build();
        colunas.add(new ColunaRepository(colunaNome, null));

        // Adiciona Saldo
        AbstractColumn colunaSaldo = ColumnBuilder.getNew()
                .setColumnProperty("saldo", BigDecimal.class.getName()).setPattern("0.00")
                .setTitle(new BundleUtil().getLabel("Saldo")).setWidth(new Integer(75))
                .build();
        colunas.add(new ColunaRepository(colunaSaldo, null));

        // Adiciona CustoMedio
        AbstractColumn colunaCustoMedio = ColumnBuilder.getNew()
                .setColumnProperty("custoMedio", BigDecimal.class.getName())
                .setTitle(new BundleUtil().getLabel("Custo_Medio")).setWidth(new Integer(75)).setPattern(conf.getMoedaPadrao().getSigla() + " 0.00")
                .build();
        colunas.add(new ColunaRepository(colunaCustoMedio, DJCalculation.SUM));

        // Adiciona CustoTotal
        AbstractColumn colunaCustoTotal = ColumnBuilder.getNew()
                .setColumnProperty("custoTotal", BigDecimal.class.getName())
                .setTitle(new BundleUtil().getLabel("Custo_Total")).setWidth(new Integer(75)).setPattern(conf.getMoedaPadrao().getSigla() + " 0.00")
                .build();
        colunas.add(new ColunaRepository(colunaCustoTotal, null));

        // Adiciona Marca
        AbstractColumn colunaMarca = ColumnBuilder.getNew()
                .setColumnProperty("item.marca.nome", String.class.getName())
                .setTitle(new BundleUtil().getLabel("Marca")).setWidth(new Integer(100))
                .build();
        colunas.add(new ColunaRepository(colunaMarca, null));

        // Adiciona GrupoFiscal
        AbstractColumn colunaGrupoFiscal = ColumnBuilder.getNew()
                .setColumnProperty("item.grupoFiscal.nome", String.class.getName())
                .setTitle(new BundleUtil().getLabel("Grupo_Fiscal")).setWidth(new Integer(100))
                .build();
        colunas.add(new ColunaRepository(colunaGrupoFiscal, null));

        // Adiciona Grupo
        AbstractColumn colunaGrupo = ColumnBuilder.getNew()
                .setColumnProperty("item.grupo.nome", String.class.getName())
                .setTitle(new BundleUtil().getLabel("Grupo")).setWidth(new Integer(100))
                .build();
        colunas.add(new ColunaRepository(colunaGrupo, null));

        return colunas;
    }

}
