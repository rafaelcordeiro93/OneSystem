/* 
 * Deve ser criado todos os gatilhos e procedure das tabelas no banco de dados
 */
/**
 * Author:  Rafael Fernando Rauber
 * Created: 24/10/2017
 */
CREATE OR REPLACE FUNCTION somaSaldoDeEstoque(id_item bigint, id_deposito bigint, id_contadeestoque bigint, id_lote bigint, tipoDetalhamento varchar, quantidade numeric) RETURNS VOID AS $BODY$
    BEGIN

            IF(tipoDetalhamento = 'LOTES' AND id_lote <> null) THEN 

                -- Se não existir SaldoDeEstoque para o item, deposito, contadeestoque e lote deve ser criado um novo, caso existir deve ser atualizado o saldo.
                IF(0 = (SELECT COUNT(*) FROM saldodeestoque as saldo WHERE saldo.item_id = id_item AND saldo.deposito_id = id_deposito AND saldo.contadeestoque_id = id_contadeestoque AND saldo.loteitem_id = id_lote)) THEN
                    -- Faz o insert na tabela saldodeestoque
                    INSERT INTO saldodeestoque 
                         VALUES ((select nextval('seq_saldodeestoque')), quantidade, id_contadeestoque, id_deposito, id_item, id_lote);
                ELSE 
                    -- Faz o update na tabela SaldoDeEstoquePorDeposito
                    UPDATE saldodeestoque 
                       SET saldo = saldo + quantidade
                     WHERE loteitem_id = id_lote
                       AND deposito_id = id_deposito
                       AND item_id = id_item
                       AND contadeestoque_id = id_contadeestoque;
                END IF;

            -- Se não existir SaldoDeEstoque para o item, deposito e contadeestoque deve ser criado um novo, caso existir deve ser atualizado o saldo.
            ELSIF(0 = (SELECT COUNT(*) FROM saldodeestoque as saldo 
                        WHERE saldo.item_id = id_item AND saldo.deposito_id = id_deposito AND saldo.contadeestoque_id = id_contadeestoque)) THEN
                -- Faz o insert na tabela saldodeestoque
                INSERT INTO saldodeestoque 
                     VALUES ((select nextval('seq_saldodeestoque')), quantidade, id_contadeestoque, id_deposito, id_item, null);
           ELSE 
                -- Faz o update na tabela SaldoDeEstoque
                UPDATE saldodeestoque 
                   SET saldo = saldo + quantidade
                 WHERE item_id = id_item
                   AND contadeestoque_id = id_contadeestoque
                   AND deposito_id = id_deposito;
           END IF;

    END;
$BODY$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION subtraiSaldoDeEstoque(id_item bigint, id_deposito bigint, id_contadeestoque bigint, id_lote bigint, tipoDetalhamento varchar, quantidade numeric) RETURNS VOID AS $BODY$
    BEGIN

            IF(tipoDetalhamento = 'LOTES' AND id_lote <> null) THEN 

                -- Se não existir SaldoDeEstoque para o item, deposito, contadeestoque e lote deve ser criado um novo, caso existir deve ser atualizado o saldo.
                IF(0 = (SELECT COUNT(*) FROM saldodeestoque as saldo WHERE saldo.item_id = id_item AND saldo.deposito_id = id_deposito AND saldo.contadeestoque_id = id_contadeestoque AND saldo.loteitem_id = id_lote)) THEN
                    -- Faz o insert na tabela saldodeestoque
                    INSERT INTO saldodeestoque 
                         VALUES ((select nextval('seq_saldodeestoque')), (0 - quantidade), id_contadeestoque, id_deposito, id_item, id_lote);
                ELSE 
                    -- Faz o update na tabela SaldoDeEstoquePorDeposito
                    UPDATE saldodeestoque 
                       SET saldo = saldo - quantidade
                     WHERE loteitem_id = id_lote
                       AND deposito_id = id_deposito
                       AND item_id = id_item
                       AND contadeestoque_id = id_contadeestoque;
                END IF;

            -- Se não existir SaldoDeEstoque para o item, deposito e contadeestoque deve ser criado um novo, caso existir deve ser atualizado o saldo.
            ELSIF(0 = (SELECT COUNT(*) FROM saldodeestoque as saldo 
                        WHERE saldo.item_id = id_item AND saldo.deposito_id = id_deposito AND saldo.contadeestoque_id = id_contadeestoque)) THEN
                -- Faz o insert na tabela saldodeestoque
                INSERT INTO saldodeestoque 
                     VALUES ((select nextval('seq_saldodeestoque')), (0 - quantidade), id_contadeestoque, id_deposito, id_item, null);
           ELSE 
                -- Faz o update na tabela SaldoDeEstoque
                UPDATE saldodeestoque 
                   SET saldo = saldo - quantidade
                 WHERE item_id = id_item
                   AND contadeestoque_id = id_contadeestoque
                   AND deposito_id = id_deposito;
           END IF;

    END;
