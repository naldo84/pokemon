package com.pokemonProject.pokemon;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
class PokemonApplicationTests {

	@Test
	@DisplayName("Verificar se o contexto do spring inicializa corretamente. ")
	void contextLoads() {
	}

	@Test
	@DisplayName("Verificar se o método main inicia a aplicação sem erros")
	void deveExecutarOMetodoMainSemErros(){
		assertDoesNotThrow(() -> PokemonApplication.main(new String[] {}));

	}
}
