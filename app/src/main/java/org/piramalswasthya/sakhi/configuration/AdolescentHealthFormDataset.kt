package org.piramalswasthya.sakhi.configuration

import android.content.Context
import android.net.Uri
import android.text.InputType
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.piramalswasthya.sakhi.helpers.Languages
import org.piramalswasthya.sakhi.model.FormElement
import org.piramalswasthya.sakhi.model.InputType.DATE_PICKER
import org.piramalswasthya.sakhi.model.InputType.DROPDOWN
import org.piramalswasthya.sakhi.model.InputType.EDIT_TEXT
import org.piramalswasthya.sakhi.model.InputType.RADIO
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
@Entity
data class AdolescentHealthCache(
    @PrimaryKey(autoGenerate = true)
    var id :Int? = null,
    var ashaId :Int? =null,
    var benId:Int?=null,
    var name: String? = null,
    var age: Int? = null,
    var visitDate: Long? = null,
    var healthStatus: String? = null,
    var ifaTabletDistributed: Boolean? = null,
    var ifaTabletQuantity: Int? = null,
    var menstrualHygieneAwareness: Boolean? = null,
    var sanitaryNapkinDistributed: Boolean? = null,
    var noOfPacketsDistributed: Int? = null,
    var place: String? = null,
    var distributionDate: Long? = null,
    var ashaIncentive: Int? = null,
    var referredToHealthFacility: String? = null,
    var counselingProvided: Boolean? = null,
    var counselingType: String? = null,
    var followUpDate: Long? = null,
    var referralStatus: String? = null
) : FormDataModel

class AdolescentHealthFormDataset(context: Context, language: Languages) : Dataset(context, language) {

    companion object {
        private fun getCurrentDateString(): String {
            val calendar = Calendar.getInstance()
            val mdFormat = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
            return mdFormat.format(calendar.time)
        }

        private fun getCurrentDateMillis(): Long {
            return Calendar.getInstance().timeInMillis
        }
    }


    // Form Elements
    private val name = FormElement(
        id = 1,
        inputType = EDIT_TEXT,
        title = "Name",
        arrayId = -1,
        required = true,
        allCaps = true,
        etInputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS
    )

    private val age = FormElement(
        id = 2,
        inputType = EDIT_TEXT,
        title = "Age",
        arrayId = -1,
        required = true,
        etInputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_NORMAL,
        etMaxLength = 2,
        min = 10L,
        max = 19L
    )

    private val visitDate = FormElement(
        id = 3,
        inputType = DATE_PICKER,
        title = "Visit Date",
        arrayId = -1,
        required = true,
        max = getCurrentDateMillis()
    )

    private val healthStatus = FormElement(
        id = 4,
        inputType = DROPDOWN,
        title = "Health Status",
        arrayId = -1,
        entries = arrayOf("Healthy", "Anemic", "Malnourished"),
        required = true,
        hasDependants = true
    )

    private val ifaTabletDistribution = FormElement(
        id = 5,
        inputType = RADIO,
        title = "IFA Tablet Distribution",
        arrayId = -1,
        entries = arrayOf("Yes", "No"),
        required = true,
        hasDependants = true
    )

    private val ifaTabletQuantity = FormElement(
        id = 6,
        inputType = EDIT_TEXT,
        title = "Quantity of IFA Tablets",
        arrayId = -1,
        required = true,
        etInputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_NORMAL,
        min = 1L,
        max = 100L
    )

    private val menstrualHygieneAwareness = FormElement(
        id = 7,
        inputType = RADIO,
        title = "Menstrual Hygiene Awareness",
        arrayId = -1,
        entries = arrayOf("Yes", "No"),
        required = false
    )

    private val sanitaryNapkinDistributed = FormElement(
        id = 8,
        inputType = RADIO,
        title = "Sanitary Napkin Distributed",
        arrayId = -1,
        entries = arrayOf("Yes", "No"),
        required = true,
        hasDependants = true
    )

    private val noOfPacketsDistributed = FormElement(
        id = 9,
        inputType = EDIT_TEXT,
        title = "No. of Packets Distributed",
        arrayId = -1,
        required = true,
        min = 1L,
        etInputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_NORMAL,
        max = 20L
    )

