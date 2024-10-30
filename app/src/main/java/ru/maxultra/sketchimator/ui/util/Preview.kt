package ru.maxultra.sketchimator.ui.util

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview

@Preview(name = "Day", group = "uiMode", showBackground = true, backgroundColor = 0xFFFFFFFF)
@Preview(name = "Night", uiMode = UI_MODE_NIGHT_YES, group = "uiMode", showBackground = true, backgroundColor = 0xFF000000)
annotation class DayNightPreview