$BODY$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION alteraNumeroNotaFiscal(id_notaemitida bigint, id_lotenotafiscal bigint, id_filial bigint) RETURNS VOID AS $BODY$
BEGIN
            UPDATE notaemitida 
               SET numeronf = (select nextval('seq_numeronotafiscal'))
             WHERE id = id_notaemitida
               AND lotenotafiscal_id = id_lotenotafiscal
               AND filial_id = id_filial;
          
END;
$BODY$ LANGUAGE plpgsql;


/* 
 * Este procedimento deve fazer a atualização do saldo de estoque quando for
 * recebido uma operação de inserção, atualização ou deleção de algum 
 * registro de Estoque.
 */
CREATE OR REPLACE FUNCTION atualizaEstoque() RETURNS TRIGGER AS $BODY$
    DECLARE
        tipoOperacao varchar; 
        tipoDetalhamento varchar;
    BEGIN

    -- Verifica o tipo da operação
        IF (TG_OP = 'INSERT') THEN

            -- Busca tipo de operação
            tipoOperacao = (SELECT operacaofisica from operacaodeestoque where id = NEW.operacaodeestoque_id);
            
            -- Busca detalhamento
            tipoDetalhamento = (SELECT detalhamento from item where id = NEW.item_id);
            
            IF ('SEM_ALTERACAO' = tipoOperacao) THEN
                RETURN NULL;
            ELSIF ('ENTRADA' = tipoOperacao) THEN
                -- chama procedure para somar saldo de estoque
                PERFORM somaSaldoDeEstoque(NEW.item_id, NEW.deposito_id, NEW.contadeestoque_id, NEW.loteitem_id, tipoDetalhamento, NEW.quantidade);

                RETURN NEW;
            ELSIF ('SAIDA' = tipoOperacao) THEN
                -- chama procedure para subtrair de estoque
                PERFORM subtraiSaldoDeEstoque(NEW.item_id, NEW.deposito_id, NEW.contadeestoque_id, NEW.loteitem_id, tipoDetalhamento, NEW.quantidade);

                RETURN NEW;
            END IF;
        ELSIF (TG_OP = 'UPDATE') THEN
            
            -- Busca tipo de operação
            tipoOperacao = (SELECT operacaofisica from operacaodeestoque where id = NEW.operacaodeestoque_id);
            
            -- Busca detalhamento
            tipoDetalhamento = (SELECT detalhamento from item where id = NEW.item_id);

            -- Se o estoque for cancelado deve fazer operação contrária
            IF (NEW.cancelado = true) THEN
                IF ('SEM_ALTERACAO' = tipoOperacao) THEN
                    RETURN NULL;
                ELSIF ('ENTRADA' = tipoOperacao) THEN
                    -- chama procedure para subtrair de estoque
                    PERFORM subtraiSaldoDeEstoque(NEW.item_id, NEW.deposito_id, NEW.contadeestoque_id, NEW.loteitem_id, tipoDetalhamento, NEW.quantidade);
                    
                    RETURN NEW;
                ELSIF ('SAIDA' = tipoOperacao) THEN
                    -- chama procedure para somar saldo de estoque
                    PERFORM somaSaldoDeEstoque(NEW.item_id, NEW.deposito_id, NEW.contadeestoque_id, NEW.loteitem_id, tipoDetalhamento, NEW.quantidade);

                    RETURN NEW;
                END IF;
            -- Se o estoque não for cancelado deve fazer a operação de update normal.
            ELSIF(NEW.cancelado = false) THEN
                IF ('SEM_ALTERACAO' = tipoOperacao) THEN
                    RETURN NULL;
                ELSIF ('ENTRADA' = tipoOperacao) THEN
                    -- chama procedure para somar saldo de estoque
                    PERFORM somaSaldoDeEstoque(NEW.item_id, NEW.deposito_id, NEW.contadeestoque_id, NEW.loteitem_id, tipoDetalhamento, NEW.quantidade);

                    RETURN NEW;
                ELSIF ('SAIDA' = tipoOperacao) THEN
                    -- chama procedure para subtrair de estoque
                    PERFORM subtraiSaldoDeEstoque(NEW.item_id, NEW.deposito_id, NEW.contadeestoque_id, NEW.loteitem_id, tipoDetalhamento, NEW.quantidade);

                    RETURN NEW;
                END IF;
            END IF;
        ELSIF (TG_OP = 'DELETE') THEN
            
            -- Busca tipo de operação
            tipoOperacao = (SELECT operacaofisica from operacaodeestoque where id = OLD.operacaodeestoque_id);

            -- Busca detalhamento
            tipoDetalhamento = (SELECT detalhamento from item where id = OLD.item_id);

            IF ('SEM_ALTERACAO' = tipoOperacao) THEN
                    RETURN NULL;
                ELSIF ('ENTRADA' = tipoOperacao) THEN
                    -- chama procedure para subtrair de estoque
                    PERFORM subtraiSaldoDeEstoque(OLD.item_id, OLD.deposito_id, OLD.contadeestoque_id, OLD.loteitem_id, tipoDetalhamento, OLD.quantidade);

                    RETURN NEW;
                ELSIF ('SAIDA' = tipoOperacao) THEN
                    -- chama procedure para somar saldo de estoque
                    PERFORM somaSaldoDeEstoque(OLD.item_id, OLD.deposito_id, OLD.contadeestoque_id, OLD.loteitem_id, tipoDetalhamento, OLD.quantidade);

                    RETURN OLD;
                END IF;
        END IF;        
    RETURN NULL;
    END;
