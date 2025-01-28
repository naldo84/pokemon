package com.pokemonProject.pokemon.controller;

import com.pokemonProject.pokemon.dto.PokemonDto;
import com.pokemonProject.pokemon.model.PokemonModel;
import com.pokemonProject.pokemon.repository.PokemonRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController
public class PokemonController {

    @Autowired
    PokemonRepository pokemonRepository; //inclusão do ponto de injeção

    @PostMapping("/pokemons")
    public ResponseEntity<Object> cadatrarPokemon(@RequestBody @Valid PokemonDto pokemonDto){
        Optional<PokemonModel> pokemonExistente = pokemonRepository.findByNumPokedex(pokemonDto.numPokedex());

        if (pokemonExistente.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Pokemon já cadastrado");
        }

        var pokemonModel = new PokemonModel();

        BeanUtils.copyProperties(pokemonDto, pokemonModel);
        PokemonModel pokemonSalvo = pokemonRepository.save(pokemonModel);
        URI location = URI.create("/pokemons/" + pokemonSalvo.getNumPokedex());

        return ResponseEntity.created(location).body(pokemonSalvo);
    }

    @GetMapping("/pokemons")
    public ResponseEntity<List<PokemonModel>> consultarPokemons(){
        return ResponseEntity.ok().body(pokemonRepository.findAll());
    }

    @GetMapping("/pokemons/{id}")   //Pesquisa pelo id do BD
    public ResponseEntity<Object> consultarPokemonById(@PathVariable(value = "id")UUID id){
        Optional<PokemonModel> pokemonEncontrado = pokemonRepository.findById(id);

        if (pokemonEncontrado.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pokémon com o ID fornecido não encontrado!");

        return ResponseEntity.ok().body(pokemonEncontrado.get());
    }

    @GetMapping("/pokemons/numPokedex/{numPokedex}")
    public ResponseEntity<Object> consultarByNumPokedex(@PathVariable(value = "numPokedex") int numPokedex){
        Optional<PokemonModel> pokemonEncontrado = pokemonRepository.findByNumPokedex(numPokedex);

        if (pokemonEncontrado.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pokémon com o ID fornecido não encontrado!");

        return ResponseEntity.ok().body(pokemonEncontrado.get());
    }

    @PutMapping("/pokemons/numPokedex/{numPokedex}")
    public ResponseEntity<Object> alterarPokemon(@PathVariable (value = "numPokedex") int numPokedex,
                                                       @RequestBody @Valid PokemonDto pokemonDto){
        Optional<PokemonModel> pokemonEncontrado = pokemonRepository.findByNumPokedex(numPokedex);

        if (pokemonEncontrado.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pokémon com o ID fornecido não encontrado!");

        var pokemonModel =pokemonEncontrado.get();
        BeanUtils.copyProperties(pokemonDto, pokemonModel);

        return ResponseEntity.ok().body(pokemonRepository.save(pokemonModel));
    }

    @DeleteMapping("/pokemons/numPokedex/{numPokedex}")
    public ResponseEntity<Object> excluirPokemon(@PathVariable(value = "numPokedex") int numPokedex){
        Optional<PokemonModel> pokemonEncontrado = pokemonRepository.findByNumPokedex(numPokedex);

        if (pokemonEncontrado.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pokémon com o ID fornecido não encontrado!");

        pokemonRepository.delete(pokemonEncontrado.get());

        return ResponseEntity.ok().body("Pokemon de nro " + pokemonEncontrado.get().getNumPokedex() + " deletado!");
    }

    @DeleteMapping("/pokemons/{id}")
    public ResponseEntity<Object> excluirPokemon(@PathVariable(value = "id") UUID id){
        Optional<PokemonModel> pokemonEncontrado = pokemonRepository.findById(id);

        if (pokemonEncontrado.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pokémon com o ID fornecido não encontrado!");

        pokemonRepository.delete(pokemonEncontrado.get());

        return ResponseEntity.ok().body("Pokemon de nro " + pokemonEncontrado.get().getNumPokedex() + " deletado!");
    }

    @DeleteMapping("pokemons/delete")
    public ResponseEntity<Object> excluirTodosPokemons(){
        pokemonRepository.deleteAll();

        return ResponseEntity.ok().body("Todos os pokemons foram excluídos!");
    }
}
