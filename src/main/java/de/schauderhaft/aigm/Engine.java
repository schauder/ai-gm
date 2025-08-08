package de.schauderhaft.aigm;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
class Engine {

    private final ChatClient llm;
    private final Memory memory;
    private State state;


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

    public Interaction process(String input) {

        PromptTemplate promptTemplate = new PromptTemplate("""
                You are a dungeon master! You are going to play a single shot adventure with one player.
                Please create the basic plot outline for an adventure dealing with {keywords}""");
        Prompt prompt = promptTemplate.create(Map.of("keywords", input));
        String basicPlot = llm.prompt(prompt).call().content();

        memory.commitToMemory("basic_plot", basicPlot);

        return new OutputRequest(basicPlot);
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
