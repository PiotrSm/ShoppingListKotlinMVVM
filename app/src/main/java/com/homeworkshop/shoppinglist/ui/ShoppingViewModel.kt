package com.homeworkshop.shoppinglist.ui

import androidx.lifecycle.ViewModel
import com.homeworkshop.shoppinglist.data.db.entities.ShoppingItem
import com.homeworkshop.shoppinglist.data.repositories.ShoppingRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ShoppingViewModel(
    private val repository: ShoppingRepository
) : ViewModel(){

    fun upsert(item: ShoppingItem) = GlobalScope.launch {
        repository.upstert(item)
    }

    fun delete(item: ShoppingItem) = GlobalScope.launch {
        repository.delete(item)
    }

    fun getAllShoppingItems() = repository.geAllShoppingItems()
}