/*
 * Hekstaroid Android Libraries by StephanusDai Developer
 * @link http://sdailover.github.io/
 * @mail team@sdailover.web.id
 * @copyright Copyright (c) 2023 StephanusDai Developer
 * @license http://sdailover.github.io/license.html
 */

package io.github.sdailover.hekstaroid.utils;

/*
 * Hekstaroid Library Packages for Android by StephanusDai Developer
 * Email   : team@sdailover.web.id
 * Website : http://www.sdailover.web.id
 * (C) ID 2023 Stephanus Dai Developer (sdailover.github.io)
 * All rights reserved.
 *
 * Licensed under the Clause BSD License, Version 3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://sdailover.github.io/license.html
 *
 * This software is provided by the STEPHANUS DAI DEVELOPER and
 * CONTRIBUTORS "AS IS" and Any Express or IMPLIED WARRANTIES, INCLUDING,
 * BUT NOT LIMITED TO, the implied warranties of merchantability and
 * fitness for a particular purpose are disclaimed in no event shall the
 * Stephanus Dai Developer or Contributors be liable for any direct,
 * indirect, incidental, special, exemplary, or consequential damages
 * arising in anyway out of the use of this software, even if advised
 * of the possibility of such damage.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.google.android.material.appbar.AppBarLayout;

import io.github.sdailover.hekstaroid.app.SDActivity;
import io.github.sdailover.hekstaroid.services.SDScreenEventListener;

/**
 * Provides information about the size and density of a primary display.
 *
 * <p>A primary display does not necessarily represent a particular physical display
 * device such as the built-in screen or an external monitor. The contents of a primary
 * display may be presented on one or more physical displays according to the devices
 * that are currently attached and whether mirroring has been enabled.</p>
 *
 * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
 *       and other contributors. See credits file.
 */
public final class SDScreenManager {
    public static final int ROTATION_UNKNOWN = -1;

    /**
     * Request that the visibility of the status bar or other screen/window
     * decorations be changed.
     *
     * @param activity the {@link Activity} based on applied {@link Context}.
     * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
     */
    public static void hideSystemUI(@NonNull Activity activity) {
        hideSystemUI(activity, false, false);
    }

    /**
     * Request that the visibility of the status bar or other screen/window
     * decorations be changed.
     *
     * @param activity the {@link Activity} based on applied {@link Context}.
     * @param showAppBar the allowed for {@link AppBarLayout} to display.
     * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
     */
    public static void hideSystemUI(@NonNull Activity activity,
                                    Boolean showAppBar) {
        hideSystemUI(activity, showAppBar, false);

    }

    /**
     * Request that the visibility of the status bar or other screen/window
     * decorations be changed.
     *
     * @param activity the {@link Activity} based on applied {@link Context}.
     * @param showAppBar the allowed for {@link AppBarLayout} to display.
     * @param showNavBar the allowed for NavigationBar system to display.
     * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
     */
    public static void hideSystemUI(@NonNull Activity activity,
                                    Boolean showAppBar, Boolean showNavBar) {
        Window window = activity.getWindow();
        hideWindowUI(window, showAppBar, showNavBar);
    }

    /**
     * Request that the visibility of the status bar or other screen/window
     * decorations be changed.
     *
     * @param activity the {@link Activity} based on applied {@link Context}.
     * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
     */
    public static void showSystemUI(@NonNull Activity activity) {
        Window window = activity.getWindow();
        if (window != null) {
            showWindowUI(window);
        }
    }

    /**
     * Request that the visibility of the status bar or other screen/window
     * decorations be changed.
     *
     * @param window the window in which the dialog should run.
     * @param showAppBar the allowed for {@link AppBarLayout} to display.
     * @param showNavBar the allowed for NavigationBar system to display.
     * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
     */
    public static void hideWindowUI(@NonNull Window window,
                                    Boolean showAppBar, Boolean showNavBar) {
        View decorView = window.getDecorView();
        window.clearFlags(
                WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN
        );
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        if (Build.VERSION.SDK_INT >= 30) {
            WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(window, decorView);
            if (showAppBar == false && showNavBar == true) {
                WindowCompat.setDecorFitsSystemWindows(window, false);
                controller.hide(WindowInsetsCompat.Type.statusBars());
                controller.show(WindowInsetsCompat.Type.navigationBars());
            } else if (showAppBar == true && showNavBar == false) {
                WindowCompat.setDecorFitsSystemWindows(window, true);
                controller.hide(WindowInsetsCompat.Type.statusBars() |
                        WindowInsetsCompat.Type.navigationBars());
                controller.show(WindowInsetsCompat.Type.captionBar());
            } else if (showAppBar == true && showNavBar == true) {
                WindowCompat.setDecorFitsSystemWindows(window, true);
                controller.hide(WindowInsetsCompat.Type.statusBars());
                controller.show(WindowInsetsCompat.Type.captionBar());
                controller.show(WindowInsetsCompat.Type.navigationBars());
            } else {
                WindowCompat.setDecorFitsSystemWindows(window, false);
                controller.hide(WindowInsetsCompat.Type.statusBars() |
                        WindowInsetsCompat.Type.systemBars() |
                        WindowInsetsCompat.Type.captionBar() |
                        WindowInsetsCompat.Type.navigationBars());
            }
            controller.setSystemBarsBehavior(WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
        } else {
            if (showAppBar == false && showNavBar == true) {
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE |
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            } else if (showAppBar == true && showNavBar == false) {
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE |
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
            } else if (showAppBar == true && showNavBar == true) {
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE |
                        View.SYSTEM_UI_FLAG_FULLSCREEN);
            } else {
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE |
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
            }
        }
    }

