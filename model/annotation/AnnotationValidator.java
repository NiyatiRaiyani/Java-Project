package model.annotation;

import java.lang.reflect.Field;

public class AnnotationValidator {

    /**
     * @param obj
     * @return
     */
    public static String[] validate(Object obj) {

        java.util.ArrayList<String> errors = new java.util.ArrayList<>();

        if (obj == null) {
            return new String[]{"Object cannot be null"};
        }

        Field[] fields = obj.getClass().getDeclaredFields();

        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(obj);

                if (field.isAnnotationPresent(Positive.class)) {
                    Positive annotation =
                            field.getAnnotation(Positive.class);

                    if (value instanceof Number number
                            && number.longValue() <= 0) {
                        errors.add(annotation.message());
                    }
                }

                if (field.isAnnotationPresent(MaxLength.class)
                        && value instanceof String text) {

                    MaxLength annotation =
                            field.getAnnotation(MaxLength.class);

                    if (text.length() > annotation.value()) {
                        errors.add("maximum length is "
                                + annotation.value());
                    }
                }

            } catch (IllegalAccessException e) {
                errors.add("Unable to access field: " + field.getName());
            }
        }

        return errors.toArray(String[]::new);
    }
}
