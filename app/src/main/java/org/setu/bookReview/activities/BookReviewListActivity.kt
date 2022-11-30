package org.setu.bookReview.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import org.setu.bookReview.R
import org.setu.bookReview.adapters.BookReviewAdapter
import org.setu.bookReview.adapters.BookReviewListener
import org.setu.bookReview.databinding.ActivityBookReviewListBinding
import org.setu.bookReview.main.MainApp
import org.setu.bookReview.models.BookReviewModel


class BookReviewListActivity : AppCompatActivity(), BookReviewListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityBookReviewListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookReviewListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = BookReviewAdapter(app.bookReviews.findAll(),this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, BookReviewActivity::class.java)
                getResult.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                (binding.recyclerView.adapter)?.
                notifyItemRangeChanged(0,app.bookReviews.findAll().size)
            }
        }

    override fun onBookReviewClick(bookReview: BookReviewModel) {
        val launcherIntent = Intent(this, BookReviewActivity::class.java)
        launcherIntent.putExtra("bookReview_edit", bookReview)
        getClickResult.launch(launcherIntent)
    }

    private val getClickResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                (binding.recyclerView.adapter)?.
                notifyItemRangeChanged(0,app.bookReviews.findAll().size)
            }
        }
}
