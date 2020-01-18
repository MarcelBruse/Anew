package de.quotas.activities.editor

import android.widget.Spinner
import androidx.lifecycle.LiveData
import com.google.android.material.textfield.TextInputEditText
import de.quotas.models.Quota
import de.quotas.models.QuotaFactory
import de.quotas.models.time.TimePeriodEnum
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class QuotaObserverTest {

    @Test
    fun onChanged() {
        val textField = mockk<TextInputEditText>()
        every { textField.setText("Quota") } returns Unit

        val periodSpinner = mockk<Spinner>()
        every { periodSpinner.setSelection(TimePeriodEnum.DAILY.ordinal) } returns Unit

        val quota: LiveData<Quota> = QuotaFactory.newQuota()
        quota.value?.name = "Quota"

        val quotaObserver = QuotaObserver(textField, periodSpinner)
        quotaObserver.onChanged(quota.value)

        verify { textField.setText("Quota") }
        verify { periodSpinner.setSelection(0) }
    }

}