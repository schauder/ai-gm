package de.schauderhaft.aigm;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

@Component
class Engine {

	private final ChatClient llm;
	private final Memory memory;
	private State state = State.START;


	Engine(ChatClient llm, Memory memory) {
		this.llm = llm;
		this.memory = memory;
	}


	Interaction start() {
		return new InputRequest("""
				Welcome to the Digital Game Master!
				
				What should the adventure be about? Please answer with a list of keywords.
				""");
	}

	Interaction process(String input) {
		return state.process(llm, memory, input);
	}

	enum State {


		START {
			@Override
			Interaction process(ChatClient llm, Memory memory, String input) {

				String basicPlot = callLLM(llm, """
						You are a dungeon master! You are going to play a single shot adventure with one player.
						Please create the basic plot outline for an adventure dealing with %s
						""".formatted(input));

				memory.commitToMemory("basic_plot", basicPlot);

				String initialScene = callLLM(llm, """
						You are a dungeon master! You are going to play a single shot adventure with one player.
						
						This is the basic plot outline you have: 
						===
						%s
						===
						
						Describe the initial scene to the players.
						""".formatted(basicPlot));

				return new InputRequest(initialScene + "\n\n What do you want to do?");
			}
		}, IN_PROGRESS {
			@Override
			Interaction process(ChatClient llm, Memory memory, String input) {

				String newPrompt = State.callLLM(llm, """
						Here is what the players want to do:
						===
						%s
						===
						
						Describe what happens to the players.
						""".formatted(input));

				return new InputRequest(newPrompt + "\n\n What do you want to do?");
			}
		};

		abstract Interaction process(ChatClient llm, Memory memory, String input);

		private static String callLLM(ChatClient llm, String prompt) {
			return llm.prompt(prompt).call().content();
		}
	}
}
