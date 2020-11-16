package com.homeworkshop.shoppinglist.ui

import com.homeworkshop.shoppinglist.data.db.entities.ShoppingItem

interface AddDialogListener {
    fun onAddButtonClicked(item:ShoppingItem)
}