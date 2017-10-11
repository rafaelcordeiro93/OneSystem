package br.com.onesystem.war.service.impl;

import br.com.onesystem.domain.Nota;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.primefaces.context.RequestContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Hibernate;

public abstract class BasicCrudMBImpl<Bean> {

    protected Bean beanSelecionado;
    protected List<Bean> beans;
    protected List<Bean> beansFiltrados;

    public void exibirNaTela(String selecao) {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("responsive", true);
        opcoes.put("width", 950);
        opcoes.put("draggable", false);
        opcoes.put("height", 471);
        opcoes.put("contentWidth", "100%");
        opcoes.put("contentHeight", "100%");
        opcoes.put("headerElement", "customheader");

        RequestContext.getCurrentInstance().openDialog("/menu/" + selecao, opcoes, null);
    }

    public abstract void abrirDialogo();

    public abstract List<Bean> complete(String query);

    public abstract String abrirEdicao();

    public void selecionar() {
        inicializaRegistro(beanSelecionado); //Inicializa listas do registro selecionado.
        RequestContext.getCurrentInstance().closeDialog(beanSelecionado);
    }

    public void inicializaRegistro(Bean bean) {
        if (bean != null) {
            try {
                try {
                    bean = beans.get(beans.indexOf(bean));
                } catch (ArrayIndexOutOfBoundsException aie) {
                    bean = getId(bean);
                    if (bean == null) {
                        return;
                    }
                }
                Method[] methods = bean.getClass().getMethods();
                for (Method m : methods) {
                    if (m.getReturnType().equals(List.class)) {
                        Method mList = List.class.getMethod("size", null);

                        m.setAccessible(true);
                        mList.setAccessible(true);

                        Object objeto = m.invoke(bean, null);
//                    Object test = mList.invoke(objeto, null);
                        Hibernate.initialize(objeto);
                    }
                }
            } catch (IllegalAccessException ex) {
                throw new RuntimeException("Erro de acesso ao método.");
            } catch (IllegalArgumentException ex) {
                throw new RuntimeException("Erro parametros inválidos ao acessar o método.");
            } catch (InvocationTargetException ex) {
                System.out.println("BasicCrudMBImpl - LAZY: " + ex.getMessage());
            } catch (NoSuchMethodException ex) {
                throw new RuntimeException("Erro o método não existe.");
            } catch (SecurityException ex) {
                throw new RuntimeException("Erro de segurança ao realizar o acesso.");
            }
        }
    }

    private Bean getId(Bean bean) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Method mId = bean.getClass().getMethod("getId");
        mId.setAccessible(true);
        Object id = mId.invoke(bean, null);
        if (id != null) {
            for (Bean b : beans) {
                Method m = b.getClass().getMethod("getId");
                m.setAccessible(true);
                Object idB = m.invoke(b, null);
                if (id.equals(idB)) {
                    return b;
                }
            }
        }
        return null;
    }

    public Bean getBeanSelecionado() {
        return beanSelecionado;
    }

    public void setBeanSelecionado(Bean beanSelecionado) {
        this.beanSelecionado = beanSelecionado;
    }

    public List<Bean> getBeans() {
        return beans;
    }

    public void setBeans(List<Bean> beans) {
        this.beans = beans;
    }

    public List<Bean> getBeansFiltrados() {
        return beansFiltrados;
    }

    public void setBeansFiltrados(List<Bean> beansFiltrados) {
        this.beansFiltrados = beansFiltrados;
    }

}
