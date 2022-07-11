package com.constantine.data.repository

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.constantine.domain.repository.ConfigRepository
import java.util.Date
import javax.inject.Inject

class ConfigurationRepository @Inject constructor(
    private val context: Context
) : ConfigRepository {

    private val preferences: SharedPreferences by lazy {
        context.getSharedPreferences(ConfigurationRepository::class.java.name, MODE_PRIVATE)
    }

    override fun initialize() {
        if (preferences.getLong(FIRST_LAUNCH, 0L) == 0L) {
            updatePreference {
                it.putLong(FIRST_LAUNCH, Date().time)
            }
        }
    }

    override fun getInstallTimestamp(): Long {
        return preferences.getLong(FIRST_LAUNCH, Date().time)
    }

    private fun updatePreference(action: (SharedPreferences.Editor) -> Unit) {
        preferences.also {
            val edit = it.edit()
            action(edit)
            edit.apply()
        }
    }

    companion object {
        const val FIRST_LAUNCH = "com.constantine.data.repository" +
            ".ConfigurationRepository.FIRST_LAUNCH"
    }
}
