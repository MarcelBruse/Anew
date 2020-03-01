package de.anew.activities.tasks

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import java.util.*

abstract class LocalizationTest {

    protected val enEN = Locale("en", "EN")

    protected val deDE = Locale("de", "DE")

    protected fun createNewContextWithLocale(locale: Locale): Context? {
        Locale.setDefault(locale)
        val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
        val configuration = context.resources.configuration
        configuration.setLocale(locale)
        return context.createConfigurationContext(configuration)
    }

}