package com.example.ubook.adapter

import android.os.Bundle
import android.service.autofill.OnClickAction
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.example.ubook.ProfileCommonUserActivity
import com.example.ubook.R
import com.example.ubook.data.CompanyUserData
import com.example.ubook.data.ServiceCompanyUserData
import com.example.ubook.fragments.AddServiceCompanyUserFragment
import com.example.ubook.fragments.SearchCommonUserFragment
import com.example.ubook.fragments.ServiceViewCommonUserFragment

//Chan  ge the reading action
class InactiveServiceAdapter (private val serviceList: ArrayList<ServiceCompanyUserData>) : RecyclerView.Adapter<InactiveServiceAdapter.InactiveServiceDataViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InactiveServiceDataViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_service,parent,false)
        return InactiveServiceDataViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: InactiveServiceDataViewHolder, position: Int) {
        val currentItem = serviceList[position]
        holder.serviceName.text = currentItem.place
        holder.countryName.text = currentItem.country
        holder.cityName.text = currentItem.city
        holder.currPlace = currentItem.place
        holder.currDescription = currentItem.description
        holder.currCountry = currentItem.country
        holder.currCity = currentItem.city
        holder.currAddress = currentItem.address
        holder.currWeekdays = currentItem.weekdays
        holder.currWeekend = currentItem.weekend
        holder.currContactUs = currentItem.contact
        holder.currMotivPhr = currentItem.motivationalPhrase
        holder.currStatus = currentItem.status.toString()

    }

    override fun getItemCount(): Int {
        return serviceList.size
    }

    class InactiveServiceDataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val serviceName : TextView = itemView.findViewById(R.id.serviceNameTv)
        val countryName : TextView = itemView.findViewById(R.id.countryTv)
        val cityName : TextView = itemView.findViewById(R.id.cityTv)
        var currPlace : String? = null
        var currDescription : String? = null
        var currCountry : String? = null
        var currCity : String? = null
        var currAddress : String? = null
        var currWeekdays : String? = null
        var currWeekend : String? = null
        var currContactUs : String? = null
        var currMotivPhr : String? = null
        var currStatus : String? = null

        init {
            itemView.setOnClickListener{ v: View ->
                //Message of service position (just 4 fun)
                Toast.makeText(itemView.context, "You clicked on item # ${position + 1}", Toast.LENGTH_SHORT).show()
                val activity = v.context as AppCompatActivity
                //declare instance you want to replace
                val serviceEditFragment = AddServiceCompanyUserFragment()
                //proceed to do the safe replacement
                val bundle = Bundle()
                bundle.putString("currPlace", currPlace)
                bundle.putString("currDescription", currDescription)
                bundle.putString("currCountry", currCountry)
                bundle.putString("currCity", currCity)
                bundle.putString("currAddress", currAddress)
                bundle.putString("currWeekdays", currWeekdays)
                bundle.putString("currWeekend", currWeekend)
                bundle.putString("currContactUs", currContactUs)
                bundle.putString("currMotivPhr", currMotivPhr)
                bundle.putString("currStatus", currStatus)
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

    fun addItem(i : Int, serviceData : ServiceCompanyUserData) {

        serviceList.add(i, serviceData)
        notifyDataSetChanged()
    }

    fun returnCurrentName(i: Int): String {
        val currentItem = serviceList[i]
        return currentItem.place.toString()
    }
}