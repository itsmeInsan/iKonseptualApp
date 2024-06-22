package com.example.iKonseptual

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PenyelidikanPenyidikanAdapter(
    private val dataList: List<DataPenyelidikanPenyidikan>,
    private  var onClickListener: OnClickListener? = null) : RecyclerView.Adapter<PenyelidikanPenyidikanAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val namaTextView: TextView = itemView.findViewById(R.id.name_textView)
        val jaksaTextView: TextView = itemView.findViewById(R.id.jaksa_textView)
        val dateTextView: TextView = itemView.findViewById(R.id.date_textView)
        // Add other views here if necessary
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_jadwal, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataPenyelidikanPenyidikan = dataList[position]
        holder.namaTextView.text = dataPenyelidikanPenyidikan.Nama
        holder.jaksaTextView.text = dataPenyelidikanPenyidikan.Jaksa_yang_melaksanakan
        holder.dateTextView.text = dataPenyelidikanPenyidikan.Waktu_Pelaksanaan
        // Bind other views here if necessary
        holder.itemView.setOnClickListener{
            onClickListener?.onClick(position, dataPenyelidikanPenyidikan)
        }
    }

    interface OnClickListener {
        fun onClick(position: Int, model: DataPenyelidikanPenyidikan)
    }
    override fun getItemCount() = dataList.size
}