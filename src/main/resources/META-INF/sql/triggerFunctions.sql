/* 
 * Deve ser criado todos os gatilhos e procedure das tabelas no banco de dados
 */
/**
 * Author:  Rafael Fernando Rauber
 * Created: 24/10/2017
 */

CREATE OR REPLACE FUNCTION somaSaldoEstoquePorDeposito(id_item bigint, id_deposito bigint, quantidade numeric) RETURNS VOID AS $BODY$
    BEGIN

           -- Se não existir SaldoDeEstoquePorDeposito para o item e deposito, deve ser criado um novo, caso existir deve ser atualizado o saldo.
           IF(0 = (SELECT COUNT(*) FROM saldodeestoquepordeposito as saldo WHERE saldo.item_id = id_item AND saldo.deposito_id = id_deposito)) THEN
            -- Faz o insert na tabela SaldoDeEstoquePorDeposito
             INSERT INTO saldodeestoquepordeposito 
                    VALUES ((select nextval('seq_saldodeestoquepordeposito')), quantidade, id_deposito, id_item);
           ELSE 
           -- Faz o update na tabela SaldoDeEstoquePorDeposito
             UPDATE saldodeestoquepordeposito 
                SET saldo = saldo + quantidade
              WHERE item_id = id_item
                AND deposito_id = id_deposito;
           END IF;

    END;
$BODY$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION subtraiSaldoEstoquePorDeposito(id_item bigint, id_deposito bigint, quantidade numeric) RETURNS VOID AS $BODY$
    BEGIN

           -- Se não existir SaldoDeEstoquePorDeposito para o item e deposito, deve ser criado um novo, caso existir deve ser atualizado o saldo.
           IF(0 = (SELECT COUNT(*) FROM saldodeestoquepordeposito as saldo WHERE saldo.item_id = id_item AND saldo.deposito_id = id_deposito)) THEN
            -- Faz o insert na tabela SaldoDeEstoquePorDeposito
             INSERT INTO saldodeestoquepordeposito 
                    VALUES ((select nextval('seq_saldodeestoquepordeposito')), (0 - quantidade), id_deposito, id_item);
           ELSE 
           -- Faz o update na tabela SaldoDeEstoquePorDeposito
             UPDATE saldodeestoquepordeposito 
                SET saldo = saldo - quantidade
              WHERE item_id = id_item
                AND deposito_id = id_deposito;
           END IF;

    END;
$BODY$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION somaSaldoLotePorDeposito(id_deposito bigint, id_lote bigint, quantidade numeric) RETURNS VOID AS $BODY$
    BEGIN

           -- Se não existir SaldoDeEstoquePorDeposito para o item e deposito, deve ser criado um novo, caso existir deve ser atualizado o saldo.
           IF(0 = (SELECT COUNT(*) FROM saldodelotepordeposito as saldo WHERE saldo.deposito_id = id_deposito AND saldo.loteitem_id = id_lote)) THEN
            -- Faz o insert na tabela SaldoDeEstoquePorDeposito
             INSERT INTO saldodelotepordeposito 
                    VALUES ((select nextval('seq_saldodelotepordeposito')), quantidade, id_deposito, id_lote);
           ELSE 
           -- Faz o update na tabela SaldoDeEstoquePorDeposito
             UPDATE saldodelotepordeposito 
                SET saldo = saldo + quantidade
              WHERE loteitem_id = id_lote
                AND deposito_id = id_deposito;
           END IF;

    END;
$BODY$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION subtraiSaldoLotePorDeposito(id_deposito bigint, id_lote bigint, quantidade numeric) RETURNS VOID AS $BODY$
    BEGIN

           -- Se não existir SaldoDeEstoquePorDeposito para o item e deposito, deve ser criado um novo, caso existir deve ser atualizado o saldo.
           IF(0 = (SELECT COUNT(*) FROM saldodelotepordeposito as saldo WHERE saldo.deposito_id = id_deposito AND saldo.loteitem_id = id_lote)) THEN
            -- Faz o insert na tabela SaldoDeEstoquePorDeposito
             INSERT INTO saldodelotepordeposito 
                    VALUES ((select nextval('seq_saldodelotepordeposito')), (0 - quantidade), id_deposito, id_lote);
           ELSE 
           -- Faz o update na tabela SaldoDeEstoquePorDeposito
             UPDATE saldodelotepordeposito 
                SET saldo = saldo - quantidade
              WHERE loteitem_id = id_lote
                AND deposito_id = id_deposito;
           END IF;

    END;
