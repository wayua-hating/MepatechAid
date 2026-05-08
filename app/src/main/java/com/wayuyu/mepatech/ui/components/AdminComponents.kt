package com.wayuyu.mepatech.ui.components


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AdminActionButton(
    text: String,
    onClick: () -> Unit,
    color: androidx.compose.ui.graphics.Color
) {
    val colors = MaterialTheme.colorScheme

    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colors.surface.copy(alpha = 0.08f)
        )
    ) {
        Text(text = text, color = color)
    }
}