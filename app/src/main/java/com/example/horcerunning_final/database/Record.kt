package com.example.horcerunning_final.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Record(
    @PrimaryKey val id: Int,
    @ColumnInfo val Bethorse: String,
    @ColumnInfo val Betmoney: Int,
    @ColumnInfo val Winner: String,
    @ColumnInfo val Earn: Int,
    @ColumnInfo val Captial: Int
)