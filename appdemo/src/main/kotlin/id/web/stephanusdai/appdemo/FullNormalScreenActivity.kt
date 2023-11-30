/*
 * Hekstaroid Android Libraries by StephanusDai Developer
 * @link http://sdailover.github.io/
 * @mail sdailover@stephanusdai.web.id
 * @copyright Copyright (c) 2023 StephanusDai Developer
 * @license http://sdailover.github.io/license.html
 */

package id.web.stephanusdai.appdemo

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.ScrollView
import androidx.constraintlayout.widget.ConstraintLayout
import io.github.sdailover.hekstaroid.Hekstaroid
import io.github.sdailover.hekstaroid.app.SDAppCompatActivity
import io.github.sdailover.hekstaroid.utils.SDDisplayMetrics
import io.github.sdailover.hekstaroid.utils.SDScreenMode

class FullNormalScreenActivity : SDAppCompatActivity() {
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
        contentScroll.addView(createMenuContext())
    }

    private fun createMenuContext(): View {
        val context = this@FullNormalScreenActivity
        val mainView = RelativeLayout(this)

        val contentView = LinearLayout(this)
        with(Button(this)) {
            setText("Toggle Fullscreen Mode")
            setOnClickListener({
                if (isFullScreen == false) {
                    isFullScreen = true
                    Hekstaroid.Instance(context).displayManager.applyToFullScreen()
                } else {
                    isFullScreen = false
                    Hekstaroid.Instance(context).displayManager.applyToNormalScreen()
                }
            })
            contentView.addView(this)
        }
        with(Button(this)) {
            setText("Show AppBar - Hide Navbar")
            setOnClickListener({
                isFullScreen = true
                Hekstaroid.Instance(context).displayManager.applyToFullScreen(true, false)
            })
            contentView.addView(this)
        }
        with(Button(this)) {
            setText("Hide AppBar - Show Navbar")
            setOnClickListener({
                isFullScreen = true
                Hekstaroid.Instance(context).displayManager.applyToFullScreen(false, true)
            })
            contentView.addView(this)
        }
        with(Button(this)) {
            setText("Show AppBar - Show Navbar")
            setOnClickListener({
                isFullScreen = true
                Hekstaroid.Instance(context).displayManager.applyToFullScreen(true, true)
            })
            contentView.addView(this)
        }
        mainView.addView(contentView).apply {
            with(contentView) {
                try {
                    setOrientation(LinearLayout.VERTICAL)
                    setGravity(Gravity.CENTER)
                    with(getLayoutParams() as RelativeLayout.LayoutParams) {
                        addRule(RelativeLayout.CENTER_IN_PARENT)
                    }
                    with(getLayoutParams() as ViewGroup.LayoutParams) {
                        width  = RelativeLayout.LayoutParams.WRAP_CONTENT
                        height = RelativeLayout.LayoutParams.WRAP_CONTENT
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        return mainView;
    }
}