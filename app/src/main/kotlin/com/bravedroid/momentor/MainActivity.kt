package com.bravedroid.momentor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import com.bravedroid.momentor.libraries.presentation.designsystem.theme.MomentorTheme
import com.bravedroid.momentor.libraries.presentation.navigation.NavigatorAccessor
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    internal lateinit var navigatorAccessor: NavigatorAccessor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MomentorTheme {
                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.auto(
                        lightScrim = MaterialTheme.colorScheme.primary.toArgb(),
                        darkScrim = MaterialTheme.colorScheme.primary.toArgb()
                    ),
                    navigationBarStyle = SystemBarStyle.auto(
                        lightScrim = MaterialTheme.colorScheme.primary.toArgb(),
                        darkScrim = MaterialTheme.colorScheme.primary.toArgb()
                    )
                )

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavAppHost(navigatorAccessor)
                }
            }
        }
    }
}

// build logic
//     fix problem with the swipe to refresh
//     migrate the features to their own modules
//     check the storage of the images
//     load the images from the network and the local storage
//     add the ability to share the images
//     interact with the feeds

