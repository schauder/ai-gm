package de.schauderhaft.aigm;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
class GameMasterAI {
	private final ChatClient chatClient;
	private final Map<String, String> promptMap = Map.of(
			"inProgress","""
					Here is what the players want to do:
					===
					{input}
					===
					
					Describe what happens to the players.
					""",
			"createPlot","""
					You are a dungeon master! You are going to play a single shot adventure with one player.
					Please create the basic plot outline for an adventure dealing with %s
					""",
			"initialScene", """
					You are a dungeon master! You are going to play a single shot adventure with one player.
					
					This is the basic plot outline you have: 
					===
					{basic_plot}
					===
					
					Describe the initial scene to the players.
					"""
			);

	GameMasterAI(ChatClient chatClient) {
		this.chatClient = chatClient;
	}

	String call(String promptKey, Map<String, Object> templateVariables) {

		String promptTemplateString = promptMap.get(promptKey);

		PromptTemplate promptTemplate = new PromptTemplate(promptTemplateString);
		Prompt prompt = promptTemplate.create(templateVariables);

		return chatClient.prompt(prompt).call().content();
	}
}
