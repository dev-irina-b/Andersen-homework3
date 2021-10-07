package android.example.homework3

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class LoadImageGlideActivity : AppCompatActivity() {

    private lateinit var editText: EditText
    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load_image)

        editText = findViewById(R.id.editText)
        imageView = findViewById(R.id.imageView)
        editText.imeOptions = EditorInfo.IME_ACTION_DONE

        setUpViews()
    }

    private fun setUpViews() {
        editText.setOnEditorActionListener { _, i, _ ->
            if (i == EditorInfo.IME_ACTION_DONE) {
                loadImage()
                editText.clearFocus()
            }
            false
        }
    }

    private fun loadImage() {
        Glide
            .with(this)
            .load(editText.text.toString())
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    Toast.makeText(this@LoadImageGlideActivity, e.toString(), Toast.LENGTH_LONG)
                        .show()
                    imageView.setImageBitmap(null)
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    editText.setText("")
                    return false
                }
            })
            .into(imageView)
    }
}