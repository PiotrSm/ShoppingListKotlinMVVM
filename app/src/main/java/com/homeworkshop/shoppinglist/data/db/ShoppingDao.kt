package com.homeworkshop.shoppinglist.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.homeworkshop.shoppinglist.data.db.entities.ShoppingItem

@Dao
interface ShoppingDao {
    //te funkcje musza działać w wątkach - couritines, i dlatego aby je stosowac w wątkach musimy dodac słowo suspend
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(item:ShoppingItem)

    @Delete
    suspend fun delete(item: ShoppingItem)

    @Query(value = "SELECT * FROM shopping_items ORDER BY item_name")
    fun getAllShoppingItem(): LiveData<List<ShoppingItem>>
}