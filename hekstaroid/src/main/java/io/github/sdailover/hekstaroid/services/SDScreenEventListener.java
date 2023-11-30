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
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.Display;
import android.view.OrientationEventListener;
import android.view.WindowManager;

import io.github.sdailover.hekstaroid.maps.SDScreenChangeListener;
import io.github.sdailover.hekstaroid.utils.SDDisplayMetrics;
import io.github.sdailover.hekstaroid.utils.SDScreenMode;

/**
 * Helper class for receiving notifications from the SensorManager when
 * the orientation or rotaion of the device has changed.
 *
 * Reference see {@link OrientationEventListener}
 * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
 *       and other contributors. See credits file.
 */
public abstract class SDScreenEventListener implements SDScreenChangeListener {
    private static final String TAG = "SDScreenEventListener";
    private static final boolean DEBUG = false;
    private static final boolean localLOGV = false;
    private int rotation = ROTATION_UNKNOWN;
    private SDScreenMode orientation = SDScreenMode.ORIENTATION_UNDEFINED;
    private SDDisplayMetrics displayMetrics = SCREEN_UNKNOWN;
    private SensorManager sensorManager;
    private WindowManager windowManager;
    private Configuration displayConfigs;
    private Display defaultDisplay;
    private boolean enabled = false;
    private int rate;
    private Sensor sensor;
    private SensorEventListener sensorEventListener;
    private SDScreenListener oldListener;
    public static final int ROTATION_UNKNOWN = -1;
    public static final int ORIENTATION_UNKNOWN = -1;
    public static final SDDisplayMetrics SCREEN_UNKNOWN = new SDDisplayMetrics();

    /**
     * Creates a new SDScreenEventListener.
     * Reference see {@link OrientationEventListener}
     * @param context for the SDScreenEventListener.
     */
    public SDScreenEventListener(Context context) {
        this(context, SensorManager.SENSOR_DELAY_NORMAL);
    }

    /**
     * Creates a new SDScreenEventListener.
     * Reference see {@link OrientationEventListener}
     *
     * @param context for the SDScreenEventListener.
     * @param rate at which sensor events are processed (see also
     * {@link android.hardware.SensorManager SensorManager}). Use the default
     * value of {@link android.hardware.SensorManager#SENSOR_DELAY_NORMAL
     * SENSOR_DELAY_NORMAL} for simple screen orientation change detection.
     *
     * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
     */
    public SDScreenEventListener(Context context, int rate) {
        this.sensorManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
        this.rate = rate;
        this.sensor = this.sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.displayMetrics = new SDDisplayMetrics();
        if (this.sensor != null) {
            // Create listener only if sensors do exist
            this.sensorEventListener = new SensorEventListenerImpl();
        }
        if (context.getApplicationContext() != null) {
            Context appContext = context.getApplicationContext();
            this.windowManager =
                    (WindowManager) appContext.getSystemService(Context.WINDOW_SERVICE);
            if (appContext.getResources() != null) {
                Resources resources = appContext.getResources();
                if (resources.getConfiguration() != null) {
                    displayConfigs =    resources.getConfiguration();
                }
            }
        } else {
            this.windowManager =
                    (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            if (context.getResources() != null) {
                Resources resources = context.getResources();
                if (resources.getConfiguration() != null) {
                    displayConfigs =    resources.getConfiguration();
                }
            }
        }

        if (this.windowManager.getDefaultDisplay() != null) {
            this.defaultDisplay = this.windowManager.getDefaultDisplay();
        }
    }

    void registerListener(SDScreenListener lis) {
        oldListener = lis;
    }

    /**
     * Enables the SDScreenEventListener so it will monitor the sensor and call
     * {@link #onScreenChanged} when the device orientation or rotation changes.
     *
     * Reference see {@link OrientationEventListener#enable()}
     */
    public void enable() {
        if (sensor == null) {
            Log.w(TAG, "Cannot detect sensors. Not enabled");
            return;
        }
        if (enabled == false) {
            if (localLOGV) Log.d(TAG, "SDScreenEventListener enabled");
            sensorManager.registerListener(sensorEventListener, sensor, rate);
            enabled = true;
        }
    }

    /**
     * Disables the SDScreenEventListener.
     *
     * Reference see {@link OrientationEventListener#disable()}
     */
    public void disable() {
        if (sensor == null) {
            Log.w(TAG, "Cannot detect sensors. Invalid disable");
            return;
        }
        if (enabled == true) {
            if (localLOGV) Log.d(TAG, "SDScreenEventListener disabled");
            sensorManager.unregisterListener(sensorEventListener);
            enabled = false;
        }
    }

    /**
     * Run SensorManager service for tracking SensorEvent to get rotation value.
     * After get rotation or orientation value of screen device. Get DisplayMetrics
     * for size and other screen attributes.
     *
     * <p>Reference see {@link OrientationEventListener}
     * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
     */
    class SensorEventListenerImpl implements SensorEventListener {
        private static final int _DATA_X = 0;
        private static final int _DATA_Y = 1;
        private static final int _DATA_Z = 2;

        public void onSensorChanged(SensorEvent event) {
            float[] values = event.values;
            Point displaySize = new Point(0,0);

            int newRotation = ROTATION_UNKNOWN;
            int newOrientation = ORIENTATION_UNKNOWN;
            float X = -values[_DATA_X];
            float Y = -values[_DATA_Y];
            float Z = -values[_DATA_Z];
            float magnitude = X * X + Y * Y;

            float tangle = 0f; float atan = 0f;
            // Don't trust the angle if the magnitude is small compared to the y value
            if (magnitude * 4 >= Z * Z) {
                float OneEightyOverPi = 57.29577957855f;
                float angle = (float) Math.atan2(-Y, X) * OneEightyOverPi;
                atan = (float) Math.atan2(-Y, X);
                tangle = angle;
                newRotation = 90 - (int) Math.round(angle);
                // normalize to 0 - 359 range
                while (newRotation >= 360) {
                    newRotation -= 360;
                }
                while (newRotation < 0) {
                    newRotation += 360;
                }
            }
            //noinspection deprecation
            if (oldListener != null) {
                //noinspection deprecation
                oldListener.onSensorChanged(Sensor.TYPE_ACCELEROMETER, event.values);
            }

            if (newRotation != rotation) {
                rotation = newRotation;
                newOrientation = displayConfigs.orientation;

                if (newOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                    orientation = SDScreenMode.ORIENTATION_LANDSCAPE;
                } else if (newOrientation == Configuration.ORIENTATION_PORTRAIT) {
                    orientation = SDScreenMode.ORIENTATION_PORTRAIT;
                } else {
                    orientation = SDScreenMode.ORIENTATION_UNDEFINED;
                }
                defaultDisplay.getMetrics(displayMetrics.metrics);
                defaultDisplay.getRealMetrics(displayMetrics.realMetrics);
                onScreenChanged(newRotation, orientation, displayMetrics);
            }
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }

    /**
     * Returns true if sensor is enabled and false otherwise
     */
    public boolean canDetectScreen() {
        return sensor != null;
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