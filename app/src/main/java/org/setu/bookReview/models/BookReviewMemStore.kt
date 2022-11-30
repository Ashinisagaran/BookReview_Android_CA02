package org.setu.bookReview.models

import timber.log.Timber.i
import kotlin.random.Random

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class BookReviewMemStore : BookReviewStore {

    val bookReviews = ArrayList<BookReviewModel>()

    override fun findAll(): List<BookReviewModel> {
        return bookReviews
    }

//    override fun create(bookReview: BookReviewModel) {
//        bookReview.id = getId()
//        bookReviews.add(bookReview)
//        logAll()
//    }

    override fun update(bookReview: BookReviewModel) {
//        var foundBookReview = findOneByName(bookReview.bookTitle!!)
        var foundBookReview: BookReviewModel? = bookReviews.find { p -> p.id == bookReview.id }
        if (foundBookReview != null) {
            foundBookReview.bookTitle = bookReview.bookTitle
            foundBookReview.review = bookReview.review
            foundBookReview.rating = bookReview.rating
            foundBookReview.genre = bookReview.genre
            foundBookReview.stageOfReading = bookReview.stageOfReading
            logAll()
        }
    }

    private fun logAll() {
        bookReviews.forEach { i("$it") }
    }

    override fun findAllGenres(): List<BookReviewModel> {
        return bookReviews.distinctBy { it.genre }
    }

    override fun findOneByName(name: String) : BookReviewModel? {
        var foundBookReview: BookReviewModel? = bookReviews.find { p -> p.bookTitle == name }
        return foundBookReview
    }

    override fun findStage(stage: String) : List<BookReviewModel> {
        return bookReviews.filter { p -> p.stageOfReading == stage }
    }

    override fun findGenre(genre: String) : List<BookReviewModel> {
        return bookReviews.filter { p -> p.genre == genre }
    }

    override fun findUsingSpecificName(name: String) : List<BookReviewModel> {
        return bookReviews.filter { p -> p.bookTitle.contains(name) }
    }

    override fun findOne(id: Long) : BookReviewModel? {
        var foundBookReview: BookReviewModel? = bookReviews.find { p -> p.id == id }
        return foundBookReview
    }

    override fun lookUpGenre(genre: String) : BookReviewModel? {
        var foundBookReview: BookReviewModel? = bookReviews.find { p -> p.genre == genre }
        return foundBookReview
    }

    override fun filterRating(rating: Int) : List<BookReviewModel> {
        return bookReviews.filter { p -> p.rating >= rating}
    }

    override fun sortRating() : List<BookReviewModel> {
        return bookReviews.sortedByDescending { p -> p.rating }
    }

    override fun create(bookReview: BookReviewModel) {
        bookReview.id = generateRandomId()
        bookReviews.add(bookReview)

    }

    private fun generateRandomId() : Long{
        return Random.nextLong(0, Long.MAX_VALUE)
    }

//    override fun update(bookReview: BookReviewModel) {
//        var foundBookReview = findOneByName(bookReview.bookTitle!!)
//        if (foundBookReview != null) {
//            foundBookReview.bookTitle = bookReview.bookTitle
//            foundBookReview.genre = bookReview.genre
//            foundBookReview.rating = bookReview.rating
//            foundBookReview.review = bookReview.review
//        }
//
//    }

    override fun rate(bookReview: BookReviewModel) {
        var foundBookReview = findOneByName(bookReview.bookTitle!!)
        if (foundBookReview != null) {
            foundBookReview.bookTitle = bookReview.bookTitle
            foundBookReview.rating = bookReview.rating
        }

    }

    override fun review(bookReview: BookReviewModel) {
        var foundBookReview = findOneByName(bookReview.bookTitle!!)
        if (foundBookReview != null) {
            foundBookReview.bookTitle = bookReview.bookTitle
            foundBookReview.review = bookReview.review
        }

    }

    override fun delete(bookReview: BookReviewModel) {
        bookReviews.remove(bookReview)

    }
}
