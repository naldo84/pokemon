package com.pokemonProject.pokemon.controller;

import com.pokemonProject.pokemon.dto.PokemonDto;
import com.pokemonProject.pokemon.model.PokemonModel;
import com.pokemonProject.pokemon.service.PokemonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PokemonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private PokemonService pokemonService;

    @InjectMocks
    private PokemonController pokemonController;

    private String responsePokemon;
    private String responsePokemonArray;
    private PokemonModel pokemonModel;
    private PokemonDto pokemonDto;
    private PokemonDto pokemonDtoAlterado;
    private String messagemPokemonNaoLocalizado;
    private String responsePokemonDtoAlterado;

    @BeforeEach
    void setUp() {
        messagemPokemonNaoLocalizado = "Pokémon não localizado!";

        mockMvc = MockMvcBuilders.standaloneSetup(pokemonController).build();
        pokemonModel = new PokemonModel();
        pokemonModel.setIdPokemon(UUID.fromString("d9b2d63d-a233-4260-8b8f-d4e16b7c1e69"));
        pokemonModel.setNome("Pikachu");
        pokemonModel.setNumPokedex(25);
        pokemonModel.setTipo("Elétrico");
        pokemonModel.setUrlPokemon("https://imagem-do-pikachu.com");
        pokemonModel.setDescricao("Um pokémon elétrico muito popular.");

        // Montando a resposta esperada do JSON
        responsePokemon = """
            {
            "idPokemon": "d9b2d63d-a233-4260-8b8f-d4e16b7c1e69",
            "nome": "Pikachu",
            "numPokedex": 25,
            "tipo": "Elétrico",
            "urlPokemon": "https://imagem-do-pikachu.com",
            "descricao": "Um pokémon elétrico muito popular."
        }
    """;

        responsePokemonArray = """
    [{
        "idPokemon": "d9b2d63d-a233-4260-8b8f-d4e16b7c1e69",
        "nome": "Pikachu",
        "numPokedex": 25,
        "tipo": "Elétrico",
        "urlPokemon": "https://imagem-do-pikachu.com",
        "descricao": "Um pokémon elétrico muito popular."
    }]
    """;

        pokemonDto = new PokemonDto(
                "Pikachu",
                25,
                "Elétrico",
                "https://imagem-do-pikachu.com",
                "Um pokémon elétrico muito popular."
        );

        pokemonDtoAlterado = new PokemonDto(
                "Pikachu Alterado",
                25,
                "Elétrico Alterado",
                "URL do Pikachu alterada",
                "Descrição do Pikachu alterada"
        );

        responsePokemonDtoAlterado = """
        {
            "nome": "Pikachu Alterado",
            "numPokedex": 25,
            "tipo": "Elétrico Alterado",
            "urlPokemon": "URL do Pikachu alterada",
            "descricao": "Descrição do Pikachu alterada"
        }
    """;


    }

    @Test
    @DisplayName("Deve cadastrar um Pokémon com sucesso e retornar status 201")
    void cadatrarPokemonComSucesso() throws Exception {
        when(pokemonService.cadastrarPokemon(any(PokemonDto.class))).thenReturn(pokemonModel);

        mockMvc.perform(post("/pokemons")
                .contentType("application/json")
                .content("{\"nome\": \"" + pokemonModel.getNome() + "\", \"tipo\": \"" + pokemonModel.getTipo() + "\", \"urlPokemon\": \"" + pokemonModel.getUrlPokemon() + "\", \"descricao\": \"" + pokemonModel.getDescricao() + "\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/pokemons/" + pokemonModel.getNumPokedex()))
                .andExpect(content().json(responsePokemon));

        verify(pokemonService, times(1)).cadastrarPokemon(any(PokemonDto.class));
    }

    @Test
    @DisplayName("Deve apresentar erro ao tentar cadastrar um pokémon com o id da pokedex já cadastrado")
    void cadastrarPokemonComErro() throws Exception{
        when(pokemonService.cadastrarPokemon(any(PokemonDto.class)))
                .thenThrow(new RuntimeException("Pikachu de nro 25 já está cadastrado!"));

        mockMvc.perform(post("/pokemons")
                .contentType("application/json")
                .content("{\"nome\": \"" + pokemonModel.getNome() + "\", \"tipo\": \"" + pokemonModel.getTipo() + "\", \"urlPokemon\": \"" + pokemonModel.getUrlPokemon() + "\", \"descricao\": \"" + pokemonModel.getDescricao() + "\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Pikachu de nro 25 já está cadastrado!"));

        verify(pokemonService,times(1)).cadastrarPokemon(any(PokemonDto.class));
    }

    @Test
    @DisplayName("Deve consultar todos os pokémons com sucesso")
    void consultarPokemonsComSucesso() throws Exception {
        when(pokemonService.consultarPokemons()).thenReturn(Collections.singletonList(pokemonModel));

        mockMvc.perform(get("/pokemons")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().json(responsePokemonArray));

        verify(pokemonService, times(1)).consultarPokemons();
    }

    @Test
    @DisplayName("Deve consultar todos os pokémons com sucesso mesmo com a lista vazia")
    void consultarPokemonsComListaVazia() throws Exception {
        when(pokemonService.consultarPokemons()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/pokemons")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(pokemonService, times(1)).consultarPokemons();
    }



    @Test
    @DisplayName("Deve consultar o pokemon pelo ID")
    void consultarPokemonByIdComSuceso() throws Exception {
        when(pokemonService.consultarPokemonById(pokemonModel.getIdPokemon()))
                .thenReturn(pokemonModel);

        mockMvc.perform(get("/pokemons/{id}", pokemonModel.getIdPokemon())
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().json(responsePokemon));
    }

    @Test
    @DisplayName("Deve apresentar erro consultar o pokemon com ID incorreto")
    void consultarPokemonByIdComErro() throws Exception {
        when(pokemonService.consultarPokemonById(pokemonModel.getIdPokemon()))
                .thenThrow(new RuntimeException(messagemPokemonNaoLocalizado));

        mockMvc.perform(get("/pokemons/{id}", pokemonModel.getIdPokemon())
                .contentType("application/json"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(messagemPokemonNaoLocalizado));
    }

    @Test
    @DisplayName("Deve consultar o pokemon pelo numPokedex")
    void consultarByNumPokedexComSucesso() throws Exception {
        when(pokemonService.consultarPokemonByNumPokedex(pokemonModel.getNumPokedex()))
                .thenReturn(pokemonModel);

        mockMvc.perform(get("/pokemons/numPokedex/{numPokedex}", pokemonModel.getNumPokedex())
                .content("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().json(responsePokemon));

    }

    @Test
    @DisplayName("Deve apresentar erro consultar o pokemon com NumPokedex incorreto")
    void consultarPokemonByNumPokedexComErroComSucesso() throws Exception {
        when(pokemonService.consultarPokemonByNumPokedex(pokemonModel.getNumPokedex()))
                .thenThrow(new RuntimeException(messagemPokemonNaoLocalizado));

        mockMvc.perform(get("/pokemons/numPokedex/{numPokedex}", pokemonModel.getNumPokedex())
                        .content("application/json"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(messagemPokemonNaoLocalizado));
    }

    @Test
    void alterarPokemonComSucesso() throws Exception {
       when(pokemonService.alterarPokemonByNumPokedex(pokemonDtoAlterado.numPokedex(), pokemonDtoAlterado))
               .thenReturn(pokemonModel);

        mockMvc.perform(put("/pokemons/numPokedex/{numPokedex}", pokemonModel.getNumPokedex())
                .contentType("application/json")
                .content(responsePokemonDtoAlterado))
                .andExpect(status().isOk())
                .andExpect(content().json(responsePokemon));

        verify(pokemonService, times(1)).alterarPokemonByNumPokedex(pokemonModel.getNumPokedex(), pokemonDtoAlterado);
    }

    @Test
    void alterarPokemonByNumPokedexComErro() throws Exception {
        when(pokemonService.alterarPokemonByNumPokedex(pokemonModel.getNumPokedex(), pokemonDtoAlterado))
                .thenThrow(new RuntimeException(messagemPokemonNaoLocalizado));

        mockMvc.perform(put("/pokemons/numPokedex/{numPokedex}", pokemonModel.getNumPokedex())
                        .contentType("application/json")
                        .content(responsePokemonDtoAlterado))
                .andExpect(status().isNotFound())
                .andExpect(content().string(messagemPokemonNaoLocalizado));

        verify(pokemonService, times(1)).alterarPokemonByNumPokedex(pokemonModel.getNumPokedex(), pokemonDtoAlterado);
    }

    @Test
    @DisplayName("Deve excluir o pokémon pelo ID com sucesso")
    void excluirPokemonByIdComSucesso() throws Exception {
        when(pokemonService.consultarPokemonById(pokemonModel.getIdPokemon())).thenReturn(pokemonModel);
        // Opcional: Explicitamente dizer que o metodo não faz nada quando chamado
        doNothing().when(pokemonService).excluirPokemonById(pokemonModel.getIdPokemon());

        mockMvc.perform(delete("/pokemons/{idPokemon}", pokemonModel.getIdPokemon())
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string("Pokemon " + pokemonModel.getIdPokemon() + " deletado!!"));

        verify(pokemonService, times(1)).excluirPokemonById(pokemonModel.getIdPokemon());
    }

    @Test
    @DisplayName("Deve excluir o pokémon pelo ID com erro")
    void excluirPokemonByIdComErro() throws Exception {
        when(pokemonService.consultarPokemonById(pokemonModel.getIdPokemon()))
                .thenThrow(new RuntimeException(messagemPokemonNaoLocalizado));

        mockMvc.perform(delete("/pokemons/{id}", pokemonModel.getIdPokemon())
                        .contentType("application/json"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(messagemPokemonNaoLocalizado));

        verify(pokemonService, times(1)).consultarPokemonById(pokemonModel.getIdPokemon());
    }

    @Test
    @DisplayName("Deve excluir o pokémon pelo numPokedex com sucesso")
    void excluirPokemonByNumPokedexComSucesso() throws Exception {
        when(pokemonService.consultarPokemonByNumPokedex(pokemonModel.getNumPokedex())).thenReturn(pokemonModel);
        // Opcional: Explicitamente dizer que o metodo não faz nada quando chamado
        doNothing().when(pokemonService).excluirPokemonByNumPokedex(pokemonModel.getNumPokedex());

        mockMvc.perform(delete("/pokemons/numPokedex/{numPokedex}", pokemonModel.getNumPokedex())
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string("Pokemon " + pokemonModel.getNumPokedex() + " deletado!!"));

        verify(pokemonService, times(1)).excluirPokemonByNumPokedex(pokemonModel.getNumPokedex());
    }

    @Test
    @DisplayName("Deve excluir o pokémon pelo numPokedex com erro")
    void excluirPokemonByNumPokedexComErro() throws Exception {
        when(pokemonService.consultarPokemonByNumPokedex(pokemonModel.getNumPokedex()))
                .thenThrow(new RuntimeException(messagemPokemonNaoLocalizado));

        mockMvc.perform(delete("/pokemons/numPokedex/{numPokedex}", pokemonModel.getNumPokedex())
                        .contentType("application/json"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(messagemPokemonNaoLocalizado));

        verify(pokemonService, times(1)).consultarPokemonByNumPokedex(pokemonModel.getNumPokedex());
    }

    @Test
    void excluirTodosPokemonsComSucesso() throws Exception {
        doNothing().when(pokemonService).excluirAll();

        mockMvc.perform(delete("/pokemons/delete")
                .contentType("application/json"))
                .andExpect(status().isAccepted())
                .andExpect(content().string("Todos os pokemons foram excluídos!"));

        verify(pokemonService, times(1)).excluirAll();
    }

    @Test
    void excluirTodosPokemonsComErro() throws Exception {
        doThrow(new RuntimeException("Não há pokemons para excluir!")).when(pokemonService).excluirAll();

        mockMvc.perform(delete("/pokemons/delete")
                .contentType("application/json"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Não há pokemons para excluir!"));

        verify(pokemonService, times(1)).excluirAll();
    }
}