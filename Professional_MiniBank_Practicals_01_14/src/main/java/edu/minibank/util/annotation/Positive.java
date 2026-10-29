package edu.minibank.util.annotation;
import java.lang.annotation.*;
@Retention(RetentionPolicy.RUNTIME) @Target(ElementType.FIELD)
public @interface Positive { String message() default "must be > 0"; }
