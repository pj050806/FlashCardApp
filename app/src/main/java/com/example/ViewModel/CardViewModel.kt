package com.example.ViewModel

import android.app.Application
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
    lateinit var allCards: LiveData<List<FlashCard>>
    val allLabels: LiveData<List<String>>

    init {
        println("CardViewModel init...")
        val cardDao = AppDatabase.getDatabase(application, viewModelScope).flashCardDao()
        repository = CardRepository(cardDao)
        allLabels = repository.allLabels
    }

    fun insert(card: FlashCard) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(card)
    }

    fun initAllCards(label: String) {
        repository.setSelectedLabel(label)
        allCards = repository.allCards
    }

    fun deleteCard(card: FlashCard) = repository.deleteCard(card)

    fun deleteCard(front: String, back: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteCard(front, back)
    }

}

