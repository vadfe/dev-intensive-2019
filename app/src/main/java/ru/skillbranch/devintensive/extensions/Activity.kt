package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.view.inputmethod.InputMethodManager
import android.opengl.ETC1.getHeight
import android.R.attr.bottom
import android.R.attr.top
import android.R.attr.bottom
import android.util.Log


fun Activity.hideKeyboard(){
    val view = this.currentFocus
    if (view != null) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun Activity.isKeyboardOpen():Boolean{
    val view = this.currentFocus
    val r = Rect()
    view.getRootView().getWindowVisibleDisplayFrame(r)
    val screenHeight = view.getRootView().getHeight()
    val heightDifference = screenHeight - (r.bottom - r.top)
    return if(heightDifference > (screenHeight / 3)) true else false
}

fun Activity.isKeyboardClosed():Boolean = !isKeyboardOpen()