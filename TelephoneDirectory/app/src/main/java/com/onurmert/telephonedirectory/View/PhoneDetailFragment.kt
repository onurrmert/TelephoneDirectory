package com.onurmert.telephonedirectory.View

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.onurmert.telephonedirectory.Model.PhoneModel
import com.onurmert.telephonedirectory.ViewModel.PhoneDetailViewModel
import com.onurmert.telephonedirectory.databinding.FragmentPhoneDetailBinding
import java.util.*

class PhoneDetailFragment : Fragment() {

    private lateinit var viewModel: PhoneDetailViewModel

    private lateinit var binding: FragmentPhoneDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPhoneDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(PhoneDetailViewModel::class.java)
        viewModel.getPhoneModel(requireContext(), getID())
    }

    override fun onResume() {
        super.onResume()

        getPhoneModel()

        binding.updateButton.setOnClickListener {
            val d = PhoneDetailFragmentDirections.actionPhoneDetailFragmentToBottomFragment(getID())
            Navigation.findNavController(it).navigate(d)
        }
    }

    private fun callPermission(phoneModel: PhoneModel){
        try {
            if (ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CALL_PHONE) !=
                PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(
                        Manifest.permission.CALL_PHONE,
                    ), 2)
            }else{
                val callIntent = Intent(Intent.ACTION_CALL)
                callIntent.setData(Uri.parse("tel:${phoneModel.phoneNumber}"))
                startActivity(callIntent)
            }
        }catch (e: Exception){
            Log.d("error: ", e.localizedMessage)
            println(e.localizedMessage)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    @SuppressLint("SetTextI18n")
    private fun init(phoneModel: PhoneModel){
        binding.nameTextView.setText(phoneModel.userName.toUpperCase(Locale.ROOT))
        binding.numberTextView.setText("No: " + phoneModel.phoneNumber)
        if (!phoneModel.equals("")){
            binding.emailTextView.setText(phoneModel.email)
        }else{
            binding.emailTextView.visibility = View.GONE
        }
        binding.callButton.setOnClickListener {
            callPermission(phoneModel)
        }
    }

    private fun getPhoneModel(){
        viewModel.phoneModel.observe(requireActivity(), {
            item ->
                init(item)
        })
    }

    private fun getID() : Int{
        val bundle = arguments
        val args = PhoneDetailFragmentArgs.fromBundle(bundle!!)
        return args.id
    }
}