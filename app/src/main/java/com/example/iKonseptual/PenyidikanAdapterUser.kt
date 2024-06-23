package com.example.iKonseptual

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class PenyidikanAdapterUser (
    private val context: Context,
    private val dataList: List<DataPenyelidikanPenyidikan>,
    private var onClickListener: OnClickListener? = null
) : RecyclerView.Adapter<PenyidikanAdapterUser.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val namaTextView: TextView = itemView.findViewById(R.id.name_textView)
        val jaksaTextView: TextView = itemView.findViewById(R.id.jaksa_textView)
        val dateTextView: TextView = itemView.findViewById(R.id.date_textView)
        val card: CardView = itemView.findViewById(R.id.card_Jadwal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_jadwal, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataPenyidikan = dataList[position]
        holder.namaTextView.text = dataPenyidikan.Nama
        holder.jaksaTextView.text = dataPenyidikan.Jaksa_yang_melaksanakan
        holder.dateTextView.text = dataPenyidikan.Waktu_Pelaksanaan
        // Bind other views here if necessary
        holder.card.setOnClickListener {
            onClickListener?.onClick(position, dataPenyidikan)
            val labelDetail = "Detail Jadwal Penyidikan"
            val role = 1
            val intent = Intent(context, RincianActivity::class.java).apply {
                putExtra("Nama", dataPenyidikan.Nama)
                putExtra("Perkara", dataPenyidikan.Perkara)
                putExtra("Id_Perkara", dataPenyidikan.no)
                putExtra("Waktu_Pelaksanaan", dataPenyidikan.Waktu_Pelaksanaan)
                putExtra("Tempat", dataPenyidikan.Tempat_Pelaksanaan)
                putExtra("Jaksa_yang_melaksanakan", dataPenyidikan.Jaksa_yang_melaksanakan)
                putExtra("Keperluan", dataPenyidikan.Keperluan)
                putExtra("title_d", labelDetail)
                putExtra("id", role)
            }
            context.startActivity(intent)
        }
    }

    interface OnClickListener {
        fun onClick(position: Int, model: DataPenyelidikanPenyidikan)
    }
    override fun getItemCount() = dataList.size
}