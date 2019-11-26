package com.raaziat.pagingexample.utils

import android.content.Context
import android.widget.Toast

 fun toast(status: String, context: Context) {
    Toast.makeText(context, status, Toast.LENGTH_SHORT).show()
}