/*
 * Hekstaroid Android Libraries by StephanusDai Developer
 * @link http://sdailover.github.io/
 * @mail team@sdailover.web.id
 * @copyright Copyright (c) 2023 StephanusDai Developer
 * @license http://sdailover.github.io/license.html
 */

package io.github.sdailover.hekstaroid.services;

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
import android.content.Context;
import android.hardware.SensorListener;
import android.view.OrientationEventListener;
import android.view.OrientationListener;

import io.github.sdailover.hekstaroid.maps.SDScreenChangeListener;
import io.github.sdailover.hekstaroid.utils.SDDisplayMetrics;
import io.github.sdailover.hekstaroid.utils.SDScreenMode;

/**
 * Helper class for receiving notifications from the SensorManager when
 * the orientation or rotaion of the device has changed.
 *
 * @deprecated use {@link SDScreenEventListener} instead.
 *  This class internally uses the SDScreenEventListener.
 *
 * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
 *       and other contributors. See credits file.
 */
@Deprecated
public abstract class SDScreenListener  implements SensorListener, SDScreenChangeListener {
    private SDScreenEventListener screenEventListener;
    public static final int ROTATION_UNKNOWN = SDScreenEventListener.ROTATION_UNKNOWN;
    public static final int ORIENTATION_UNKNOWN = SDScreenEventListener.ORIENTATION_UNKNOWN;
    public static final SDDisplayMetrics SCREEN_UNKNOWN = SDScreenEventListener.SCREEN_UNKNOWN;

    /**
     * Creates a new SDScreenListener.
     * Reference see {@link android.view.OrientationListener}
     *
     * @param context for the SDScreenListener.
     */
    public SDScreenListener(Context context) {
        screenEventListener = new SDScreenEventListenerInternal(context);
    }

    /**
     * Creates a new SDScreenListener.
     * Reference see {@link android.view.OrientationListener}
     *
     * @param context for the OrientationListener.
     * @param rate at which sensor events are processed (see also
     * {@link android.hardware.SensorManager SensorManager}). Use the default
     * value of {@link android.hardware.SensorManager#SENSOR_DELAY_NORMAL
     * SENSOR_DELAY_NORMAL} for simple screen orientation change detection.
     */
    public SDScreenListener(Context context, int rate) {
        screenEventListener = new SDScreenEventListenerInternal(context, rate);
    }

    /**
     * Called class for receiving notifications from the SensorManager when
     * the orientation or rotaion of the device has changed.
     *
     * Reference see {@link android.view.OrientationListener}
     * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
     */
    class SDScreenEventListenerInternal extends SDScreenEventListener {
        SDScreenEventListenerInternal(Context context) {
            super(context);
        }

        SDScreenEventListenerInternal(Context context, int rate) {
            super(context, rate);
            // register so that onSensorChanged gets invoked
            registerListener(SDScreenListener.this);
        }

        public void onScreenChanged(int rotation, SDScreenMode orientation, SDDisplayMetrics displayMetrics) {
            SDScreenListener.this.onScreenChanged(rotation, orientation, displayMetrics);
        }
    }

    /**
     * Enables the OrientationListener so it will monitor the sensor and call
     * {@link #onScreenChanged} when the device orientation or rotation changes.
     *
     * Reference see {@link OrientationEventListener#enable()}
     */
    public void enable() {
        screenEventListener.enable();
    }

    /**
     * Disables the SDScreenListener.
     *
     * Reference see {@link OrientationEventListener#disable()}
     */
    public void disable() {
        screenEventListener.disable();
    }

    public void onAccuracyChanged(int sensor, int accuracy) {
    }

    public void onSensorChanged(int sensor, float[] values) {
        // just ignore the call here onOrientationChanged is invoked anyway
    }

    /**
     * Called when the device orientation or rotation has changed.
     * there are three resulting parameters, an orientation parameter in {@link SDScreenMode},
     * a rotation parameter in degrees ranging from 0 to 359, and a {@link SDDisplayMetrics}
     * parameter that determines the screen size.
     *
     * @param rotation The rotation of screen device has been changed.
     * @param orientation  The orientation of screen device has been changed.
     * @param displayMetrics The displayMetrics of screen device has been changed.
     * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
     */
    abstract public void onScreenChanged(int rotation, SDScreenMode orientation, SDDisplayMetrics displayMetrics);

}