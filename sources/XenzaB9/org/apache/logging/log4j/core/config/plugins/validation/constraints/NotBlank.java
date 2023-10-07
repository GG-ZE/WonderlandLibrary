// 
// Decompiled by Procyon v0.6.0
// 

package org.apache.logging.log4j.core.config.plugins.validation.constraints;

import org.apache.logging.log4j.core.config.plugins.validation.validators.NotBlankValidator;
import org.apache.logging.log4j.core.config.plugins.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.Documented;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Constraint(NotBlankValidator.class)
public @interface NotBlank {
    String message() default "{} is blank";
}
