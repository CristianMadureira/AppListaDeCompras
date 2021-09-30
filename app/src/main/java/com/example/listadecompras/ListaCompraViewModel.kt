package com.example.listadecompras

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.listadecompras.data.db.ItemDao
import com.example.listadecompras.data.db.ItemEntity
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class ListaCompraViewModel(private val itemDao: ItemDao) : ViewModel() {

    private fun inserirItem(item: ItemEntity) {
        viewModelScope.launch {
            itemDao.inserir(item)
        }
        }

    private fun getNovoItemEntrada(itemNome: String, itemPreco: String, itemQuantidade: String): ItemEntity {
        return ItemEntity(
            nome = itemNome,
            valor = itemPreco.toDouble(),
            quantidade = itemQuantidade.toInt()
        )
    }

    public fun addNovoItem(itemNome: String, itemPreco: String, itemQuantidade: String) {
        val novoItem = getNovoItemEntrada(itemNome, itemPreco, itemQuantidade)
        inserirItem(novoItem)
    }

    }


class ListaCompraViewModelFactory(private val itemDao: ItemDao): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        TODO("Not yet implemented")
        if(modelClass.isAssignableFrom(ListaCompraViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ListaCompraViewModel(itemDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }


}