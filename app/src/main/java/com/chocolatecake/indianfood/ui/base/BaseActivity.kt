package com.chocolatecake.indianfood.ui.base
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.viewbinding.ViewBinding
import com.chocolatecake.indianfood.R

abstract class BaseActivity<VB: ViewBinding> : AppCompatActivity() {

    abstract val  LOG_TAG : String
    abstract val bindingInflater: (LayoutInflater) -> VB
    private var _binding: VB? = null

    protected val binding: VB
        get() = _binding as VB

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        installSplashScreen()
        super.onCreate(savedInstanceState)
        _binding = bindingInflater(layoutInflater)
        setUp()
        setContentView(requireNotNull(_binding).root)
        addCallbacks()
    }

    abstract fun setUp()
    abstract fun addCallbacks()

    protected fun log(value: Any){
        Log.v(LOG_TAG,value.toString())
    }

}