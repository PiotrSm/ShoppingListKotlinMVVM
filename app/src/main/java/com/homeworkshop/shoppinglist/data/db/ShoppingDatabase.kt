package com.homeworkshop.shoppinglist.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.homeworkshop.shoppinglist.data.db.entities.ShoppingItem

@Database(entities = arrayOf(ShoppingItem::class), version = 1)
abstract class ShoppingDatabase : RoomDatabase() {

    abstract fun getShoppingDao(): ShoppingDao

    //tworzymy singletona bazy danych
    companion object {
        @Volatile
        private var instance: ShoppingDatabase? = null
        private val LOCK = Any()

        //operator fun invoke jest funkcją wywoływaną w chwili gdy utworzymy obiekt tej klasy np ShoppingDatabase()
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ShoppingDatabase::class.java,
                "ShoppingDB.db"
            ).build()

    }
}