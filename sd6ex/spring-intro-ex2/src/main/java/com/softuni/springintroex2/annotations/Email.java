package com.softuni.springintroex2.annotations;

import jakarta.validation.Payload;

public @interface Email {
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
