package edu.minibank.service;
import edu.minibank.util.annotation.Id;
import java.lang.reflect.Field;
import java.util.*;
public class Repository<T> {
    private final Map<String, T> items = new HashMap<>();
    public T save(T entity) {
        items.put(readId(entity), entity);
        return entity;
    }
    @SafeVarargs public final void saveAll(T... entities) { for (T entity : entities) save(entity); }
    public T findById(String id) { return items.get(id); }
    public ArrayList<T> findAll() { return new ArrayList<>(items.values()); }
    public boolean delete(String id) { return items.remove(id) != null; }
    public int size() { return items.size(); }
    private String readId(T entity) {
        for (Field field : entity.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                try { field.setAccessible(true); return String.valueOf(field.get(entity)); }
                catch (ReflectiveOperationException e) { throw new IllegalStateException("Cannot read @Id field", e); }
            }
        }
        throw new IllegalArgumentException("Entity has no @Id field: " + entity.getClass().getName());
    }
    public static <N extends Number> double average(N[] values) {
        if (values.length == 0) return 0;
        double total = 0;
        for (N value : values) total += value.doubleValue();
        return total / values.length;
    }
}
