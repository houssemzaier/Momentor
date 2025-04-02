package com.bravedroid.momentor.presentation.features.home

import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import com.bravedroid.momentor.data.model.HomeItem
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
 import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
 import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
 import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import androidx.compose.material3.pulltorefresh.PullToRefreshBox

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
 import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
 import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
 import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
 import androidx.compose.ui.draw.clip
 import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.bravedroid.momentor.data.model.Photo
import com.bravedroid.momentor.data.model.User
import kotlinx.coroutines.flow.collectLatest
import java.util.Date

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val viewState by viewModel.viewState.collectAsState()
    val context = LocalContext.current
    val pullToRefreshState = rememberPullToRefreshState()

    // Handle one-time events
    LaunchedEffect(key1 = true) {
        viewModel.events.collectLatest { event ->
            when (event) {
                is HomeViewModel.HomeEvent.ShowError -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                }
                is HomeViewModel.HomeEvent.RefreshComplete -> {
                    Toast.makeText(context, "Content refreshed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // Handle pull-to-refresh state
    LaunchedEffect(viewState.isRefreshing) {
        if (!viewState.isRefreshing) {
            pullToRefreshState.animateToHidden()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Home") }
            )
        }
    ) { padding ->
        PullToRefreshBox(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            isRefreshing = viewState.isRefreshing,
            onRefresh = {
                viewModel.sendIntent(HomeViewModel.HomeIntent.RefreshHomeItems)
            },
            state = pullToRefreshState,
            indicator = {
                PullToRefreshDefaults.Indicator(
                    modifier = Modifier.align(Alignment.TopCenter),
                    isRefreshing = viewState.isRefreshing,
                    state = pullToRefreshState
                )
            }
        ) {
            when {
                viewState.isLoading && !viewState.isRefreshing -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                viewState.error != null && viewState.items.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Error: ${viewState.error}",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
                else -> {
                    HomeContent(items = viewState.items)
                }
            }
        }
    }
}

@Composable
fun HomeContent(items: List<HomeItem>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item { Box(modifier = Modifier.padding(16.dp)) }

        items(items) { item ->
            Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                when (item) {
                    is HomeItem.Highlight -> HighlightItem(item)
                    is HomeItem.Recommendation -> RecommendationItem(item)
                    is HomeItem.RecentActivity -> RecentActivityItem(item)
                }
            }
        }
    }
}

@Composable
fun HighlightItem(item: HomeItem.Highlight) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(item.photo.imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = item.photo.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Overlay with like count
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(8.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.7f))
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Likes",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${item.photo.likes}",
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }

            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Highlight",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.secondary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = item.photo.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = item.photo.description,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun RecommendationItem(item: HomeItem.Recommendation) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Profile picture
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.user.avatarUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "Profile picture of ${item.user.name}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.secondaryContainer)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Recommended User",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.secondary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = item.user.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = item.user.bio,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun RecentActivityItem(item: HomeItem.RecentActivity) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Profile picture
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(item.user.avatarUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Profile picture of ${item.user.name}",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = item.user.name,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = "posted a new photo",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(item.photo.imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = item.photo.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Overlay with title
                Text(
                    text = item.photo.title,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.surface,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(12.dp)
                )
            }
        }
    }
}

// Preview functions for each component
@Preview(showBackground = true)
@Composable
fun HighlightItemPreview() {
    val photo = Photo(
        id = "1",
        title = "Beautiful Mountain View",
        description = "A stunning view from the top of Mount Everest captured during sunrise.",
        imageUrl = "https://example.com/image1.jpg",
        authorId = "user1",
        createdAt = 0,
        likes = 256
    )
    HighlightItem(HomeItem.Highlight(photo))
}

@Preview(showBackground = true)
@Composable
fun RecommendationItemPreview() {
    val user = User(
        id = "user2",
        name = "Jane Smith",
        bio = "Wildlife photographer passionate about nature conservation",
        avatarUrl = "https://example.com/avatar2.jpg"
    )
    RecommendationItem(HomeItem.Recommendation(user))
}

@Preview(showBackground = true)
@Composable
fun RecentActivityItemPreview() {
    val user = User(
        id = "user3",
        name = "John Doe",
        bio = "Travel enthusiast exploring hidden gems around the world",
        avatarUrl = "https://example.com/avatar3.jpg"
    )
    val photo = Photo(
        id = "3",
        title = "Urban Landscape",
        description = "A cityscape showing the contrast between modern and historic buildings.",
        imageUrl = "https://example.com/image3.jpg",
        authorId = "user3",
        createdAt = 0,
        likes = 142
    )
    RecentActivityItem(HomeItem.RecentActivity(photo, user))
}

@Preview(showBackground = true)
@Composable
fun HomeContentPreview() {
    val photos = listOf(
        Photo(
            id = "1",
            title = "Beautiful Mountain View",
            description = "A stunning view from the top of Mount Everest captured during sunrise.",
            imageUrl = "https://example.com/image1.jpg",
            authorId = "user1",
            createdAt = 0,
            likes = 256
        ),
        Photo(
            id = "2",
            title = "Desert Sunset",
            description = "The golden hour in the Sahara Desert with amazing colors.",
            imageUrl = "https://example.com/image2.jpg",
            authorId = "user2",
            createdAt = 0,
            likes = 198
        ),
        Photo(
            id = "3",
            title = "Urban Landscape",
            description = "A cityscape showing the contrast between modern and historic buildings.",
            imageUrl = "https://example.com/image3.jpg",
            authorId = "user3",
            createdAt = 0,
            likes = 142
        )
    )

    val users = listOf(
        User(
            id = "user1",
            name = "Alex Johnson",
            bio = "Nature photographer with a passion for mountains",
            avatarUrl = "https://example.com/avatar1.jpg"
        ),
        User(
            id = "user2",
            name = "Jane Smith",
            bio = "Wildlife photographer passionate about nature conservation",
            avatarUrl = "https://example.com/avatar2.jpg"
        ),
        User(
            id = "user3",
            name = "John Doe",
            bio = "Travel enthusiast exploring hidden gems around the world",
            avatarUrl = "https://example.com/avatar3.jpg"
        )
    )

    val items = listOf(
        HomeItem.Highlight(photos[0]),
        HomeItem.Recommendation(users[1]),
        HomeItem.RecentActivity(photos[1], users[1]),
        HomeItem.RecentActivity(photos[2], users[2])
    )

    HomeContent(items = items)
}
