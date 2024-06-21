package com.example.iKonseptual

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PerkaraPentingAdapter(private val dataList: List<DataPerkaraPenting>) : RecyclerView.Adapter<PerkaraPentingAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val judulPerkaraTextView: TextView = itemView.findViewById(R.id.judulPerkaraTextView)
        // Add other views here if necessary
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_perkara_penting, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataPerkara = dataList[position]
        holder.judulPerkaraTextView.text = dataPerkara.Judul_Perkara
        // Bind other views here if necessary
    }

    override fun getItemCount() = dataList.size
}