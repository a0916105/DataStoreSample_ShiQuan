package tw.idv.jew.datastoresample

import android.app.Application
import androidx.datastore.createDataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.io.IOException
import tw.idv.jew.datastoretest.Counter

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val dataStore = application.createDataStore(
        name = "testData"
    )

    private val counterDataStore = application.createDataStore(
        fileName = "counter.ph",
        serializer = CounterSerializer
    )

    fun increaseCounter() {
        viewModelScope.launch {
            dataStore.edit { preferences ->
                val value = preferences[PrefKey.KEY_COUNTER] ?: 0
                dataStore.data.first()
                preferences[PrefKey.KEY_COUNTER] = value + 1
            }
        }
    }

    fun updateCounter() {
        viewModelScope.launch {
            counterDataStore.updateData {
                Counter.newBuilder()
                    .setValue(it.value + 1)
                    .setTimestamp(System.currentTimeMillis())
                    .setTag("test abc")
                    .build()
            }
        }
    }

    val counter: Flow<Int> = dataStore.data
        .catch {
            if (it is IOException) {
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[PrefKey.KEY_COUNTER] ?: 0
        }
}