package it.unidoc.cdr.core.ui.backend.rest.cdr.common;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class Annotations {
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD})
    public static @interface AsFilterHeaderGridColumns {
        boolean enabled() default false;
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD})
    public static @interface AsToggleableGridColumn {
        boolean enabled() default false;
    }
}
