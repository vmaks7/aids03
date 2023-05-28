package com.vandanov.aids03.data.room

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [RegisterItemDBModel::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun registerListDao(): RegisterListDao

    companion object {

        // паттерн синглтон
        private var INSTANCE: AppDatabase? = null
        // синглтоны должны быть синхронизированы
        // добавляем объект синхронизации
        private val LOCK = Any()
        //имя базы данных
        private const val DB_NAME = "register_item.db"

        fun getInstance(application: Application): AppDatabase {
            // если перемнной присвоено значение, то сразу возвращаем
            INSTANCE?.let {
                return it
            }
            //даблчек (двойная проверка)
            //если несколько потоков одновремеено вызвали этот метод, когда INSTANCE=null
            synchronized(LOCK) {
                INSTANCE?.let {
                    return it
                }
                val db = Room.databaseBuilder(
                    application,
                    AppDatabase::class.java,
                    DB_NAME
                )
                        //для ТЕСТА! операции с БД будут выполняться на главном потоке
                    //.allowMainThreadQueries()
                    .build()
                INSTANCE = db
                return db
            }
        }
    }
}