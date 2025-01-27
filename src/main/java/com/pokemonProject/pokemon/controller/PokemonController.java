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

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController
public class PokemonController {

    @Autowired
    PokemonRepository pokemonRepository; //inclusão do ponto de injeção

    @PostMapping("/pokemons")
    public ResponseEntity<PokemonModel> cadatrarPokemon(@RequestBody @Valid PokemonDto pokemonDto){
        var pokemonModel = new PokemonModel();
        BeanUtils.copyProperties(pokemonDto, pokemonModel);
        System.out.println("PokemonModel" + pokemonModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(pokemonRepository.save(pokemonModel));
    }

    @GetMapping("/pokemons")
    public ResponseEntity<List<PokemonModel>> consultarPokemons(){
        return ResponseEntity.ok().body(pokemonRepository.findAll());
    }

    @GetMapping("/pokemon/{id}")   //Pesquisa pelo id do BD
    public ResponseEntity<PokemonModel> consultarPokemonById(@PathVariable(value = "id")UUID id){
        Optional<PokemonModel> pokemonEncontrado = pokemonRepository.findById(id);

        return ResponseEntity.ok().body(pokemonEncontrado.get());
    }

    @GetMapping("/pokemons/numPokedex/{numPokedex}")
    public ResponseEntity<PokemonModel> consultarByNumPokedex(@PathVariable(value = "numPokedex") int numPokedex){
        Optional<PokemonModel> pokemonEncontrado = Optional.ofNullable(pokemonRepository.findByNumPokedex(numPokedex));

        return ResponseEntity.ok().body(pokemonEncontrado.get());
    }
}
