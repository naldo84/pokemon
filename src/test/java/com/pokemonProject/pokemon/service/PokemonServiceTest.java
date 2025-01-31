package com.pokemonProject.pokemon.service;

import com.pokemonProject.pokemon.dto.PokemonDto;
import com.pokemonProject.pokemon.model.PokemonModel;
import com.pokemonProject.pokemon.repository.PokemonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PokemonServiceTest {


    @InjectMocks
    private PokemonService pokemonService;

    @Mock
    private PokemonRepository pokemonRepository;

    private PokemonModel pokemonModel;
    private PokemonDto pokemonDto;

    @BeforeEach
    public void setUp(){
        pokemonModel = new PokemonModel();
        pokemonModel.setIdPokemon(UUID.fromString("7e900984-c145-41fa-981d-0f062b7e66c9"));
        pokemonModel.setNome("Bulbassauro");
        pokemonModel.setNumPokedex(1);
        pokemonModel.setTipo("Planta");
        pokemonModel.setUrlPokemon("Url do pokemon");
        pokemonModel.setDescricao("Descrição do pokemon");

        pokemonDto = new PokemonDto("Bullbasauro Alterado", 1, "Planta Alterada", "URL alterada", "Descrição do Bulbassauro alterada");
    }

    @Test
    @DisplayName("Deve cadastrar pokemon com sucesso")
    void cadastrarPokemonComSucesso(){
        when(pokemonRepository.findByNumPokedex(pokemonDto.numPokedex()))
                .thenReturn(Optional.empty());

        when(pokemonRepository.save(any(PokemonModel.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        PokemonModel resultado = pokemonService.cadastrarPokemon(pokemonDto);

        assertNotNull(resultado);
        assertEquals(pokemonDto.nome(), resultado.getNome());
        assertEquals(pokemonDto.numPokedex(), resultado.getNumPokedex());
        assertEquals(pokemonDto.tipo(), resultado.getTipo());
        assertEquals(pokemonDto.urlPokemon(), resultado.getUrlPokemon());
        assertEquals(pokemonDto.descricao(), resultado.getDescricao());

        verify(pokemonRepository, times(1)).save(any(PokemonModel.class));
    }

    @Test
    @DisplayName("Deve apresentar erro ao cadastrar pokemon ")
    void cadastrarPokemonComErro(){
        when(pokemonRepository.findByNumPokedex(pokemonDto.numPokedex()))
                .thenReturn(Optional.of(pokemonModel));

        RuntimeException resultado = assertThrows(RuntimeException.class, () -> {
            pokemonService.cadastrarPokemon(pokemonDto);
        });

        assertEquals((pokemonDto.nome() + " de nro " + pokemonDto.numPokedex() + " já está cadastrado!"), resultado.getMessage());
    }

    @Test
    @DisplayName("Deve consultar todos os pokemons")
    void consultarTodosOsPokeons(){
        when(pokemonRepository.findAll())
                .thenReturn(Collections.singletonList(pokemonModel));
        List<PokemonModel> resultado = pokemonService.consultarPokemons();

        verify(pokemonRepository, times(1)).findAll();
        assertEquals(1, resultado.size());
        assertEquals("Bulbassauro", resultado.get(0).getNome());
    }

    @Test
    @DisplayName("Deve consultar Pokemon pelo numPokedex com sucesso")
    void consultarPokemonPorNumPokedexComSucesso() {
        int numPokedex = 1;

        when(pokemonRepository.findByNumPokedex(numPokedex))
                .thenReturn(Optional.of(pokemonModel));

        Optional<PokemonModel>  resultado = Optional.ofNullable(pokemonService.consultarPokemonByNumPokedex(numPokedex));

        assertTrue(resultado.isPresent());
        assertEquals(pokemonModel.getIdPokemon(), resultado.get().getIdPokemon());
        assertEquals(pokemonModel.getNome(), resultado.get().getNome());
        assertEquals(pokemonModel.getNumPokedex(), resultado.get().getNumPokedex());
        assertEquals(pokemonModel.getTipo(), resultado.get().getTipo());
        assertEquals(pokemonModel.getUrlPokemon(), resultado.get().getUrlPokemon());
        assertEquals(pokemonModel.getDescricao(), resultado.get().getDescricao());
    }

    @Test
    @DisplayName("Deve consultar Pokemon pelo numPokedex com erro")
    void consultarPokemonporNumPokedexComErro() {
        when(pokemonRepository.findByNumPokedex(pokemonModel.getNumPokedex()))
                .thenReturn(Optional.empty());
       RuntimeException resultException = assertThrows(RuntimeException.class, () -> {
                   pokemonService.consultarPokemonByNumPokedex(pokemonModel.getNumPokedex());
               });

       assertEquals("Pokémon não localizado!", resultException.getMessage());
    }

    @Test
    @DisplayName("Deve consultar Pokemon pelo id com sucesso")
    void consultarPokemonPorIdComSucesso() {
        UUID id = UUID.fromString("7c468e66-d5dc-4206-8322-8f322d619455");

        when(pokemonRepository.findById(id))
                .thenReturn(Optional.of(pokemonModel));

        Optional<PokemonModel>  resultado = Optional.ofNullable(pokemonService.consultarPokemonById(id));

        assertTrue(resultado.isPresent());
        assertEquals(pokemonModel.getIdPokemon(), resultado.get().getIdPokemon());
        assertEquals(pokemonModel.getNome(), resultado.get().getNome());
        assertEquals(pokemonModel.getNumPokedex(), resultado.get().getNumPokedex());
        assertEquals(pokemonModel.getTipo(), resultado.get().getTipo());
        assertEquals(pokemonModel.getUrlPokemon(), resultado.get().getUrlPokemon());
        assertEquals(pokemonModel.getDescricao(), resultado.get().getDescricao());
    }

    @Test
    @DisplayName("Deve consultar Pokemon pelo numPokedex com erro")
    void consultarPokemonPorIdComErro() {
        when(pokemonRepository.findById(pokemonModel.getIdPokemon()))
                .thenReturn(Optional.empty());
        RuntimeException resultException = assertThrows(RuntimeException.class, () -> {
            pokemonService.consultarPokemonById(pokemonModel.getIdPokemon());
        });

        assertEquals("Pokémon não localizado!", resultException.getMessage());
    }

    @Test
    @DisplayName(("Deve alterar um pokemon utilizando o numPokedex com sucesso"))
    void alterarPodemonUtilizandoNumPokedexComSucesso(){
        int numPokedex = 1;
        when(pokemonRepository.findByNumPokedex(numPokedex))
                .thenReturn(Optional.of(pokemonModel));

        PokemonModel pokemonResultado = pokemonService.alterarPokemonByNumPokedex(numPokedex, pokemonDto);

        assertNotNull(pokemonResultado);
        assertEquals(pokemonDto.nome(), pokemonResultado.getNome());
        assertEquals(pokemonDto.numPokedex(), pokemonResultado.getNumPokedex());
        assertEquals(pokemonDto.tipo(), pokemonResultado.getTipo());
        assertEquals(pokemonDto.urlPokemon(), pokemonResultado.getUrlPokemon());
        assertEquals(pokemonDto.descricao(), pokemonResultado.getDescricao());

        verify(pokemonRepository, times(1)).save(pokemonResultado);
    }

    @Test
    @DisplayName("Deve apresentar erro alterar um pokemon utilizando o numPokedex")
    void alterarPokemonComErro(){
        int numPokedex = 1;
        when(pokemonRepository.findByNumPokedex(numPokedex))
                .thenReturn(Optional.empty());

        RuntimeException resultException = assertThrows(RuntimeException.class,
                () -> pokemonService.alterarPokemonByNumPokedex(numPokedex, pokemonDto));

        assertEquals("Pokémon não localizado!", resultException.getMessage());
    }

    @Test
    @DisplayName("Deve excluir um pokemon com sucesso utilizando o numPokedex")
    void excluirPokemonComSucesso(){
        when(pokemonRepository.findByNumPokedex(pokemonModel.getNumPokedex()))
                .thenReturn(Optional.of(pokemonModel));

       pokemonService.excluirPokemonByNumPokedex(pokemonModel.getNumPokedex());

       verify(pokemonRepository, times(1)).delete(pokemonModel);
    }

    @Test
    @DisplayName("Deve lançar erro ao tentar excluir um pokemon não encontrado")
    void excluirPokemonComErro() {
        when(pokemonRepository.findByNumPokedex(pokemonModel.getNumPokedex()))
                .thenReturn(Optional.empty());

        RuntimeException resultadoException = assertThrows(RuntimeException.class, () -> pokemonService.excluirPokemonByNumPokedex(pokemonModel.getNumPokedex()));

        assertEquals("Pokémon não localizado!", resultadoException.getMessage());
    }

    @Test
    @DisplayName("Deve excluir um pokemon com sucesso utilizando o id")
    void excluirPokemonComSucessoUtilizandoOId(){
        when(pokemonRepository.findById(pokemonModel.getIdPokemon()))
                .thenReturn(Optional.of(pokemonModel));

        pokemonService.excluirPokemonById(pokemonModel.getIdPokemon());

        verify(pokemonRepository, times(1)).delete(pokemonModel);
    }

    @Test
    @DisplayName("Deve lançar erro ao tentar excluir um pokemon não encontrado")
    void excluirPokemonComErroUtilizandoOId() {
        when(pokemonRepository.findById(pokemonModel.getIdPokemon()))
                .thenReturn(Optional.empty());

        RuntimeException resultadoException = assertThrows(RuntimeException.class, () -> pokemonService.excluirPokemonById(pokemonModel.getIdPokemon()));

        assertEquals("Pokémon não localizado!", resultadoException.getMessage());

    }

    @Test
    @DisplayName("Excluir todos os pokemons com sucesso")
    void excluirTodosOsPokemons(){
        when(pokemonRepository.count()).thenReturn(1L);
        pokemonService.excluirAll();

        verify(pokemonRepository, times(1)).deleteAll();
    }

    @Test
    @DisplayName("Excluir todos os pokemons e apresentar erro")
    void excluirTodosOsPokemonsComErro(){
        when(pokemonRepository.count()).thenReturn(1L);
        doThrow(new RuntimeException("Erro ao excluir os pokemons!")).when(pokemonRepository).deleteAll();

        RuntimeException resultException = assertThrows(RuntimeException.class, () -> pokemonService.excluirAll());

        assertEquals("Erro ao excluir os pokemons!", resultException.getMessage());
    }
}