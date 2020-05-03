package com.example.database

import androidx.lifecycle.LiveData

class CardRepository (private val cardDao: FlashCardDao) {
    val allCards: LiveData<List<FlashCard>> = cardDao.getAll()

    suspend fun insert(fashCard:FlashCard) {
        cardDao.insert(fashCard)
    }
}