package com.tinyapps.presentation.base

import android.view.View
import androidx.fragment.app.Fragment
import com.tinyapps.presentation.base.BaseFragment
import com.tinyapps.presentation.base.hideKeyboard
import com.tinyapps.presentation.base.inTransaction


fun BaseFragment.addFragment(fragment: BaseFragment, frameId: Int){
    childFragmentManager.inTransaction { add(frameId, fragment) }
}


fun BaseFragment.replaceFragment(fragment: BaseFragment, frameId: Int) {
    childFragmentManager.inTransaction{replace(frameId, fragment)}
}

fun Fragment.hideKeyboard(view: View) {
    this.activity?.let {
        view.hideKeyboard(it)
    }
}