package de.schauderhaft.aigm;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
class Engine {

	private final Memory memory;
	private final GameMasterAI gm;

	private State state = new Start();


	Engine(GameMasterAI gm, Memory memory) {

		this.gm = gm;
		this.memory = memory;
	}

	Interaction start() {

		return new InputRequest("""
				Welcome to the Digital Game Master!
				
				What should the adventure be about? Please answer with a list of keywords.
				""");
	}

	Interaction process(String input) {
		Interaction process = state.process(input);
		state = state.next();
		return process;
	}

	interface State {
		Interaction process(String input);

		State next();
	}

	class Start implements State {
		@Override
		public Interaction process(String input) {

			String basicPlot = gm.call("createPlot", Map.of("input", input));

			memory.commitToMemory("basic_plot", basicPlot);

			String initialScene = gm.call("initialScene", Map.of( "basic_plot", basicPlot));

			return new InputRequest(initialScene + "\n\n What do you want to do?");
		}

		@Override
		public State next() {
			return new InProgress();
		}
	}

	class InProgress implements State {
		@Override
		public Interaction process(String input) {

			String newPrompt = gm.call("inProgress", Map.of("input", input));

			return new InputRequest(newPrompt + "\n\n What do you want to do?");
		}

		@Override
		public State next() {
			return this;
		}
	}
}
