package com.wayuyu.mepatech.ui.screens.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.wayuyu.mepatech.models.AdminViewModel
import com.wayuyu.mepatech.models.RequestItem
import com.wayuyu.mepatech.ui.components.RequestCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminRequestsScreen(
    onBack: () -> Unit
) {

    val viewModel: AdminViewModel = viewModel()
    val requests = viewModel.requests

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Incident Command Center") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            items(requests) { request ->

                RequestCard(
                    request = request,
                    onApprove = {
                        viewModel.approveRequest(request.requestId)
                    },
                    onReject = {
                        viewModel.rejectRequest(request.requestId)
                    }
                )
            }
        }
    }
}