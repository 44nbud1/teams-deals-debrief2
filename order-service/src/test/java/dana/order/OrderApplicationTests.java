package dana.order;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class OrderApplicationTests {

    @Autowired
    OrderApplication orderApplication;

    @Test
    public void contextLoads() throws Exception {
        assertThat(orderApplication).isNotNull();
    }

}
