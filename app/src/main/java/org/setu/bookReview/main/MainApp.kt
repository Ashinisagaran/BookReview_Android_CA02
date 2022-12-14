package org.setu.bookReview.main

import android.app.Application
import org.setu.bookReview.models.BookReviewJSONStore
import org.setu.bookReview.models.BookReviewStore

import timber.log.Timber

class MainApp : Application() {

    lateinit var bookReviews: BookReviewStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        bookReviews = BookReviewJSONStore(applicationContext)
        Timber.i("Book Review started")
    }
}