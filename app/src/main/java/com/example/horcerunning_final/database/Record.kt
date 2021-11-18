package com.example.horcerunning_final.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Record(
    @PrimaryKey(autoGenerate = true) val id: Int? = 0,
    @ColumnInfo val Bethorse: String,
    @ColumnInfo val Betmoney: Int,
    @ColumnInfo val Winner: String,
    @ColumnInfo val Earn: Int,
    @ColumnInfo val Captial: Int
)