package com.homeworkshop.shoppinglist.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.homeworkshop.shoppinglist.R
import com.homeworkshop.shoppinglist.data.db.ShoppingDatabase
import com.homeworkshop.shoppinglist.data.db.entities.ShoppingItem
import com.homeworkshop.shoppinglist.data.repositories.ShoppingRepository
import com.homeworkshop.shoppinglist.other.ShoppingItemAdapter
import kotlinx.android.synthetic.main.activity_shopping.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class ShoppingActivity : AppCompatActivity() , KodeinAware{
    //wstrzyknięcie obiektu factory przy pomocy kodein, tymsamym nie potrzebne jest tworzenie obiektow bazy,repository i factory w metodzie onCreate
    override val kodein by kodein()
    private val factory: ShoppingViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping)

        //Bardzo zła praktyka inicjować baze danych, repozytorium i faktory w aktywności. Wtedy one są zależne.
        //Jeżeli zmienimy activity albo przeniesiemy to wtedy także trzeba zmieniać te elementy
        //Lepiej je stworzyć globalnie i wstrzyknąć. Wtedy będą niezależne od activity i jego zmian
//        val database = ShoppingDatabase(this)
//        val repository = ShoppingRepository(database)
//        val factory = ShoppingViewModelFactory(repository)

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