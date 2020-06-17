package com.tinyapps.presentation.base

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.tinyapps.presentation.base.navigation.NavigateAction
import com.tinyapps.presentation.base.navigation.NavigateViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

abstract class BaseActivity : AppCompatActivity() {
    private val navigateViewModel by viewModel<NavigateViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate ${this::class.java.simpleName} ${this.hashCode()}")
        navigateViewModel.navigateLiveData.observe(this, Observer { action ->
            Log.d(TAG, "navigate ${this::class.java.simpleName} ${this.hashCode()} $action")
            when (action) {
                is NavigateAction.BackAction -> {
                    onNavigateBack()
                }
                is NavigateAction.ToAction -> {
                    onNavigateTo(action)
                }
            }
        })
    }

    open fun onNavigateTo(action: NavigateAction.ToAction) {

    }

    open fun onNavigateBack() {
        onBackPressed()
    }

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

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy ${this::class.java.simpleName} ${this.hashCode()}")
    }

    override fun onBackPressed() {
        Log.d(TAG, "onBackPressed ${this::class.java.simpleName} ${this.hashCode()}")
        val fragmentList = supportFragmentManager.fragments

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
            super.onBackPressed()
        }
    }

    companion object {
        private val TAG : String = BaseActivity::class.java.simpleName
    }
}