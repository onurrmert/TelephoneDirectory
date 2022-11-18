package com.onurmert.telephonedirectory.View

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.onurmert.telephonedirectory.Model.PhoneModel
import com.onurmert.telephonedirectory.ViewModel.UpdateViewModel
import com.onurmert.telephonedirectory.databinding.FragmentUpdateBottomBinding

class UpdateBottomFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentUpdateBottomBinding

    private lateinit var viewModel: UpdateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentUpdateBottomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(UpdateViewModel::class.java)
        viewModel.getPhoneModel(requireContext(), getID())
        setEditText()

        binding.updateButton.setOnClickListener {
            getPhoneModel()
        }
    }

    private fun getPhoneModel() : PhoneModel?{
        val name = binding.nameEdit.text.toString().trim()
        val number = binding.numberEdit.text.toString().trim()
        val email = binding.emailEdit.text.toString().trim()
        if (name != ""){
            if (number != ""){
                if (!email.equals("")){
                    viewModel.update(requireContext(), PhoneModel(getID(), number, name, email))
                    findNavController().popBackStack()
                }else{
                    viewModel.update(requireContext(), PhoneModel(getID(), number, name))
                    findNavController().popBackStack()
                }
            }else{
                binding.numberEdit.setError("Fill in the blanks")
            }
        }else{
            binding.nameEdit.setError("Fill in the blanks")
        }
        return null
    }

    private fun setEditText(){
        viewModel.phoneModel.observe(requireActivity(), Observer {
            item ->
            binding.nameEdit.setText(item.userName)
            binding.emailEdit.setText(item.email)
            binding.numberEdit.setText(item.phoneNumber)
        })
    }

    private fun getID() : Int{
        val bundle = arguments
        val args = PhoneDetailFragmentArgs.fromBundle(bundle!!)
        return args.id
    }
}