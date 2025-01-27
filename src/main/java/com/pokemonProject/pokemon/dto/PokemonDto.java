package com.pokemonProject.pokemon.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PokemonDto(
        @NotBlank String nome,
        @NotNull int numPokedex,
        @NotNull String tipo,
        @NotNull String urlPokemon,
        String descricao) {
}
