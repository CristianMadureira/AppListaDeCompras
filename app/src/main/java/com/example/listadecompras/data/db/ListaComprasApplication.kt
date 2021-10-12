package com.example.listadecompras.data.db

import android.app.Application
import kotlinx.coroutines.InternalCoroutinesApi

class ListaComprasApplication : Application() {

    @InternalCoroutinesApi
    val database: ItemRoomDatabase by lazy {
        try {
            ItemRoomDatabase.getDataBase(this) }
        catch (error:Exception) {
            throw error
        }
        }

}