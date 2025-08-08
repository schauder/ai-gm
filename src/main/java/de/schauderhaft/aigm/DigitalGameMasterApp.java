package de.schauderhaft.aigm;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class DigitalGameMasterApp implements CommandLineRunner {

	private final ChatClient llm;
	private final Memory memory;

	public DigitalGameMasterApp(ChatClient llm, Memory memory) {
		this.llm = llm;
		this.memory = memory;
	}

	@Override
	public void run(String... args) {
		welcome();
	}

	private void welcome() {
		Scanner in = new Scanner(System.in);
		System.out.println("Welcome to the Digital Game Master!");
		System.out.println("What should the adventure be about? Please answer with a list of keywords.");
		String input = in.nextLine();
		in.close();
		System.out.println(input);

		String basicPlot = llm.prompt("""
				You are a dungeon master! You are going to play a single shot adventure with one player.
				Please create the basic plot outline for an adventure dealing with %s
				""".formatted(input)
		).call().content();

		memory.commitToMemory("basic_plot", basicPlot);

		System.out.println(basicPlot);

	}
}
