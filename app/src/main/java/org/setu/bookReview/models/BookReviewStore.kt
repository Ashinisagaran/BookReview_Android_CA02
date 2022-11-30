package org.setu.bookReview.models

interface BookReviewStore {
    fun findAll(): List<BookReviewModel>
    fun findAllGenres(): List<BookReviewModel>
    fun findOneByName(name: String): BookReviewModel?
    fun findStage(stage: String) : List<BookReviewModel>
    fun findGenre(genre: String) : List<BookReviewModel>
    fun findUsingSpecificName(name: String): List<BookReviewModel>
    fun findOne(id: Long): BookReviewModel?
    fun lookUpGenre(genre: String): BookReviewModel?
    fun filterRating(rating: Int) : List<BookReviewModel>
    fun sortRating () : List<BookReviewModel>
    fun create(bookReview: BookReviewModel)
    fun update(bookReview: BookReviewModel)
    fun rate(bookReview: BookReviewModel)
    fun review(bookReview: BookReviewModel)
    fun delete(bookReview: BookReviewModel)
}