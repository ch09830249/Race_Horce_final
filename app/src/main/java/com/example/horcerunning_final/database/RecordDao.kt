package com.example.horcerunning_final.database

import androidx.room.*

//Before add suspend modifier, please add "androidx.room:room-ktx:$room_version"
@Dao
interface RecordDao {
    @Query("SELECT * FROM record")
    suspend fun getAll(): List<Record>
    @Query("INSERT OR REPLACE INTO record(id, Bethorse, Betmoney, Winner, Earn, Captial) VALUES(:id, :bethorse,:betmoney, :winner, :earn, :capital)")
    suspend fun insertData(id: Int, bethorse: String, betmoney: Int, winner: String, earn: Int, capital: Int)
    @Query("DELETE FROM record")
    suspend fun deleteall()
    @Update
    suspend fun update(record: Record)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg record: Record)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(record: Record)
    @Delete
    suspend fun delete(record: Record)
}