package com.example.listadecompras.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.listadecompras.R
import com.example.listadecompras.data.db.ItemEntity

class ListaCompraAdapter(val listner: ClickItemListaListner) : RecyclerView.Adapter<ListaCompraAdapter.MyViewHolder>() {


    var data = listOf<ItemEntity>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.adapter_lista, parent, false)
        return MyViewHolder(view, listner = listner)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = data[position]
        val res = holder.itemView.context.resources

        holder.itemNome.text = "${item.nome}"
        holder.itemTotal.text = "R$${item.valor.toString()}"
        holder.itemQuantidade.text = "${item.quantidade.toString()}"

        holder.itemView.setOnLongClickListener(){
            listner.onItemLongClickListener(data[position].id)
            true
        }
    }

    public fun calcularTotalItem(){

    }

    override fun getItemCount(): Int {
        return data.size
    }

    public interface ClickItemListaListner {

        fun onItemLongClickListener(itemId: Long)

    }

    public class MyViewHolder(itemView: View, listner: ClickItemListaListner) : RecyclerView.ViewHolder(itemView) {

        val itemNome: TextView = itemView.findViewById(R.id.editProduto)
        val itemTotal: TextView = itemView.findViewById(R.id.textTotal)
        val itemQuantidade: TextView = itemView.findViewById(R.id.editQuantidade)


    }
}