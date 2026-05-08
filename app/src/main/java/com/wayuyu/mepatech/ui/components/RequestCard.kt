package com.wayuyu.mepatech.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wayuyu.mepatech.models.RequestItem

@Composable
fun RequestCard(
    request: RequestItem,
    onApprove: (() -> Unit)? = null,
    onReject: (() -> Unit)? = null
) {

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(modifier = Modifier.padding(16.dp)) {

            Text("Type: ${request.type}")
            Text("Location: ${request.location}")

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Status: ${request.status}",
                style = MaterialTheme.typography.bodyMedium,
                color = when (request.status) {
                    "Approved" -> androidx.compose.ui.graphics.Color(0xFF2E7D32)
                    "Rejected" -> androidx.compose.ui.graphics.Color(0xFFC62828)
                    else -> androidx.compose.ui.graphics.Color(0xFFF9A825)
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            // ONLY SHOW FOR ADMIN
            if (onApprove != null && onReject != null) {

                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    Button(onClick = onApprove) {
                        Text("Approve")
                    }

                    Button(onClick = onReject) {
                        Text("Reject")
                    }
                }
            }
        }
    }
}