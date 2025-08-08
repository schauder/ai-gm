package de.schauderhaft.aigm;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
class DigitalGameMasterApp implements CommandLineRunner {

	private final Engine engine;

	DigitalGameMasterApp(Engine engine) {
		this.engine = engine;
	}

	@Override
	public void run(String... args) {

		try (Scanner in = new Scanner(System.in)) {

			Interaction interaction = engine.start();

			System.out.println(interaction.output());

			while (interaction instanceof InputRequest) {

				String input = in.nextLine();

				interaction = engine.process(input);

				System.out.println(interaction.output());

			}
		}
	}

}
