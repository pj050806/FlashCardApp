package com.example.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [FlashCard::class, CardPile::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun flashCardDao(): FlashCardDao

    private class AppDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onOpen(db: SupportSQLiteDatabase) {
            println("onOpen")
            super.onOpen(db)
        }

        override fun onCreate(db: SupportSQLiteDatabase) {
            println("onCreate")
            super.onCreate(db)

            INSTANCE?.let { database ->
                scope.launch {
                    val dao = database.flashCardDao()
                    populateDatabase(dao)
                }
            }
        }

        suspend fun populateDatabase(flashCardDao: FlashCardDao) {
            //flashCardDao.deleteAll()

            val pile = CardPile("TestWörter")
            val firstCard = FlashCard("Wort1 vorn", "Wort1 hinten", pile.label)
            val secondCard = FlashCard("Wort2 vorn", "Wort2 hinten", pile.label)
            flashCardDao.insert(pile)
            flashCardDao.insert(firstCard)
            println("first insert")
            flashCardDao.insert(secondCard)
            println("second insert")
        }

    }

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase ?= null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase =
            INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "flashcard_database"
                )
                 .addCallback(AppDatabaseCallback(scope))
                 .build()
                INSTANCE = instance
                Log.d("AppDatabse","database build")
                return instance
            }


        //just for the short use of kotlin syntax
        fun getDatabase2nd(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder (
                    context.applicationContext,
                    AppDatabase::class.java,
                    "flashcard_database"
                ).build().also { INSTANCE = it }
            }
    }
}