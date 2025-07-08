package dev.andrescoder.superheroapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.andrescoder.superheroapp.data.DataSource
import dev.andrescoder.superheroapp.ui.theme.SuperHeroAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SuperHeroAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    SuperheroesApp()
                }
            }
        }
    }
}

@Composable
fun SuperheroesApp() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SuperHeroTopAppBar()
        }
    ) {
        val heroes = DataSource.loadHeros()
        HeroesList(heroes = heroes, contentPadding = it)
    }
}

@Preview(showBackground = true)
@Composable
fun SuperHeroAppPreview() {
    SuperHeroAppTheme {
        SuperheroesApp()
    }
}