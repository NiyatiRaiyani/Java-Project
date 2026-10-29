
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

public class Repository<T> {

    private HashMap<String, T> data = new HashMap<>();

    public T save(T entity) {
        try {
            Class<?> type = entity.getClass();

            while (type != null) {
                for (Field field : type.getDeclaredFields()) {
                    if (field.isAnnotationPresent(Id.class)) {
                        field.setAccessible(true);
                        String id = field.get(entity).toString();
                        data.put(id, entity);
                        return entity;
                    }
                }
                type = type.getSuperclass();
            }

            throw new RuntimeException("No @Id field found!");

        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public T findById(String id) {
        return data.get(id);
    }

    public ArrayList<T> findAll() {
        return new ArrayList<>(data.values());
    }

    public boolean delete(String id) {
        return data.remove(id) != null;
    }

    @SafeVarargs
    public final void saveAll(T... items) {
        for (T item : items) {
            save(item);
        }
    }

    public static <N extends Number> double average(N[] values) {
        if (values.length == 0) {
            return 0;
        }

        double total = 0;

        for (N value : values) {
            total += value.doubleValue();
        }

        return total / values.length;
    }

    public HashMap<String, T> getData() {
        return data;
    }

    public void setData(HashMap<String, T> data) {
        this.data = data;
    }
}
