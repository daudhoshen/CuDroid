package com.ismail.cu.util

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding

fun Activity.enableEdgeToEdgeXmlActivity(
    reserveNavInset: Boolean = true
) = runCatching {
    if (Build.VERSION.SDK_INT == Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
        window.enableEdgeToEdgeWindow(
            reserveNavInset = reserveNavInset,
            reserveImeInset = true
        )
    } else {
        (this as? AppCompatActivity)?.enableEdgeToEdge()
        window.enableEdgeToEdgeWindow(
            reserveNavInset = reserveNavInset,
            reserveImeInset = true
        )
    }
}.getOrElse {
    this.window.enableEdgeToEdgeWindow(
        reserveNavInset = reserveNavInset,
        reserveImeInset = true
    )
}

fun Activity.setupSystemBarsAppearance(
    isLightSystemBars: Boolean
) = this.window.setupSystemBarsAppearance(
    isLightSystemBars = isLightSystemBars
)

@Suppress("DEPRECATION")
private fun Window.enableEdgeToEdgeWindow(
    rootView: View = this.decorView,
    reserveNavInset: Boolean = true,
    reserveImeInset: Boolean = true
) = runCatching {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        isNavigationBarContrastEnforced = false
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.VANILLA_ICE_CREAM) {
            setDecorFitsSystemWindows(false)
            statusBarColor = Color.TRANSPARENT
            navigationBarColor = Color.TRANSPARENT
        }
        rootView.setOnApplyWindowInsetsListener { view, insets ->
            runCatching {
                val navInset = if (reserveNavInset) insets.getInsets(
                    WindowInsets.Type.systemBars() or
                            WindowInsets.Type.displayCutout()
                ).bottom else 0
                val systemBarsBottomHeight = navInset.coerceAtLeast(0)
                val imeInset = if (reserveImeInset) insets.getInsets(
                    WindowInsets.Type.ime()
                ).bottom else 0
                view.updatePadding(bottom = (systemBarsBottomHeight + imeInset).coerceAtLeast(0))
            }.getOrElse {
                Log.e(
                    "WindowInsets",
                    "Failed to apply window insets",
                    it
                )
            }
            insets
        }
    } else {
        WindowCompat.setDecorFitsSystemWindows(this, false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            isNavigationBarContrastEnforced = false
        }
        statusBarColor = Color.TRANSPARENT
        navigationBarColor = Color.TRANSPARENT
        ViewCompat.setOnApplyWindowInsetsListener(rootView) { view, insets ->
            runCatching {
                val navInset = if (reserveNavInset) insets.getInsets(
                    WindowInsetsCompat.Type.systemBars() or
                            WindowInsetsCompat.Type.displayCutout()
                ).bottom else 0
                val systemBarsBottomHeight = navInset.coerceAtLeast(0)
                val imeInset = if (reserveImeInset) insets.getInsets(
                    WindowInsetsCompat.Type.ime()
                ).bottom else 0
                view.updatePadding(bottom = (systemBarsBottomHeight + imeInset).coerceAtLeast(0))
            }.getOrElse {
                Log.e(
                    "WindowInsets",
                    "Failed to apply window insets",
                    it
                )
            }
            insets
        }
    }
}.getOrElse {
    Log.e(
        "WindowInsets",
        "Failed to initialize edge-to-edge window configuration",
        it
    )
}

@Suppress("DEPRECATION")
private fun Window.setupSystemBarsAppearance(
    isLightSystemBars: Boolean
) = runCatching {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.VANILLA_ICE_CREAM) {
            setDecorFitsSystemWindows(false)
        }
        isNavigationBarContrastEnforced = false
        insetsController?.let { controller ->
            val appearanceFlags = WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS or
                    WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS
            if (!isLightSystemBars) controller.setSystemBarsAppearance(
                appearanceFlags,
                appearanceFlags
            ) else controller.setSystemBarsAppearance(
                0,
                appearanceFlags
            )
        }
    } else {
        WindowCompat.setDecorFitsSystemWindows(this, false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            isNavigationBarContrastEnforced = false
        }
        WindowCompat.getInsetsController(this, decorView).apply {
            isAppearanceLightStatusBars = !isLightSystemBars
            isAppearanceLightNavigationBars = !isLightSystemBars
        }
    }
}.getOrElse {
    Log.e(
        "WindowInsets",
        "Failed to initialize system bars appearance",
        it
    )
}