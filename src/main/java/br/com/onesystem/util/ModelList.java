/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class ModelList<T> extends ListDataModel<Model> implements SelectableDataModel<Model>, Serializable {

    private List<Model<T>> removidos = new ArrayList<>();

    public ModelList() {
        super(new ArrayList<Model>());
    }

    public ModelList(List<T> data) {
        super(new ArrayList<Model>());
        if (data != null) {
            List<Model<T>> list = new ArrayList<>();
            for (T t : data) {
                Model<T> model = new Model<>(new Long(list.size()), t);
                list.add(model);
            }
            setWrappedData(list);
        }
    }

    @Override
    public Object getRowKey(Model model) {
        return model.getId();
    }

    @Override
    public Model getRowData(String string) {
        List<Model<T>> models = (List<Model<T>>) getWrappedData();
        for (Model<T> model : models) {
            if (model.getId().equals(new Long(string))) {
                return model;
            }
        }
        return null;
    }

    public void add(T t) {
        List<Model<T>> list = (List<Model<T>>) getWrappedData();
        Long id = list.isEmpty() ? new Long(0) : (list.stream().max(Comparator.comparing(Model::getId)).map(Model::getId).get()) + 1;
        list.add(new Model<>(id, t));
    }

    public void atualiza(Model m) {
        List<Model<T>> list = (List<Model<T>>) getWrappedData();
        list.set(list.indexOf(m), m);
    }

    public void atualiza(int index, T t) {
        List<Model<T>> list = (List<Model<T>>) getWrappedData();
        list.set(index, new Model<>(new Long(index), t));
    }

    public void remove(int index) {
        List<Model<T>> list = (List<Model<T>>) getWrappedData();
        Model m = getRowData(String.valueOf(index));
        removidos.add(m);
        list.remove(index);
    }

    public void remove(Model m) {
        List<Model<T>> list = (List<Model<T>>) getWrappedData();
        removidos.add(m);
        list.remove(m);
    }

    public void removeAll() {
        setWrappedData(new ArrayList<>());
    }

    public T get(int index) {
        return (T) getRowData(String.valueOf(index)).getObject();
    }

    public List<Model> getRemovidos() {
        return Collections.unmodifiableList(removidos);
    }

    public List<T> getList() {
        return ((List<Model<T>>) getWrappedData()).stream().map(m -> m.getObject()).collect(Collectors.toList());
    }

    public void reorder(int fromIndex, int toIndex) {
        List<Model<T>> list = (List<Model<T>>) getWrappedData();

        if (toIndex >= fromIndex) {
            Collections.rotate(list.subList(fromIndex, toIndex + 1), -1);
        } else {
            Collections.rotate(list.subList(toIndex, fromIndex + 1), 1);
        }
    }

}
