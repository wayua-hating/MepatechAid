package com.wayuyu.mepatech.ui.screens.database

import com.google.firebase.database.*
import com.wayuyu.mepatech.models.RequestItem

class RequestsRepository {

    private val db = FirebaseDatabase
        .getInstance()
        .getReference("requests")

    // 🔷 GET ALL REQUESTS
    fun getRequests(

        callback: (List<RequestItem>) -> Unit

    ) {

        db.addValueEventListener(

            object : ValueEventListener {

                override fun onDataChange(
                    snapshot: DataSnapshot
                ) {

                    val list =
                        mutableListOf<RequestItem>()

                    for (child in snapshot.children) {

                        val request =
                            child.getValue(
                                RequestItem::class.java
                            )

                        if (request != null) {

                            list.add(request)
                        }
                    }

                    callback(list)
                }

                override fun onCancelled(
                    error: DatabaseError
                ) {

                }
            }
        )
    }

    // 🔷 GET CURRENT USER REQUESTS
    fun getUserRequests(

        userId: String,

        callback: (List<RequestItem>) -> Unit

    ) {

        db.orderByChild("userId")
            .equalTo(userId)

            .addValueEventListener(

                object : ValueEventListener {

                    override fun onDataChange(
                        snapshot: DataSnapshot
                    ) {

                        val list =
                            mutableListOf<RequestItem>()

                        for (child in snapshot.children) {

                            val request =
                                child.getValue(
                                    RequestItem::class.java
                                )

                            if (request != null) {

                                list.add(request)
                            }
                        }

                        callback(list)
                    }

                    override fun onCancelled(
                        error: DatabaseError
                    ) {

                    }
                }
            )
    }
}