package com.example.listadecompras.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.listadecompras.ListaCompraViewModel
import com.example.listadecompras.ListaCompraViewModelFactory
import com.example.listadecompras.data.db.ListaComprasApplication
import com.example.listadecompras.databinding.DialogAdicionarItemBinding
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@InternalCoroutinesApi
class AdicionarItemDialogFragment : DialogFragment() {

    private lateinit var binding: DialogAdicionarItemBinding

    private val viewModel by viewModels<ListaCompraViewModel> {
        ListaCompraViewModelFactory((context?.applicationContext as ListaComprasApplication).database.itemDao())
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dialog = AlertDialog.Builder(requireContext())

        val inflater: LayoutInflater = layoutInflater
        binding = DialogAdicionarItemBinding.inflate(inflater)
        dialog.setView(binding.root)
        dialog.setTitle("Adicionar item")

        binding.buttonConfirmar.setOnClickListener {
            viewModel.addNovoItem(
                binding.editNome.text.toString(), binding.editPreco.text.toString(),
                binding.editQuantidade.text.toString()
            )
            dismiss()
        }

        binding.buttonCancelar.setOnClickListener {
            dismiss()
        }

        binding.editNome.addTextChangedListener {
            viewModel.nome.value = it.toString()
        }

        binding.editPreco.addTextChangedListener {
            viewModel.total.value = it.toString()
        }

        binding.editQuantidade.addTextChangedListener {
            viewModel.quantidade.value = it.toString()
        }

        lifecycleScope.launch {
            viewModel.entradaValida().collect {
                binding.buttonConfirmar.isEnabled = it
            }
        }
        return dialog.create()

    }


}