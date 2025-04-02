package com.bravedroid.momentor.data.model

sealed class HomeItem {
    data class Highlight(val photo: Photo) : HomeItem()
    data class Recommendation(val user: User) : HomeItem()
    data class RecentActivity(val photo: Photo, val user: User) : HomeItem()
}
