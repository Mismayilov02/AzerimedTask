package com.mismayilov.core.managers

import android.os.Bundle

interface NavigationManager {
    fun bottomNavigationVisibility(isVisible: Boolean)
    fun navigateByNavigationName(navigationName: String, startDestination: String? =null)
    fun navigateByDirection(direction:Any)
    fun navigateWithBundle(navigationName: String, bundle: Bundle)
    fun back()
}