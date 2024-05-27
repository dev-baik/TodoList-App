package com.example.todolist_app.presentation.register.ui

import android.app.Activity
import com.example.todolist_app.R
import com.example.todolist_app.databinding.ActivityCameraBinding
import com.example.todolist_app.presentation.base.BaseActivity
import com.example.todolist_app.presentation.register.Camera

class CameraActivity : BaseActivity<ActivityCameraBinding>(R.layout.activity_camera) {

    private val camera = Camera(this, Handler())

    override fun onCreateAction() {
        super.onCreateAction()
        camera.initCamera(binding.layoutPreview)
        setClickListener()
    }

    private fun setClickListener() = with(binding) {
        btnCapture.setOnClickListener {
            camera.takePhoto()
        }
    }

    inner class Handler {
        fun captureImageListener(uri: String) {
            intent.putExtra(IMAGE_URI, uri)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    companion object {
        const val IMAGE_URI = "image_uri"
    }
}
