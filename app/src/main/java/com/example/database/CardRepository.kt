package com.example.database

import androidx.lifecycle.LiveData

class CardRepository (private val cardDao: FlashCardDao) {

    val allLabels: LiveData<List<String>> = cardDao.getAllLabels()
    lateinit var allCards: LiveData<List<FlashCard>>

    suspend fun insert(flashCard:FlashCard) {
        cardDao.insert(flashCard)
    }

    fun setSelectedLabel(label: String) {
        allCards = cardDao.loadAllByLabel(label)
    }

    fun deleteCard(flashCard: FlashCard) {
        cardDao.delete(flashCard)
    }

    suspend fun deleteCard(front: String, back: String) {
        cardDao.delete(front, back)
    }
}