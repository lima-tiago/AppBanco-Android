package com.example.bancosaqueapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdaptadorDeLancamentos(val listaLancamentos: List<OperacaoFinanceira>): RecyclerView.Adapter<AdaptadorDeLancamentos.LancamentoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LancamentoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_lancamento,parent,false)
        return LancamentoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listaLancamentos.size
    }

    override fun onBindViewHolder(holder: LancamentoViewHolder, position: Int) {
        holder.bindLancamento(listaLancamentos[position])
    }

    class LancamentoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bindLancamento(operacaoFinanceira: OperacaoFinanceira) {
            itemView.findViewById<TextView>(R.id.txtSaldoAtual).text = operacaoFinanceira.saldoDepois.toString()
        }

    }
}

