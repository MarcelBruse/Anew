package de.quotas.activities.editor

import android.widget.Spinner
import androidx.lifecycle.Observer
import com.google.android.material.textfield.TextInputEditText
import de.quotas.models.Quota
import de.quotas.models.time.TimePeriodEnum

class QuotaObserver(
    private val quotaNameField: TextInputEditText,
    private val quotaPeriodSpinner: Spinner
) : Observer<Quota> {

    override fun onChanged(quota: Quota?) {
        quota?.let {
            quotaNameField.setText(it.name)
            val position = TimePeriodEnum.getValueByTimePeriod(it.period).ordinal
            quotaPeriodSpinner.setSelection(position)
        }
    }

}