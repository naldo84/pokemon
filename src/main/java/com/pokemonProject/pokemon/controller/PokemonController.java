package com.pokemonProject.pokemon.controller;

import com.pokemonProject.pokemon.model.PokemonModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PokemonController {

    List<String> pokemons = new ArrayList<>();

    @GetMapping("/pokemons")
    public List<String> consultarPokemons(){
        pokemons.add("Pikachu");
        pokemons.add("Charmander");
        return pokemons;
    }
}
