package com.wayuyu.mepatech.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.wayuyu.mepatech.ui.components.AdminActionButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Admin(
    onViewRequests: () -> Unit,
    onLogout: () -> Unit
) {

    val colors = MaterialTheme.colorScheme

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Admin Command Center",
                        color = colors.onBackground
                    )
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            colors.background,
                            colors.surface.copy(alpha = 0.8f)
                        )
                    )
                )
                .padding(padding)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // HEADER
            Text(
                text = "Welcome Admin",
                style = MaterialTheme.typography.headlineMedium,
                color = colors.onBackground
            )

            Text(
                text = "Manage relief requests, emergencies & system operations",
                style = MaterialTheme.typography.bodyMedium,
                color = colors.onBackground.copy(alpha = 0.6f)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // DASHBOARD ACTIONS (GLASS STYLE BUTTONS)

            AdminActionButton(
                text = "View All Requests",
                onClick = onViewRequests,
                color = colors.primary
            )

            Spacer(modifier = Modifier.weight(1f))

            // LOGOUT (CRITICAL ACTION)
            Button(
                onClick = onLogout,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colors.error
                )
            ) {
                Text("Logout", color = colors.onError)
            }
        }
    }
}