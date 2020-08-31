package de.evoila.elasticsearch;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;


@SpringBootApplication
public class ElasticsearchApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(ElasticsearchApplication.class)
				.initializers(new SpringApplicationContextInitializer())
				.listeners(new TestController())
				.application()
				.run(args);
	}
}
