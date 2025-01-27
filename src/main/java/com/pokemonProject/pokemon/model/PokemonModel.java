package com.pokemonProject.pokemon.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "tb_pokemon")
public class PokemonModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idPokemon;
    private String nome;
    private int numPokedex;
    private String tipo;
    private String urlPokemon;
    private String descricao;

    public UUID getIdPokemon() {
        return idPokemon;
    }

    public void setIdPokemon(UUID idPokemon) {
        this.idPokemon = idPokemon;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getNumPokedex() {
        return numPokedex;
    }

    public void setNumPokedex(int numPokedex) {
        this.numPokedex = numPokedex;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
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
