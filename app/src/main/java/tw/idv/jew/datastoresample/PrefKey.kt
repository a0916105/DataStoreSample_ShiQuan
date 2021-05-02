package tw.idv.jew.datastoresample

import androidx.datastore.preferences.core.preferencesKey

object PrefKey {
    val KEY_COUNTER = preferencesKey<Int>("key_counter")
}