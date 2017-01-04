package br.com.onesystem.war.service.impl;
/**
 * *****************************************
 * NOSSOS IMPORTS
 * *****************************************
 * */
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;

import br.com.onesystem.war.service.impl.BasicMBImpl;

/**
 * *****************************************
 * *****************************************
 * *****************************************
 *
 */

/*
   * Passamos através de um Generics "Bean" o tipo da nossa Classe,
   * mas sabemos e devemos ter a certeza que ela sempre estende de 
   * AbstractBean. Dessa forma, independente do tipo da nossa 
   * classe podemos usar métodos como
   * equals, hashCode, getId() e setId().
   * */
public abstract class BasicCrudMBImpl<Bean>
        extends BasicMBImpl {

    /*
          * Definimos aqui como protected para que a classe 
          * que estará estendendo
          * nosso BasicCrudMBImpl possa acessar diretamente 
          * nosso bean. Esse objeto bean servirá para todo 
          * tipo de manipulação do usuário, ou seja,
          * quando ele for inserir um novo Funcionario é 
          * através do objeto "bean", quando
          * ele for alterar um Funcionario também é através do 
          * objeto "bean" e o mesmo acontece com
          * o método de remoção.
          * */
    protected Bean bean;

    /*
          * A maioria, se não todos os CRUD's, contam com uma lista 
          * de objetos retornados do banco, por exemplo:
          * Lista de Clientes, lista de processos e assim por diante. 
          * Armazenaremos nossa lista no objeto "beans"
          * */
    protected List<Bean> beans;

    /*
          * Quando estamos trabalhando com JSF, temos que atualizar
          * os componentes necessários após uma
          * inserção, deleção ou alteração no banco, então temos que 
          * parametrizar os componentes que deverão
          * ser atualizados após essas operações. Para isso recebemos
          * uma lista com os id's dos componentes
          * que devem ser atualizados após determinadas operações.
          *
          *  Ex: Ao clicar em salvar no formulário de Endereço você 
          * quer que este endereço apareça em um
          *  "<p:dataTable>" para o usuário, então você passa 
          * ID deste componente dataTable.
          * */
    private List<String> componentesUpdateOnSave;

    /*
          * Como o próprio nome já propõe, este método é responsável 
          * por preparar a inserção de um novo
          * objeto. Mas atenção: este irá apenas criar o objeto em 
          * memória, para que você possa manipulá-lo, e não
          * salvá-lo no banco.
          * */
    public void novo(ActionEvent event) {

        beforeNew(event);

        //Cria o novo objeto de acordo com o tipo 
        // "Bean" passado pelo Generics
        bean = getNewInstanceOfBean();

        /*
                * Recebemos através de um <f:attribute> 
                 * a lista de componentes que devem ser atualizados
                * quando toda a operação terminar.
                * */
        componentesUpdateOnSave = (List<String>) event.getComponent()
                .getAttributes().get("componentesParaAtualizar");

        afterNew(event);
    }

    /*
          * Nosso método alterar não tem nada de mais, 
          * apenas recebe também uma lista de componentes
          * que devem ser atualizados
          * */
    public void alterar(ActionEvent event) {

        beforeEdit(event);

        componentesUpdateOnSave = (List<String>) event.getComponent()
                .getAttributes().get("componentesParaAtualizar");

        afterEdit(event);
    }

    /*
          * Este é o método responsável por retornar uma 
          * instância do nosso "Bean"
          * extraindo dele o tipo que foi passado, ou seja,
          * se seu "Bean" na verdade é um
          * "Cliente", ele criará um objeto do tipo Cliente;
          * */
    private Bean getNewInstanceOfBean() {
        try {
            ParameterizedType superClass
                    = (ParameterizedType) getClass()
                            .getGenericSuperclass();
            Class<Bean> type
                    = (Class<Bean>) superClass
                            .getActualTypeArguments()[0];
            return type.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
          * Deleta nosso bean do banco de dados. Não usamos aqui a
          * lista de componentes que devem
          * ser atualizados, pois a operação de deleção é feita 
          * geralmente em uma <p:dataTable> e
          * na própria deleção você já faz o update dos componentes. 
          * Totalmente diferente de operações como
          * alterar e inserir, que dependem da abertura de outra janela, 
          * independente da janela que estamos
          * trabalhando atualmente.
          * Pense da seguinte forma: Você pode criar um formulário 
          * de inserção de clientes e querer chamá-lo através
          * de outra tela que não tem nada haver com o contexto atual, 
          * assim você precisa abstrair o componente que
          * será atualizado após a inserção do novo cliente.
          * */
    public void deletar(ActionEvent event) {
        try {

            beforeRemove(event);

//            getBoPadrao().delete((AbstractBean) bean);
            addInfoMessage("Registro deletado com sucesso");

            bean = null;

            afterRemove(event);

        } catch (BOException e) {
            addErrorMessage(e.getMessage());
            FacesContext.getCurrentInstance()
                    .validationFailed();
        } catch (Exception e) {
            FacesContext.getCurrentInstance()
                    .validationFailed();
            addErrorMessage("Erro ao deletar. "
                    + e.getMessage());
        }
    }

    /*
          * Salva o bean no banco. Este método serve tanto para 
          * atualizar quanto para inserir um novo,
          * verificando apenas se o mesmo tem ou não ID associado a ele.
          * */
    public void salvar(ActionEvent event) {
        try {

            beforeSave(event);
            if (((AbstractBean) bean).getId() == null) {
//                bean = (Bean) getBoPadrao().save((AbstractBean) bean);
                addInfoMessage("Registro salvo com sucesso");

            } else {
//                getBoPadrao().update((AbstractBean) bean);
                addInfoMessage("Registro atualizado com sucesso");
            }

            bean = null;

            atualizarComponentes(componentesUpdateOnSave);

            afterSave(event);
        } catch (BOException e) {
            FacesContext.getCurrentInstance()
                    .validationFailed();
            addErrorMessage(e.getMessage());
        } catch (Exception e) {
            FacesContext.getCurrentInstance()
                    .validationFailed();
            addErrorMessage("Erro ao salvar. "
                    + e.getMessage());
        }
    }

    /*
          * Método responsável por atualizar nossos componentes
          * */
    private void atualizarComponentes(List<String> componentes) {
        for (String compId : componentes) {
            RequestContext.getCurrentInstance().update(compId);
        }
    }

    /*
          * Esse ponto é muito importante para abstração da 
          * nossa classe, pois com os métodos abaixo garantimos
          * que outros códigos, com lógicas totalmente diferentes 
          * possam ser adicionadas a nossa classe sem
          * que cause nenhum problema.
          * */
    public abstract void beforeNew(ActionEvent event);

    public abstract void afterNew(ActionEvent event);

    public abstract void beforeEdit(ActionEvent event);

    public abstract void afterEdit(ActionEvent event);

    public abstract void beforeRemove(ActionEvent event);

    public abstract void afterRemove(ActionEvent event);

    public abstract void beforeSave(ActionEvent event);

    public abstract void afterSave(ActionEvent event);

    /*
          * Como não sabemos se será carregada a lista de beans do banco, 
          * pois podem ser utilizadas diversas querys para tal tarefa, 
          * obrigamos a implementação deste
          * método, pois precisaremos dessa lista em determinados momentos.
          * */
    public abstract List<Bean> retornaBeansDoBanco();

    /*
          * Utilizado para inicializar listas e componentes necessários. 
          * Geralmente é utilizado juntamente com o @PostConstruct.
          * */
    public abstract void init();

    /*
          * Getters e Setters
          * */
    public Bean getBean() {
        return bean;
    }

    public void setBean(Bean bean) {
        this.bean = bean;
    }

    public List<Bean> getBeans() {
        try {
            beans = (List<Bean>) retornaBeansDoBanco();
            return beans;
        } catch (BOException e) {
            addErrorMessage(e.getMessage());
        }
        return null;
    }

    public void setBeans(List<Bean> beans) {
        this.beans = beans;
    }

    /*
          * Precisamos de um BO para realizar as operações de CRUD 
          * juntamente com um DAO, na verdade
          * o DAO é transparente ao ManagedBean, ele nem deve saber 
          * que este existe. O managedBean deve se
          * comunicar com nosso BO. Como não sabemos qual BO 
          * será utilizado, então obrigamos também a
          * implementação desses métodos.
          * */
    public abstract BasicBO getBoPadrao();

    public abstract void setBoPadrao(BasicBO boPadrao);
}
