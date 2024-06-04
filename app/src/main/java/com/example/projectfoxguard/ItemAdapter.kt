package com.example.projectfoxguard

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter(private val dataList: List<DataModel>,private val activity: EventsDisplay) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       /* val textViewEventId: TextView = itemView.findViewById(R.id.textViewEventId)
        val textViewEventName: TextView = itemView.findViewById(R.id.textViewEventName)
        val textViewEventType: TextView = itemView.findViewById(R.id.textViewEventType)
        val textViewEventLocation: TextView = itemView.findViewById(R.id.textViewEventLocation)
        val textViewEventDate: TextView = itemView.findViewById(R.id.textViewEventDate)
        val textViewEventDescription: TextView = itemView.findViewById(R.id.textViewEventDescription)
        */
       val eventButton: Button = itemView.findViewById(R.id.eventButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataModel = dataList[position]
        holder.eventButton.text = "${dataModel.eventName}\n${dataModel.eventDate}"
        holder.eventButton.setOnClickListener {
            val intent = Intent(activity, UpdateDeleteActivity::class.java).apply {
                putExtra("EVENT_ID", dataModel.eventId)
            }
            activity.startActivity(intent)

        }
        /* holder.textViewEventId.text = dataModel.eventId
        holder.textViewEventName.text = dataModel.eventName
        holder.textViewEventType.text = dataModel.eventType
        holder.textViewEventLocation.text = dataModel.eventLocation
        holder.textViewEventDate.text = dataModel.eventDate
        holder.textViewEventDescription.text = dataModel.eventDescription

        */

    }


    override fun getItemCount() = dataList.size
}

