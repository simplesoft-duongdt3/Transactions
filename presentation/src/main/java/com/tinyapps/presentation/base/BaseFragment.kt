package com.tinyapps.presentation.base

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.tinyapps.presentation.base.navigation.NavigateAction
import com.tinyapps.presentation.base.navigation.NavigateViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

abstract class BaseFragment : Fragment() {
    private val navigateViewModel by sharedViewModel<NavigateViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate ${this::class.java.simpleName} ${this.hashCode()}")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated ${this::class.java.simpleName} ${this.hashCode()}")
    }

    override fun onSaveInstanceState(outState: Bundle) {}

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume ${this::class.java.simpleName} ${this.hashCode()}")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause ${this::class.java.simpleName} ${this.hashCode()}")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart ${this::class.java.simpleName} ${this.hashCode()}")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop ${this::class.java.simpleName} ${this.hashCode()}")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView ${this::class.java.simpleName} ${this.hashCode()}")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy ${this::class.java.simpleName} ${this.hashCode()}")

    }

    fun backPressed(): Boolean {
        Log.d(TAG, "backPressed ${this::class.java.simpleName} ${this.hashCode()}")
        val fragmentList = childFragmentManager.fragments

        var handled = false
        for (f in fragmentList) {
            if (f is BaseFragment) {
                handled = f.backPressed()

                if (handled) {
                    break
                }
            }
        }

        if (!handled) {
            Log.d(TAG, "onBackPressed ${this::class.java.simpleName} ${this.hashCode()}")
            return onBackPressed()
        }

        return handled
    }

    open fun onBackPressed(): Boolean {
        return false
    }

    fun navigateBack() {
        navigateViewModel.navigateBack()
    }

    fun navigateTo(action: NavigateAction.ToAction) {
        navigateViewModel.navigateTo(action)
    }

    companion object {
        private val TAG: String = BaseFragment::class.java.simpleName
    }
}