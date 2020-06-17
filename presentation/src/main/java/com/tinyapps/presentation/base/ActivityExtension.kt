package com.tinyapps.presentation.base

import android.app.Activity
import android.content.res.Resources
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

inline fun FragmentManager.inTransactionToBackStack(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().addToBackStack(null).commit()
}

fun FragmentActivity.addFragment(fragment: BaseFragment, frameId: Int){
    if (supportFragmentManager.isStateSaved) {
        return
    }
    supportFragmentManager.inTransactionToBackStack() { add(frameId, fragment) }
}

fun FragmentActivity.replaceFragment(fragment: BaseFragment, frameId: Int) {
    if (supportFragmentManager.isStateSaved) {
        return
    }
    supportFragmentManager.inTransaction{replace(frameId, fragment)}
}

fun FragmentActivity.replaceFragmentToBackStack(fragment: BaseFragment, frameId: Int) {
    if (supportFragmentManager.isStateSaved) {
        return
    }
    supportFragmentManager.inTransactionToBackStack{replace(frameId, fragment)}
}

fun FragmentActivity.popFragment() {
    if (supportFragmentManager.isStateSaved) {
        return
    }
    supportFragmentManager.popBackStackImmediate()
}

fun FragmentActivity.addFragmentDialog(fragment: DialogFragment) {
    try {
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.add(fragment, "fragmentDialog")
        ft.commitAllowingStateLoss()
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
}

val Int.pxToDp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

val Int.dpToPx: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

val Double.dpToPx: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()


fun Activity.hideKeyboard(view: View) {
    view.hideKeyboard(this)
}

