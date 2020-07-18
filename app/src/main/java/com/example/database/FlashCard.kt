package com.example.database

import android.graphics.Color
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FlashCard(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "front_side") val frontSide: String,
    @ColumnInfo(name = "back_side") val backSide: String,
    @ColumnInfo(name = "label") val label: String,
    @ColumnInfo(name = "color") val color: Int
) {
    constructor(frontSide: String, backSide: String, label: String) : this(0, frontSide, backSide, label, Color.WHITE)
    constructor(frontSide: String, backSide: String, label: String, color: Int) : this(0, frontSide, backSide, label, color)
}