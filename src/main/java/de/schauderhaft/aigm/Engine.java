package de.schauderhaft.aigm;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

@Component
class Engine {

	private final ChatClient llm;
	private final Memory memory;
	private State state;


	Engine(ChatClient llm, Memory memory) {
		this.llm = llm;
		this.memory = memory;
	}


	Interaction start(){
		return new InputRequest("""
				Welcome to the Digital Game Master!
				
				What should the adventure be about? Please answer with a list of keywords.
				""");
	}

	public Interaction process(String input) {

		String basicPlot = llm.prompt("""
				You are a dungeon master! You are going to play a single shot adventure with one player.
				Please create the basic plot outline for an adventure dealing with %s
				""".formatted(input)
		).call().content();

		memory.commitToMemory("basic_plot", basicPlot);

		return new End();
	}

	enum State {


		START {
			@Override
			Interaction process(String input) {


				return null;
			}
		};
		abstract Interaction process(String input);
		}
}
