package de.schauderhaft.aigm;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
class Memory {

	private final Map<String, String> memory = new HashMap<>();

	/**
	 * commit something to memory, overwriting, what ever was there.
	 * @param topic
	 * @param fact
	 */
	void commitToMemory(String topic, String fact) {
		memory.put(topic, fact);
	}

	/**
	 * adding stuff to memory, appending it to anything that is already there.
	 *
	 * @param topic
	 * @param fact
	 */
	void addToMemory(String topic, String fact) {
		String currentState = memory.get(topic);
		if (currentState != null) {
			fact = currentState + "\n" + fact;
		}
		memory.put(topic, fact);
	}
}
