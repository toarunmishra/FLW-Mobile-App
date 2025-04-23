package org.piramalswasthya.sakhi.ui.home_activity.child_care.adolescent_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import org.piramalswasthya.sakhi.helpers.filterAdolesenctList
import org.piramalswasthya.sakhi.helpers.filterBenList
import org.piramalswasthya.sakhi.repositories.AdolescentHealthRepo
import org.piramalswasthya.sakhi.repositories.RecordsRepo
import javax.inject.Inject

@HiltViewModel
class AdolescentHealthListViewModel @Inject constructor(
    adolescentHealthRepo: AdolescentHealthRepo
) : ViewModel() {
   private  val adolescentHealthList = adolescentHealthRepo.adolescentHealthList
    private val filter = MutableStateFlow("")

    val adolescentHealthList_ = adolescentHealthList.combine(filter) { list, filter ->
        filterAdolesenctList(list, filter)
    }


    fun filterText(text: String) {
        viewModelScope.launch {
            filter.emit(text)
        }

    }
}