$BODY$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION somaSaldoEstoquePorConta(id_item bigint, id_conta bigint, quantidade numeric) RETURNS VOID AS $BODY$
    BEGIN

           -- Se não existir SaldoDeEstoquePorConta para o item e conta, deve ser criado um novo, caso existir deve ser atualizado o saldo.
           IF(0 = (SELECT COUNT(*) FROM saldodeestoqueporconta as saldo WHERE saldo.item_id = id_item AND saldo.contadeestoque_id = id_conta)) THEN
            -- Faz o insert na tabela SaldoDeEstoquePorDeposito
             INSERT INTO saldodeestoqueporconta 
                    VALUES ((select nextval('seq_saldodeestoqueporconta')), quantidade, id_conta, id_item);
           ELSE 
           -- Faz o update na tabela SaldoDeEstoquePorConta
             UPDATE saldodeestoqueporconta 
                SET saldo = saldo + quantidade
              WHERE item_id = id_item
                AND contadeestoque_id = id_conta;
           END IF;

    END;
$BODY$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION subtraiSaldoEstoquePorConta(id_item bigint, id_conta bigint, quantidade numeric) RETURNS VOID AS $BODY$
    BEGIN

           -- Se não existir SaldoDeEstoquePorConta para o item e conta, deve ser criado um novo, caso existir deve ser atualizado o saldo.
           IF(0 = (SELECT COUNT(*) FROM saldodeestoqueporconta as saldo WHERE saldo.item_id = id_item AND saldo.contadeestoque_id = id_conta)) THEN
            -- Faz o insert na tabela SaldoDeEstoquePorDeposito
             INSERT INTO saldodeestoqueporconta 
                    VALUES ((select nextval('seq_saldodeestoqueporconta')), (0 - quantidade), id_conta, id_item);
           ELSE 
           -- Faz o update na tabela SaldoDeEstoquePorConta
             UPDATE saldodeestoqueporconta 
                SET saldo = saldo - quantidade
              WHERE item_id = id_item
                AND contadeestoque_id = id_conta;
           END IF;

    END;
$BODY$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION somaSaldoLotePorItem(id_lote bigint, quantidade numeric) RETURNS VOID AS $BODY$
    BEGIN
             UPDATE loteitem 
                SET saldo = saldo + quantidade
              WHERE id = id_lote;
           
    END;
