package com.pokemonProject.pokemon.controller;

import com.pokemonProject.pokemon.dto.PokemonDto;
import com.pokemonProject.pokemon.model.PokemonModel;
import com.pokemonProject.pokemon.repository.PokemonRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


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
}
