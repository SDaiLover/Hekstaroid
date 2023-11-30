/*
 * AndroidBottomSheetDialog by Stephanus Dai (戴偉峯)
 * @link http://www.stephanusdai.web.id/
 * @mail wiefunk@stephanusdai.web.id
 * @copyright Copyright (c) 2023 Stephanus Bagus Saputra
 * @license http://stephanusdai.web.id/license.html
 */

package id.web.stephanusdai.appdemo

import android.content.Intent
import android.net.Network
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.ScrollView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import io.github.sdailover.hekstaroid.Hekstaroid
import io.github.sdailover.hekstaroid.app.SDAppCompatActivity
import io.github.sdailover.hekstaroid.content.SDLaunchSheetDialog
import io.github.sdailover.hekstaroid.utils.SDDisplayMetrics
import io.github.sdailover.hekstaroid.utils.SDScreenMode
import java.util.Objects

class MainActivity : SDAppCompatActivity() {
    private var isLaunchSheetShown: Boolean = false
    private lateinit var container: ConstraintLayout
    private lateinit var contentScroll: ScrollView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (isLaunchSheetShown==false) {
            val splashDialog = SDLaunchSheetDialog(this)
            splashDialog.show()
            isLaunchSheetShown = true
        }
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
        val context = this@MainActivity
        val mainView = RelativeLayout(this)

        val contentView = LinearLayout(this)
        with(Button(this)) {
            setText("Fullscreen Activity")
            setOnClickListener({
                context.openActivity(FullNormalScreenActivity::class.java);
            })
            contentView.addView(this)
        }
        with(Button(this)) {
            setText("Bottom Notice Activity")
            setOnClickListener({
                context.openActivity(BottomNoticeDialogActivity::class.java);
            })
            contentView.addView(this)
        }
        with(Button(this)) {
            setText("Network Monitor Activy")
            setOnClickListener({
                context.openActivity(NetworkMonitorActivity::class.java);
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

    private fun <T: Any> openActivity(cls: Class<T>) {
        val intent = Intent(this, cls)
        startActivity(intent)
        finish()
    }
}