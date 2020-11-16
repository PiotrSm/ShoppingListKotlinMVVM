package com.homeworkshop.shoppinglist.data.repositories

import com.homeworkshop.shoppinglist.data.db.ShoppingDatabase
import com.homeworkshop.shoppinglist.data.db.entities.ShoppingItem

class ShoppingRepository(
    private val db:ShoppingDatabase) {

    suspend fun upstert(item:ShoppingItem) = db.getShoppingDao().upsert(item)

    suspend fun delete(item: ShoppingItem) = db.getShoppingDao().delete(item)

    fun geAllShoppingItems() = db.getShoppingDao().getAllShoppingItem()

}