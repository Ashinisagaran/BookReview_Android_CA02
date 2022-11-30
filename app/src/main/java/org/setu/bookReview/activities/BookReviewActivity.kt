package org.setu.bookReview.activities


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.RatingBar
import android.widget.RatingBar.OnRatingBarChangeListener
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.setu.bookReview.R
import org.setu.bookReview.databinding.ActivityBookReviewBinding
import org.setu.bookReview.helpers.showImagePicker
import org.setu.bookReview.main.MainApp
import org.setu.bookReview.models.BookReviewModel
import timber.log.Timber.i


class BookReviewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBookReviewBinding
    var bookReview = BookReviewModel()
    lateinit var app: MainApp
    var editFlag = false
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    private var ratingBar: RatingBar? = null
    private var tv: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var edit = false

        binding = ActivityBookReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp

        i("Book Review Activity started...")

        ratingBar = binding.ratingBar;
       // tv = findViewById(R.id.tv);

        if (intent.hasExtra("bookReview_edit")) {
            edit = true
            bookReview = intent.extras?.getParcelable("bookReview_edit")!!
            binding.bookTitle.setText(bookReview.bookTitle)
            binding.review.setText(bookReview.review)
           // binding.ratingBar.setText(bookReview.rating)
            binding.ratingBar.rating=bookReview.rating
            binding.genre.setText(bookReview.genre)
            binding.stageOfReading.setText(bookReview.stageOfReading)
            binding.btnAdd.setText(R.string.button_saveBook)
            Picasso.get()
                .load(bookReview.image)
                .into(binding.bookImage)
            if (bookReview.image != Uri.EMPTY) {
                binding.chooseImage.setText(R.string.change_book_image)
            }

        }

        binding.btnAdd.setOnClickListener() {
            bookReview.bookTitle = binding.bookTitle.text.toString()
            bookReview.review = binding.review.text.toString()
            bookReview.rating = binding.ratingBar.rating
            bookReview.genre = binding.genre.text.toString()
            bookReview.stageOfReading = binding.stageOfReading.text.toString()
            if (bookReview.bookTitle.isEmpty()) {
                Snackbar.make(it,R.string.enter_title_message, Snackbar.LENGTH_LONG)
                    .show()
            } else {
                if (edit) {
                    app.bookReviews.update(bookReview.copy())
                } else {
                    app.bookReviews.create(bookReview.copy())
                }
            }
            i("add Button Pressed: $bookReview")
            setResult(RESULT_OK)
            finish()
        }

        ratingBar!!.onRatingBarChangeListener =
            OnRatingBarChangeListener { ratingBar, rating, fromUser ->
                Log.d("rating", ratingBar.rating.toString() + "")
            }



        binding.chooseImage.setOnClickListener {
            showImagePicker(imageIntentLauncher)
        }

        registerImagePickerCallback()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_book_review, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Result ${result.data!!.data}")
                            bookReview.image = result.data!!.data!!
                            Picasso.get()
                                .load(bookReview.image)
                                .into(binding.bookImage)
                            binding.chooseImage.setText(R.string.change_book_image)
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }

}