package estore.estore.dao;

import java.util.List;

public interface BaseDAO<T> {
    public List<T> getAll();

    public T getById(int id);


}
