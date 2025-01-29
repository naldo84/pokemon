package com.pokemonProject.pokemon.controller;

import com.pokemonProject.pokemon.dto.PokemonDto;
import com.pokemonProject.pokemon.model.PokemonModel;
import com.pokemonProject.pokemon.service.PokemonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;


@RestController
public class PokemonController {

    @Autowired
    PokemonService pokemonService;

    @PostMapping("/pokemons")
    public ResponseEntity<Object> cadatrarPokemon(@RequestBody @Valid PokemonDto pokemonDto){
        try {
            var pokemonSalvo = pokemonService.cadastrarPokemon(pokemonDto);
            URI location = URI.create("/pokemons/" + pokemonSalvo.getNumPokedex());

            return ResponseEntity.created(location).body(pokemonSalvo);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/pokemons")
    public ResponseEntity<List<PokemonModel>> consultarPokemons(){
        List<PokemonModel> consultarPokemons = pokemonService.consultarPokemons();

        return ResponseEntity.ok().body(consultarPokemons);
    }

    @GetMapping("/pokemons/{id}")   //Pesquisa pelo id do BD
    public ResponseEntity<Object> consultarPokemonById(@PathVariable(value = "id")UUID id){
        try {
            PokemonModel pokemonEncontrado = pokemonService.consultarPokemonById(id);

            return ResponseEntity.ok().body(pokemonEncontrado);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pokemon não localizado!");
        }
    }

    @GetMapping("/pokemons/numPokedex/{numPokedex}")
    public ResponseEntity<Object> consultarByNumPokedex(@PathVariable(value = "numPokedex") int numPokedex){
        try {
            PokemonModel pokemonEncontrado = pokemonService.consultarPokemonByNumPokedex(numPokedex);

            return ResponseEntity.ok().body(pokemonEncontrado);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pokemon não localizado!");
        }
    }

    @PutMapping("/pokemons/numPokedex/{numPokedex}")
    public ResponseEntity<Object> alterarPokemon(@PathVariable (value = "numPokedex") int numPokedex,
                                                       @RequestBody @Valid PokemonDto pokemonDto) {
        try {
            PokemonModel pokemonEncontrado = pokemonService.alterarPokemonByNumPokedex(numPokedex, pokemonDto);

            return ResponseEntity.ok().body(pokemonEncontrado);

        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pokemon não localizado!");

        }
    }

    @DeleteMapping("/pokemons/numPokedex/{numPokedex}")
    public ResponseEntity<Object> excluirPokemonByNumPokedex(@PathVariable ("numPokedex") int numPokedex){
        try {
            PokemonModel pokemonEncontrado = pokemonService.consultarPokemonByNumPokedex(numPokedex);
            pokemonService.excluirPokemonByNumPokedex(pokemonEncontrado.getNumPokedex());

            return ResponseEntity.ok().body("Pokemon " + numPokedex + " deletado!!");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pokemon com o ID fornecido não encontrado!");
        }
    }

    @DeleteMapping("/pokemons/{id}")
    public ResponseEntity<Object> excluirPokemonById(@PathVariable(value = "id") UUID id){
        try {
            PokemonModel pokemonEncontrado = pokemonService.consultarPokemonById(id);

            pokemonService.excluirPokemonById(pokemonEncontrado.getIdPokemon());

            return ResponseEntity.ok().body("Pokemon " + id + " deletado!!");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pokemon com o ID fornecido não encontrado!");
        }
    }

    @DeleteMapping("pokemons/delete")
    public ResponseEntity<Object> excluirTodosPokemons(){
        try {
            pokemonService.excluirAll();

            return ResponseEntity.accepted().body("Todos os pokemons foram excluídos!");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Não há pokemons para serem excluídos!");
        }
    }
}
