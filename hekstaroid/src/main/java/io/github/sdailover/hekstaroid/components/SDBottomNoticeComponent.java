/*
 * Hekstaroid Android Libraries by StephanusDai Developer
 * @link http://sdailover.github.io/
 * @mail team@sdailover.web.id
 * @copyright Copyright (c) 2023 StephanusDai Developer
 * @license http://sdailover.github.io/license.html
 */

package io.github.sdailover.hekstaroid.components;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import io.github.sdailover.hekstaroid.content.SDBottomNoticeDialog;
import io.github.sdailover.hekstaroid.maps.SDDialogInterface;

/**
 * SDBottomNoticeComponent linking to Hekstaroid class.
 *
 * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
 *       and other contributors. See credits file.
 */
public class SDBottomNoticeComponent {
    private Activity app;
    private SDBottomNoticeDialog bottomNoticeDialog;

    public SDBottomNoticeComponent(Activity app) {
        this.app = app;
    }

    public SDBottomNoticeDialog Builder() {
        return new SDBottomNoticeDialog(app);
    }

    public void show() {
        bottomNoticeDialog = new SDBottomNoticeDialog(app);
        bottomNoticeDialog.show();
    }

    public void show(@NonNull String title) {
        bottomNoticeDialog = new SDBottomNoticeDialog(app);
        bottomNoticeDialog.setTitle(title);
        bottomNoticeDialog.show();
    }

    public void show(@NonNull String title, @NonNull String message) {
        bottomNoticeDialog = new SDBottomNoticeDialog(app);
        bottomNoticeDialog.setTitle(title);
        bottomNoticeDialog.setMessage(message);
        bottomNoticeDialog.show();
    }

    public void show(@NonNull String title, @NonNull String message,
                     @Nullable SDDialogInterface.OnBeforeDismissListener callback) {
        bottomNoticeDialog = new SDBottomNoticeDialog(app);
        bottomNoticeDialog.setTitle(title);
        bottomNoticeDialog.setMessage(message);
        bottomNoticeDialog.setOnBeforeDismissListener(callback);
        bottomNoticeDialog.show();
    }

    public void show(@NonNull String title, @NonNull String message, boolean forceExists) {
        bottomNoticeDialog = new SDBottomNoticeDialog(app);
        bottomNoticeDialog.setTitle(title);
        bottomNoticeDialog.setMessage(message);
        bottomNoticeDialog.setExistsAppOnDismiss(forceExists);
        bottomNoticeDialog.show();
    }

    public void show(@NonNull String title, @NonNull String message,
                     boolean forceExists, @Nullable SDDialogInterface.OnBeforeDismissListener callback) {
        bottomNoticeDialog = new SDBottomNoticeDialog(app);
        bottomNoticeDialog.setTitle(title);
        bottomNoticeDialog.setMessage(message);
        bottomNoticeDialog.setExistsAppOnDismiss(forceExists);
        bottomNoticeDialog.setOnBeforeDismissListener(callback);
        bottomNoticeDialog.show();
    }

    public void show(@NonNull String title, @NonNull String message,
                     @NonNull String iconResName) {
        bottomNoticeDialog = new SDBottomNoticeDialog(app);
        bottomNoticeDialog.setTitle(title);
        bottomNoticeDialog.setMessage(message);
        bottomNoticeDialog.setIconImage(iconResName);
        bottomNoticeDialog.show();
    }

    public void show(@NonNull String title, @NonNull String message,
                     @NonNull String iconResName, @NonNull String backgroundResName) {
        bottomNoticeDialog = new SDBottomNoticeDialog(app);
        bottomNoticeDialog.setTitle(title);
        bottomNoticeDialog.setMessage(message);
        bottomNoticeDialog.setIconImage(iconResName);
        bottomNoticeDialog.setBackground(backgroundResName);
        bottomNoticeDialog.show();
    }

    public void show(@NonNull String title, @NonNull String message,
                     @NonNull String iconResName,
                     @Nullable SDDialogInterface.OnBeforeDismissListener callback) {
        bottomNoticeDialog = new SDBottomNoticeDialog(app);
        bottomNoticeDialog.setTitle(title);
        bottomNoticeDialog.setMessage(message);
        bottomNoticeDialog.setIconImage(iconResName);
        bottomNoticeDialog.setOnBeforeDismissListener(callback);
        bottomNoticeDialog.show();
    }

    public void show(@NonNull String title, @NonNull String message,
                     @NonNull String iconResName, boolean forceExists) {
        bottomNoticeDialog = new SDBottomNoticeDialog(app);
        bottomNoticeDialog.setTitle(title);
        bottomNoticeDialog.setMessage(message);
        bottomNoticeDialog.setIconImage(iconResName);
        bottomNoticeDialog.setExistsAppOnDismiss(forceExists);
        bottomNoticeDialog.show();
    }

    public void show(@NonNull String title, @NonNull String message,
                     @NonNull String iconResName, boolean forceExists,
                     @Nullable SDDialogInterface.OnBeforeDismissListener callback) {
        bottomNoticeDialog = new SDBottomNoticeDialog(app);
        bottomNoticeDialog.setTitle(title);
        bottomNoticeDialog.setMessage(message);
        bottomNoticeDialog.setIconImage(iconResName);
        bottomNoticeDialog.setExistsAppOnDismiss(forceExists);
        bottomNoticeDialog.setOnBeforeDismissListener(callback);
        bottomNoticeDialog.show();
    }

    public void show(@NonNull String title, @NonNull String message,
                     @NonNull String iconResName, @NonNull String backgroundResName,
                     boolean forceExists) {
        bottomNoticeDialog = new SDBottomNoticeDialog(app);
        bottomNoticeDialog.setTitle(title);
        bottomNoticeDialog.setMessage(message);
        bottomNoticeDialog.setIconImage(iconResName);
        bottomNoticeDialog.setBackground(backgroundResName);
        bottomNoticeDialog.setExistsAppOnDismiss(forceExists);
        bottomNoticeDialog.show();
    }

    public void show(@NonNull String title, @NonNull String message,
                     @NonNull String iconResName, @NonNull String backgroundResName,
                     boolean forceExists, @Nullable SDDialogInterface.OnBeforeDismissListener callback) {
        bottomNoticeDialog = new SDBottomNoticeDialog(app);
        bottomNoticeDialog.setTitle(title);
        bottomNoticeDialog.setMessage(message);
        bottomNoticeDialog.setIconImage(iconResName);
        bottomNoticeDialog.setBackground(backgroundResName);
        bottomNoticeDialog.setExistsAppOnDismiss(forceExists);
        bottomNoticeDialog.setOnBeforeDismissListener(callback);
        bottomNoticeDialog.show();
    }
}
