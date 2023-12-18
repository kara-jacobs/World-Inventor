package com.androidatc.worldinventor

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView


class PlaceAdapter(private var places: List<Place>, context: Context) : RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder>() {

    private val db: PlaceDatabaseHelper = PlaceDatabaseHelper(context)

    class PlaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val nametxt: TextView = itemView.findViewById(R.id.nametxt)
        val weathertxt: TextView = itemView.findViewById(R.id.weathertxt)
        val terraintxt: TextView = itemView.findViewById(R.id.terraintxt)
        val loreboxtxt: TextView = itemView.findViewById(R.id.loreboxtxt)
        val updateButton: ImageView = itemView.findViewById(R.id.updateButton)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
    }

override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.place_item, parent, false)
    return PlaceViewHolder(view)
}

override fun getItemCount(): Int = places.size

override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
    val place = places[position]
    holder.nametxt.text = place.name
    holder.terraintxt.text = place.terrain
    holder.weathertxt.text = place.weather
    holder.loreboxtxt.text = place.lorebox

    holder.updateButton.setOnClickListener {
        val intent = Intent(holder.itemView.context, Update::class.java).apply{
            putExtra("place_id", place.id)
        }
        holder.itemView.context.startActivity(intent)
    }

    holder.deleteButton.setOnClickListener {
        db.deletePlace(place.id)
        refreshData(db.getAllPlaces())
        Toast.makeText(holder.itemView.context, "Location Deleted", Toast.LENGTH_SHORT).show()
    }
}

    fun refreshData(newPlaces: List<Place>){
        places = newPlaces
        notifyDataSetChanged()
    }
}
