package com.example.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FlashCard(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "front_side") val frontSide: String?,
    @ColumnInfo(name = "back_side") val backSide: String?,
    @ColumnInfo(name = "label") val label: String?
)