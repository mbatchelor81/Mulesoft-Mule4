package com.example.mulescopedemo;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MuleScopeDemoApplicationTest {

    @Test
    void applicationClassExists() {
        assertThat(MuleScopeDemoApplication.class).isNotNull();
    }
}
