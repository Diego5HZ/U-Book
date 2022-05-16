package com.example.ubook.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.ubook.R
import com.example.ubook.data.ServiceCompanyUserData
import com.example.ubook.fragments.ServiceViewCommonUserFragment

//Chan  ge the reading action
class SearchDataAdapter (private val serviceList: ArrayList<ServiceCompanyUserData>) : RecyclerView.Adapter<SearchDataAdapter.ServiceDataViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceDataViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_service,parent,false)
        return ServiceDataViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ServiceDataViewHolder, position: Int) {
        val currentItem = serviceList[position]
        holder.serviceName.text = currentItem.place
    }

    override fun getItemCount(): Int {
        return serviceList.size
    }

    class ServiceDataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val serviceName : TextView = itemView.findViewById(R.id.serviceNameTv)

        init {
            itemView.setOnClickListener{ v: View ->
                //Message of service position (just 4 fun)
                Toast.makeText(itemView.context, "You clicked on item # ${position + 1}", Toast.LENGTH_SHORT).show()
                //declare the where the activity where the adapter is working (in the corresponding fragment)
                val activity = v.context as AppCompatActivity
                //declare instance you want to replace
                val serviceViewFragment = ServiceViewCommonUserFragment()
                //proceed to do the safe replacement
                activity.supportFragmentManager.beginTransaction()
                    .replace(R.id.commonUserFragmentContainer, serviceViewFragment).addToBackStack(null)
                    .commit()
                //get information of the service and send it to the opened fragment
//                getServiceInfo()
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