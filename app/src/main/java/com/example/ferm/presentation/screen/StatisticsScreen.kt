package com.example.ferm.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun StatisticsScreen(
    modifier: Modifier = Modifier,
    values: List<Float>,              // ej: listOf(10f, 30f, 20f, 50f)
    maxValue: Float = values.maxOrNull() ?: 0f,
    barWidth: Dp = 24.dp,
    barSpacing: Dp = 12.dp,
    barColor: Color = Color(0xFF2196F3)
) {
    Row(modifier = modifier
        .fillMaxSize()
        .background(Color(0xFFCFCDD3))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(barSpacing)
        ) {
            values.forEach { v ->
                val hRatio = if (maxValue > 0f) v / maxValue else 0f
                Box(
                    modifier = Modifier
                        .width(barWidth)
                        .fillMaxHeight(hRatio)   // altura proporcional
                        .background(barColor, RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp))
                )
            }
        }
    }
}