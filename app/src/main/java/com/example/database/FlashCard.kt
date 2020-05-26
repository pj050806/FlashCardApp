package com.example.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FlashCard(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "front_side") val frontSide: String,
    @ColumnInfo(name = "back_side") val backSide: String,
    @ColumnInfo(name = "label") val label: String
) {
    constructor(frontSide: String, backSide: String, label: String) : this(0, frontSide, backSide, label)
}