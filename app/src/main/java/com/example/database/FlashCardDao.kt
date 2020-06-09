package com.example.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FlashCardDao {
    @Query("SELECT DISTINCT label from FlashCard")
    fun getAllLabels() : LiveData<List<String>>

    @Query("SELECT * FROM FlashCard")
    fun getAll() : LiveData<List<FlashCard>>

    @Query("SELECT * FROM FlashCard WHERE uid IN (:cardIds)")
    fun loadAllByIds(cardIds: IntArray): LiveData<List<FlashCard>>

    @Query("SELECT * FROM FlashCard WHERE label IN (:label) ORDER BY RANDOM()")
    fun loadAllByLabel(label: String): LiveData<List<FlashCard>>

    @Query("DELETE FROM FLASHCARD")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(flashCard: FlashCard)

    @Delete
    suspend fun delete(flashCard: FlashCard)
}