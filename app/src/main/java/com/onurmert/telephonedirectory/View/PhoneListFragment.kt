package com.onurmert.telephonedirectory.View

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.onurmert.telephonedirectory.Adapter.PhoneListAdapter
import com.onurmert.telephonedirectory.Model.PhoneModel
import com.onurmert.telephonedirectory.ViewModel.PhoneListViewModel
import com.onurmert.telephonedirectory.databinding.FragmentPhoneListBinding

class PhoneListFragment : Fragment() {

    private lateinit var binding: FragmentPhoneListBinding

    private lateinit var viewModel: PhoneListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPhoneListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(PhoneListViewModel::class.java)
        viewModel.getAll(requireContext())
    }

    override fun onResume() {
        super.onResume()

        getList()

        binding.floatingActionButton.setOnClickListener {
            val direction = PhoneListFragmentDirections.actionPhoneListFragmentToAddPhoneNumberFragment()
            Navigation.findNavController(it).navigate(direction)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun createRecycler(phoneList : List<PhoneModel>){
        val list = ArrayList<PhoneModel>()
        list.addAll(phoneList)

        binding.phoneListRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.phoneListRecyclerView.adapter = PhoneListAdapter(list)
    }

    private fun getList(){
        viewModel.phoneList.observe(requireActivity(), {
                item->
            createRecycler(item)
        })
    }
}