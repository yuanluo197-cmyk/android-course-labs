package com.example.lab03

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import java.io.FileNotFoundException

/** 相机由系统应用执行；本应用只接收返回的预览照片。 */
class CameraActivity : Activity() {
    private lateinit var photoView: ImageView
    private lateinit var statusText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        applySafeInsets()

        photoView = findViewById(R.id.photoView)
        statusText = findViewById(R.id.cameraStatus)
        restorePhoto()
        findViewById<Button>(R.id.takePhotoButton).setOnClickListener {
            try {
                @Suppress("DEPRECATION")
                startActivityForResult(Intent(MediaStore.ACTION_IMAGE_CAPTURE), CAMERA_REQUEST)
            } catch (_: ActivityNotFoundException) {
                statusText.setText(R.string.camera_no_app)
            }
        }
        findViewById<Button>(R.id.backButton).setOnClickListener { finish() }
    }

    @Deprecated("The platform callback matches the course's implicit camera example")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        @Suppress("DEPRECATION")
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode != CAMERA_REQUEST || resultCode != RESULT_OK) return

        @Suppress("DEPRECATION")
        val thumbnail = data?.extras?.get(getString(R.string.camera_thumbnail_key)) as? Bitmap
        if (thumbnail == null) {
            statusText.setText(R.string.camera_no_photo)
            return
        }
        photoView.setImageBitmap(thumbnail)
        statusText.setText(R.string.camera_photo_ready)
        openFileOutput(getString(R.string.camera_photo_filename), MODE_PRIVATE).use { output ->
            thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, output)
        }
    }

    private fun restorePhoto() {
        val bitmap = try {
            openFileInput(getString(R.string.camera_photo_filename)).use(BitmapFactory::decodeStream)
        } catch (_: FileNotFoundException) {
            null
        }
        if (bitmap != null) {
            photoView.setImageBitmap(bitmap)
            statusText.setText(R.string.camera_photo_ready)
        }
    }

    companion object {
        private const val CAMERA_REQUEST = 2
    }
}
