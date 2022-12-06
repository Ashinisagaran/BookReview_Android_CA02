package org.setu.bookReview.main

import android.app.Application
import org.setu.bookReview.models.BookReviewMemStore
import timber.log.Timber

class MainApp : Application() {

    var bookReviews = BookReviewMemStore()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        bookReviews = BookReviewMemStore()
        Timber.i("Placemark started")
    }
}