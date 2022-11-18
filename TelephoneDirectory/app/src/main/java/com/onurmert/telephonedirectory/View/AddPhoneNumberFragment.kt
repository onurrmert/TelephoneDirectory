package com.onurmert.telephonedirectory.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.onurmert.telephonedirectory.Model.PhoneModel
import com.onurmert.telephonedirectory.ViewModel.AddPhoneViewModel
import com.onurmert.telephonedirectory.databinding.FragmentAddPhoneNumberBinding

class AddPhoneNumberFragment : Fragment() {

    private lateinit var binding: FragmentAddPhoneNumberBinding

    private lateinit var viewModel: AddPhoneViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAddPhoneNumberBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(AddPhoneViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()
        init()
    }

    private fun init(){
        binding.insertButton.setOnClickListener {
            insert(it)
        }
    }

    private fun insert(view: View){
        val name = binding.nameEdit.text.toString().trim()
        val number = binding.numberEdit.text.toString().trim()
        val email = binding.emailEdit.text.toString().trim()
        if (name != ""){
            if (number != ""){
                if (!email.equals("")){
                    viewModel.insert(PhoneModel(number,name, email), requireContext())
                    direction(view)
                }else{
                    viewModel.insert(PhoneModel(number,name, ""), requireContext())
                    direction(view)
                }
            }else{
                binding.numberEdit.setError("Fill in the blanks")
            }
        }else{
            binding.nameEdit.setError("Fill in the blanks")
        }
    }

    private fun direction(view: View){
        val directions = AddPhoneNumberFragmentDirections.actionAddPhoneNumberFragmentToPhoneListFragment()
        Navigation.findNavController(view).navigate(directions)
    }
}