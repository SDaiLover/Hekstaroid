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
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;

import io.github.sdailover.hekstaroid.annotation.SuppressAutoDoc;

/**
 * SDDeviceManager is class utility that helps in device management,
 * such as managing permissions, getting imei, getting resources, and
 * others that require services from a device.
 *
 * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
 *       and other contributors. See credits file.
 */
public final class SDDeviceManager {
    /**
     * <p>Calls the basic {@link Activity} specified based on the {@link Context}
     *  whose Activity source is unknown. This is needed in several classes that
     *  really need activity space such as {@link android.view.Window} and others.
     *
     * @param context the context in which the dialog should run.
     * @return the {@link Activity} based on applied {@link Context}.
     *
     * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
     */
    public static final @Nullable Activity getActivity(@NonNull Context context)
    {
        if (context == null) {
            return null;
        } else if (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            } else {
                return getActivity(((ContextWrapper) context).getBaseContext());
            }
        }
        return null;
    }

    /**
     * Retrieves the application resource identity number by default package and type.
     *
     * @param context the context in which the dialog should run.
     * @param resName The name of the desired resource.
     *
     * @return int The associated resource identifier.  Returns 0 if no such
     *         resource was found.  (0 is not a valid resource ID.)
     *
     * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
     */
    @SuppressLint("DiscouragedApi")
    public static final @DrawableRes int getResourceID(@NonNull Context context, @NonNull String resName) {
        try {
            Context appContext = context.getApplicationContext();
            if (appContext != null) {
                String packageName = appContext.getPackageName();
                if (context.getResources() != null) {
                    Resources resources = context.getResources();
                    return resources.getIdentifier(resName, "drawable", packageName);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;
    }

    /**
     * Get Drawable of resource content by resouce NAME.
     * <p/>
     * Return a drawable object associated with a particular resource NAME and
     * styled for the specified theme. Various types of objects will be
     * returned depending on the underlying resource -- for example, a solid
     * color, PNG image, scalable image, etc.
     *
     * @param context the context in which the dialog should run.
     * @param resName The name of the desired resource.
     * @return {@link Drawable} of result from find this resource. Default is null.
     *
     * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
     */
    public static final @Nullable Drawable getDrawable(@NonNull Context context, @NonNull String resName) {
        try {
            Context appContext = context.getApplicationContext();
            if (appContext != null) {
                String packageName = appContext.getPackageName();
                if (context.getResources() != null) {
                    Resources resources = context.getResources();
                    @DrawableRes int resId =
                            resources.getIdentifier(resName, "drawable", packageName);
                    return resources.getDrawable(resId, context.getTheme());
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    /**
     * Get Drawable of resource content by resouce ID.
     * <p/>
     * Return a drawable object associated with a particular resource ID and
     * styled for the specified theme. Various types of objects will be
     * returned depending on the underlying resource -- for example, a solid
     * color, PNG image, scalable image, etc.
     *
     * @param context the context in which the dialog should run.
     * @param resId The desired resource identifier, as generated by the aapt
     *           tool. This integer encodes the package, type, and resource
     *           entry. The value 0 is an invalid identifier.
     * @return {@link Drawable} of result from find this resource. Default is null.
     *
     * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
     */
    public static final @Nullable Drawable getDrawable(@NonNull Context context, @DrawableRes int resId) {
        try {
            Context appContext = context.getApplicationContext();
            if (appContext != null) {
                String packageName = appContext.getPackageName();
                if (context.getResources() != null) {
                    Resources resources = context.getResources();
                    return resources.getDrawable(resId, context.getTheme());
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    /**
     * Get device imei number, to call it requires device permission
     * {@link android.app.Activity#requestPermissions(String[], int)}. Add also permission
     * at AndroidManifest.xml:
     *
     * <pre>
     *     &lt;uses-permission android:name=&#34;android.permission.READ_PRIVILEGED_PHONE_STATE&#34; &#47;&gt;
     * </pre>
     *
     * @param context the context in which the dialog should run.
     * @return int number ime of phone device. if not found, return default is null.
     *
     * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
     */
    @Deprecated
    @SuppressAutoDoc
    @SuppressLint("HardwareIds")
    @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
    public static final @Nullable  String getPhoneNumberIMEI(Context context) {
        String deviceUniqueIdentifier = null;
        TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        if (null != telephonyManager) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                //noinspection deprecation
                deviceUniqueIdentifier = telephonyManager.getDeviceId();
            } else {
                deviceUniqueIdentifier = telephonyManager.getImei();
            }
        }
        if (null == deviceUniqueIdentifier || 0 == deviceUniqueIdentifier.length()) {
            deviceUniqueIdentifier =
                    Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        return deviceUniqueIdentifier;
    }
}