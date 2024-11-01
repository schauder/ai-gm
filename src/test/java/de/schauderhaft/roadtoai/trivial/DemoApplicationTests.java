package de.schauderhaft.roadtoai.trivial;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	ChatModel chatModel;

	@Test
	void contextLoads() {

		ChatClient chatClient = ChatClient.create(chatModel);

		String result = chatClient.prompt().user("Hi, who am I talking to?").call().content();

		assertThat(result).isNotEmpty();
	}

}
