package com.example.ubook.adapter

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
import com.example.ubook.fragments.SearchCommonUserFragment
import com.example.ubook.fragments.ServiceViewCommonUserFragment

//Chan  ge the reading action
class InactiveServiceAdapter (private val serviceList: ArrayList<CompanyUserData>) : RecyclerView.Adapter<InactiveServiceAdapter.InactiveServiceDataViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InactiveServiceDataViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_service,parent,false)
        return InactiveServiceDataViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: InactiveServiceDataViewHolder, position: Int) {
        val currentItem = serviceList[position]
        holder.boxName.text = currentItem.companyName
    }

    override fun getItemCount(): Int {
        return serviceList.size
    }

    class InactiveServiceDataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val boxName : TextView = itemView.findViewById(R.id.serviceNameTv)

        init {
            itemView.setOnClickListener{ v: View ->
                //Message of service position (just 4 fun)
                Toast.makeText(itemView.context, "You clicked on item # ${position + 1}", Toast.LENGTH_SHORT).show()
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

    fun addItem(i : Int, boxData : CompanyUserData) {

        serviceList.add(i, boxData)
        notifyDataSetChanged()
    }

    fun returnCurrentName(i: Int): String {
        val currentItem = serviceList[i]
        return currentItem.companyName.toString()
    }
}