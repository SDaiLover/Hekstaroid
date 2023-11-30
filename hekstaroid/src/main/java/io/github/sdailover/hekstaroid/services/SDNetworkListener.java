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
import android.content.Context;
import android.net.ConnectivityManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;

import io.github.sdailover.hekstaroid.maps.SDNetworkInterface;
import io.github.sdailover.hekstaroid.utils.SDDisplayMetrics;
import io.github.sdailover.hekstaroid.utils.SDNetworkType;
import io.github.sdailover.hekstaroid.utils.SDScreenMode;

/**
 * Helper class to monitor network connectivity when
 * network changes occur on the device.
 *
 * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
 *       and other contributors. See credits file.
 */
@Deprecated
public abstract class SDNetworkListener implements SDNetworkInterface.SDNetworkChangeListener {
    private SDNetworkEventListener networkEventListener;
    public static final int CONNECTION_UNKNOWN = SDNetworkEventListener.CONNECTION_UNKNOWN;

    public SDNetworkListener(Context context) {
        networkEventListener = new SDNetworkListener.SDNetworkEventListenerInternal(context);
    }

    class SDNetworkEventListenerInternal extends SDNetworkEventListener {
        SDNetworkEventListenerInternal(Context context) {
            super(context);
            // register so that onNetworkChanged gets invoked
            registerListener(SDNetworkListener.this);
        }

        public void onNetworkChanged(boolean isAvailable, @NonNull SDNetworkType connectionType) {
            SDNetworkListener.this.onNetworkChanged(isAvailable, connectionType);
        }
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
