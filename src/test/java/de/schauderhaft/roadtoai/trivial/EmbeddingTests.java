package de.schauderhaft.roadtoai.trivial;

import org.junit.jupiter.api.Test;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.model.EmbeddingModelDescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(classes = DemoApplication.class)
class EmbeddingTests {

	@Autowired
	EmbeddingModel model;

	@Test
	void compareDistance() {
		assertThat(model).isNotNull();
	}
}
