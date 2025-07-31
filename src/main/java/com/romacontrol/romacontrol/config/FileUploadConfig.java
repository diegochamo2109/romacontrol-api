package com.romacontrol.romacontrol.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import jakarta.servlet.MultipartConfigElement;

@Configuration
public class FileUploadConfig {

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();

        // Límite de tamaño por archivo
        factory.setMaxFileSize(DataSize.ofMegabytes(5));

        // Límite total por solicitud
        factory.setMaxRequestSize(DataSize.ofMegabytes(10));

        return factory.createMultipartConfig();
    }
}
