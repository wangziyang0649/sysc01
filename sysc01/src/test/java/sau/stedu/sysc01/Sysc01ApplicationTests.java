package sau.stedu.sysc01;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class Sysc01ApplicationTests {


    @Test
    void contextLoads() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        for (int i=0 ; i<10 ; i++){
            System.out.println(encoder.encode("123"));
        }
    }

}
