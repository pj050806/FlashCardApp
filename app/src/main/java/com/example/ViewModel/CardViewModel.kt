package com.example.ViewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.database.AppDatabase
import com.example.database.CardRepository
import com.example.database.FlashCard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CardViewModel(application: Application) : AndroidViewModel(application) {

    private  val repository: CardRepository
    val allCards: LiveData<List<FlashCard>>

    init {
        println("CardViewModel init...")
        val cardDao = AppDatabase.getDatabase(application, viewModelScope).flashCardDao()
        repository = CardRepository(cardDao)
        allCards = repository.allCards
        Log.d("allCards size ViewModel", allCards.value?.size.toString())
    }

    suspend fun insert(card: FlashCard) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(card)
    }
}

