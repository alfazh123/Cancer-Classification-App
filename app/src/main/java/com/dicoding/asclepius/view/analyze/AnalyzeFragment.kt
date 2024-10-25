package com.dicoding.asclepius.view.analyze

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.dicoding.asclepius.data.local.entity.ClasificationEntity
import com.dicoding.asclepius.databinding.FragmentAnalyzeBinding
import com.dicoding.asclepius.helper.ImageClassifierHelper
import com.dicoding.asclepius.utils.ViewModelFactory
import com.dicoding.asclepius.view.ResultActivity
import com.dicoding.asclepius.view.ClasificationsViewModel
import org.tensorflow.lite.task.vision.classifier.Classifications
import java.text.NumberFormat
import java.util.Date
import kotlin.math.abs

class AnalyzeFragment : Fragment() {

    private var _binding: FragmentAnalyzeBinding? = null

    private val binding get() = _binding!!

//    private val viewModel: ClasificationsViewModel by viewModels<>()

    private val viewModel by viewModels<ClasificationsViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    private var currentImageUri: Uri? = null
    private var resultAnalyze: String = ""

    private lateinit var imageClassifierHelper: ImageClassifierHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAnalyzeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        if (!allPermissionsGranted()) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
//                requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
//            }else{
//                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
//            }
//        }

        binding.galleryButton.setOnClickListener { startGallery() }
        binding.analyzeButton.setOnClickListener {
            if (viewModel.currentImageUri.value != null) {
                analyzeImage()
            } else {
                showToast("No image selected")
            }
        }

        viewModel.currentImageUri.observe(viewLifecycleOwner) { uri ->
            if (uri!==null) {
                currentImageUri = uri
                showImage()
            }
        }
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            viewModel.updateCurrectImageUri(uri)
        } else {
            Log.d("Photo Picker", "No image selected")
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "Show Image: $it")
            binding.previewImageView.setImageURI(it)
        }
    }

    private fun analyzeImage() {
        imageClassifierHelper = ImageClassifierHelper(
            context = requireContext(),
            classifierListener = object: ImageClassifierHelper.ClassifierListener {
                override fun onError(error: String) {
                    activity?.runOnUiThread {
                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onResults(results: List<Classifications>?, inferenceTime: Long) {
                    activity?.runOnUiThread {
                        results?.let { it ->
                            if (it.isNotEmpty() && it[0].categories.isNotEmpty()) {
                                println(it)
                                val label = it[0].categories[0].label
                                val score = NumberFormat.getPercentInstance().format(it[0].categories[0].score).trim()
                                resultAnalyze = "$label ($score)"

                                // insert to database
//                                val count = viewModel.getAllClasification().value?.size?.plus(1) ?: 1

                                val id = Date().time.toInt()
                                val entity = ClasificationEntity(
                                    clasificationId = abs(id),
                                    imageUriClassification = currentImageUri.toString(),
                                    clasificationResult = resultAnalyze
                                )
                                viewModel.insertClassification(entity)

                                // move to result activity
                                moveToResult()
                            } else {
                                resultAnalyze = "No Result"
                                moveToResult()
                            }
                        }
                    }
                }


            }
        ).apply {
            classifyStaticImage(currentImageUri!!)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun moveToResult() {
        val intent = Intent(context, ResultActivity::class.java)
        intent.putExtra(ResultActivity.EXTRA_IMAGE_URI, currentImageUri.toString())
        intent.putExtra(ResultActivity.EXTRA_PREDICTION, resultAnalyze)
        startActivity(intent)
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}