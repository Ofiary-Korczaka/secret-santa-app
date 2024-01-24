package com.example.secretsantaapp.util;

import static org.springframework.test.util.AssertionErrors.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CommonUtilTests {
  @Test
  void testEmailValidation() {
    String message1 = "Incorrect email is matched";
    String message2 = "Correct email is not matched";

    assertEquals(message2, true, CommonUtil.validateEmail("jan.kowalski@gmail.com"));
    assertEquals(message2, true, CommonUtil.validateEmail("janko.w323alski@x.com"));
    assertEquals(message2, true, CommonUtil.validateEmail("jankow323alski@gmail.om"));
    assertEquals(message1, false, CommonUtil.validateEmail("jan.kowalski@gMail.com"));
    assertEquals(message1, false, CommonUtil.validateEmail("jan.kowalski@gmail.c"));
    assertEquals(message1, false, CommonUtil.validateEmail("jan.kowalskgmail.m"));
  }
}
