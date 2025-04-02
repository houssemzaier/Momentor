package com.bravedroid.momentor.camera

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object DataStoreHelper {
    private val Context.dataStore by preferencesDataStore(name = "settings")
    private val IMAGE_PATH_KEY = stringPreferencesKey("image_path")

    suspend fun saveImagePath(context: Context, path: String) {
        context.dataStore.edit { prefs ->
            prefs[IMAGE_PATH_KEY] = path
        }
    }

    fun getImagePath(context: Context): Flow<String?> {
        return context.dataStore.data.map { it[IMAGE_PATH_KEY] }
    }
}
