package com.practice.movieshub.utils

import android.content.Context
import android.widget.Toast

object ToastUtils {

    fun shortToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

    }

    fun longToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()

    }
}