$BODY$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION subtraiSaldoLotePorItem(id_lote bigint, quantidade numeric) RETURNS VOID AS $BODY$
    BEGIN
             UPDATE loteitem 
                SET saldo = saldo - quantidade
              WHERE id = id_lote;
           
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
                RAISE NOTICE 'INS: Sem Alteração';
                RETURN NULL;
            ELSIF ('ENTRADA' = tipoOperacao) THEN
                RAISE NOTICE 'INS: Entrada';

                -- chama procedure para somar saldo de estoque
                PERFORM somaSaldoEstoquePorDeposito(NEW.item_id, NEW.deposito_id, NEW.quantidade);
                PERFORM somaSaldoEstoquePorConta(NEW.item_id, NEW.contadeestoque_id, NEW.quantidade);

                -- chama procedure para somar saldo de lote por item e por deposito
                IF(tipoDetalhamento = 'LOTES') THEN 
                   PERFORM somaSaldoLotePorItem(NEW.loteitem_id, NEW.quantidade);
                   PERFORM somaSaldoLotePorDeposito(NEW.deposito_id, NEW.loteitem_id, NEW.quantidade);
                END IF;

                RETURN NEW;
            ELSIF ('SAIDA' = tipoOperacao) THEN
                RAISE NOTICE 'INS: Saída';
                
                -- chama procedure para subtrair de estoque
                PERFORM subtraiSaldoEstoquePorDeposito(NEW.item_id, NEW.deposito_id, NEW.quantidade);
                PERFORM subtraiSaldoEstoquePorConta(NEW.item_id, NEW.contadeestoque_id, NEW.quantidade);

                -- chama procedure para subtrair saldo de lote por item e por deposito
                IF(tipoDetalhamento = 'LOTES') THEN 
                   PERFORM subtraiSaldoLotePorItem(NEW.loteitem_id, NEW.quantidade);
                   PERFORM subtraiSaldoLotePorDeposito(NEW.deposito_id, NEW.loteitem_id, NEW.quantidade);
                END IF;

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
                    RAISE NOTICE 'ALT C: Sem Alteração';
                    RETURN NULL;
                ELSIF ('ENTRADA' = tipoOperacao) THEN
                RAISE NOTICE 'ALT C: Entrada';
                    
                    -- chama procedure para subtrair de estoque
                    PERFORM subtraiSaldoEstoquePorDeposito(NEW.item_id, NEW.deposito_id, NEW.quantidade);
                    PERFORM subtraiSaldoEstoquePorConta(NEW.item_id, NEW.contadeestoque_id, NEW.quantidade);
                    
                    -- chama procedure para subtrair saldo de lote por item e por deposito
                    IF(tipoDetalhamento = 'LOTES') THEN 
                       PERFORM subtraiSaldoLotePorItem(NEW.loteitem_id, NEW.quantidade);
                       PERFORM subtraiSaldoLotePorDeposito(NEW.deposito_id, NEW.loteitem_id, NEW.quantidade);
                    END IF;

                    RETURN NEW;
                ELSIF ('SAIDA' = tipoOperacao) THEN
                RAISE NOTICE 'ALT C: Saída';

                    -- chama procedure para somar saldo de estoque
                    PERFORM somaSaldoEstoquePorDeposito(NEW.item_id, NEW.deposito_id, NEW.quantidade);
                    PERFORM somaSaldoEstoquePorConta(NEW.item_id, NEW.contadeestoque_id, NEW.quantidade);

                    -- chama procedure para somar saldo de lote por item e por deposito
                    IF(tipoDetalhamento = 'LOTES') THEN 
                        PERFORM somaSaldoLotePorItem(NEW.loteitem_id, NEW.quantidade);
                        PERFORM somaSaldoLotePorDeposito(NEW.deposito_id, NEW.loteitem_id, NEW.quantidade);
                    END IF;

                    RETURN NEW;
                END IF;
            -- Se o estoque não for cancelado deve fazer a operação de update normal.
            ELSE
                IF ('SEM_ALTERACAO' = tipoOperacao) THEN
                    RAISE NOTICE 'ALT: Sem Alteração';

                    RETURN NULL;
                ELSIF ('ENTRADA' = tipoOperacao) THEN
                    RAISE NOTICE 'ALT: Entrada';

                    -- chama procedure para somar saldo de estoque
                    PERFORM somaSaldoEstoquePorDeposito(NEW.item_id, NEW.deposito_id, NEW.quantidade);
                    PERFORM somaSaldoEstoquePorConta(NEW.item_id, NEW.contadeestoque_id, NEW.quantidade);

                    -- chama procedure para somar saldo de lote por item e por deposito
                    IF(tipoDetalhamento = 'LOTES') THEN 
                       PERFORM somaSaldoLotePorItem(NEW.loteitem_id, NEW.quantidade);
                       PERFORM somaSaldoLotePorDeposito(NEW.deposito_id, NEW.loteitem_id, NEW.quantidade);
                    END IF;

                    RETURN NEW;
                ELSIF ('SAIDA' = tipoOperacao) THEN
                    RAISE NOTICE 'ALT: Saída';

                    -- chama procedure para subtrair de estoque
                    PERFORM subtraiSaldoEstoquePorDeposito(NEW.item_id, NEW.deposito_id, NEW.quantidade);
                    PERFORM subtraiSaldoEstoquePorConta(NEW.item_id, NEW.contadeestoque_id, NEW.quantidade);

                    -- chama procedure para subtrair saldo de lote por item e por deposito
                    IF(tipoDetalhamento = 'LOTES') THEN 
                       PERFORM subtraiSaldoLotePorItem(NEW.loteitem_id, NEW.quantidade);
                       PERFORM subtraiSaldoLotePorDeposito(NEW.deposito_id, NEW.loteitem_id, NEW.quantidade);
                    END IF;

                    RETURN NEW;
                END IF;
            END IF;
        ELSIF (TG_OP = 'DELETE') THEN
            
            -- Busca tipo de operação
            tipoOperacao = (SELECT operacaofisica from operacaodeestoque where id = OLD.operacaodeestoque_id);

            -- Busca detalhamento
            tipoDetalhamento = (SELECT detalhamento from item where id = OLD.item_id);

            IF ('SEM_ALTERACAO' = tipoOperacao) THEN
                    RAISE NOTICE 'DEL: Sem Alteração';

                    RETURN NULL;
                ELSIF ('ENTRADA' = tipoOperacao) THEN
                    RAISE NOTICE 'DEL: Entrada';

                    -- chama procedure para subtrair de estoque
                    PERFORM subtraiSaldoEstoquePorDeposito(OLD.item_id, OLD.deposito_id, OLD.quantidade);
                    PERFORM subtraiSaldoEstoquePorConta(OLD.item_id, OLD.contadeestoque_id, OLD.quantidade);

                    -- chama procedure para subtrair saldo de lote por item e por deposito
                    IF(tipoDetalhamento = 'LOTES') THEN 
                       PERFORM subtraiSaldoLotePorItem(OLD.loteitem_id, OLD.quantidade);
                       PERFORM subtraiSaldoLotePorDeposito(OLD.deposito_id, OLD.loteitem_id, OLD.quantidade);
                    END IF;

                    RETURN NEW;
                ELSIF ('SAIDA' = tipoOperacao) THEN
                    RAISE NOTICE 'DEL: Saída';

                    -- chama procedure para somar saldo de estoque
                    PERFORM somaSaldoEstoquePorDeposito(OLD.item_id, OLD.deposito_id, OLD.quantidade);
                    PERFORM somaSaldoEstoquePorConta(OLD.item_id, OLD.contadeestoque_id, OLD.quantidade);

                    -- chama procedure para somar saldo de lote por item e por deposito
                    IF(tipoDetalhamento = 'LOTES') THEN 
                       PERFORM somaSaldoLotePorItem(OLD.loteitem_id, OLD.quantidade);
                       PERFORM somaSaldoLotePorDeposito(OLD.deposito_id, OLD.loteitem_id, OLD.quantidade);
                    END IF;


                    RETURN OLD;
                END IF;
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