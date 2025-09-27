package cn.y.yaicodezgen;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy(exposeProxy = true)
@SpringBootApplication
@MapperScan("cn.y.yaicodezgen.mapper")
public class YAiCodeZgenApplication {

    public static void main(String[] args) {
        SpringApplication.run(YAiCodeZgenApplication.class, args);
    }

}
