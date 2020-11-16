package com.homeworkshop.shoppinglist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.homeworkshop.shoppinglist.data.db.ShoppingDatabase
import com.homeworkshop.shoppinglist.data.db.entities.ShoppingItem
import com.homeworkshop.shoppinglist.data.repositories.ShoppingRepository
import com.homeworkshop.shoppinglist.other.ShoppingItemAdapter
import com.homeworkshop.shoppinglist.ui.AddDialogListener
import com.homeworkshop.shoppinglist.ui.AddShoppingDialog
import com.homeworkshop.shoppinglist.ui.ShoppingViewModel
import com.homeworkshop.shoppinglist.ui.ShoppingViewModelFactory
import kotlinx.android.synthetic.main.activity_shopping.*

class ShoppingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping)

        //Bardzo zła praktyka inicjować baze danych, repozytorium i faktory w aktywności. Wtedy one są zależne.
        //Jeżeli zmienimy activity albo przeniesiemy to wtedy także trzeba zmieniać te elementy
        //Lepiej je stworzyć globalnie i wstrzyknąć. Wtedy będą niezależne od activity i jego zmian
        val database = ShoppingDatabase(this)
        val repository = ShoppingRepository(database)
        val factory = ShoppingViewModelFactory(repository)

        //Utworzyliśmy osobno factory aby móc przekazywać w parametrze obiekt repository do ViewModelu
        val viewModel = ViewModelProviders.of(this,factory).get(ShoppingViewModel::class.java)

        val adapter =  ShoppingItemAdapter(listOf(),viewModel)
        rvShoppingItems.layoutManager = LinearLayoutManager(this)
        rvShoppingItems.adapter = adapter

        viewModel.getAllShoppingItems().observe(this, Observer {
            adapter.items = it
            adapter.notifyDataSetChanged()
        })

        fab.setOnClickListener{
            AddShoppingDialog(this,
                object: AddDialogListener{
                    override fun onAddButtonClicked(item: ShoppingItem) {
                        viewModel.upsert(item)
                    }
                }
            ).show()
        }
    }
}