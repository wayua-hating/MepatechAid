package com.wayuyu.mepatech.models

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.wayuyu.mepatech.ui.screens.database.RequestsRepository

class MyRequestsViewModel : ViewModel() {

    private val repo = RequestsRepository()

    var requests by mutableStateOf<List<RequestItem>>(emptyList())
        private set

    private val userId = FirebaseAuth.getInstance().currentUser?.uid

    init {
        if (userId != null) {
            loadRequests(userId)
        }
    }

    private fun loadRequests(userId: String) {
        repo.getUserRequests(userId) { list ->
            requests = list
        }
    }
}