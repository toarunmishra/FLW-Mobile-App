package org.piramalswasthya.sakhi.model

import org.piramalswasthya.sakhi.configuration.AdolescentHealthCache

data class AdolescentHealthDomain(
    val id: Int? = null,
    val benId: Int? = null,
    val name: String? = null,
    val age: Int? = null,
    val visitDate: Long? = null,
    val visitDateFormatted: String = visitDate?.let { formatDate(it) } ?: "N/A",
    val healthStatus: String? = null,
    val ifaTabletDistributed: Boolean? = null,
    val ifaTabletQuantity: Int? = null,
    val menstrualHygieneAwareness: Boolean? = null,
    val sanitaryNapkinDistributed: Boolean? = null,
    val noOfPacketsDistributed: Int? = null,
    val place: String? = null,
    val distributionDate: Long? = null,
    val distributionDateFormatted: String = distributionDate?.let { formatDate(it) } ?: "N/A",
    val ashaIncentive: Int? = null,
    val referredToHealthFacility: String? = null,
    val counselingProvided: Boolean? = null,
    val counselingType: String? = null,
    val followUpDate: Long? = null,
    val followUpDateFormatted: String = followUpDate?.let { formatDate(it) } ?: "N/A",
    val referralStatus: String? = null,
    var syncState: SyncState? = null
)

// Placeholder helper function to format timestamps
fun formatDate(timestamp: Long): String {
    // Example: Convert timestamp to "dd-MM-yyyy" format
    val sdf = java.text.SimpleDateFormat("dd-MM-yyyy", java.util.Locale.getDefault())
    return sdf.format(java.util.Date(timestamp))
}

// Placeholder SyncState enum (adjust based on your actual implementation)
enum class SyncState {
    SYNCED, PENDING, FAILED
}

fun AdolescentHealthCache.asAdolescentHealthDomain(): AdolescentHealthDomain {
    return AdolescentHealthDomain(
        id = id,
        benId = benId,
        name = name?.takeIf { it.isNotEmpty() } ?: "Not Available",
        age = age ?: 0,
        visitDate = visitDate,
        healthStatus = healthStatus?.takeIf { it.isNotEmpty() } ?: "Not Available",
        ifaTabletDistributed = ifaTabletDistributed ?: false,
        ifaTabletQuantity = ifaTabletQuantity ?: 0,
        menstrualHygieneAwareness = menstrualHygieneAwareness ?: false,
        sanitaryNapkinDistributed = sanitaryNapkinDistributed ?: false,
        noOfPacketsDistributed = noOfPacketsDistributed ?: 0,
        place = place?.takeIf { it.isNotEmpty() } ?: "Not Available",
        distributionDate = distributionDate,
        ashaIncentive = ashaIncentive ?: 0,
        referredToHealthFacility = referredToHealthFacility?.takeIf { it.isNotEmpty() } ?: "Not Available",
        counselingProvided = counselingProvided ?: false,
        counselingType = counselingType?.takeIf { it.isNotEmpty() } ?: "Not Available",
        followUpDate = followUpDate,
        referralStatus = referralStatus?.takeIf { it.isNotEmpty() } ?: "Not Available",
    )
}