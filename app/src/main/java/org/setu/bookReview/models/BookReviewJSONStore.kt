package org.setu.bookReview.models

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import org.setu.bookReview.helpers.*
import timber.log.Timber
import java.lang.reflect.Type
import java.util.*

const val JSON_FILE = "bookReviews.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()
val listType: Type = object : TypeToken<ArrayList<BookReviewModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class BookReviewJSONStore(private val context: Context) : BookReviewStore {

    var bookReviews = mutableListOf<BookReviewModel>()

    init {
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<BookReviewModel> {
        return bookReviews
    }


    override fun update(bookReview: BookReviewModel) {
        var foundBookReview: BookReviewModel? = bookReviews.find { p -> p.id == bookReview.id }
        if (foundBookReview != null) {
            foundBookReview.bookTitle = bookReview.bookTitle
            foundBookReview.review = bookReview.review
            foundBookReview.rating = bookReview.rating
            foundBookReview.genre = bookReview.genre
            foundBookReview.stageOfReading = bookReview.stageOfReading
            foundBookReview.image = bookReview.image
            foundBookReview.lat = bookReview.lat
            foundBookReview.lng = bookReview.lng
            foundBookReview.zoom = bookReview.zoom
            logAll()
        }
        serialize()
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
        serialize()
    }

    override fun rate(bookReview: BookReviewModel) {
        var foundBookReview = findOneByName(bookReview.bookTitle!!)
        if (foundBookReview != null) {
            foundBookReview.bookTitle = bookReview.bookTitle
            foundBookReview.rating = bookReview.rating
        }
        serialize()
    }

    override fun review(bookReview: BookReviewModel) {
        var foundBookReview = findOneByName(bookReview.bookTitle!!)
        if (foundBookReview != null) {
            foundBookReview.bookTitle = bookReview.bookTitle
            foundBookReview.review = bookReview.review
        }
        serialize()
    }

    override fun delete(bookReview: BookReviewModel) {
        bookReviews.remove(bookReview)
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(bookReviews, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        bookReviews = gsonBuilder.fromJson(jsonString, listType)
    }

    private fun logAll() {
        bookReviews.forEach { Timber.i("$it") }
    }
}

class UriParser : JsonDeserializer<Uri>,JsonSerializer<Uri> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Uri {
        return Uri.parse(json?.asString)
    }

    override fun serialize(
        src: Uri?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }
}