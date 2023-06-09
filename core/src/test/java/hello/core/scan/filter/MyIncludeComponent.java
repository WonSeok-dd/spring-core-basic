package hello.core.scan.filter;

import java.lang.annotation.*;

//@Component 의 Annotation
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyIncludeComponent {
}
