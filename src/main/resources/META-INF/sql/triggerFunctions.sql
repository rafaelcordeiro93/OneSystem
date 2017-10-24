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

/* 
 * Este procedimento deve fazer a atualização do saldo de estoque quando for
 * recebido uma operação de inserção, atualização ou deleção de algum 
 * registro de Estoque.
 */
CREATE OR REPLACE FUNCTION atualizaEstoque() RETURNS TRIGGER AS $BODY$
    DECLARE
        tipoOperacao varchar; 
    BEGIN

    -- Verifica o tipo da operação
        IF (TG_OP = 'INSERT') THEN

            -- Busca tipo de operação
            tipoOperacao = (SELECT operacaofisica from operacaodeestoque where id = NEW.operacaodeestoque_id);
            
            IF ('SEM_ALTERACAO' = tipoOperacao) THEN
                RAISE NOTICE 'INS: Sem Alteração';
                RETURN NULL;
            ELSIF ('ENTRADA' = tipoOperacao) THEN
                RAISE NOTICE 'INS: Entrada';
                UPDATE item 
                   SET saldo = saldo + NEW.quantidade 
                WHERE id = NEW.item_id;

                -- chama procedure para somar saldo de estoque
                PERFORM somaSaldoEstoquePorDeposito(NEW.item_id, NEW.deposito_id, NEW.quantidade);
                
                RETURN NEW;
            ELSIF ('SAIDA' = tipoOperacao) THEN
                RAISE NOTICE 'INS: Saída';
                UPDATE item 
                   SET saldo = saldo - NEW.quantidade 
                WHERE id = NEW.item_id;
                
                -- chama procedure para subtrair de estoque
                PERFORM subtraiSaldoEstoquePorDeposito(NEW.item_id, NEW.deposito_id, NEW.quantidade);

                RETURN NEW;
            END IF;
        ELSIF (TG_OP = 'UPDATE') THEN
            
            -- Busca tipo de operação
            tipoOperacao = (SELECT operacaofisica from operacaodeestoque where id = NEW.operacaodeestoque_id);

            -- Se o estoque for cancelado deve fazer operação contrária
            IF (NEW.cancelado = true) THEN
                IF ('SEM_ALTERACAO' = tipoOperacao) THEN
                    RAISE NOTICE 'ALT C: Sem Alteração';
                    RETURN NULL;
                ELSIF ('ENTRADA' = tipoOperacao) THEN
                RAISE NOTICE 'ALT C: Entrada';
                    
                    UPDATE item 
                       SET saldo = saldo - NEW.quantidade 
                    WHERE id = NEW.item_id;

                    -- chama procedure para subtrair de estoque
                    PERFORM subtraiSaldoEstoquePorDeposito(NEW.item_id, NEW.deposito_id, NEW.quantidade);

                    RETURN NEW;
                ELSIF ('SAIDA' = tipoOperacao) THEN
                RAISE NOTICE 'ALT C: Saída';
                    UPDATE item 
                       SET saldo = saldo + NEW.quantidade 
                    WHERE id = NEW.item_id;

                    -- chama procedure para somar saldo de estoque
                    PERFORM somaSaldoEstoquePorDeposito(NEW.item_id, NEW.deposito_id, NEW.quantidade);

                    RETURN NEW;
                END IF;
            -- Se o estoque não for cancelado deve fazer a operação de update normal.
            ELSE
                IF ('SEM_ALTERACAO' = tipoOperacao) THEN
                    RAISE NOTICE 'ALT: Sem Alteração';

                    RETURN NULL;
                ELSIF ('ENTRADA' = tipoOperacao) THEN
                    RAISE NOTICE 'ALT: Entrada';

                    UPDATE item 
                       SET saldo = saldo + NEW.quantidade 
                    WHERE id = NEW.item_id;

                     -- chama procedure para somar saldo de estoque
                    PERFORM somaSaldoEstoquePorDeposito(NEW.item_id, NEW.deposito_id, NEW.quantidade);

                    RETURN NEW;
                ELSIF ('SAIDA' = tipoOperacao) THEN
                    RAISE NOTICE 'ALT: Saída';
                    UPDATE item 
                       SET saldo = saldo - NEW.quantidade 
                    WHERE id = NEW.item_id;

                    -- chama procedure para subtrair de estoque
                    PERFORM subtraiSaldoEstoquePorDeposito(NEW.item_id, NEW.deposito_id, NEW.quantidade);

                    RETURN NEW;
                END IF;
            END IF;
        ELSIF (TG_OP = 'DELETE') THEN
            
            -- Busca tipo de operação
            tipoOperacao = (SELECT operacaofisica from operacaodeestoque where id = OLD.operacaodeestoque_id);

            IF ('SEM_ALTERACAO' = tipoOperacao) THEN
                    RAISE NOTICE 'DEL: Sem Alteração';

                    RETURN NULL;
                ELSIF ('ENTRADA' = tipoOperacao) THEN
                    RAISE NOTICE 'DEL: Entrada';

                    UPDATE item 
                       SET saldo = saldo - OLD.quantidade 
                    WHERE id = OLD.item_id;

                    -- chama procedure para subtrair de estoque
                    PERFORM subtraiSaldoEstoquePorDeposito(OLD.item_id, OLD.deposito_id, OLD.quantidade);

                    RETURN NEW;
                ELSIF ('SAIDA' = tipoOperacao) THEN
                    RAISE NOTICE 'DEL: Saída';

                    UPDATE item 
                       SET saldo = saldo + OLD.quantidade 
                    WHERE id = OLD.item_id;

                    -- chama procedure para somar saldo de estoque
                    PERFORM somaSaldoEstoquePorDeposito(OLD.item_id, OLD.deposito_id, OLD.quantidade);

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