package entities;

import com.api.login.LoginApplication;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LoginApplication.class)
@TestPropertySource(locations = "classpath:tests")
public class LoginApplicationTest {

    @Test
    void contextLoads(){

    }
}
