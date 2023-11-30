/*
 * AndroidBottomSheetDialog by Stephanus Dai (戴偉峯)
 * @link http://www.stephanusdai.web.id/
 * @mail wiefunk@stephanusdai.web.id
 * @copyright Copyright (c) 2023 Stephanus Bagus Saputra
 * @license http://stephanusdai.web.id/license.html
 */

package id.web.stephanusdai.appdemo

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext: Context = InstrumentationRegistry.getInstrumentation().getTargetContext()
        assertEquals("io.github.sdailover.hekstaroid.test", appContext.getPackageName())
    }
}