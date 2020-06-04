package com.example.database

import androidx.lifecycle.LiveData

class CardRepository (private val cardDao: FlashCardDao) {
    val allCards: LiveData<List<FlashCard>> = cardDao.loadAllByLabel("Testwörter1")

    suspend fun insert(flashCard:FlashCard) {
        cardDao.insert(flashCard)
    }
}