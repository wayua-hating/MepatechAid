package com.wayuyu.mepatech.models

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase
import com.wayuyu.mepatech.ui.screens.database.RequestsRepository

class AdminViewModel : ViewModel() {

    private val db = FirebaseDatabase.getInstance().reference
    private val repo = RequestsRepository()

    var requests by mutableStateOf<List<RequestItem>>(emptyList())
        private set

    init {
        loadRequests()
    }

    private fun loadRequests() {
        repo.getRequests { list ->

            // 🔥 FIX: Convert RequestItem → ReliefRequest
            requests = list.map { item ->

                RequestItem(
                    requestId = item.requestId,
                    userId = item.userId,
                    type = item.type,
                    location = item.location,
                    description = item.description,
                    status = item.status
                )
            }
        }
    }

    fun approveRequest(id: String) {
        db.child("requests")
            .child(id)
            .child("status")
            .setValue("Approved")
    }

    fun rejectRequest(id: String) {
        db.child("requests")
            .child(id)
            .child("status")
            .setValue("Rejected")
    }
}