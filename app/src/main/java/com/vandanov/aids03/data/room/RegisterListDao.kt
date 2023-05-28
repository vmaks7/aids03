package com.vandanov.aids03.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RegisterListDao {

    // если возращаемый тип livaData то не нужно переключать поток, он переключается автоматически
    @Query("SELECT * FROM register_items")
    fun getRegisterList(): LiveData<List<RegisterItemDBModel>>

    // здесь поток необходимо переключить вручную (добавить suspend)

    // если в БД будет добавлен объект с тем же ID, то он перезапишется, иначе будет добавлен новый
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRegisterItem(registerItemDBModel: RegisterItemDBModel)

    @Query("DELETE FROM register_items WHERE id=:registerItemID")
    suspend fun deleteRegisterItem(registerItemID: Int)

    // возвращает 1 объект из БД
    @Query("SELECT * FROM register_items WHERE id=:registerItemID LIMIT 1")
    suspend fun getRegisterListID(registerItemID: Int): RegisterItemDBModel
}