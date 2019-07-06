package com.study.ichat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * MapperScan：扫描mybatis mapper包路径
 * @author cxd27419
 */
@MapperScan(basePackages = "com.study.ichat.mapper")
@ComponentScan(basePackages = {"com.study.ichat","org.n3r.idworker"})
@SpringBootApplication
public class IchatApplication {

    public static void main(String[] args) {
        SpringApplication.run(IchatApplication.class, args);
    }

}
