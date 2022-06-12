package com.bangkit.berbuah.ui.fragments

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bangkit.berbuah.R
import com.bangkit.berbuah.databinding.FragmentHomeBinding
import com.bangkit.berbuah.ui.activities.CameraActivity
import com.bangkit.berbuah.utils.Utils.rotateBitmap
import com.bangkit.berbuah.utils.Utils.uriToFile
import com.bangkit.berbuah.viewmodel.HomeViewModel
import java.io.File
import java.nio.ByteBuffer
import java.nio.ByteOrder


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var getFile: File? = null

    private var imageSize = 32

//    private lateinit var imageLabeler: ImageLabeler

    companion object {
        const val CAMERA_X_RESULT = 200
        private val REQUIRED_PERMISSIONS = arrayOf(android.Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.hide()

        getPermission()

//        imageLabeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS)

        binding.apply {
//
            btnCamera.setOnClickListener { startCamera() }
            btnGallery.setOnClickListener { startGallery() }
            btnDeteksi.setOnClickListener { startDetection() }
        }

//        binding.btnCamera.setOnClickListener(View.OnClickListener {
//            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//            startActivityForResult(cameraIntent, 3)
////            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
////                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
////                startActivityForResult(cameraIntent, 3)
////            } else {
////                requestPermissions(arrayOf(Manifest.permission.CAMERA), 100)
////            }
//        })
//        binding.btnGallery.setOnClickListener(View.OnClickListener {
//            val cameraIntent =
//                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//            startActivityForResult(cameraIntent, 1)
//        })

    }

//    private fun classifyImage(image: Bitmap) {
//        val model = Fruits.newInstance(requireContext())
//
//        // Creates inputs for reference.
//        val inputFeature0 =
//            TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
//        val byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3)
//        byteBuffer.order(ByteOrder.nativeOrder())
//        val intValues = IntArray(imageSize * imageSize)
//        image.getPixels(intValues, 0, image.width, 0, 0, image.width, image.height)
//        var pixel = 0
//        //iterate over each pixel and extract R, G, and B values. Add those values individually to the byte buffer.
//        for (i in 0 until imageSize) {
//            for (j in 0 until imageSize) {
//                val `val` = intValues[pixel++] // RGB
//                byteBuffer.putFloat((`val` shr 16 and 0xFF) * (1f / 1))
//                byteBuffer.putFloat((`val` shr 8 and 0xFF) * (1f / 1))
//                byteBuffer.putFloat((`val` and 0xFF) * (1f / 1))
//            }
//        }
//        inputFeature0.loadBuffer(byteBuffer)
//
//        // Runs model inference and gets result.
//        val outputs = model.process(inputFeature0)
//        val outputFeature0 = outputs.outputFeature0AsTensorBuffer
//        val confidences = outputFeature0.floatArray
//        // find the index of the class with the biggest confidence.
//        var maxPos = 0
//        var maxConfidence = 0f
//        for (i in confidences.indices) {
//            if (confidences[i] > maxConfidence) {
//                maxConfidence = confidences[i]
//                maxPos = i
//            }
//        }
//        val classes = arrayOf(
//            "Anggur",
//            "Buah Naga",
//            "Jeruk",
//            "Kelapa",
//            "Nanas",
//            "Pepaya",
//            "Stroberi")
//        binding.tvResult.text = classes[maxPos]
//
//        // Releases model resources if no longer used.
//        model.close()
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if (resultCode == AppCompatActivity.RESULT_OK) {
//            if (requestCode == 3) {
//                var image = data!!.extras!!["data"] as Bitmap
//                val dimension = Math.min(image!!.width, image.height)
//                image = ThumbnailUtils.extractThumbnail(image, dimension, dimension)
//                binding.previewImage.setImageBitmap(image)
//                image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false)
//                classifyImage(image)
//            } else {
//                val dat = data!!.data
//                var image = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, dat)
//                binding.previewImage.setImageBitmap(image)
//                image = Bitmap.createScaledBitmap(image!!, imageSize, imageSize, false)
//                classifyImage(image)
//            }
//        }
//        super.onActivityResult(requestCode, resultCode, data)
//    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    context,
                    getString(R.string.invalid_permission),
                    Toast.LENGTH_SHORT
                ).show()
                onDestroy()
            }
        }
    }

    private fun getPermission() {
        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                context as Activity,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }

    private fun startCamera() {
        launcherIntentCameraX.launch(Intent(context, CameraActivity::class.java))
//        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        registerForActivityResult(ActivityResultContracts.StartActivityForResult)
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = it.data?.getSerializableExtra("picture") as File
            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean

            getFile = myFile
            val result = rotateBitmap(
                BitmapFactory.decodeFile(getFile?.path),
                isBackCamera
            )

            binding.previewImage.setImageBitmap(result)
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            val selectedImg: Uri = it.data?.data as Uri
            val myFile = uriToFile(selectedImg, requireActivity())
            getFile = myFile
            binding.previewImage.setImageURI(selectedImg)
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private fun startDetection() {
        var hasil_deteksi = "Pepaya"
        binding.tvResult.text = hasil_deteksi
        binding.tvResult.setOnClickListener {  }

    }

//    private fun predctiImage(image: Bitmap) {
//        val model = Fruits.newInstance(context)
//
//        // Creates inputs for reference.
//        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
//        inputFeature0.loadBuffer(byteBuffer)
//
//        // Runs model inference and gets result.
//        val outputs = model.process(inputFeature0)
//        val outputFeature0 = outputs.outputFeature0AsTensorBuffer
//
//        // Releases model resources if no longer used.
//        model.close()
//    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}