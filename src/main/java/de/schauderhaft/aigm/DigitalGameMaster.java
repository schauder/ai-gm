package de.schauderhaft.aigm;

import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import javax.sql.DataSource;

@Configuration
public class DigitalGameMaster {

	public static void main(String[] args) {
		SpringApplication.run(DigitalGameMasterApp.class, args);
	}

	@Bean
	DataSource dataSource() {
		var image = DockerImageName.parse("pgvector/pgvector:pg16")
				.asCompatibleSubstituteFor("postgres");
		var pgVector = new PostgreSQLContainer<>(image);
		pgVector.start();

		PGSimpleDataSource ds = new PGSimpleDataSource();
		ds.setServerName(pgVector.getHost());
		ds.setPortNumber(pgVector.getFirstMappedPort());
		ds.setDatabaseName(pgVector.getDatabaseName());
		ds.setUser(pgVector.getUsername());
		ds.setPassword(pgVector.getPassword());

		return ds;
	}

	@Bean
	ChatClient llm(ChatModel chatModel) {
		return ChatClient.create(chatModel);
	}
}
