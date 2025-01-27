package com.pokemonProject.pokemon.repository;

import com.pokemonProject.pokemon.model.PokemonModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PokemonRepository extends JpaRepository<PokemonModel, UUID> {
}
