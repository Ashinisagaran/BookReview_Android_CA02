package org.setu.bookReview.activities


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
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
import org.setu.bookReview.models.Location
import timber.log.Timber.i


class BookReviewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBookReviewBinding
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    var bookReview = BookReviewModel()
    lateinit var app: MainApp
    var editFlag = false
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    private var ratingBar: RatingBar? = null
    private var genre: TextView? = null
    private var stageOfReading: TextView? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var edit = false

        binding = ActivityBookReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp

        i("Book Review Activity started...")

        // get reference to the string array that we just created
        val genres = resources.getStringArray(R.array.select_genre)
        val stageOfReadings = resources.getStringArray(R.array.select_stage_of_reading)
        // create an array adapter and pass the required parameter
        // in our case pass the context, drop down layout , and array.
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_menu, genres)
        val arrayAdapter2 = ArrayAdapter(this, R.layout.dropdown_menu, stageOfReadings)
        // get reference to the autocomplete text view
        val autocompleteTV = findViewById<AutoCompleteTextView>(R.id.genre)
        val autocompleteTV2 = findViewById<AutoCompleteTextView>(R.id.stageOfReading)
        // set adapter to the autocomplete tv to the arrayAdapter
        autocompleteTV.setAdapter(arrayAdapter)
        autocompleteTV2.setAdapter(arrayAdapter2)

        ratingBar = binding.ratingBar
        genre = binding.genre
        stageOfReading = binding.stageOfReading

        if (intent.hasExtra("bookReview_edit")) {
            edit = true
            bookReview = intent.extras?.getParcelable("bookReview_edit")!!
            binding.bookTitle.setText(bookReview.bookTitle)
            binding.author.setText(bookReview.author)
            binding.review.setText(bookReview.review)
            binding.ratingBar.rating=bookReview.rating
            binding.genre.setText(bookReview.genre, false)
            binding.genre.setSelection(binding.genre.text.count()) // kotlin
            binding.stageOfReading.setText(bookReview.stageOfReading, false)
            binding.stageOfReading.setSelection(binding.stageOfReading.text.count())
            Picasso.get()
                .load(bookReview.image)
                .into(binding.bookImage)
        }

        binding.bookSave.setOnClickListener() {
            bookReview.bookTitle = binding.bookTitle.text.toString()
            bookReview.author = binding.author.text.toString()
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

        binding.imageAdd.setOnClickListener {
            showImagePicker(imageIntentLauncher,this)
        }

        binding.bookReviewLocation.setOnClickListener {
            val location = Location(52.245696, -7.139102, 15f)
            if (bookReview.zoom != 0f) {
                location.lat =  bookReview.lat
                location.lng = bookReview.lng
                location.zoom = bookReview.zoom
            }
            val launcherIntent = Intent(this, MapActivity::class.java)
            .putExtra("location", location)
            mapIntentLauncher.launch(launcherIntent)
        }

        registerImagePickerCallback()

        registerMapCallback()

    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Location ${result.data.toString()}")
                            val location = result.data!!.extras?.getParcelable<Location>("location")!!
                            i("Location == $location")
                            bookReview.lat = location.lat
                            bookReview.lng = location.lng
                            bookReview.zoom = location.zoom
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
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

                            val image = result.data!!.data!!
                            contentResolver.takePersistableUriPermission(image,
                                Intent.FLAG_GRANT_READ_URI_PERMISSION)
                            bookReview.image = image

                            Picasso.get()
                                .load(bookReview.image)
                                .into(binding.bookImage)
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }



}