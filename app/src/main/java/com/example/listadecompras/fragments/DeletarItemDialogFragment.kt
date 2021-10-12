package com.example.listadecompras.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.listadecompras.ListaCompraViewModel
import com.example.listadecompras.ListaCompraViewModelFactory
import com.example.listadecompras.R
import com.example.listadecompras.data.db.ListaComprasApplication
import kotlinx.coroutines.InternalCoroutinesApi
import java.lang.NullPointerException

@InternalCoroutinesApi
class DeletarItemDialogFragment: DialogFragment() {

    private val vieModel by viewModels<ListaCompraViewModel> {
        ListaCompraViewModelFactory((context?.applicationContext as ListaComprasApplication).database.itemDao())
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dialog = AlertDialog.Builder(requireContext())
        val inflater: LayoutInflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.delete_item_dialog, null)

        dialog.setView(dialogView)
        dialog.setTitle("Quer mesmo deletar este item?")
        dialog.setPositiveButton("Confirmar", DialogInterface.OnClickListener { dialog, which ->
           vieModel.deletarItem(arguments?.getLong("id")?:
           throw NullPointerException())
            dismiss()
        })
        dialog.setNegativeButton("Cancelar", DialogInterface.OnClickListener { dialog, which ->
            dismiss()
        })

        return dialog.create()
    }
}