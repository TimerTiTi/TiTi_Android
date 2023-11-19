package com.titi.feature.time.ui.measure

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.titi.core.designsystem.theme.TiTiTheme
import com.titi.domain.time.model.RecordTimes

class MeasuringActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val recordTimes = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(RECORD_TIMES_KEY, RecordTimes::class.java)
        } else {
            intent.getParcelableExtra(RECORD_TIMES_KEY) as? RecordTimes
        }

        setContent {
            TiTiTheme {

            }
        }
    }

    companion object {
        const val RECORD_TIMES_KEY = "recordTimesKey"
    }

}