package org.piramalswasthya.sakhi.database.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.piramalswasthya.sakhi.configuration.AdolescentHealthCache
import org.piramalswasthya.sakhi.helpers.Konstants
import org.piramalswasthya.sakhi.model.BenBasicCache

@Dao
interface AdolescentHealthDao {
    @Query("SELECT * FROM AdolescentHealthCache")
    fun getAllAdolescentHealthList(): Flow<List<AdolescentHealthCache>>

    @Insert
    suspend fun insertAdolescentHealthRecord(cache: AdolescentHealthCache)



}