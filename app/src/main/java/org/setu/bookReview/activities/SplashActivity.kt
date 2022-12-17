package org.setu.bookReview.activities


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.ProgressBar
import org.setu.bookReview.R
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.time.DurationUnit


class SplashActivity : Activity() {
     lateinit var handler: Handler
    private lateinit var mProgress: ProgressBar

    private lateinit var timer: Timer
    private lateinit var progressBar: ProgressBar
    private var i = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splashfile)

        progressBar = findViewById(R.id.splash_screen_progress_bar)
        progressBar.progress = 0;

        handler=Handler()
        handler!!.postDelayed({
        val intent = Intent(this@SplashActivity, RegistrationActivity::class.java)
        startActivity(intent)
        finish()
        }, 3000)

    }
}