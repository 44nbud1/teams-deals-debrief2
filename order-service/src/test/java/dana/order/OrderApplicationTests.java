package dana.order;

import dana.order.usecase.validate.ValidateTOPUP;
import dana.order.usecase.validate.ValidateTransactionHistory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderApplicationTests {

	// TRANSACTION HISTORY
	@Autowired
	ValidateTransactionHistory validateTransactionHistory;

	@Test
	void validateCheckDate() {
		assertEquals(true, validateTransactionHistory.checkDate("2020-06-12"));
		assertEquals(false, validateTransactionHistory.checkDate("2020-06-2jy"));
		assertEquals(false, validateTransactionHistory.checkDate("2020-20-45"));
		assertEquals(false, validateTransactionHistory.checkDate("2020/06/12"));
	}

	@Test
	void validateCheckPage(){
		assertEquals(true, validateTransactionHistory.checkPage("12"));
		assertEquals(false, validateTransactionHistory.checkPage("-20"));
		assertEquals(false, validateTransactionHistory.checkPage("abc"));
	}

	// TOPUP

	@Autowired
	ValidateTOPUP validateTOPUP;

	@Test
	void validateAmountTOPUP(){
		assertEquals(true, validateTOPUP.amountCheck("200000"));
		assertEquals(true, validateTOPUP.amountCheck("10000.00"));
		assertEquals(false, validateTOPUP.amountCheck("-200000"));
		assertEquals(false, validateTOPUP.amountCheck("200f"));
	}

	@Test
	void validateVirtualNumber(){
		assertEquals(true, validateTOPUP.virtualNumberCheck("9030085794422971"));
		assertEquals(true, validateTOPUP.virtualNumberCheck("903008123456789"));
		assertEquals(true, validateTOPUP.virtualNumberCheck("90300812345678"));
		assertEquals(false, validateTOPUP.virtualNumberCheck("90306285794422971"));
		assertEquals(false, validateTOPUP.virtualNumberCheck("903008123"));
	}

}
