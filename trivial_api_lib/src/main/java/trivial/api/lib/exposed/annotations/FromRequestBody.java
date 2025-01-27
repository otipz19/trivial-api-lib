package trivial.api.lib.exposed.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collection;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface FromRequestBody {
    boolean isGenericCollection() default false;
    Class<? extends Collection> collectionType() default Collection.class;
    Class<?> elementType() default Object.class;
}
