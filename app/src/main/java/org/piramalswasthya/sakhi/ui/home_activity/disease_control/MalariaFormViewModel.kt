package org.piramalswasthya.sakhi.ui.home_activity.disease_control

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
import org.piramalswasthya.sakhi.configuration.MalariaFormDataSet
import org.piramalswasthya.sakhi.configuration.PregnantWomanRegistrationDataset
import org.piramalswasthya.sakhi.database.room.SyncState
import org.piramalswasthya.sakhi.database.shared_preferences.PreferenceDao
import org.piramalswasthya.sakhi.model.HRPPregnantAssessCache
import org.piramalswasthya.sakhi.model.PregnantWomanRegistrationCache
import org.piramalswasthya.sakhi.repositories.BenRepo
import org.piramalswasthya.sakhi.repositories.EcrRepo
import org.piramalswasthya.sakhi.repositories.HRPRepo
import org.piramalswasthya.sakhi.repositories.MaternalHealthRepo
import org.piramalswasthya.sakhi.ui.home_activity.maternal_health.pregnant_women_registration.form.PregnancyRegistrationFormFragmentArgs
import org.piramalswasthya.sakhi.utils.HelperUtil.getDiffYears
import timber.log.Timber
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class MalariaFormViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    preferenceDao: PreferenceDao,
    @ApplicationContext context: Context,
    private val maternalHealthRepo: MaternalHealthRepo,
    ecrRepo: EcrRepo,
    private val hrpRepo: HRPRepo,
    private val benRepo: BenRepo
//    private val householdRepo: HouseholdRepo,
//    userRepo: UserRepo
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

    //    private lateinit var user: UserDomain
    private val dataset =
        MalariaFormDataSet(context, preferenceDao.getCurrentLanguage())
    val formList = dataset.listFlow

    private lateinit var pregnancyRegistrationForm: PregnantWomanRegistrationCache

    private var assess: HRPPregnantAssessCache? = null

    init {
        viewModelScope.launch {
            val asha = preferenceDao.getLoggedInUser()!!





        }
    }

    fun updateListOnValueChanged(formId: Int, index: Int) {
        viewModelScope.launch {
            dataset.updateList(formId, index)
        }

    }

    

    fun setRecordExist(b: Boolean) {
        _recordExists.value = b

    }


}