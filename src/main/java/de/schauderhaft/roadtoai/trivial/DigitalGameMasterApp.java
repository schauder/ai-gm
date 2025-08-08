package de.schauderhaft.roadtoai.trivial;

import org.springframework.boot.CommandLineRunner;

import java.util.Scanner;

public class DigitalGameMasterApp implements CommandLineRunner {
	@Override
	public void run(String... args) throws Exception {
		welcome();
	}

	private void welcome() {
		Scanner in = new Scanner(System.in);
		System.out.println("Welcome to the Digital Game Master!");
		System.out.println("What should the adventure be about? Please answer with a list of keywords.");
		String input = in.nextLine();
		System.out.println(input);

	}
}
