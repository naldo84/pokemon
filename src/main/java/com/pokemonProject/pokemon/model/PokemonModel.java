package com.pokemonProject.pokemon.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Entity
@Table(name = "tb_pokemon")
public class PokemonModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idPokemon;

    @NotNull
    private String nome;

    @NotNull
    private String tipo;

    private String urlPokemon;
    private String descricao;

    public UUID getIdPokemon() {
        return idPokemon;
    }

    public void setIdPokemon(UUID idPokemon) {
        this.idPokemon = idPokemon;
    }

    public @NotNull String getNome() {
        return nome;
    }

    public void setNome(@NotNull String nome) {
        this.nome = nome;
    }

    public @NotNull String getTipo() {
        return tipo;
    }

    public void setTipo(@NotNull String tipo) {
        this.tipo = tipo;
    }

    public String getUrlPokemon() {
        return urlPokemon;
    }

    public void setUrlPokemon(String urlPokemon) {
        this.urlPokemon = urlPokemon;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
