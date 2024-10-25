package com.dicoding.asclepius.view.history

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.data.local.entity.ClasificationEntity
import com.dicoding.asclepius.databinding.FragmentHistoryBinding
import com.dicoding.asclepius.utils.ViewModelFactory
import com.dicoding.asclepius.view.ClasificationsViewModel
import com.dicoding.asclepius.view.adapter.HistoryAdapter
import kotlinx.coroutines.launch

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!


    private val viewModel by viewModels<ClasificationsViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }

//    private val requestPermissionLauncher =
//        registerForActivityResult(
//            ActivityResultContracts.RequestPermission()
//        ) { isGranted: Boolean ->
//            if (isGranted) {
//                Toast.makeText(context, "Permission request granted", Toast.LENGTH_LONG).show()
//            } else {
//                Toast.makeText(context, "Permission request denied", Toast.LENGTH_LONG).show()
//            }
//        }
//
//    private fun allPermissionsGranted(): Boolean = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
//        ContextCompat.checkSelfPermission(
//            requireActivity(), Manifest.permission.READ_MEDIA_IMAGES
//        ) == PackageManager.PERMISSION_GRANTED
//    }else{
//        ContextCompat.checkSelfPermission(
//            requireActivity(),
//            Manifest.permission.READ_EXTERNAL_STORAGE
//        ) == PackageManager.PERMISSION_GRANTED
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvHistory.setHasFixedSize(true)



        val layoutManager = LinearLayoutManager(context)
        binding.rvHistory.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        binding.rvHistory.addItemDecoration(itemDecoration)

        lifecycleScope.launch {
            viewModel.getAllClasification().observe(viewLifecycleOwner) { history ->
                Log.d("HistoryFragment", "history: $history")
                binding.rvHistory.layoutManager = LinearLayoutManager(context)
                val listHistoryAdapter = HistoryAdapter()
                listHistoryAdapter.submitList(history)
                binding.rvHistory.adapter = listHistoryAdapter
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}