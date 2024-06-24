package com.example.iKonseptual

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class PenyelidikanAdapterUser(
    private val context: Context,
    private val dataList: List<DataPenyelidikanPenyidikan>,
    private var onClickListener: OnClickListener? = null
) : RecyclerView.Adapter<PenyelidikanAdapterUser.ViewHolder>() {

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
        val dataPenyelidikanPenyidikan = dataList[position]
        holder.namaTextView.text = dataPenyelidikanPenyidikan.Nama
        holder.jaksaTextView.text = dataPenyelidikanPenyidikan.Jaksa_yang_melaksanakan
        holder.dateTextView.text = dataPenyelidikanPenyidikan.Waktu_Pelaksanaan
        // Bind other views here if necessary
        holder.card.setOnClickListener {
            onClickListener?.onClick(position, dataPenyelidikanPenyidikan)
            val labelDetail = "Detail Jadwal Penyelidikan"
            val role = 1
            val intent = Intent(context, RincianActivity::class.java).apply {
                putExtra("Nama", dataPenyelidikanPenyidikan.Nama)
                putExtra("Perkara", dataPenyelidikanPenyidikan.Perkara)
                putExtra("Id_Perkara", dataPenyelidikanPenyidikan.no)
                putExtra("Waktu_Pelaksanaan", dataPenyelidikanPenyidikan.Waktu_Pelaksanaan)
                putExtra("Tempat", dataPenyelidikanPenyidikan.Tempat_Pelaksanaan)
                putExtra("Jaksa_yang_melaksanakan", dataPenyelidikanPenyidikan.Jaksa_yang_melaksanakan)
                putExtra("Keperluan", dataPenyelidikanPenyidikan.Keperluan)
                putExtra("title_d", labelDetail)
                putExtra("role", role)
            }
            context.startActivity(intent)
        }
    }

    interface OnClickListener {
        fun onClick(position: Int, model: DataPenyelidikanPenyidikan)
    }
    override fun getItemCount() = dataList.size
}