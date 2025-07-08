package dev.andrescoder.superheroapp.data

import dev.andrescoder.superheroapp.model.Hero
import dev.andrescoder.superheroapp.model.HeroesRepository

object DataSource {
    fun loadHeros(): List<Hero> {
        return HeroesRepository.heroes
    }
}