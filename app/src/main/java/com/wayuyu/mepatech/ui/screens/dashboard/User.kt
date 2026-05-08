package com.wayuyu.mepatech.ui.screens.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun User(
    onRequestRelief: () -> Unit,
    onViewRequests: () -> Unit,

    onProfile: () -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("ReliefLink") },
                actions = {
                    IconButton(onClick = onProfile) {
                        Icon(Icons.Default.Person, contentDescription = "Profile")
                    }
                }
            )
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // 🔷 HEADER
            item {
                Column {
                    Text(
                        text = "Hello 👋",
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Text(
                        text = "How can we help you today?",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            // 🔷 QUICK ACTIONS
            item {
                Text(
                    text = "Quick Actions",
                    style = MaterialTheme.typography.titleMedium
                )
            }

            item {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

                    DashboardCard(
                        title = "Request Food",
                        icon = Icons.Default.Fastfood,
                        onClick = onRequestRelief
                    )

                    DashboardCard(
                        title = "Request Water",
                        icon = Icons.Default.WaterDrop,
                        onClick = onRequestRelief
                    )

                    DashboardCard(
                        title = "Request Blankets",
                        icon = Icons.Default.Bed,
                        onClick = onRequestRelief
                    )

                }
            }

            // 🔷 STATUS SECTION
            item {
                Text(
                    text = "Your Requests",
                    style = MaterialTheme.typography.titleMedium
                )
            }

            item {
                Card(
                    shape = MaterialTheme.shapes.medium,
                    elevation = CardDefaults.cardElevation(4.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                        RequestStatusItem("Food Request", "Pending")
                        RequestStatusItem("Water Request", "Approved")
                    }
                }
            }

            // 🔷 VIEW ALL BUTTON
            item {
                Button(
                    onClick = onViewRequests,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text("View All Requests")
                }
            }
        }
    }
}

@Composable
fun DashboardCard(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(3.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(28.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
fun RequestStatusItem(title: String, status: String) {

    val color = when (status) {
        "Approved" -> MaterialTheme.colorScheme.primary
        "Rejected" -> MaterialTheme.colorScheme.error
        else -> MaterialTheme.colorScheme.secondary
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(title)
        Text(status, color = color)
    }
}

@Preview(showBackground = true)
@Composable
fun UserPreview() {
    User(
        onRequestRelief = {},
        onViewRequests = {},
        onProfile = {}
    )
}
