
import java.lang.reflect.Field;
import java.util.ArrayList;

public class AnnotationValidator {

    @SuppressWarnings("CollectionsToArray")
    public static String[] validate(Object obj) {

        ArrayList<String> errors = new ArrayList<>();

        Field[] fields = obj.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);

            try {
                Object value = field.get(obj);

                if (field.isAnnotationPresent(Positive.class)) {
                    if (value instanceof Number
                            && ((Number) value).doubleValue() <= 0) {

                        Positive annotation =
                                field.getAnnotation(Positive.class);

                        errors.add(annotation.message());
                    }
                }

                if (field.isAnnotationPresent(MaxLength.class)) {
                    MaxLength annotation =
                            field.getAnnotation(MaxLength.class);

                    if (value instanceof String
                            && ((String) value).length() > annotation.value()) {
                        errors.add("Maximum length is "
                                + annotation.value());
                    }
                }

            } catch (IllegalAccessException e) {
                errors.add("Could not validate " + field.getName());
            }
        }

        return errors.toArray(new String[0]);
    }
}
