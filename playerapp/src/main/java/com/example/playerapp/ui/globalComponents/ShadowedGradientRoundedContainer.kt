package com.example.playerapp.ui.globalComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.playerapp.utils.imageComponentBorderGradient
import com.example.playerapp.utils.imageComponentGradient


@Composable
fun ShadowedGradientRoundedContainer(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 26.dp,
    contentPadding: PaddingValues = PaddingValues(
        vertical = 14.dp,
        horizontal = 16.dp
    ),
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    content: @Composable () -> Unit,
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 20.dp)
    ) {
        Column(
            modifier = modifier
                .background(
                    brush = imageComponentBorderGradient,
                    shape = CircleShape.copy(CornerSize(cornerRadius))
                )
                .padding(0.5.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = imageComponentGradient,
                        shape = CircleShape.copy(CornerSize(cornerRadius))
                    )
                    .padding(paddingValues = contentPadding),
                horizontalAlignment = horizontalAlignment,
            ) {
                content()
            }
        }
    }
}

@Preview
@Composable
fun ShadowedGradientRoundedContainerPreview() {
    ShadowedGradientRoundedContainer {

    }
}