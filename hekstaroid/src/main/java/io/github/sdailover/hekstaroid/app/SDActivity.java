/*
 * Hekstaroid Android Libraries by StephanusDai Developer
 * @link http://sdailover.github.io/
 * @mail team@sdailover.web.id
 * @copyright Copyright (c) 2023 StephanusDai Developer
 * @license http://sdailover.github.io/license.html
 */

package io.github.sdailover.hekstaroid.app;

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
import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;

import io.github.sdailover.hekstaroid.maps.SDNetworkInterface;
import io.github.sdailover.hekstaroid.maps.SDScreenChangeListener;
import io.github.sdailover.hekstaroid.services.SDNetworkEventListener;
import io.github.sdailover.hekstaroid.services.SDScreenEventListener;
import io.github.sdailover.hekstaroid.utils.SDDisplayMetrics;
import io.github.sdailover.hekstaroid.utils.SDNetworkType;
import io.github.sdailover.hekstaroid.utils.SDScreenMode;

/**
 * An activity is a single, focused thing that the user can do.  Almost all
 * activities interact with the user, so the Activity class takes care of
 * creating a window for you in which you can place your UI with
 * {@link #setContentView}.
 *
 * <p>Add {@link SDScreenEventListener} with similiar
 * {@link Activity#onConfigurationChanged(Configuration)} implementation for get
 * size, rotation, orientation and other identity screen device when rotation or orientation
 * screen device has been changed.
 *
 * @see android.app.Activity
 * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
 *       and other contributors. See credits file.
 */
public class SDActivity extends Activity
        implements SDScreenChangeListener, SDNetworkInterface.SDNetworkChangeListener {
    SDScreenEventListener screenEventListener;
    SDNetworkEventListener networkEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        networkEventListener = new SDNetworkEventListener(this) {
            @Override
            public void onNetworkChanged(boolean isAvailable, @NonNull SDNetworkType connectionType) {
                SDActivity.this.onNetworkChanged(isAvailable, connectionType);
            }
        };
        screenEventListener = new SDScreenEventListener(this) {
            @Override
            public void onScreenChanged(int rotation, SDScreenMode orientation, SDDisplayMetrics displayMetrics) {
                SDActivity.this.onScreenChanged(rotation, orientation, displayMetrics);
            }
        };
    }

    @Override
    public void onScreenChanged(int rotation, SDScreenMode orientation, SDDisplayMetrics displayMetrics) { }

    @Override
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public void onNetworkChanged(boolean isAvailable, SDNetworkType connectionType) { }

    @Override
    protected void onStart() {
        super.onStart();
        if (networkEventListener != null) {
            if (networkEventListener.canDetectConnection()) {
                networkEventListener.enable();
            }
        }
        if (screenEventListener != null) {
            if (screenEventListener.canDetectScreen()) {
                screenEventListener.enable();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (networkEventListener != null) {
            if (networkEventListener.canDetectConnection()) {
                networkEventListener.disable();
            }
        }
        if (screenEventListener != null) {
            if (screenEventListener.canDetectScreen()) {
                screenEventListener.disable();
            }
        }
    }

}