package ru.maxultra.sketchimator.feature_frame_list.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.TextButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.maxultra.sketchimator.R
import ru.maxultra.sketchimator.core_ui.core_components.Surface
import ru.maxultra.sketchimator.core_ui.theme.SketchimatorTheme
import ru.maxultra.sketchimator.core_ui.theme.tokens.PaletteTokens
import ru.maxultra.sketchimator.core_ui.util.DayNightPreview
import ru.maxultra.sketchimator.feature_frame_list.ui.vm.TopBarListener
import ru.maxultra.sketchimator.feature_frame_list.ui.vm.TopBarVm

@Composable
fun TopBar(
    vm: TopBarVm,
    listener: TopBarListener,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.systemBars),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = stringResource(R.string.frame_count_template, vm.frameCount),
                modifier = Modifier.weight(1f)
            )
            TextButton(onClick = listener.onRemoveAllClick) {
                Text(
                    color = PaletteTokens.Red,
                    text = stringResource(R.string.remove_all),
                )
            }
        }
    }
}

@DayNightPreview
@Composable
private fun TopBarPreview() {
    SketchimatorTheme {
        TopBar(
            vm = TopBarVm(
                frameCount = 10,
            ),
            listener = TopBarListener(
                onBackClick = {},
                onRemoveAllClick = {},
            ),
        )
    }
}
