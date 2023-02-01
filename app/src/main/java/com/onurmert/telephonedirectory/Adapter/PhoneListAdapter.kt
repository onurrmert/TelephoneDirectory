package com.onurmert.telephonedirectory.Adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.onurmert.telephonedirectory.Model.PhoneModel
import com.onurmert.telephonedirectory.R
import com.onurmert.telephonedirectory.Room.PhoneDatabase
import com.onurmert.telephonedirectory.View.PhoneListFragmentDirections
import com.onurmert.telephonedirectory.databinding.PhoneListRowBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PhoneListAdapter(val phoneList: ArrayList<PhoneModel>)
    : RecyclerView.Adapter<PhoneListAdapter.PhoneViewHolder>()  {

    class PhoneViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val binding = PhoneListRowBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhoneViewHolder {
        val layout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.phone_list_row, parent, false)
        return PhoneViewHolder(layout)
    }

    override fun onBindViewHolder(holder: PhoneViewHolder, position: Int) {

        holder.binding.nameTextView.setText(phoneList.get(position).userName)
        holder.binding.phoneNumberTextView.setText(phoneList.get(position).phoneNumber)
        holder.binding.rowView.setOnClickListener {
            navigation(it, phoneList.get(position).id)
        }
        holder.binding.imageView.setOnClickListener {
            popup(it, holder.itemView.context, phoneList.get(position), position)
        }
    }

    override fun getItemCount(): Int {
        return phoneList.size
    }

    private fun navigation(view: View, id : Int){
        val direction = PhoneListFragmentDirections.actionPhoneListFragmentToPhoneDetailFragment(id)
        Navigation.findNavController(view).navigate(direction)
    }

    private fun popup(view: View, context: Context, phoneModel: PhoneModel, position: Int){

        val popupMenu = android.widget.PopupMenu(context, view)
        popupMenu.inflate(R.menu.menu_detail)

        popupMenu.setOnMenuItemClickListener {

            when(it.itemId){
                R.id.delete->{
                    delete(context, phoneList.get(position), position)
                }
                R.id.update->{
                    val direction = PhoneListFragmentDirections.actionPhoneListFragmentToBottomFragment(phoneModel.id)
                    Navigation.findNavController(view).navigate(direction)
                }
            }
            return@setOnMenuItemClickListener false
        }
        popupMenu.show()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun delete(context: Context, phoneModel: PhoneModel, position: Int){
        val alert = AlertDialog.Builder(context)
        alert.setMessage("Do you want to delete?")
        alert.setPositiveButton("Ok", DialogInterface.OnClickListener { dialogInterface, i ->
            CoroutineScope(Dispatchers.Main).launch {
                PhoneDatabase.getDatabase(context).phoneDao().delete(phoneModel.id)
            }
            phoneList.removeAt(position)
            notifyDataSetChanged()
        }).show()
    }
}