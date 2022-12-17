package org.setu.bookReview.activities

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.setu.bookReview.R
import org.setu.bookReview.adapters.BookReviewAdapter
import org.setu.bookReview.adapters.BookReviewListener
import org.setu.bookReview.databinding.ActivityBookReviewListBinding
import org.setu.bookReview.main.MainApp
import org.setu.bookReview.models.BookReviewModel



class SearchActivity : AppCompatActivity(), BookReviewListener, BottomNavigationView.OnNavigationItemSelectedListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityBookReviewListBinding
    lateinit var adapter: BookReviewAdapter

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
        adapter = binding.recyclerView.adapter as BookReviewAdapter

        val search = findViewById<SearchView>(R.id.search)

        val searchIcon = search.findViewById<ImageView>(androidx.appcompat.R.id.search_mag_icon)
        searchIcon.setColorFilter(Color.WHITE)

        val cancelIcon = search.findViewById<ImageView>(androidx.appcompat.R.id.search_close_btn)
        cancelIcon.setColorFilter(Color.WHITE)

        val textView = search.findViewById<TextView>(androidx.appcompat.R.id.search_src_text)
        textView.setTextColor(Color.WHITE)

        val navigationView = binding.bottomNavigationView
        navigationView.setOnNavigationItemSelectedListener(this)
        navigationView.getMenu().clear()
        navigationView.inflateMenu(R.menu.nav_menu)

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
               adapter.filter.filter(newText)
                return false
            }

        })

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.actionSearch -> {
                val launcherIntent = Intent(this, SearchActivity::class.java)
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

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){

            R.id.homeFragment->{
                val launcherIntent = Intent(this, BookReviewListActivity::class.java)
                getResult.launch(launcherIntent)
            }
//            R.id.mapFragment->{
//                val launcherIntent = Intent(this, MapActivity::class.java)
//                getResult.launch(launcherIntent)
//            }
            R.id.searchFragment->{
                val launcherIntent = Intent(this, SearchActivity::class.java)
                getResult.launch(launcherIntent)
            }
        }

        return super.onOptionsItemSelected(item)
    }
}
