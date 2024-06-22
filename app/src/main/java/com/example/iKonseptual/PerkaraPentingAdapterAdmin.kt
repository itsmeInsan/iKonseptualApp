package com.example.iKonseptual

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class PerkaraPentingAdapterAdmin(
    private val context: Context,
    private val dataList: List<DataPerkaraPenting>,
    private var onClickListener: OnClickListener? = null
) : RecyclerView.Adapter<PerkaraPentingAdapterAdmin.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val judulPerkaraTextView: TextView = itemView.findViewById(R.id.judulPerkaraTextView)
        val id: TextView = itemView.findViewById(R.id.idPerkara)
        val card: CardView = itemView.findViewById(R.id.card_Perkara_Penting)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_perkara_penting, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataPerkara = dataList[position]
        holder.judulPerkaraTextView.text = dataPerkara.Judul_Perkara
        holder.id.text = dataPerkara.no.toString()
        holder.card.setOnClickListener {
            onClickListener?.onClick(position, dataPerkara)
            val intent = Intent(context, AdppActivity::class.java).apply {
                putExtra("Judul_Perkara", dataPerkara.Judul_Perkara)
                putExtra("Id_Perkara", dataPerkara.no)
                putExtra("Identitas_tersangka", dataPerkara.Identitas_tersangka)
                putExtra("Penahanan", dataPerkara.Penahanan)
                putExtra("Pasal", dataPerkara.Pasal)
                putExtra("Kasus_Posisi", dataPerkara.Kasus_Posisi)
            }
            context.startActivity(intent)
        }
    }

    interface OnClickListener {
        fun onClick(position: Int, model: DataPerkaraPenting)
    }

    override fun getItemCount() = dataList.size
}
