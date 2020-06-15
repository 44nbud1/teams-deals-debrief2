package com.okta.examples;

import com.okta.examples.model.request.EditProfileRequest;
import com.okta.examples.model.request.ForgotPasswordRequest;
import com.okta.examples.model.request.LoginRequest;
import com.okta.examples.model.request.RegisterRequest;
import com.okta.examples.model.status.DealsStatus;
import com.okta.examples.service.validation.AuthenticationValidation;
import com.okta.examples.service.validation.TransactionValidation;
import com.okta.examples.service.validation.UserValidation;
import com.okta.examples.validation.AdminValidationTest;
import com.okta.examples.validation.AuthenticationValidationTest;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class OktaApplicationTests {

	@Test
	void contextLoads() {

	}

	@Autowired
	AuthenticationValidation authenticationValidation;

	@Test
	public void registerTestValidation(){
		System.out.println("Registration Validation Test");
		RegisterRequest registerRequest = new RegisterRequest();

		assertFalse(authenticationValidation.register(registerRequest, "/").getStatusCode().is2xxSuccessful());

		registerRequest.setName("kevin");
		registerRequest.setEmail("kevinard11@gmail.com");
		registerRequest.setPhoneNumber("+6281287878787");
		registerRequest.setPassword("P@ssw0rd");
		registerRequest.setConfirmPassword("P@ssw0rd");
		assertTrue(authenticationValidation.register(registerRequest, "/").getStatusCode().is2xxSuccessful());

		registerRequest.setName("k");
		assertEquals(DealsStatus.NAME_INVALID.getValue(), authenticationValidation.register(registerRequest, "/").getBody().get("status"));

		registerRequest.setName("kevin");
		registerRequest.setEmail("kevinard11gmail.com");
		assertEquals(DealsStatus.EMAIL_INVALID.getValue(), authenticationValidation.register(registerRequest, "/").getBody().get("status"));

		registerRequest.setEmail("kevinard11@gmail.com");
		registerRequest.setPhoneNumber("+6280287878787");
		assertEquals(DealsStatus.PHONE_NUMBER_INVALID.getValue(), authenticationValidation.register(registerRequest, "/").getBody().get("status"));

		registerRequest.setPhoneNumber("+6281287878787");
		registerRequest.setPassword("P@sswrd");
		assertEquals(DealsStatus.PASSWORD_INVALID.getValue(), authenticationValidation.register(registerRequest, "/").getBody().get("status"));

		registerRequest.setPassword("P@ssw0rd");
		registerRequest.setConfirmPassword("P@sw0rd");
		assertEquals(DealsStatus.PASSWORD_MISS_MATCH.getValue(), authenticationValidation.register(registerRequest, "/").getBody().get("status"));
	}

	@Test
	public void loginTestValidation(){
		System.out.println("Login Validation Test");
		LoginRequest loginRequest = new LoginRequest();

		assertFalse(authenticationValidation.login(loginRequest, "/").getStatusCode().is2xxSuccessful());

		loginRequest.setPhoneNumber("+6281287878787");
		loginRequest.setPassword("P@ssw0rd");
		assertTrue(authenticationValidation.login(loginRequest, "/").getStatusCode().is2xxSuccessful());

		loginRequest.setPhoneNumber("+6280287878787");
		assertEquals(DealsStatus.PHONE_NUMBER_INVALID.getValue(), authenticationValidation.login(loginRequest, "/").getBody().get("status"));

		loginRequest.setPhoneNumber("+6281287878787");
		loginRequest.setPassword("P@sswrd");
		assertEquals(DealsStatus.PASSWORD_INVALID.getValue(), authenticationValidation.login(loginRequest, "/").getBody().get("status"));
	}

	@Test
	public void requestOtpTestValidation(){
		System.out.println("Request Otp Validation Test");
		JSONObject test = new JSONObject();

		assertFalse(authenticationValidation.requestOtp(test, "/").getStatusCode().is2xxSuccessful());

		test.put("phoneNumber", "+6281287878787");
		assertTrue(authenticationValidation.requestOtp(test, "/").getStatusCode().is2xxSuccessful());

		test.put("phoneNumber", "+6280287878787");
		assertEquals(DealsStatus.PHONE_NUMBER_INVALID.getValue(), authenticationValidation.requestOtp(test, "/").getBody().get("status"));

	}

	@Test
	public void matchOtpTestValidation(){
		System.out.println("Match Otp Validation Test");
		JSONObject test = new JSONObject();

		assertFalse(authenticationValidation.matchOtp(test, "/").getStatusCode().is2xxSuccessful());

		test.put("otp", "0000");
		assertTrue(authenticationValidation.matchOtp(test, "/").getStatusCode().is2xxSuccessful());

		test.put("otp", "aa");
		assertEquals(DealsStatus.DATA_INVALID.getValue(), authenticationValidation.matchOtp(test, "/").getBody().get("status"));

	}

	@Test
	public void forgotPasswordValidation(){
		System.out.println("Forgot Password Validation Test");
		ForgotPasswordRequest forgotPasswordRequest = new ForgotPasswordRequest();

		assertFalse(authenticationValidation.forgotPassword(forgotPasswordRequest, "/").getStatusCode().is2xxSuccessful());

		forgotPasswordRequest.setPassword("P@ssw0rd");
		forgotPasswordRequest.setConfirmPassword("P@ssw0rd");
		assertTrue(authenticationValidation.forgotPassword(forgotPasswordRequest, "/").getStatusCode().is2xxSuccessful());

		forgotPasswordRequest.setPassword("Passw0rd");
		assertEquals(DealsStatus.PASSWORD_INVALID.getValue(), authenticationValidation.forgotPassword(forgotPasswordRequest, "/").getBody().get("status"));

		forgotPasswordRequest.setPassword("P@ssw0rd");
		forgotPasswordRequest.setConfirmPassword("Passw0rd");
		assertEquals(DealsStatus.PASSWORD_MISS_MATCH.getValue(), authenticationValidation.forgotPassword(forgotPasswordRequest, "/").getBody().get("status"));

	}

	@Autowired
	TransactionValidation transactionValidation;

	@Test
	public void createOrderTestValidation(){
		System.out.println("Create Order Validation Test");
		JSONObject test = new JSONObject();

		assertFalse(transactionValidation.createOrder(test, "").getStatusCode().is2xxSuccessful());

		test.put("idVoucher", 1);
		assertTrue(transactionValidation.createOrder(test, "").getStatusCode().is2xxSuccessful());

	}

	@Test
	public void payOrderTestValidation(){
		System.out.println("Pay Order Validation Test");
		JSONObject test = new JSONObject();

		assertFalse(transactionValidation.payOrder(test, "").getStatusCode().is2xxSuccessful());

		test.put("idTransaction", 1);
		assertTrue(transactionValidation.payOrder(test, "").getStatusCode().is2xxSuccessful());
	}

	@Test
	public void payTopupTestValidation(){
		System.out.println("Pay Top Up Validation Test");
		JSONObject test = new JSONObject();

		assertFalse(transactionValidation.payTopup(test, "").getStatusCode().is2xxSuccessful());

		test.put("virtualNumber", "9080087897994929");
		test.put("amount", "10000.00");
		assertTrue(transactionValidation.payTopup(test, "").getStatusCode().is2xxSuccessful());

		test.put("virtualNumber", "900");
		assertEquals(DealsStatus.VIRTUAL_ACCOUNT_INVALID.getValue(), transactionValidation.payTopup(test, "/").getBody().get("status"));

		test.put("virtualNumber", "9080087897994929");
		test.put("amount", "aa");
		assertEquals(DealsStatus.AMOUNT_INVALID.getValue(), transactionValidation.payTopup(test, "/").getBody().get("status"));

	}

	@Autowired
	UserValidation userValidation;

	@Test
	public void editProfileTestValidation(){
		System.out.println("Edit Profile Validation Test");
		EditProfileRequest editProfileRequest = new EditProfileRequest();

		assertFalse(userValidation.editProfile(editProfileRequest, "/").getStatusCode().is2xxSuccessful());

		editProfileRequest.setName("kevin");
		editProfileRequest.setEmail("kevinard710@gmail.com");
		assertTrue(userValidation.editProfile(editProfileRequest, "/").getStatusCode().is2xxSuccessful());

		editProfileRequest.setName("k");
		assertEquals(DealsStatus.NAME_INVALID.getValue(), userValidation.editProfile(editProfileRequest, "/").getBody().get("status"));

		editProfileRequest.setName("kevin");
		editProfileRequest.setEmail("kevinard710gmail.com");
		assertEquals(DealsStatus.EMAIL_INVALID.getValue(), userValidation.editProfile(editProfileRequest, "/").getBody().get("status"));

		editProfileRequest.setName(null);editProfileRequest.setEmail(null);
		editProfileRequest.setOldPassword("P@ssw0rd");
		editProfileRequest.setNewPassword("P@ssw0rd");
		editProfileRequest.setConfirmPassword("P@ssw0rd");
		assertTrue(userValidation.editProfile(editProfileRequest, "/").getStatusCode().is2xxSuccessful());

		editProfileRequest.setOldPassword("Pssw0rd");
		assertEquals(DealsStatus.PASSWORD_INVALID.getValue(), userValidation.editProfile(editProfileRequest, "/").getBody().get("status"));

		editProfileRequest.setOldPassword("P@ssw0rd");
		editProfileRequest.setNewPassword("Pssw0rd");
		assertEquals(DealsStatus.NEW_PASSWORD_INVALID.getValue(), userValidation.editProfile(editProfileRequest, "/").getBody().get("status"));

		editProfileRequest.setOldPassword("P@ssw0rd");
		editProfileRequest.setNewPassword("P@ssw0rd");
		editProfileRequest.setConfirmPassword("Pssw0rd");
		assertEquals(DealsStatus.NEW_PASSWORD_MISS_MATCH.getValue(), userValidation.editProfile(editProfileRequest, "/").getBody().get("status"));

	}
}
