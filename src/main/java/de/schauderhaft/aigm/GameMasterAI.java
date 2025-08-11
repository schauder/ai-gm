package de.schauderhaft.aigm;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

@Component
class GameMasterAI {
	private final ChatClient chatClient;
	private final Properties promptProperties;

	GameMasterAI(ChatClient chatClient, @Value("classpath:prompts.properties") Resource promptsResource) throws IOException {
		this.chatClient = chatClient;
		this.promptProperties = new Properties();
		this.promptProperties.load(promptsResource.getInputStream());
	}

	String call(String promptKey, Map<String, Object> templateVariables) {

		String promptTemplateString = promptProperties.getProperty("prompt." + promptKey);

		Assert.isTrue(!(promptTemplateString == null || promptTemplateString.isBlank()), "Prompt template not found or empty for key: " + promptKey);
		

		PromptTemplate promptTemplate = new PromptTemplate(promptTemplateString);
		Prompt prompt = promptTemplate.create(templateVariables);

		return chatClient.prompt(prompt).call().content();
	}
}
