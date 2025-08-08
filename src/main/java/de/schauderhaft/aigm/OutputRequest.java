package de.schauderhaft.aigm;

class OutputRequest implements Interaction {
    private final String output;

    OutputRequest(String output) {
        this.output = output;
    }

    @Override
    public String output() {
        return output;
    }
}
