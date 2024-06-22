package com.aan.deteksimaling.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.aan.deteksimaling.R

sealed class AppScreen(@StringRes val title: Int, @DrawableRes val icon: Int, val route: String) {

    object Dashboard : AppScreen(R.string.dashboard, R.drawable.baseline_home_24, "nav_dashboard"){
        object Maling : AppScreen(R.string.maling, R.drawable.baseline_home_24, "maling")
        object Cctv : AppScreen(R.string.cctv, R.drawable.baseline_camera_24, "cctv")

    }

}