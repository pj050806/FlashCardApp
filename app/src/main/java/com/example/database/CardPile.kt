package com.example.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CardPile(@PrimaryKey val label: String)