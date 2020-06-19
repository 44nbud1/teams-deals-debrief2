package dana.order;

import dana.order.entity.DealsStatus;
import dana.order.entity.TransactionHistoryModel;
import dana.order.usecase.exception.OrderFailedException;
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

    OrderFailedException exception = new OrderFailedException(DealsStatus.OK);

    @Test
    public void exceptionTest(){
        assertThat(exception.getStatus().is2xxSuccessful()).isTrue();
    }

}
