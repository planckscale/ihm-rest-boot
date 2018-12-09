package com.home.ihm.demo;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Ignore("pointless, long running")
public class DemoApplicationTests {

	@Test
	public void applicationContextTest() {
		DemoApplication.main(new String[] {});
	}
}
