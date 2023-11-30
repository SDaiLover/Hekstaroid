/*
 * Hekstaroid Android Libraries by StephanusDai Developer
 * @link http://sdailover.github.io/
 * @mail team@sdailover.web.id
 * @copyright Copyright (c) 2023 StephanusDai Developer
 * @license http://sdailover.github.io/license.html
 */

package id.web.stephanusdai.appdemo

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import io.github.sdailover.hekstaroid.Hekstaroid
import io.github.sdailover.hekstaroid.app.SDAppCompatActivity
import io.github.sdailover.hekstaroid.utils.SDDisplayMetrics
import io.github.sdailover.hekstaroid.utils.SDNetworkInfo
import io.github.sdailover.hekstaroid.utils.SDNetworkManager
import io.github.sdailover.hekstaroid.utils.SDNetworkType
import io.github.sdailover.hekstaroid.utils.SDScreenMode

class NetworkMonitorActivity : SDAppCompatActivity() {
    private lateinit var container: ConstraintLayout;
    private lateinit var contentScroll: ScrollView;
    private var networkInfoDetails : SDNetworkInfo? = null;
    private var networkIconStatusResId = R.drawable.ic_baseline_notfoundweb_red;
    private var networkStatusText : String = "Network Status";
    private var webHostStatusText : String = "google.com";
    private @ColorInt var networkStatusTextColor : Int = Color.rgb(149, 0, 0);
    private @ColorInt var webHostStatusTextColor : Int = Color.rgb(149, 0, 0)

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
        contentScroll.addView(createComponents())
    }

    @SuppressLint("MissingPermission")
    override fun onNetworkChanged(isAvailable: Boolean, connectionType: SDNetworkType) {
        super.onNetworkChanged(isAvailable, connectionType)
        val context = this@NetworkMonitorActivity
        when (isAvailable) {
            true -> {
                when (connectionType) {
                    SDNetworkType.CONNECTION_WIFI -> {
                        networkIconStatusResId = R.drawable.ic_baseline_wifinetwork_green
                        networkStatusText = "WIFI Connection"
                        networkStatusTextColor = Color.rgb(0, 71, 0)
                    }
                    SDNetworkType.CONNECTION_CELLULAR -> {
                        networkIconStatusResId = R.drawable.ic_baseline_mobilenetwork_orange
                        networkStatusText = "Mobile Connection"
                        networkStatusTextColor = Color.rgb(141, 170, 0)
                    }
                    else -> { }
                }
            }
            false -> {
                networkIconStatusResId = R.drawable.ic_baseline_notfoundweb_red
                networkStatusText = "No Connection"
                networkStatusTextColor = Color.rgb(149, 0, 0)
            }
        }
        webHostStatusText = SDNetworkManager.getHostName(webHostStatusText)
        if (SDNetworkManager.isAvailableWebsite(context, webHostStatusText)) {
            webHostStatusTextColor = Color.rgb(0, 71, 0)
        } else {
            webHostStatusTextColor = Color.rgb(149, 0, 0)
        }
        if (::networkStatusLabelLayout.isInitialized) {
            networkStatusLabelLayout.setText(networkStatusText)
            networkStatusLabelLayout.setTextColor(networkStatusTextColor)
        }
        if (::networkStatusImageLayout.isInitialized) {
            networkStatusImageLayout.setImageResource(networkIconStatusResId)
        }
        if (::webHostStatusValueLayout.isInitialized) {
            webHostStatusValueLayout.setText(webHostStatusText)
            webHostStatusValueLayout.setTextColor(networkStatusTextColor)
        }
        networkInfoDetails = SDNetworkManager.getRouteInfo(context);
        createNetworkInfoComponents();
    }

    private fun createNetworkInfoComponents() {
        if (::routeInfoContentLayout.isInitialized) {
            routeInfoContentLayout.removeAllViews()
            if (networkInfoDetails != null) {
                if (networkInfoDetails!!.cellularName != null) {
                    with(TextView(this)) {
                        setTextSize(12f)
                        setText("Network Name: " + networkInfoDetails!!.cellularName)
                        routeInfoContentLayout.addView(this)
                        with(getLayoutParams() as ViewGroup.MarginLayoutParams) {
                            setMargins(0, 8, 0, 0)
                        }
                    }
                }
                if (networkInfoDetails!!.cellularType != null) {
                    with(TextView(this)) {
                        setTextSize(12f)
                        setText("Data Type: " +
                                networkInfoDetails!!.cellularType.toString().
                                replace("TYPE_", ""))
                        routeInfoContentLayout.addView(this)
                        with(getLayoutParams() as ViewGroup.MarginLayoutParams) {
                            setMargins(0, 8, 0, 0)
                        }
                    }

                }
                if (networkInfoDetails!!.ssidWifi != null) {
                    with(TextView(this)) {
                        setTextSize(12f)
                        setText("Wifi SSID: " + networkInfoDetails!!.ssidWifi)
                        routeInfoContentLayout.addView(this)
                        with(getLayoutParams() as ViewGroup.MarginLayoutParams) {
                            setMargins(0, 8, 0, 0)
                        }
                    }
                }
                if (networkInfoDetails!!.ipAddress != null) {
                    with(TextView(this)) {
                        setTextSize(12f)
                        setText("IP Address: " + networkInfoDetails!!.ipAddress)
                        routeInfoContentLayout.addView(this)
                        with(getLayoutParams() as ViewGroup.MarginLayoutParams) {
                            setMargins(0, 8, 0, 0)
                        }
                    }
                }
                if (networkInfoDetails!!.netmask != null) {
                    with(TextView(this)) {
                        setTextSize(12f)
                        setText("Netmask: " + networkInfoDetails!!.netmask)
                        routeInfoContentLayout.addView(this)
                        with(getLayoutParams() as ViewGroup.MarginLayoutParams) {
                            setMargins(0, 8, 0, 0)
                        }
                    }
                }
                if (networkInfoDetails!!.gateway != null) {
                    with(TextView(this)) {
                        setTextSize(12f)
                        setText("Gateway: " + networkInfoDetails!!.gateway)
                        routeInfoContentLayout.addView(this)
                        with(getLayoutParams() as ViewGroup.MarginLayoutParams) {
                            setMargins(0, 8, 0, 0)
                        }
                    }
                }
                if (networkInfoDetails!!.dns1 != null) {
                    with(TextView(this)) {
                        setTextSize(12f)
                        setText("DNS Primary: " + networkInfoDetails!!.dns1)
                        routeInfoContentLayout.addView(this)
                        with(getLayoutParams() as ViewGroup.MarginLayoutParams) {
                            setMargins(0, 8, 0, 0)
                        }
                    }
                }
                if (networkInfoDetails!!.dns2 != null) {
                    with(TextView(this)) {
                        setTextSize(12f)
                        setText("DNS Secondary: " + networkInfoDetails!!.dns2)
                        routeInfoContentLayout.addView(this)
                        with(getLayoutParams() as ViewGroup.MarginLayoutParams) {
                            setMargins(0, 8, 0, 0)
                        }
                    }
                }
            } else {
                with(TextView(this)) {
                    setTextSize(12f)
                    setText("No Available Network Information!")
                    routeInfoContentLayout.addView(this)
                    with(getLayoutParams() as ViewGroup.MarginLayoutParams) {
                        setMargins(0, 8, 0, 0)
                    }
                }
            }
        }
    }

    private fun createComponents(): View {
        val context = this@NetworkMonitorActivity
        val mainView = RelativeLayout(this)

        val contentView = LinearLayout(this)

        routeInfoContentLayout = LinearLayout(this)

        networkStatusImageLayout = ImageView(this)
        with(networkStatusImageLayout) {
            contentView.addView(this)
            try {
                setBackgroundColor(Color.TRANSPARENT)
                setImageResource(networkIconStatusResId)
                setScaleType(ImageView.ScaleType.FIT_XY)
                with(getLayoutParams() as ViewGroup.LayoutParams) {
                    width  = 300
                    height = 300
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        networkStatusLabelLayout = TextView(this)
        with(networkStatusLabelLayout) {
            setTextSize(24f)
            setText(networkStatusText)
            setTextColor(networkStatusTextColor)
            contentView.addView(this)
            with(getLayoutParams() as ViewGroup.MarginLayoutParams) {
                setMargins(0, 16, 0, 0)
            }
        }
        webHostStatusLabelLayout = TextView(this)
        with(webHostStatusLabelLayout) {
            setTextSize(16f)
            setText("Check Website Connection :")
            contentView.addView(this)
            with(getLayoutParams() as ViewGroup.MarginLayoutParams) {
                setMargins(0, 32, 0, 0)
            }
        }
        webHostStatusValueLayout = TextView(this)
        with(webHostStatusValueLayout) {
            setTextSize(16f)
            setTypeface(Typeface.DEFAULT_BOLD);
            setText(webHostStatusText)
            setTextColor(webHostStatusTextColor)
            contentView.addView(this)
            with(getLayoutParams() as ViewGroup.MarginLayoutParams) {
                setMargins(0, 8, 0, 0)
            }
        }
        with(TextView(this)) {
            setTextSize(16f)
            setText("Detail of Connection :")
            contentView.addView(this)
            with(getLayoutParams() as ViewGroup.MarginLayoutParams) {
                setMargins(0, 32, 0, 0)
            }
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
            contentView.addView(routeInfoContentLayout).apply {
                with(routeInfoContentLayout) {
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
                createNetworkInfoComponents();
            }
        }
        return mainView;
    }

    private lateinit var networkStatusLabelLayout : TextView;
    private lateinit var networkStatusImageLayout : ImageView;
    private lateinit var webHostStatusLabelLayout : TextView;
    private lateinit var webHostStatusValueLayout : TextView;
    private lateinit var routeInfoContentLayout : LinearLayout;
}