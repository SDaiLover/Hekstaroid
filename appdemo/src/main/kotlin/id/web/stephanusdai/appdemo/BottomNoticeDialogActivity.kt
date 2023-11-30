/*
 * Hekstaroid Android Libraries by StephanusDai Developer
 * @link http://sdailover.github.io/
 * @mail team@sdailover.web.id
 * @copyright Copyright (c) 2023 StephanusDai Developer
 * @license http://sdailover.github.io/license.html
 */

package id.web.stephanusdai.appdemo

import android.app.AlertDialog
import android.content.DialogInterface
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
import io.github.sdailover.hekstaroid.maps.SDDialogInterface
import io.github.sdailover.hekstaroid.utils.SDDisplayMetrics
import io.github.sdailover.hekstaroid.utils.SDScreenMode

class BottomNoticeDialogActivity : SDAppCompatActivity() {
    private lateinit var container: ConstraintLayout;
    private lateinit var contentScroll: ScrollView;


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
        contentScroll.addView(createBottomNoticeDialog())
    }

    private fun createBottomNoticeDialog(): View {
        val context = this@BottomNoticeDialogActivity
        val mainView = RelativeLayout(this)

        val contentView = LinearLayout(this)
        with(Button(this)) {
            setText("Show Bottom Notice")
            setOnClickListener {
                Hekstaroid.Instance(context).bottomNotice.show("Information", "Test", {noticeDialog ->
                    noticeDialog?.cancelDismiss = true;
                    var alertDialog = AlertDialog.Builder(getContext());
                    with(AlertDialog.Builder(getContext())) {
                        setTitle("Exit")
                        setMessage("Are Your Sure to Exit this?")
                        setNegativeButton("YES", { dialogInterface: DialogInterface, i: Int ->
                            noticeDialog?.cancelDismiss = false;
                            noticeDialog?.dismiss();
                        })
                        setPositiveButton("NO", { dialogInterface: DialogInterface, i: Int ->
                            noticeDialog?.cancelDismiss = true;
                            noticeDialog?.cancel();
                        })
                        show()
                    }
                });
            }
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