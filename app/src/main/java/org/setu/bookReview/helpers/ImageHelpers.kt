package org.setu.bookReview.helpers

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import org.setu.bookReview.R

fun showImagePicker(intentLauncher : ActivityResultLauncher<Intent>) {
    var chooseFile = Intent(Intent.ACTION_OPEN_DOCUMENT)
    chooseFile.type = "image/*"
    chooseFile = Intent.createChooser(chooseFile, R.string.select_book_image.toString())
    intentLauncher.launch(chooseFile)
}