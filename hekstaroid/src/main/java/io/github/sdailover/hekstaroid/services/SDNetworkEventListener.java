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
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.OrientationEventListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;

import io.github.sdailover.hekstaroid.maps.SDNetworkInterface;
import io.github.sdailover.hekstaroid.utils.SDDisplayMetrics;
import io.github.sdailover.hekstaroid.utils.SDNetworkManager;
import io.github.sdailover.hekstaroid.utils.SDNetworkType;

/**
 * Helper class to monitor network connectivity when 
 * network changes occur on the device.
 * 
 * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
 *       and other contributors. See credits file.
 */
public abstract class SDNetworkEventListener
        implements SDNetworkInterface.SDNetworkChangeListener {
    private static final String TAG = "SDNetworkEventListener";
    private static final boolean localLOGV = false;
    private boolean enabled = false;
    private int connectionType = CONNECTION_UNKNOWN;
    private Context context;
    private ConnectivityManager connectivityManager;
    private NetworkInfo activeNetworkInfo;
    private BroadcastReceiver broadcastReceiver;
    private ConnectivityManager.NetworkCallback networkCallbackListener;
    private SDNetworkListener oldListener;
    public static final int CONNECTION_UNKNOWN = -1;

    /**
     * Create instance a new SDNetworkEventListener.
     * @param context for the SDNetworkEventListener.
     * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
     */
    public SDNetworkEventListener(@NonNull Context context) {
        this.context = context;
        this.connectivityManager =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        this.activeNetworkInfo = this.connectivityManager.getActiveNetworkInfo();
        this.networkCallbackListener = new SDNetworkEventListener.SDNetworkCallbackImpl();
        this.broadcastReceiver = new SDNetworkEventListener.SDBroadcastReceiverImpl();
    }
    @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
    void registerListener(SDNetworkListener listener) {
        oldListener = listener;
    }


    /**
     * Enables the SDScreenEventListener so it will monitor the network and call
     * {@link #onNetworkChanged} when the device network or connection changes.
     * 
     * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
     */
    @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
    public void enable() {
        if (connectivityManager == null) {
            Log.w(TAG, "Cannot detect connectivityManager. Not enabled");
            return;
        }
        if (enabled == false) {
            if (localLOGV) Log.d(TAG, "SDNetworkEventListener enabled");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                connectivityManager.registerDefaultNetworkCallback(networkCallbackListener);
            } else {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
                context.registerReceiver(broadcastReceiver, intentFilter);
            }
            enabled = true;
        }
    }

    /**
     * Disables the SDScreenEventListener.
     *
     * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
     */
    @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
    public void disable() {
        if (connectivityManager == null) {
            Log.w(TAG, "Cannot detect connectivityManager. Invalid disable");
            return;
        }
        if (enabled == true) {
            if (localLOGV) Log.d(TAG, "SDNetworkEventListener disabled");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                connectivityManager.unregisterNetworkCallback(networkCallbackListener);
            } else {
                context.unregisterReceiver(broadcastReceiver);
            }
            enabled = false;
        }
    }

    /**
     * Run NetworkCallback service for tracking NetworkEvent to get connection value.
     * After get connection value of network device. Get Type of connection and
     * available value of connection.
     *
     * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
     */
    class SDNetworkCallbackImpl extends ConnectivityManager.NetworkCallback {
        /**
         * Called when the framework has a hard loss of the network or when the
         * graceful failure ends.
         *
         * @param network The {@link Network} lost.
         * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
         */
        @Override
        public void onLost(@NonNull Network network) {
            super.onLost(network);
            //noinspection deprecation
            if (oldListener != null) {
                //noinspection deprecation
                oldListener.onNetworkChanged(false, SDNetworkType.CONNECTION_NONE);
            }
            onNetworkChanged(false, SDNetworkType.CONNECTION_NONE);
        }

        /**
         * Called when the network the framework connected to for this request
         * changes capabilities but still satisfies the stated need.
         *
         * @param network The {@link Network} whose capabilities have changed.
         * @param networkCapabilities The new {@link android.net.NetworkCapabilities} for this
         *                            network.
         * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
         */
        @Override
        public void onCapabilitiesChanged(@NonNull Network network, @NonNull NetworkCapabilities networkCapabilities) {
            super.onCapabilitiesChanged(network, networkCapabilities);
            if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                //noinspection deprecation
                if (oldListener != null) {
                    //noinspection deprecation
                    oldListener.onNetworkChanged(true, SDNetworkType.CONNECTION_WIFI);
                }
                connectionType = ConnectivityManager.TYPE_WIFI;
                onNetworkChanged(true, SDNetworkType.CONNECTION_WIFI);
            } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                //noinspection deprecation
                if (oldListener != null) {
                    //noinspection deprecation
                    oldListener.onNetworkChanged(true, SDNetworkType.CONNECTION_CELLULAR);
                }
                onNetworkChanged(true, SDNetworkType.CONNECTION_CELLULAR);
            } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                //noinspection deprecation
                if (oldListener != null) {
                    //noinspection deprecation
                    oldListener.onNetworkChanged(true, SDNetworkType.CONNECTION_ETHERNET);
                }
                onNetworkChanged(true, SDNetworkType.CONNECTION_ETHERNET);
            } else {
                //noinspection deprecation
                if (oldListener != null) {
                    //noinspection deprecation
                    oldListener.onNetworkChanged(true, SDNetworkType.CONNECTION_UNDEFINED);
                }
                onNetworkChanged(true, SDNetworkType.CONNECTION_UNDEFINED);
            }
        }
    }

    /**
     * Handling BroadcastReceiver service for monitor network and get received
     * information of connection when the device network changes.
     *
     * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
     */
    class SDBroadcastReceiverImpl extends BroadcastReceiver {
        /**
         * This method is called when the BroadcastReceiver is receiving an Intent
         * broadcast.  During this time you can use the other methods on
         * BroadcastReceiver to view/modify the current result values.  This method
         * is always called within the main thread of its process, unless you
         * explicitly asked for it to be scheduled on a different thread using
         * {@see Context#registerReceiver(BroadcastReceiver,
         * IntentFilter, String, Handler)}. When it runs on the main
         * thread you should
         * never perform long-running operations in it (there is a timeout of
         * 10 seconds that the system allows before considering the receiver to
         * be blocked and a candidate to be killed). You cannot launch a popup dialog
         * in your implementation of onReceive().
         *
         * <p><b>If this BroadcastReceiver was launched through a &lt;receiver&gt; tag,
         * then the object is no longer alive after returning from this
         * function.</b> This means you should not perform any operations that
         * return a result to you asynchronously. If you need to perform any follow up
         * background work, schedule a {@link JobService} with
         * {@link JobScheduler}.
         * <p>
         * If you wish to interact with a service that is already running and previously
         * bound using {@see Context#bindService(Intent, ServiceConnection, int) bindService()},
         * you can use {@link #peekService}.
         *
         * <p>The Intent filters used in {@link Context#registerReceiver}
         * and in application manifests are <em>not</em> guaranteed to be exclusive. They
         * are hints to the operating system about how to find suitable recipients. It is
         * possible for senders to force delivery to specific recipients, bypassing filter
         * resolution.  For this reason, {@link #onReceive(Context, Intent) onReceive()}
         * implementations should respond only to known actions, ignoring any unexpected
         * Intents that they may receive.
         *
         * @param context The Context in which the receiver is running.
         * @param intent  The Intent being received.
         * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
         */
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager newConnectivityManager =
                    (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo newActiveNetworkInfo = newConnectivityManager.getActiveNetworkInfo();
            int newConnectionType = CONNECTION_UNKNOWN;
            if (newActiveNetworkInfo != null) {
                newConnectionType = newActiveNetworkInfo.getType();
                if (newConnectionType == ConnectivityManager.TYPE_ETHERNET) {
                    //noinspection deprecation
                    if (oldListener != null) {
                        //noinspection deprecation
                        oldListener.onNetworkChanged(true, SDNetworkType.CONNECTION_ETHERNET);
                    }
                    if (newConnectionType != connectionType) {
                        connectionType = ConnectivityManager.TYPE_ETHERNET;
                        onNetworkChanged(true, SDNetworkType.CONNECTION_ETHERNET);
                    }
                } else if (newActiveNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    //noinspection deprecation
                    if (oldListener != null) {
                        //noinspection deprecation
                        oldListener.onNetworkChanged(true, SDNetworkType.CONNECTION_WIFI);
                    }
                    if (newConnectionType != connectionType) {
                        connectionType = ConnectivityManager.TYPE_WIFI;
                        onNetworkChanged(true, SDNetworkType.CONNECTION_WIFI);
                    }
                } else if (newActiveNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                    //noinspection deprecation
                    if (oldListener != null) {
                        //noinspection deprecation
                        oldListener.onNetworkChanged(true, SDNetworkType.CONNECTION_CELLULAR);
                    }
                    if (newConnectionType != connectionType) {
                        connectionType = ConnectivityManager.TYPE_MOBILE;
                        onNetworkChanged(true, SDNetworkType.CONNECTION_CELLULAR);
                    }
                } else {
                    //noinspection deprecation
                    if (oldListener != null) {
                        //noinspection deprecation
                        oldListener.onNetworkChanged(true, SDNetworkType.CONNECTION_UNDEFINED);
                    }
                    if (newConnectionType != connectionType) {
                        connectionType = CONNECTION_UNKNOWN;
                        onNetworkChanged(true, SDNetworkType.CONNECTION_UNDEFINED);
                    }
                }
            } else {
                //noinspection deprecation
                if (oldListener != null) {
                    //noinspection deprecation
                    oldListener.onNetworkChanged(false, SDNetworkType.CONNECTION_NONE);
                }
                if (newConnectionType != connectionType) {
                    connectionType = CONNECTION_UNKNOWN;
                    onNetworkChanged(false, SDNetworkType.CONNECTION_NONE);
                }
            }
        }
    }

    /**
     * Returns true if connectivityManager is available and false otherwise
     */
    public boolean canDetectConnection() {
        return connectivityManager != null;
    }

    /**
     * Called when the device network or connection has changed.
     * there are three resulting parameters, a isAvailable parameter in boolean,
     * and a connectionType parameter in {@link SDNetworkType} that
     *
     * @param isAvailable the available connnection of network device has been changed.
     * @param connectionType the connectionType of network device has been changed.
     * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
     */
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    abstract public void onNetworkChanged(boolean isAvailable, @NonNull SDNetworkType connectionType);
}
