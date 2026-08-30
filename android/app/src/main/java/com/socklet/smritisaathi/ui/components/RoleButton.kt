package com.socklet.smritisaathi.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.socklet.smritisaathi.ui.theme.Dimensions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoleButton(
    title: String,
    description: String,
    icon: ImageVector,
    backgroundColor: androidx.compose.ui.graphics.Color,
    contentColor: androidx.compose.ui.graphics.Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(160.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = Dimensions.CardElevation
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(Dimensions.Space24),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = contentColor,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.height(Dimensions.Space16))
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center,
                color = contentColor
            )
            Spacer(modifier = Modifier.height(Dimensions.Space8))
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = contentColor.copy(alpha = 0.8f)
            )
        }
    }
}
