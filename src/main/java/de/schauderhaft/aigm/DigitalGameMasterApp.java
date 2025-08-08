package de.schauderhaft.aigm;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class DigitalGameMasterApp implements CommandLineRunner {

	private final Engine engine;

	public DigitalGameMasterApp(Engine engine) {
		this.engine = engine;
	}

	@Override
	public void run(String... args) {
		welcome();
	}

	private void welcome() {

		Interaction interaction = engine.start();

		System.out.println(interaction.output());

		while (interaction instanceof InputRequest) {

			Scanner in = new Scanner(System.in);
			String input = in.nextLine();
			in.close();

			interaction = engine.process(input);

			System.out.println(interaction.output());

		}

		new End();

	}
}
