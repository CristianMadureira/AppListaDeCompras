package com.example.listadecompras.activity

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listadecompras.ListaCompraViewModel
import com.example.listadecompras.ListaCompraViewModelFactory
import com.example.listadecompras.R
import com.example.listadecompras.adapter.ListaCompraAdapter
import com.example.listadecompras.data.db.ItemEntity
import com.example.listadecompras.data.db.ListaComprasApplication
import com.example.listadecompras.databinding.ActivityMainBinding
import com.example.listadecompras.fragments.AdicionarItemDialogFragment
import com.example.listadecompras.fragments.DeletarItemDialogFragment
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.random.Random
import androidx.recyclerview.widget.ListAdapter as ListAdapter1

@InternalCoroutinesApi
class MainActivity : AppCompatActivity(), ListaCompraAdapter.ClickItemListaListner {


    private val viewModel by viewModels<ListaCompraViewModel>{
        ListaCompraViewModelFactory((applicationContext as ListaComprasApplication).database.itemDao())
    }


    private lateinit var binding: ActivityMainBinding


    //Configurar adapter
    private val adapter: ListaCompraAdapter = ListaCompraAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Inflando o layout
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setAddButtonListener()
        observeItens()

        //Criando layoutManager
        val linearLayoutManager = LinearLayoutManager(applicationContext)

        //setando LayoutManager e adapter no Recycler
        binding.recyclerItens.layoutManager = linearLayoutManager
        binding.recyclerItens.setHasFixedSize(true)
        binding.recyclerItens.adapter = adapter

    }

    override fun onItemLongClickListener(itemId: Long) {
        val deleteItemFragment: DialogFragment = DeletarItemDialogFragment()
        deleteItemFragment.arguments = bundleOf("id" to itemId)
        deleteItemFragment.show(supportFragmentManager, "")

    }

    private fun setAddButtonListener() {
       binding.fabAdd.setOnClickListener {
           val addItem: DialogFragment = AdicionarItemDialogFragment()
           addItem.show(supportFragmentManager, "")
        }

    }


    private fun observeItens() {
        lifecycleScope.launch {
            viewModel.flow.collect {
            adapter.data = it
                binding.editTotalLista.text = "TOTAL: R$:${calcularTotalLista(it)}"
           }

        }
    }

    private fun calcularTotalLista(lista: List<ItemEntity>)= lista.sumOf { it.valor }
}