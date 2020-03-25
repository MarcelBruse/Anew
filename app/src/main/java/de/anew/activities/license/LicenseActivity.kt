/*
 * Copyright 2020 Marcel Bruse
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.anew.activities.license

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import de.anew.R

class LicenseActivity : AppCompatActivity(), OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.license_activity)
        setSupportActionBar(findViewById(R.id.license_toolbar))
        val licensesButton = findViewById<Button>(R.id.licenses_button)
        licensesButton.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        OssLicensesMenuActivity.setActivityTitle(getString(R.string.third_party_licenses))
        startActivity(Intent(this, OssLicensesMenuActivity::class.java))
    }

}
