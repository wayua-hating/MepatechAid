package com.wayuyu.mepatech.ui.screens.profiles

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.wayuyu.mepatech.models.RequestItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestReliefScreen(
    onSubmit: () -> Unit,
    onBack: () -> Unit
) {

    val context = LocalContext.current

    val fusedLocationClient =
        LocationServices.getFusedLocationProviderClient(context)

    var selectedRelief by remember {
        mutableStateOf("Food")
    }

    var location by remember {
        mutableStateOf("")
    }

    var description by remember {
        mutableStateOf("")
    }

    var isLoading by remember {
        mutableStateOf(false)
    }

    var errorMessage by remember {
        mutableStateOf("")
    }

    val options = listOf(
        "Food",
        "Water",
        "Blankets"
    )

    val permissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { isGranted ->

            if (isGranted) {

                fusedLocationClient.lastLocation
                    .addOnSuccessListener { loc ->

                        if (loc != null) {

                            try {

                                val geocoder = Geocoder(context)

                                val addresses =
                                    geocoder.getFromLocation(
                                        loc.latitude,
                                        loc.longitude,
                                        1
                                    )

                                location =
                                    if (!addresses.isNullOrEmpty()) {

                                        val address = addresses[0]

                                        listOfNotNull(
                                            address.locality,
                                            address.adminArea,
                                            address.countryName
                                        ).joinToString(", ")

                                    } else {

                                        "Lat: ${loc.latitude}, Lng: ${loc.longitude}"
                                    }

                            } catch (e: Exception) {

                                location =
                                    "Lat: ${loc.latitude}, Lng: ${loc.longitude}"
                            }

                        } else {

                            location = "Unable to detect location"
                        }
                    }

            } else {

                location = "Permission denied"
            }
        }

    Scaffold(

        topBar = {

            TopAppBar(

                title = {
                    Text("Request Assistance")
                }
            )
        }

    ) { padding ->

        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),

            verticalArrangement = Arrangement.spacedBy(16.dp)

        ) {

            // 🔷 HEADER
            Text(
                text = "What do you need?",
                style = MaterialTheme.typography.headlineSmall
            )

            Text(
                text = "Select the type of help you need",
                style = MaterialTheme.typography.bodyMedium
            )

            // 🔷 RELIEF TYPE
            Card(
                shape = MaterialTheme.shapes.medium
            ) {

                Row(

                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth(),

                    horizontalArrangement =
                        Arrangement.spacedBy(8.dp)

                ) {

                    options.forEach { item ->

                        FilterChip(

                            selected =
                                selectedRelief == item,

                            onClick = {
                                selectedRelief = item
                            },

                            label = {
                                Text(item)
                            }
                        )
                    }
                }
            }

            // 🔷 LOCATION
            Card {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        "Your Location",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(

                        value = location,

                        onValueChange = {
                            location = it
                        },

                        label = {
                            Text("Enter or detect location")
                        },

                        leadingIcon = {
                            Icon(
                                Icons.Default.LocationOn,
                                contentDescription = null
                            )
                        },

                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedButton(

                        onClick = {

                            val permissionCheck =
                                ContextCompat.checkSelfPermission(
                                    context,
                                    Manifest.permission.ACCESS_FINE_LOCATION
                                )

                            if (
                                permissionCheck ==
                                PackageManager.PERMISSION_GRANTED
                            ) {

                                permissionLauncher.launch(
                                    Manifest.permission.ACCESS_FINE_LOCATION
                                )

                            } else {

                                permissionLauncher.launch(
                                    Manifest.permission.ACCESS_FINE_LOCATION
                                )
                            }
                        },

                        modifier = Modifier.fillMaxWidth()

                    ) {

                        Text("Auto Detect Location")
                    }
                }
            }

            // 🔷 DESCRIPTION
            Card {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        "Additional Details",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(

                        value = description,

                        onValueChange = {
                            description = it
                        },

                        label = {
                            Text("Describe your situation")
                        },

                        leadingIcon = {

                            Icon(
                                Icons.Default.Info,
                                contentDescription = null
                            )
                        },

                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // 🔷 SUBMIT BUTTON
            Button(

                onClick = {

                    // VALIDATION
                    if (location.isBlank()) {

                        errorMessage =
                            "Location is required"

                        return@Button
                    }

                    if (description.isBlank()) {

                        errorMessage =
                            "Description is required"

                        return@Button
                    }

                    isLoading = true
                    errorMessage = ""

                    val database =
                        FirebaseDatabase.getInstance()

                    val ref =
                        database.getReference("requests")

                    val requestId =
                        ref.push().key!!

                    val request = RequestItem(

                        requestId = requestId,

                        userId =
                            FirebaseAuth
                                .getInstance()
                                .currentUser?.uid ?: "",

                        type = selectedRelief,

                        location = location,

                        description = description,

                        status = "Pending"
                    )

                    ref.child(requestId)
                        .setValue(request)

                        .addOnSuccessListener {

                            isLoading = false

                            println("REQUEST SAVED")

                            // CLEAR FORM
                            description = ""
                            location = ""

                            onSubmit()
                        }

                        .addOnFailureListener {

                            isLoading = false

                            errorMessage =
                                it.message ?: "Unknown error"

                            println("ERROR: ${it.message}")
                        }
                },

                modifier = Modifier.fillMaxWidth(),

                enabled = !isLoading

            ) {

                if (isLoading) {

                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp
                    )

                } else {

                    Text("Submit Request")
                }
            }

            // 🔷 ERROR MESSAGE
            if (errorMessage.isNotEmpty()) {

                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RequestReliefScreenPreview() {

    RequestReliefScreen(

        onSubmit = {},

        onBack = {}
    )
}