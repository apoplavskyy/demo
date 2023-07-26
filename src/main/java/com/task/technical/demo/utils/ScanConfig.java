package com.task.technical.demo.utils;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:config.properties")
@Getter
public class ScanConfig {
    @Value("${ScanConfig.depth:4}")
    private Long depth;
}