    private val place = FormElement(
        id = 10,
        inputType = DROPDOWN,
        title = "Place",
        arrayId = -1,
        entries = arrayOf("Home", "Community center", "School", "Subcenter"),
        required = true
    )

    private val distributionDate = FormElement(
        id = 11,
        inputType = DATE_PICKER,
        title = "Distribution Date",
        arrayId = -1,
        required = true,
        max = getCurrentDateMillis()
    )

    private val referredToHealthFacility = FormElement(
        id = 12,
        inputType = EDIT_TEXT,
        title = "Referred to Health Facility",
        arrayId = -1,
        required = false,
    )

    private val counselingProvided = FormElement(
        id = 13,
        inputType = RADIO,
        title = "Counseling Provided",
        arrayId = -1,
        entries = arrayOf("Yes", "No"),
        required = true
    )

    private val counselingType = FormElement(
        id = 14,
        inputType = DROPDOWN,
        title = "Counseling Type",
        arrayId = -1,
        entries = arrayOf("Individual", "Group"),
        required = false
    )

    private val followUpDate = FormElement(
        id = 15,
        inputType = DATE_PICKER,
        title = "Follow-up Date",
        arrayId = -1,
        required = true,
        max = getCurrentDateMillis()
    )

    private val referralStatus = FormElement(
        id = 16,
        inputType = DROPDOWN,
        title = "Referral Status",
        arrayId = -1,
        entries = arrayOf("Pending", "Completed"),
        required = false
    )


    private val firstPage: List<FormElement> by lazy {
        listOf(
            name,
            age,
            visitDate,
            healthStatus,
            ifaTabletDistribution,
            menstrualHygieneAwareness,
            sanitaryNapkinDistributed,
            counselingProvided,
            counselingType,
            followUpDate,
            referralStatus
        )
    }

    suspend fun setFirstPage(ben: AdolescentHealthCache?) {
        val list = firstPage.toMutableList()
        if (visitDate.value == null) {
            visitDate.value = getCurrentDateString()
        }
        ben?.let { saved ->
            name.value = saved.name
            age.value = saved.age?.toString()
            visitDate.value = saved.visitDate?.let { getDateFromLong(it) }
            healthStatus.value = saved.healthStatus
            ifaTabletDistribution.value = if (saved.ifaTabletDistributed == true) ifaTabletDistribution.entries!![0] else ifaTabletDistribution.entries!![1]
            ifaTabletQuantity.value = saved.ifaTabletQuantity?.toString()
            menstrualHygieneAwareness.value = if (saved.menstrualHygieneAwareness == true) menstrualHygieneAwareness.entries!![0] else menstrualHygieneAwareness.entries!![1]
            sanitaryNapkinDistributed.value = if (saved.sanitaryNapkinDistributed == true) sanitaryNapkinDistributed.entries!![0] else sanitaryNapkinDistributed.entries!![1]
            noOfPacketsDistributed.value = saved.noOfPacketsDistributed?.toString()
            place.value = saved.place
            distributionDate.value = saved.distributionDate?.let { getDateFromLong(it) }
            referredToHealthFacility.value = saved.referredToHealthFacility
            counselingProvided.value = if (saved.counselingProvided == true) counselingProvided.entries!![0] else counselingProvided.entries!![1]
            counselingType.value = saved.counselingType
            followUpDate.value = saved.followUpDate?.let { getDateFromLong(it) }
            referralStatus.value = saved.referralStatus
        }

        // Dynamic field additions based on dependencies
        if (ifaTabletDistribution.value == ifaTabletDistribution.entries!![0]) {
            list.add(list.indexOf(ifaTabletDistribution) + 1, ifaTabletQuantity)


        }

        if (sanitaryNapkinDistributed.value == sanitaryNapkinDistributed.entries!![0]) {
            list.add(list.indexOf(sanitaryNapkinDistributed) + 1, noOfPacketsDistributed)
            list.add(list.indexOf(noOfPacketsDistributed) + 1, place)
            list.add(list.indexOf(place) + 1, distributionDate)
        }

        if (healthStatus.value == healthStatus.entries!![1] || healthStatus.value == healthStatus.entries!![2]) {
            referredToHealthFacility.required = true
            list.add(list.indexOf(healthStatus) + 1, referredToHealthFacility)
        }

        setUpPage(list)
    }

