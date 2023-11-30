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
import android.util.DisplayMetrics;

/**
 * A structure describing general information about a display, such as its
 * size, density, and font scaling. {@link DisplayMetrics} has two types of metrics,
 * namely {@link #realMetrics} and {@link #metrics}.
 * <p>To access {@link SDDisplayMetrics} members, apply object initialization
 * {@link DisplayMetrics} object initialization is implemented through two variables.
 * Where {@link #realMetrics} is used in
 * <code>getWindowManager().getDefaultDisplay().getRealMetrics(realMetrics);</code>
 * and while {@link #metrics} is used in
 * <code>getWindowManager().getDefaultDisplay().getMetrics(metrics);</code>
 *
 * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
 *       and other contributors. See credits file.
 */
public class SDDisplayMetrics {
    /**
     * To get a structure that describes general information about a display,
     * such as font size, density, and scale by initializing  an object on
     * <code>getWindowManager().getDefaultDisplay().getMetrics(SDDisplayMetrics.metrics);</code>
     * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
     */
    public DisplayMetrics metrics;

    /**
     * To get a structure that describes general information about a display,
     * such as font size, density, and scale by initializing  an object on
     * <code>getWindowManager().getDefaultDisplay().getMetrics(SDDisplayMetrics.metrics);</code>
     * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
     */
    public DisplayMetrics realMetrics;

    public SDDisplayMetrics() {
        metrics = new DisplayMetrics();
        realMetrics = new DisplayMetrics();
    }

    @Override
    public String toString() {
        return "RealMetrics[" + realMetrics.toString() + "]; Metrics[" + metrics.toString() + "]";
    }
}
