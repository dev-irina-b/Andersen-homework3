package android.example.homework3

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import java.net.URL
import kotlin.concurrent.thread

class LoadImageBaseActivity : AppCompatActivity() {

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
        thread {
            try {
                val url = URL(editText.text.toString())
                val stream = url.openStream()
                val result = BitmapFactory.decodeStream(stream)
                stream.close()
                runOnUiThread {
                    try {
                        imageView.setImageBitmap(result!!)
                        editText.setText("")
                    } catch (e: Exception) {
                        Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
                        imageView.setImageBitmap(null)
                    }
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
                    imageView.setImageBitmap(null)
                }
            }
        }
    }
}