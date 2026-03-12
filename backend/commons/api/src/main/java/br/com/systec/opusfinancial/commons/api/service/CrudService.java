package br.com.systec.opusfinancial.commons.api.service;

public interface CrudService<T> {

    T create(T obj);

    T update(T obj);

    T delete(T obj);
}
