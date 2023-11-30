/*
 * Hekstaroid Android Libraries by StephanusDai Developer
 * @link http://sdailover.github.io/
 * @mail team@sdailover.web.id
 * @copyright Copyright (c) 2023 StephanusDai Developer
 * @license http://sdailover.github.io/license.html
 */

package io.github.sdailover.hekstaroid.maps;

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
import android.content.DialogInterface;

import androidx.annotation.InspectableProperty;
import androidx.annotation.Nullable;

import io.github.sdailover.hekstaroid.annotation.RemotableViewMethod;

/**
 * Interface that defines a dialog-type class that can be shown, dismissed, or
 * canceled, and may have buttons that can be clicked.
 * <p/>
 * is a derived class from {@link DialogInterface} with several additional
 * interfaces in it, such as the {@link SDDialogInterface.OnBeforeDismissListener}
 * interface which aims to be able to run some code before processing the
 * {@link DialogInterface.OnDismissListener} interface, void {@link #setCancelDismiss(boolean)}
 * and {@link #getCancelDismiss} which aims to handle other {@link DialogInterface}
 * so as to prevent the dismiss process which can miss the appearance
 * of the {@link android.app.Dialog} in the event.
 * <p/>
 * Extended and Reference by {@link DialogInterface}
 * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
 *       and other contributors. See credits file.
 */
public interface SDDialogInterface extends DialogInterface {
    /**
     * Get result from event {@link OnBeforeDismissListener} which will prevent
     * the dismissal process so that it can handle other code processes and
     * if there is a {@link android.app.Dialog} instance on
     * inside {@link OnBeforeDismissListener},
     * it can bring it up first and without skipping it.
     *
     * @return boolean the cancel or return to prosess {@link #dismiss()}.
     * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
     */
    boolean getCancelDismiss();

    /**
     * Set cancel or not {@link #dismiss()} when you want to proses run another
     * {@link DialogInterface} or {@link Thread} before dismissed.
     * <p/>
     * <div class="special reference">
     * <h3>Developer Guides</h3>
     * <p>To handle another {@link DialogInterface} or proses {@link Thread}, use
     * <code>setCancelDismiss(true)</code> in section
     * {@link OnBeforeDismissListener#onBeforeDismiss(SDDialogInterface)}. And then
     * <code>setCancelDismiss(false)</code> in section another
     * {@link DialogInterface} or proses {@link Thread}</p>
     * </div>
     *
     * @param canceled the set is true or false to prevent {@link #dismiss()}.
     * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
     */
    void setCancelDismiss(boolean canceled);

    /**
     * Set {@link OnBeforeDismissListener} to process run some code when the
     * dialog is dismissed.
     *
     * @param listener The {@link OnBeforeDismissListener} instance.
     * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
     */
    void setOnBeforeDismissListener(@Nullable OnBeforeDismissListener listener);

    /**
     * Interface used to allow the creator of a dialog to run some code when
     * before the dialog is dismissed.
     *
     * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
     */
    interface OnBeforeDismissListener {
        /**
         * This method will be invoked when before the dialog is dismissed.
         *
         * @param dialog the dialog that was dismissed will be passed into the
         *               method
         * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
         */
        void onBeforeDismiss(SDDialogInterface dialog);
    }
}
