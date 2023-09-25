package com.example.car;

import com.example.car.Controller.CarController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class CarApplicationTests {

	@Autowired
	private CarController carController;

	@Test
	void contextLoads() {
		assertThat(carController).isNotNull();
	}


	//올바른 자격 증명으로 인증 테스트
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testAuthentication() throws Exception{
		// Testing authentication with correct credentials
		this.mockMvc.perform(post("/login").content("{\"username\":\"admin\", \"password\":\"$2y$04$qkXadKe.SOuiZQgheqSSZOqVoAzOxCKc3jhPpHXgPV1x6rEx827hW\"}").
						header(HttpHeaders.CONTENT_TYPE, "application/json")).
				andDo(print()).andExpect(status().isOk());

	}
}
