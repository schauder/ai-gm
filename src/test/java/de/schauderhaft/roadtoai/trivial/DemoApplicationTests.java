package de.schauderhaft.roadtoai.trivial;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class DemoApplicationTests {

	private static final Logger log = LoggerFactory.getLogger(DemoApplicationTests.class);

	private final ChatClient chatClient;

	DemoApplicationTests(@Autowired ChatModel chatModel) {
		chatClient = ChatClient.create(chatModel);
	}

	@Test
	void helloOpenAi() {

		String result = chatClient.prompt().user("Hi, who am I talking to?").call().content();

		assertThat(result).isNotEmpty();

		log.info(result);
	}

}
