package com.example.ftpapp;

import com.example.ftpapp.service.FtpReaderService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FtpAppApplicationTest {

    @Test
    void ftpReaderServiceCanBeInstantiated() {
        FtpReaderService service = new FtpReaderService();
        assertThat(service).isNotNull();
    }
}
