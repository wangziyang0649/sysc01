package sau.stedu.sysc01;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "sau.stedu.sysc01.mapper")
public class Sysc01Application {

    public static void main(String[] args) {
        SpringApplication.run(Sysc01Application.class, args);
    }

}
