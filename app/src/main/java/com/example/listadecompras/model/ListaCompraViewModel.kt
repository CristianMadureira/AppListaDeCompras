package com.example.listadecompras

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.listadecompras.data.db.ItemDao
import com.example.listadecompras.data.db.ItemEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class ListaCompraViewModel(private val itemDao: ItemDao) : ViewModel() {

    val flow = itemDao.getItens()


    val nome = MutableStateFlow("")
    val total = MutableStateFlow("")
    val quantidade = MutableStateFlow("")


    private fun inserirItem(item: ItemEntity) {
        viewModelScope.launch {
            itemDao.inserir(item)
        }
    }

    public fun deletarItem(id: Long) {
        viewModelScope.launch {
            itemDao.deletar(id)
        }
    }


    private fun getNovoItemEntrada(
        itemNome: String,
        itemPreco: String,
        itemQuantidade: String
    ): ItemEntity {
        return ItemEntity(
            nome = itemNome,
            valor = itemPreco.toDouble() * itemQuantidade.toInt(),
            quantidade = itemQuantidade.toInt()
        )
    }

    public fun addNovoItem(itemNome: String, itemPreco: String, itemQuantidade: String) {
        val novoItem = getNovoItemEntrada(itemNome, itemPreco, itemQuantidade)
        inserirItem(novoItem)
    }

    public fun entradaValida() = combine(nome, total, quantidade) { nome, total, quantidade ->
        !(nome.isBlank() || total.isBlank() || quantidade.isBlank())
    }

}


class ListaCompraViewModelFactory(private val itemDao: ItemDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListaCompraViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ListaCompraViewModel(itemDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }


}