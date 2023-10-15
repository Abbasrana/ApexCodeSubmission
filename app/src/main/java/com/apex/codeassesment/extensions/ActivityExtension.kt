package com.apex.codeassesment.extensions

import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.apex.codeassesment.data.model.User
import com.apex.codeassesment.ui.details.DetailsActivity


fun AppCompatActivity.navigateDetails(user: User) {
    val putExtra = Intent(this, DetailsActivity::class.java).putExtra("saved-user-key", user)
    startActivity(putExtra)
}

fun AppCompatActivity.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun View.show() = View.VISIBLE.also { this.visibility = it }
fun View.hide() = View.GONE.also { this.visibility = it }