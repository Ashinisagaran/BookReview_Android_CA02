package org.setu.bookReview.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BookReviewModel(var id: Long = 0,
                           var bookTitle: String = "",
                           var review: String = "",
                           var rating: Float = 0F,
                           var genre: String = "",
                           var stageOfReading: String = "",
                           var image: Uri = Uri.EMPTY,
                           var lat: Double = 0.0,
                           var lng: Double = 0.0,
                           var zoom: Float = 0f) : Parcelable

@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable
