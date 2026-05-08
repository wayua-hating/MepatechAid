package com.wayuyu.mepatech.ui.screens.onboarding

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AssignmentLate
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wayuyu.mepatech.models.Onboarding
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingScreen(
    onNext: () -> Unit,
    onSkip: () -> Unit
) {
    val pages = listOf(
        Onboarding(
            title = "Welcome to ReliefLink",
            description = "Connecting you to emergency help with ease.",
            imageVector = Icons.Default.AssignmentLate
        ),
        Onboarding(
            title = "Fast & Reliable",
            description = "Request food, water, or blankets in just a few taps.",
            imageVector = Icons.Default.AccessTime
        ),
        Onboarding(
            title = "Stay Informed",
            description = "Track your requests and get updates in real time.",
            imageVector = Icons.Default.Info
        )
    )

    val pagerState = rememberPagerState(
        pageCount = { pages.size }
    )


    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { page ->
                OnboardingPageView(pages[page])
            }


            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(onClick = onSkip) {
                    Text("Skip")
                }
                val coroutineScope = rememberCoroutineScope()

                Button(
                    onClick = {
                        coroutineScope.launch {
                            if (pagerState.currentPage < pages.size - 1) {
                                pagerState.scrollToPage(pagerState.currentPage + 1)
                            } else {
                                onNext()
                            }
                        }
                    }
                ) {
                    Text(
                        if (pagerState.currentPage < pages.size - 1)
                            "Next"
                        else
                            "Get Started"
                    )
                }

            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun OnboardingPageView(page: Onboarding) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = page.imageVector,
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = page.title,
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = page.description,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(horizontal = 32.dp),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}



@Preview(showBackground = true)
@Composable
fun OnboardingPreview() {
    OnboardingScreen(
        onNext = {},
        onSkip = {}
    )
}
