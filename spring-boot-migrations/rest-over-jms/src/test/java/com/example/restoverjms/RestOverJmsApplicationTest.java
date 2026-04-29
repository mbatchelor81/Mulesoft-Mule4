package com.example.restoverjms;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RestOverJmsApplicationTest {

    @Test
    void applicationClassExists() {
        assertThat(RestOverJmsApplication.class).isNotNull();
    }
}
