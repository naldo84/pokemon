package com.pokemonProject.pokemon.service;

import com.pokemonProject.pokemon.dto.PokemonDto;
import com.pokemonProject.pokemon.model.PokemonModel;
import com.pokemonProject.pokemon.repository.PokemonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
        return pokemonRepository.findAll();
    }

    public PokemonModel consultarPokemonById(UUID id) {
        Optional<PokemonModel> consultarPokemon = pokemonRepository.findById(id);

        if (consultarPokemon.isEmpty())
            throw new RuntimeException("Pokemon não localizado!");

        return consultarPokemon.get();
    }

    public PokemonModel consultarPokemonByNumPokedex(int numPokedex) {
        Optional<PokemonModel> consultarPokemon = pokemonRepository.findByNumPokedex(numPokedex);

        if (consultarPokemon.isEmpty()) {
            throw new RuntimeException("Pokemon não localizado!");
        }

        return consultarPokemon.get();
    }

    public PokemonModel alterarPokemonByNumPokedex(int numPokedex, PokemonDto pokemonDto) {
        Optional<PokemonModel> consultarPokemon = pokemonRepository.findByNumPokedex(numPokedex);

        if (consultarPokemon.isEmpty()) {
            throw new RuntimeException("Pokemon não localizado!");
        }

        var pokemonModel = consultarPokemon.get();
        BeanUtils.copyProperties(pokemonDto, pokemonModel);
        pokemonRepository.save(pokemonModel);

        return pokemonModel;
    }

    public void excluirPokemonByNumPokedex(int numPokedex) {
        Optional<PokemonModel> consultarPokemon = pokemonRepository.findByNumPokedex(numPokedex);

        if (consultarPokemon.isEmpty()) {
            throw new RuntimeException("Pokemon não localizado!");
        }

        pokemonRepository.delete(consultarPokemon.get());
    }

    public void excluirPokemonById(UUID id) {
        Optional<PokemonModel> consultarPokemon = pokemonRepository.findById(id);

        if (consultarPokemon.isEmpty()) {
            throw new RuntimeException("Pokemon não localizado!");
        }

        pokemonRepository.delete(consultarPokemon.get());
    }

    public boolean excluirAll(){
        long count = pokemonRepository.count();

        if (count == 0)
            return false;

        try {
            logger.info("Iniciando a exclusão de todos os pokemons...");
            pokemonRepository.deleteAll();
            logger.info("Todos os pokemons foram excluídos com sucesso!");
            return true;

        } catch (Exception e){
            logger.error("Erro ao tentar excluir os Pokémons: {}", e.getMessage());
            throw new RuntimeException("Erro ao excluir os pokemons!");

        }
    }
}