$BODY$ LANGUAGE plpgsql;

/* 
 * Este procedimento deve fazer a atualização da numeracao quando for
 * recebido uma operação de inserção de algum registro de NotaEmitida.
 */
CREATE OR REPLACE FUNCTION atualizaNumeracaoNotaFiscal() RETURNS TRIGGER AS $BODY$
        BEGIN

    -- Verifica o tipo da operação
        IF (TG_OP = 'INSERT') THEN
            -- Chama metodo que faz o update na numeracao da nota
            PERFORM alteraNumeroNotaFiscal(NEW.id, NEW.lotenotafiscal_id, NEW.filial_id);
        END IF;
    RETURN NULL;
    END;
$BODY$ LANGUAGE plpgsql;


/*
* Gatilho que chamará o procedimento atualizaEstoque após a inserção, 
* atualização ou deleção de algum Estoque.
*/
DROP TRIGGER IF EXISTS chamaAtualizacaoDeEstoque ON estoque;

CREATE TRIGGER chamaAtualizacaoDeEstoque
AFTER INSERT OR UPDATE OR DELETE ON estoque
FOR EACH ROW
EXECUTE PROCEDURE atualizaEstoque();


DROP TRIGGER IF EXISTS chamaAtualizacaoDeNumeracaoNotaFiscal ON notaemitida;

CREATE TRIGGER chamaAtualizacaoDeNumeracaoNotaFiscal
AFTER INSERT ON notaemitida
FOR EACH ROW
EXECUTE PROCEDURE atualizaNumeracaoNotaFiscal();