package org.piramalswasthya.sakhi.repositories

import kotlinx.coroutines.flow.map
import org.piramalswasthya.sakhi.configuration.AdolescentHealthCache
import org.piramalswasthya.sakhi.database.room.dao.AdolescentHealthDao
import org.piramalswasthya.sakhi.model.asAdolescentHealthDomain
import javax.inject.Inject

class AdolescentHealthRepo @Inject constructor(
    private val adolescentHealthDao: AdolescentHealthDao
) {

    suspend fun saveAdolescentHealthRecord(cache: AdolescentHealthCache) {
        adolescentHealthDao.insertAdolescentHealthRecord(cache)
    }


     val adolescentHealthList =  adolescentHealthDao.getAllAdolescentHealthList().map {list->list.map { it.asAdolescentHealthDomain() }  }
    val adolescentHealthListCount = adolescentHealthList.map { it.size }



}