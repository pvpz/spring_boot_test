package com.example.demo.service;

import java.util.List;

public interface CommonService<T> {

     List<T> getAll();

     void add(T object);

    void delete(T object);

    T get(long id);

    void save(T object);

    void deleteAll();
}
