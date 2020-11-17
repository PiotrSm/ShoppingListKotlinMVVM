package com.homeworkshop.shoppinglist

import android.app.Application
import com.homeworkshop.shoppinglist.data.db.ShoppingDatabase
import com.homeworkshop.shoppinglist.data.repositories.ShoppingRepository
import com.homeworkshop.shoppinglist.ui.ShoppingViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.androidCoreModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class ShoppingApplication : Application(), KodeinAware {
    override val kodein: Kodein =  Kodein.lazy {
        import(androidCoreModule(this@ShoppingApplication))
        bind() from singleton { ShoppingDatabase(instance()) }
        bind() from singleton { ShoppingRepository(instance()) }
        bind() from provider { ShoppingViewModelFactory(instance()) }
    }
}
