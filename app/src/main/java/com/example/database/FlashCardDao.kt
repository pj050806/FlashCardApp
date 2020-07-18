package com.example.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FlashCardDao {
    @Query("SELECT * FROM CardPile")
    fun getAllLabels() : LiveData<List<String>>

    @Query("SELECT * FROM FlashCard")
    fun getAll() : LiveData<List<FlashCard>>

    @Query("SELECT * FROM FlashCard WHERE uid IN (:cardIds)")
    fun loadAllByIds(cardIds: IntArray): LiveData<List<FlashCard>>

    @Query("SELECT * FROM FlashCard WHERE label IN (:label) ORDER BY RANDOM()")
    fun loadAllByLabel(label: String): LiveData<List<FlashCard>>

    @Query("DELETE FROM FlashCard")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(flashCard: FlashCard)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(pile: CardPile)

    @Delete
    fun delete(vararg flashCard: FlashCard)

    @Query("DELETE FROM FlashCard WHERE front_side IN(:front, :back) AND back_side IN(:front, :back)")
    suspend fun delete(front: String, back: String)
}