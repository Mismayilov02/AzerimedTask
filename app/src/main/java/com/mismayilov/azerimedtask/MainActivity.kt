package com.mismayilov.azerimedtask

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mismayilov.core.managers.NavigationManager
import com.mismayilov.data.local.SharedPreferencesManager
import com.mismayilov.manager.LanguageManager
import com.mismayilov.uikit.util.getResourceIdByName
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() , NavigationManager {
    private lateinit var bottomNavigationView: BottomNavigationView
    private var navController: NavController? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        SharedPreferencesManager.init(this)
        checkLanguage()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkDarkMode()

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        navController = (supportFragmentManager.findFragmentById(
            R.id.fragmentContainerView
        ) as NavHostFragment).navController
        navController?.setGraph(R.navigation.base_navigation)
        bottomNavigationView.setupWithNavController(navController!!)
    }

    private fun checkLanguage() {
        val language = SharedPreferencesManager.getValue("languageCode", "en")
        LanguageManager.setLocale(this, language)
    }

    private fun checkDarkMode() {
        val darkMode = SharedPreferencesManager.getValue("darkMode", false)
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        val targetNightMode = if (darkMode) Configuration.UI_MODE_NIGHT_YES else Configuration.UI_MODE_NIGHT_NO

        if (currentNightMode != targetNightMode) {
            AppCompatDelegate.setDefaultNightMode(if (darkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    override fun bottomNavigationVisibility(isVisible: Boolean) {
        bottomNavigationView.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    override fun navigateByNavigationName(navigationName: String, startDestination: String?) {
        val res = getResourceIdByName(this, navigationName, "id")
        if (startDestination != null) {
            val startDestinationRes = getResourceIdByName(this, startDestination, "id")
            navController?.navigate(
                res,
                null,
                NavOptions.Builder().setPopUpTo(startDestinationRes, true).build()
            )
        } else navController?.navigate(res)
    }

    override fun navigateByDirection(direction: Any) {
        navController?.navigate(direction as NavDirections)
    }

    override fun navigateWithBundle(navigationName: String, bundle: Bundle) {
        val res = getResourceIdByName(this, navigationName, "id")
        navController?.navigate(res, bundle)
    }

    override fun back() {
        navController?.popBackStack()
    }


    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        Toast.makeText(this, navController?.currentDestination?.label, Toast.LENGTH_SHORT).show()
        Log.d("TAGONBACK", "onBackPressed: ${navController?.currentDestination?.label}")
        if (navController?.currentDestination?.id == com.mismayilov.home.R.id.homeFragment) {
            finishAffinity()
            System.exit(0)
        } else {
            super.onBackPressed()
        }
    }
}