/*
 * Hekstaroid Android Libraries by StephanusDai Developer
 * @link http://sdailover.github.io/
 * @mail team@sdailover.web.id
 * @copyright Copyright (c) 2023 StephanusDai Developer
 * @license http://sdailover.github.io/license.html
 */

package io.github.sdailover.hekstaroid.utils;

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
