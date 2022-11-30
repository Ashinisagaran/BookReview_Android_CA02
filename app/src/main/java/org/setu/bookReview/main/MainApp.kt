package org.setu.bookReview.main

import android.app.Application
import org.setu.bookReview.models.BookReviewMemStore
import timber.log.Timber

class MainApp : Application() {

    //    val bookReviews = ArrayList<BookReviewModel>()
    val bookReviews = BookReviewMemStore()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Timber.i("Placemark started")
    }
}