    override suspend fun handleListOnValueChanged(formId: Int, index: Int): Int {
        return when (formId) {
            name.id -> {
                validateEmptyOnEditText(name)
                validateAllCapsOrSpaceOnEditText(name)
            }
            age.id -> {
                validateEmptyOnEditText(age)
                validateIntMinMax(age)
            }
            visitDate.id -> {
                validateEmptyOnEditText(visitDate)
                -1
            }
            healthStatus.id -> {
                referredToHealthFacility.required = (index == 1 || index == 2)
                triggerDependants(
                    source = healthStatus,
                    addItems = if (index == 1 || index == 2) listOf(referredToHealthFacility) else emptyList(),
                    removeItems = listOf(referredToHealthFacility)
                )
            }
            ifaTabletDistribution.id -> {
                triggerDependants(
                    source = ifaTabletDistribution,
                    passedIndex = index,
                    triggerIndex = 0,
                    target = ifaTabletQuantity
                )
            }
            ifaTabletQuantity.id -> {
                validateEmptyOnEditText(ifaTabletQuantity)
                validateIntMinMax(ifaTabletQuantity)
            }

            sanitaryNapkinDistributed.id -> {
                triggerDependants(
                    source = sanitaryNapkinDistributed,
                    triggerIndex = 0,
                    passedIndex = index,
                    target = listOf(noOfPacketsDistributed,place,distributionDate)

                )
            }
            menstrualHygieneAwareness.id -> -1

            noOfPacketsDistributed.id -> {
                validateEmptyOnEditText(noOfPacketsDistributed)
                validateIntMinMax(noOfPacketsDistributed)
            }

            distributionDate.id -> {
                validateEmptyOnEditText(distributionDate)
                -1
            }
            referredToHealthFacility.id -> {
                validateEmptyOnEditText(referredToHealthFacility)
            }



            counselingProvided.id ->-1
            place.id -> {
                validateEmptyOnEditText(place)
                -1
            }
            counselingType.id -> -1
            followUpDate.id -> {
                validateEmptyOnEditText(visitDate)
                -1
            }

            referralStatus.id -> -1
            else -> -1
        }
    }

    override fun mapValues(cacheModel: FormDataModel, pageNumber: Int) {
        (cacheModel as AdolescentHealthCache).let { ben ->
            ben.name = name.value
            ben.age = age.value?.toInt()
            ben.visitDate = visitDate.value?.let { getLongFromDate(it) }
            ben.healthStatus = healthStatus.value
            ben.ifaTabletDistributed = ifaTabletDistribution.value == ifaTabletDistribution.entries!![0]
            ben.ifaTabletQuantity = ifaTabletQuantity.value?.toInt()
            ben.menstrualHygieneAwareness = menstrualHygieneAwareness.value == menstrualHygieneAwareness.entries!![0]
            ben.sanitaryNapkinDistributed = sanitaryNapkinDistributed.value == sanitaryNapkinDistributed.entries!![0]
            ben.noOfPacketsDistributed = noOfPacketsDistributed.value?.toInt()
            ben.place = place.value
            ben.distributionDate = distributionDate.value?.let { getLongFromDate(it) }
            ben.ashaIncentive = ben.noOfPacketsDistributed?.let { it * 1 } ?: 0 // Incentive: ₹1 per packet
            ben.referredToHealthFacility = referredToHealthFacility.value
            ben.counselingProvided = counselingProvided.value == counselingProvided.entries!![0]
            ben.counselingType = counselingType.value
            ben.followUpDate = followUpDate.value?.let { getLongFromDate(it) }
            ben.referralStatus = referralStatus.value
        }
    }

    fun setImageUriToFormElement(lastImageFormId: Int, dpUri: Uri) {
        // No image fields in this form, but included for consistency
    }
}