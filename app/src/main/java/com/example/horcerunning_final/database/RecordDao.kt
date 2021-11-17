package com.example.horcerunning_final.database

import androidx.room.*

@Dao
interface RecordDao {
    @Query("SELECT * FROM record")
    fun getAll(): List<Record>
    @Query("INSERT OR REPLACE INTO record(id, Bethorse, Betmoney, Winner, Earn, Captial) VALUES(:id, :bethorse,:betmoney, :winner, :earn, :capital)")
    fun insertData(id: Int, bethorse: String, betmoney: Int, winner: String, earn: Int, capital: Int)
    @Query("DELETE FROM record")
    fun deleteall()
    @Update
    fun update(record: Record)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg record: Record)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(record: Record)
    @Delete
    fun delete(record: Record)
}