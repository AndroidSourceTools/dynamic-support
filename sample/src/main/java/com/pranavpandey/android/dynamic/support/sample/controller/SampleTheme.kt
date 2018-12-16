/*
 * Copyright 2018 Pranav Pandey
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pranavpandey.android.dynamic.support.sample.controller

import android.content.Context
import androidx.annotation.ColorInt
import androidx.annotation.StyleRes

import com.pranavpandey.android.dynamic.support.preference.DynamicPreferences
import com.pranavpandey.android.dynamic.support.sample.R
import com.pranavpandey.android.dynamic.support.theme.DynamicTheme
import com.pranavpandey.android.dynamic.support.theme.Theme
import com.pranavpandey.android.dynamic.support.utils.DynamicResourceUtils
import com.pranavpandey.android.dynamic.utils.DynamicColorUtils

/**
 * Helper class to perform theme related operations.
 */
object SampleTheme {

    /**
     * `true` if `auto` theme is selected.
     */
    val isAutoTheme: Boolean get() = appThemeColor == Theme.AUTO


    /**
     * Getter and Setter for the app theme color.
     */
    private var appThemeColor: Int
        get() = DynamicPreferences.getInstance().loadPrefs(
                Constants.PREF_SETTINGS_APP_THEME_COLOR,
                Constants.PREF_SETTINGS_APP_THEME_COLOR_DEFAULT)
        set(@ColorInt color) = DynamicPreferences.getInstance().savePrefs(
                Constants.PREF_SETTINGS_APP_THEME_COLOR, color)

    /**
     * Getter and Setter for the app theme day color.
     */
    private var appThemeDayColor: Int
        @ColorInt get() = DynamicPreferences.getInstance().loadPrefs(
                Constants.PREF_SETTINGS_APP_THEME_DAY_COLOR,
                Constants.PREF_SETTINGS_APP_THEME_DAY_COLOR_DEFAULT)
        set(@ColorInt color) = DynamicPreferences.getInstance().savePrefs(
                Constants.PREF_SETTINGS_APP_THEME_DAY_COLOR, color)

    /**
     * Getter and Setter for the app theme night color.
     */
    private var appThemeNightColor: Int
        @ColorInt get() = DynamicPreferences.getInstance().loadPrefs(
                Constants.PREF_SETTINGS_APP_THEME_NIGHT_COLOR,
                Constants.PREF_SETTINGS_APP_THEME_NIGHT_COLOR_DEFAULT)
        set(@ColorInt color) = DynamicPreferences.getInstance().savePrefs(
                Constants.PREF_SETTINGS_APP_THEME_NIGHT_COLOR, color)

    /**
     * The app theme style according to the current settings.
     */
    val splashStyle: Int
        @StyleRes get() = if (appThemeColor == Theme.AUTO) {
            getSplashStyle(if (DynamicResourceUtils.isNight())
                appThemeNightColor
            else
                appThemeDayColor)
        } else {
            getSplashStyle(appThemeColor)
        }

    /**
     * The app theme splash style according to the current settings.
     */
    val appStyle: Int
        @StyleRes get() = if (appThemeColor == Theme.AUTO) {
            getAppStyle(if (DynamicResourceUtils.isNight())
                appThemeNightColor
            else
                appThemeDayColor)
        } else {
            getAppStyle(appThemeColor)
        }

    /**
     * The background color according to the current settings.
     */
    private val backgroundColor: Int
        @ColorInt get() = if (appThemeColor == Theme.AUTO) {
            if (DynamicResourceUtils.isNight())
                appThemeNightColor
            else
                appThemeDayColor
        } else {
            appThemeColor
        }

    /**
     * Returns the app theme style according to the supplied color.
     *
     * @param color The color used for the background.
     *
     * @return The app theme style according to the supplied color.
     */
    @StyleRes private fun getAppStyle(@ColorInt color: Int): Int {
        return if (DynamicColorUtils.isColorDark(color))
            R.style.Sample
        else
            R.style.Sample_Light
    }

    /**
     *
     * Returns the app theme splash style according to the supplied color.
     *
     * @param color The color used for the background.
     *
     * @return The app theme splash style according to the supplied color.
     */
    @StyleRes private fun getSplashStyle(@ColorInt color: Int): Int {
        return if (DynamicColorUtils.isColorDark(color))
            R.style.Sample_Splash
        else
            R.style.Sample_Light_Splash
    }

    /**
     * Set the application theme according to the current settings.
     *
     * @param applicationContext The context to set the theme.
     */
    fun setApplicationTheme(applicationContext: Context) {
        @ColorInt val colorPrimary = SampleController.instance.colorPrimaryApp
        DynamicTheme.getInstance().application.setPrimaryColor(colorPrimary)
                .setPrimaryColorDark(DynamicColorUtils.shiftColor(
                        colorPrimary, DynamicTheme.ADS_COLOR_SHIFT_DARK_DEFAULT))
                .setAccentColor(SampleController.instance.colorAccentApp)
                .setBackgroundColor(backgroundColor).autoGenerateColors();
    }

    /**
     * Set the local theme according to the current settings.
     *
     * @param context The context to set the theme.
     */
    fun setLocalTheme(context: Context) {
        @ColorInt val colorPrimary = SampleController.instance.colorPrimaryApp
        DynamicTheme.getInstance().get().setPrimaryColor(colorPrimary)
                .setPrimaryColorDark(DynamicColorUtils.shiftColor(
                        colorPrimary, DynamicTheme.ADS_COLOR_SHIFT_DARK_DEFAULT))
                .setAccentColor(SampleController.instance.colorAccentApp)
                .setBackgroundColor(backgroundColor).autoGenerateColors()
    }
}
