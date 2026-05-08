package com.wayuyu.mepatech.ui.screens.profiles



import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.wayuyu.mepatech.ui.components.RequestCard
import androidx.compose.ui.unit.dp
import com.wayuyu.mepatech.models.MyRequestsViewModel




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyRequests(
    onBack: () -> Unit
) {

    val viewModel: MyRequestsViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
    val requests = viewModel.requests

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Requests") }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {

            Text(
                text = "Your Relief Requests",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(10.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                items(requests) { request ->

                    RequestCard(request)
                }
            }
        }
    }
}

