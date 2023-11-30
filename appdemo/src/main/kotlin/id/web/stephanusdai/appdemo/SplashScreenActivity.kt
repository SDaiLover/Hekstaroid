/*
 * Hekstaroid Android Libraries by StephanusDai Developer
 * @link http://sdailover.github.io/
 * @mail team@sdailover.web.id
 * @copyright Copyright (c) 2023 StephanusDai Developer
 * @license http://sdailover.github.io/license.html
 */

package id.web.stephanusdai.appdemo

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.ScrollView
import androidx.constraintlayout.widget.ConstraintLayout
import io.github.sdailover.hekstaroid.app.SDSplashScreenActivity
import io.github.sdailover.hekstaroid.utils.SDDisplayMetrics
import io.github.sdailover.hekstaroid.utils.SDScreenMode

class SplashScreenActivity : SDSplashScreenActivity() {
    private lateinit var container: ConstraintLayout;
    private lateinit var contentScroll: ScrollView;
    private var isFullScreen: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        container =
            View.inflate(this, R.layout.activity_main, null) as ConstraintLayout
        contentScroll = container.findViewById<ScrollView> (R.id.activity_content)
        setContentView(container)
    }

    override fun onScreenChanged(rotation: Int,
                                 orientation: SDScreenMode?,
                                 displayMetrics: SDDisplayMetrics?) {
        super.onScreenChanged(rotation, orientation, displayMetrics)

        contentScroll.removeAllViews()
        contentScroll.addView(createSplashScreenDialog())
    }

    private fun createSplashScreenDialog(): View {
        val mainView = RelativeLayout(this)
        return mainView;
    }
}