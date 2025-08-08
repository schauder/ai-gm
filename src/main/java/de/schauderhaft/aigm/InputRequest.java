package de.schauderhaft.aigm;

class InputRequest implements Interaction {
	private final String output;

	InputRequest(String output) {
		this.output = output;
	}

	@Override
	public String output() {
		return output;
	}
}
