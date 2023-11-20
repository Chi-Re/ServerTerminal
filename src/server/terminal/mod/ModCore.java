package server.terminal.mod;

import java.lang.annotation.*;

/**测试接口*/
@Documented
@Target(ElementType.METHOD)
@Inherited
@Retention(RetentionPolicy.RUNTIME )
public @interface ModCore {
    String operationName();
}
