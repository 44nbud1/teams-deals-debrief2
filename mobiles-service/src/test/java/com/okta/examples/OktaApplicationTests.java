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
}
