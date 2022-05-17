package com.example.ubook.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.ubook.R
import com.example.ubook.data.CommonUserBookingData
import com.example.ubook.data.ServiceCompanyUserData
import com.example.ubook.fragments.AddServiceCompanyUserFragment
import com.example.ubook.fragments.ServiceViewCommonUserFragment
import kotlin.collections.ArrayList

//Chan ge the reading action
class BookingServiceAdapter (private val serviceList: ArrayList<CommonUserBookingData>) : RecyclerView.Adapter<BookingServiceAdapter.BookingServiceDataViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingServiceDataViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_service,parent,false)
        return BookingServiceDataViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BookingServiceDataViewHolder, position: Int) {
        val currentItem = serviceList[position]
        holder.serviceName.text = currentItem.place
        holder.currCountry = currentItem.country
        holder.currCity = currentItem.city
        holder.currAddress = currentItem.address
        holder.currDate = currentItem.date
        holder.currHour = currentItem.hour
        holder.currPlace = currentItem.place
    }

    override fun getItemCount(): Int {
        return serviceList.size
    }

    class BookingServiceDataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val serviceName : TextView = itemView.findViewById(R.id.serviceNameTv)
        var currCountry : String? = null
        var currCity : String? = null
        var currAddress : String? = null
        var currDate : String? = null
        var currHour : String? = null
        var currPlace : String? = null


        init {
            itemView.setOnClickListener{ v: View ->
                //Message of service position (just 4 fun)
                Toast.makeText(itemView.context, "You clicked on item # ${position + 1}", Toast.LENGTH_SHORT).show()
                val activity = v.context as AppCompatActivity
                //declare instance you want to replace
                val serviceEditFragment = ServiceViewCommonUserFragment()
                //proceed to do the safe replacement
                val bundle = Bundle()
                bundle.putString("currCountry", currCountry)
                bundle.putString("currCity", currCity)
                bundle.putString("currAddress", currAddress)
                bundle.putString("currDate", currDate)
                bundle.putString("currHour", currHour)
                bundle.putString("currPlace", currPlace)
                serviceEditFragment.arguments = bundle

                activity.supportFragmentManager.beginTransaction()
                    .replace(R.id.companyUserFragmentContainer, serviceEditFragment).addToBackStack(null)
                    .commit()
            }

        }

//        private fun getServiceInfo() {
//            TODO("Not yet implemented")
//        }
    }

    fun deleteItem(i : Int){
        serviceList.removeAt(i)
        notifyDataSetChanged()
    }

    fun addItem(i : Int, serviceData : CommonUserBookingData) {

        serviceList.add(i, serviceData)
        notifyDataSetChanged()
    }

    fun returnCurrentName(i: Int): String {
        val currentItem = serviceList[i]
        return currentItem.place.toString()
    }

}
