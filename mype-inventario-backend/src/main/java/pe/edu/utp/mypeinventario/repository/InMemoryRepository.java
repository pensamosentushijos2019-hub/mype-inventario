package pe.edu.utp.mypeinventario.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryRepository<T> implements Repository<T> {
    private final Map<String, T> store = new ConcurrentHashMap<>();

    public T add(String id, T entity) {
        store.put(id, entity);
        return entity;
    }

    public T getById(String id) {
        return store.get(id);
    }

    public List<T> getAll() {
        return new ArrayList<>(store.values());
    }

    public boolean delete(String id) {
        return store.remove(id) != null;
    }
}
