package com.paranid5.cooking_corner.feature.main.generate.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.generate_generating_recipe
import com.paranid5.cooking_corner.core.resources.generating
import com.paranid5.cooking_corner.ui.foundation.AppMainText
import com.paranid5.cooking_corner.ui.theme.AppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

private const val ANIM_DELAY_MS = 500L
private const val MAX_NUMBER_OF_ANIM_STEPS = 4
private val IMAGE_SIZE = 134.dp

@Composable
internal fun ProgressUi(modifier: Modifier = Modifier) {
    val animFlow = remember { dotAnimFlow() }
    val dotsNumber by animFlow.collectAsState(initial = 0)
    val placeholder = stringResource(Res.string.generate_generating_recipe)
    val text = remember(dotsNumber, placeholder) {
        "$placeholder${".".repeat(dotsNumber)}"
    }

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        AppMainText(
            text = text,
            style = AppTheme.typography.h.h2,
        )

        Spacer(Modifier.height(AppTheme.dimensions.padding.large))

        Image(
            painter = painterResource(Res.drawable.generating),
            contentDescription = null,
            modifier = Modifier.size(IMAGE_SIZE),
        )
    }
}

private fun dotAnimFlow() = flow {
    var cnt = 0
    while (true) {
        delay(ANIM_DELAY_MS)
        emit(cnt++ % MAX_NUMBER_OF_ANIM_STEPS)
    }
}
