package edu.minibank.util;
import edu.minibank.util.annotation.MaxLength;
import edu.minibank.util.annotation.Positive;
import java.lang.reflect.Field;
import java.util.ArrayList;
public final class AnnotationValidator {
    private AnnotationValidator() {}
    public static String[] validate(Object obj) {
        ArrayList<String> errors = new ArrayList<>();
        for (Field field : obj.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                Object value = field.get(obj);
                if (field.isAnnotationPresent(Positive.class) && value instanceof Number n && n.longValue() <= 0)
                    errors.add(field.getName() + ": " + field.getAnnotation(Positive.class).message());
                if (field.isAnnotationPresent(MaxLength.class) && value instanceof String s && s.length() > field.getAnnotation(MaxLength.class).value())
                    errors.add(field.getName() + ": maximum length is " + field.getAnnotation(MaxLength.class).value());
            } catch (ReflectiveOperationException | RuntimeException e) {
                errors.add(field.getName() + ": could not validate");
            }
        }
        return errors.toArray(String[]::new);
    }
}