    /**
     * Request that the visibility of the status bar or other screen/window
     * decorations be changed.
     *
     * @param window the window in which the dialog should run.
     * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
     */
    public static void showWindowUI(@NonNull Window window) {
        View decorView = window.getDecorView();
        window.clearFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN
        );

        if (Build.VERSION.SDK_INT >= 30) {
            WindowCompat.setDecorFitsSystemWindows(window, true);
            WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(window, decorView);
            controller.show(WindowInsetsCompat.Type.statusBars() |
                    WindowInsetsCompat.Type.captionBar() |
                    WindowInsetsCompat.Type.systemBars() |
                    WindowInsetsCompat.Type.navigationBars());
            controller.setSystemBarsBehavior(WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
        } else {
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        }
    }

    /**
     * Get the rotation value on the device screen.
     *
     * <p>The returned value may be {@link Surface#ROTATION_0 Surface.ROTATION_0}
     * (no rotation), {@link Surface#ROTATION_90 Surface.ROTATION_90},
     * {@link Surface#ROTATION_180 Surface.ROTATION_180}, or
     * {@link Surface#ROTATION_270 Surface.ROTATION_270}.  For
     * example, if a device has a naturally tall screen, and the user has
     * turned it on its side to go into a landscape orientation, the value
     * returned here may be either {@link Surface#ROTATION_90 Surface.ROTATION_90}
     * or {@link Surface#ROTATION_270 Surface.ROTATION_270} depending on
     * the direction it was turned.  The angle is the rotation of the drawn
     * graphics on the screen, which is the opposite direction of the physical
     * rotation of the device.  For example, if the device is rotated 90
     * degrees counter-clockwise, to compensate rendering will be rotated by
     * 90 degrees clockwise and thus the returned value here will be
     * {@link Surface#ROTATION_90 Surface.ROTATION_90}.</p>
     *
     * @param context the context in which the dialog should run.
     * @return the rotation of the screen from its "natural" orientation.
     * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
     */
    public static int getRotation(Context context) {
        int rotation = ROTATION_UNKNOWN;
        WindowManager windowManager =
                (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (context.getApplicationContext() != null) {
            Context appContext = context.getApplicationContext();
            windowManager = (WindowManager) appContext.getSystemService(Context.WINDOW_SERVICE);
        }

        if (windowManager.getDefaultDisplay() != null) {
            Display defaultDisplay = windowManager.getDefaultDisplay();
            rotation = defaultDisplay.getRotation();
        }
        return rotation;

    }

    /**
     * Gets display metrics based on the real size of this display.
     * <p>
     * The size is adjusted based on the current rotation of the display.
     * </p><p>
     * The real size may be smaller than the physical size of the screen when the
     * window manager is emulating a smaller display (using adb shell wm size).
     * </p>
     * @param context the context in which the dialog should run.
     * @return A {@link SDDisplayMetrics} object to receive the metrics.
     * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
     */
    public static SDDisplayMetrics getDisplayMetrics(Context context) {
        SDDisplayMetrics displayMetrics = new SDDisplayMetrics();
        WindowManager windowManager =
                (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (context.getApplicationContext() != null) {
            Context appContext = context.getApplicationContext();
            windowManager = (WindowManager) appContext.getSystemService(Context.WINDOW_SERVICE);
        }

        if (windowManager.getDefaultDisplay() != null) {
            Display defaultDisplay = windowManager.getDefaultDisplay();
            defaultDisplay.getMetrics(displayMetrics.metrics);
            defaultDisplay.getRealMetrics(displayMetrics.realMetrics);
        }
        return displayMetrics;
    }

    /**
     * Get Orientation device type, this method can be used when orientation is locked.
     * When if change orientation, then read last orientation display.
     * <p>To get the information when the screen change occurs, you can use and see
     * {@link SDScreenEventListener} or learn with see the implementation in {@link SDActivity}
     *
     * @param context the context in which the dialog should run.
     * @return the {@link SDScreenMode} result.
     * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
     */
    public static SDScreenMode getOrientation(Context context) {
        SDScreenMode orientation = SDScreenMode.ORIENTATION_UNDEFINED;
        Resources resources = context.getResources();
        if (context.getApplicationContext() != null) {
            Context appContext = context.getApplicationContext();
            resources = appContext.getResources();
        }
        if (resources.getConfiguration() != null) {
            Configuration configuration = resources.getConfiguration();
            if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                orientation = SDScreenMode.ORIENTATION_LANDSCAPE;
            } else if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                orientation = SDScreenMode.ORIENTATION_PORTRAIT;
            }
        }
        return orientation;
    }
}
