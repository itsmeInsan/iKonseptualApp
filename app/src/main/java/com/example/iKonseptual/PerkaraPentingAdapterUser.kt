package com.example.iKonseptual

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class PerkaraPentingAdapterUser(
    private val context: Context,
    private val dataList: List<DataPerkaraPenting>,
    private var onClickListener: OnClickListener? = null
) : RecyclerView.Adapter<PerkaraPentingAdapterUser.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val judulPerkaraTextView: TextView = itemView.findViewById(R.id.judulPerkaraTextView)
        val card: CardView = itemView.findViewById(R.id.card_Perkara_Penting)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_perkara_penting, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataPerkara = dataList[position]
        holder.judulPerkaraTextView.text = dataPerkara.Judul_Perkara
        holder.card.setOnClickListener {
            onClickListener?.onClick(position, dataPerkara)
            val intent = Intent(context, UdppActivity::class.java).apply {
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
