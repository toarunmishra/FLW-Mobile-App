package org.piramalswasthya.sakhi.ui.home_activity.child_care.adolescent_list

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.piramalswasthya.sakhi.configuration.AdolescentHealthCache
import org.piramalswasthya.sakhi.configuration.AdolescentHealthFormDataset
import org.piramalswasthya.sakhi.database.shared_preferences.PreferenceDao
import org.piramalswasthya.sakhi.repositories.AdolescentHealthRepo
import javax.inject.Inject

@HiltViewModel
class AdolescentHealthFormViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
   val  preferenceDao: PreferenceDao,
    @ApplicationContext context: Context,
    private val adolescentHealthRepo: AdolescentHealthRepo
) : ViewModel() {

    enum class State {
        IDLE, SAVING, SAVE_SUCCESS, SAVE_FAILED
    }

    private val _state = MutableLiveData(State.IDLE)
    val state: LiveData<State>
        get() = _state

    private val _benName = MutableLiveData<String>()
    val benName: LiveData<String>
        get() = _benName

    private val _benAgeGender = MutableLiveData<String>()
    val benAgeGender: LiveData<String>
        get() = _benAgeGender

    private val _recordExists = MutableLiveData<Boolean>()
    val recordExists: LiveData<Boolean>
        get() = _recordExists

    private val dataset = AdolescentHealthFormDataset(context, preferenceDao.getCurrentLanguage())
    val formList = dataset.listFlow

    private var adolescentHealthCache: AdolescentHealthCache? = null

    init {
        viewModelScope.launch {
            val asha = preferenceDao.getLoggedInUser()!!
            // Load existing record if any (e.g., from savedStateHandle or repo)
            val benId = savedStateHandle.get<Long>("benId") ?: -1L
            if (benId != -1L) {
//                adolescentHealthCache = adolescentHealthRepo.getAdolescentHealthRecord(benId)
                adolescentHealthCache?.let {
                    _benName.value = it.name ?: ""
                    _benAgeGender.value = "${it.age ?: ""} years"
                    _recordExists.value = true
                    dataset.setFirstPage(it)
                }
            } else {
                _recordExists.value = false
                dataset.setFirstPage(null)
            }
        }
    }

    fun updateListOnValueChanged(formId: Int, index: Int) {
        viewModelScope.launch {
            dataset.updateList(formId, index)
        }
    }

    fun saveForm() {
        viewModelScope.launch {
            _state.value = State.SAVING
            try {
                val cache = AdolescentHealthCache()
                cache.ashaId = preferenceDao.getLoggedInUser()!!.userId
                dataset.mapValues(cache, 1)
                withContext(Dispatchers.IO) {
                    adolescentHealthRepo.saveAdolescentHealthRecord(cache)
                }
                _state.value = State.SAVE_SUCCESS
            } catch (e: Exception) {
                _state.value = State.SAVE_FAILED
            }
        }
    }

    fun setRecordExist(b: Boolean) {
        _recordExists.value = b
    }
}