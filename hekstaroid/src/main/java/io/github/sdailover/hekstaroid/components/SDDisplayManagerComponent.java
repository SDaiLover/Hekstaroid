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

import io.github.sdailover.hekstaroid.utils.SDScreenManager;

/**
 * SDDisplayManagerComponent linking to Hekstaroid class.
 *
 * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
 *       and other contributors. See credits file.
 */
public class SDDisplayManagerComponent {
    private Activity app;

    public SDDisplayManagerComponent(Activity app) {
        this.app = app;
    }

    public void applyToFullScreen() {
        applyToFullScreen(false, false);
    }

    public void applyToFullScreen(Boolean showTitleBar) {
        applyToFullScreen(showTitleBar, false);
    }
    public void applyToFullScreen(Boolean showTitleBar, Boolean showNavigation) {
        SDScreenManager.hideSystemUI(app, showTitleBar, showNavigation);
    }

    public void applyToNormalScreen() {
        SDScreenManager.showSystemUI(app);
    }
}
