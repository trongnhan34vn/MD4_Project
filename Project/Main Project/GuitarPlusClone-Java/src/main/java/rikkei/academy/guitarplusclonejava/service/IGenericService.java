package rikkei.academy.guitarplusclonejava.service;

import java.util.List;

public interface IGenericService<E> {
    List<E> findAll();
    void save(E e);
    void remove(int id);
    E findById(int id);
}
