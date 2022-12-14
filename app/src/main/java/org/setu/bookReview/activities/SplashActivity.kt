package org.setu.bookReview.activities


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.ProgressBar
import org.setu.bookReview.R
import timber.log.Timber


class SplashActivity : Activity() {
    var handler: Handler? = null
    private var mProgress: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splashfile)

//        mProgress = (ProgressBar) findViewById(R.id.splash_screen_progress_bar)

        handler = Handler()
        handler!!.postDelayed({
            val intent = Intent(this@SplashActivity, BookReviewActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}