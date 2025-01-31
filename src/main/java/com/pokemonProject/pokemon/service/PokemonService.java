package com.pokemonProject.pokemon.service;

import com.pokemonProject.pokemon.dto.PokemonDto;
import com.pokemonProject.pokemon.model.PokemonModel;
import com.pokemonProject.pokemon.repository.PokemonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PokemonService {

    @Autowired
    PokemonRepository pokemonRepository;

    private static final Logger logger = LoggerFactory.getLogger(PokemonService.class);

    public PokemonModel cadastrarPokemon(PokemonDto pokemonDto) {
        Optional<PokemonModel> consultarPokemon = pokemonRepository.findByNumPokedex(pokemonDto.numPokedex());

        if (consultarPokemon.isPresent())
            throw new RuntimeException(pokemonDto.nome() + " de nro " + pokemonDto.numPokedex() + " já está cadastrado!");

        var pokemonModel = new PokemonModel();
        BeanUtils.copyProperties(pokemonDto, pokemonModel);

        return pokemonRepository.save(pokemonModel);
    }

    public List<PokemonModel> consultarPokemons() {
        List<PokemonModel> listaDePokemons = pokemonRepository.findAll();

        return listaDePokemons.isEmpty() ? Collections.emptyList() : listaDePokemons;
    }

    public PokemonModel consultarPokemonById(UUID id) {
        Optional<PokemonModel> consultarPokemon = pokemonRepository.findById(id);

        if (consultarPokemon.isEmpty())
            throw new RuntimeException("Pokémon não localizado!");

        return consultarPokemon.get();
    }

    public PokemonModel consultarPokemonByNumPokedex(int numPokedex) {
        Optional<PokemonModel> consultarPokemon = pokemonRepository.findByNumPokedex(numPokedex);

        if (consultarPokemon.isEmpty()) {
            throw new RuntimeException("Pokémon não localizado!");
        }

        return consultarPokemon.get();
    }

    public PokemonModel alterarPokemonByNumPokedex(int numPokedex, PokemonDto pokemonDto) {
        Optional<PokemonModel> consultarPokemon = pokemonRepository.findByNumPokedex(numPokedex);

        if (consultarPokemon.isEmpty()) {
            throw new RuntimeException("Pokémon não localizado!");
        }

        var pokemonModel = consultarPokemon.get();
        BeanUtils.copyProperties(pokemonDto, pokemonModel);
        pokemonRepository.save(pokemonModel);

        return pokemonModel;
    }

    public void excluirPokemonByNumPokedex(int numPokedex) {
        Optional<PokemonModel> consultarPokemon = pokemonRepository.findByNumPokedex(numPokedex);

        if (consultarPokemon.isEmpty()) {
            throw new RuntimeException("Pokémon não localizado!");
        }

        pokemonRepository.delete(consultarPokemon.get());
    }

    public void excluirPokemonById(UUID id) {
        Optional<PokemonModel> consultarPokemon = pokemonRepository.findById(id);

        if (consultarPokemon.isEmpty()) {
            throw new RuntimeException("Pokémon não localizado!");
        }

        pokemonRepository.delete(consultarPokemon.get());
    }

    public void excluirAll(){
        long count = pokemonRepository.count();

        if (count == 0){
            throw new RuntimeException("Não há pokemons para excluir!");
        }
        try {
            pokemonRepository.deleteAll();

        } catch (Exception e){
            throw new RuntimeException("Erro ao excluir os pokemons!");
        }
    }
}

