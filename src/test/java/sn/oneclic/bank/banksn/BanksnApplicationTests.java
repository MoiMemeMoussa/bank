package sn.oneclic.bank.banksn;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BanksnApplicationTests {

	@Test
	@DisplayName("IT : Application starts")
	public void contextLoads() {
		Assertions.assertTrue(true);
	}

}
