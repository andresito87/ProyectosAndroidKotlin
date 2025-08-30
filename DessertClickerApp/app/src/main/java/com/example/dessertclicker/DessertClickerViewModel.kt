package com.example.dessertclicker

import androidx.lifecycle.ViewModel
import com.example.dessertclicker.data.Datasource
import com.example.dessertclicker.model.Dessert
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DessertClickerViewModel(
    private val desserts: List<Dessert> = Datasource.dessertList,
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        DessertUiState(
            revenue = 0,
            dessertsSold = 0,
            currentDessertImageId = desserts[0].imageId,
            currentDessertPrice = desserts[0].price
        )
    )
    val uiState: StateFlow<DessertUiState> = _uiState

    fun onDessertClicked() {
        val currentState = _uiState.value
        val newDessertsSold = currentState.dessertsSold + 1
        val newRevenue = currentState.revenue + currentState.currentDessertPrice
        val dessertToShow = determineDessertToShow(desserts, newDessertsSold)
        _uiState.value = currentState.copy(
            revenue = newRevenue,
            dessertsSold = newDessertsSold,
            currentDessertImageId = dessertToShow.imageId,
            currentDessertPrice = dessertToShow.price
        )
    }

    /**
     * Determine which dessert to show.
     */
    fun determineDessertToShow(
        desserts: List<Dessert>,
        dessertsSold: Int,
    ): Dessert {
        var dessertToShow = desserts.first()
        for (dessert in desserts) {
            if (dessertsSold >= dessert.startProductionAmount) {
                dessertToShow = dessert
            } else {
                // The list of desserts is sorted by startProductionAmount. As you sell more desserts,
                // you'll start producing more expensive desserts as determined by startProductionAmount
                // We know to break as soon as we see a dessert who's "startProductionAmount" is greater
                // than the amount sold.
                break
            }
        }

        return dessertToShow
    }
}