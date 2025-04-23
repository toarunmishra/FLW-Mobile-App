package org.piramalswasthya.sakhi.configuration

import android.content.Context
import org.piramalswasthya.sakhi.helpers.Languages
import org.piramalswasthya.sakhi.model.FormElement
import org.piramalswasthya.sakhi.model.InputType

 class MalariaFormDataSet(
    context: Context, currentLanguage: Languages
) : Dataset(context, currentLanguage) {
    private val houseHoldNo = FormElement(
        id = 1,
        inputType = InputType.EDIT_TEXT,
        title = "House Hold No.",
        required = true
    )

    private val headOfFamilyName = FormElement(
        id = 2,
        inputType = InputType.EDIT_TEXT,
        title = "Head of Family Member Name",
        required = true
    )

    private val mobileNo = FormElement(
        id = 3,
        inputType = InputType.EDIT_TEXT,
        title = "Mobile No.",
        etInputType = android.text.InputType.TYPE_CLASS_NUMBER,
        required = true
    )

    private val name = FormElement(
        id = 4,
        inputType = InputType.EDIT_TEXT,
        title = "Name",
        required = true,
        isEnabled = false // Auto-populated
    )

    private val fatherOrHusbandName = FormElement(
        id = 5,
        inputType = InputType.EDIT_TEXT,
        title = "Father's Name / Husband Name",
        required = true,
        isEnabled = false // Auto-populated
    )

    private val age = FormElement(
        id = 6,
        inputType = InputType.EDIT_TEXT,
        title = "Age",
        etInputType = android.text.InputType.TYPE_CLASS_NUMBER,
        required = true,
        isEnabled = false // Auto-populated
    )

    private val gender = FormElement(
        id = 7,
        inputType = InputType.EDIT_TEXT,
        title = "Gender",
        required = true,
        isEnabled = false // Auto-populated
    )

    private val abhaId = FormElement(
        id = 8,
        inputType = InputType.EDIT_TEXT,
        title = "ABHA ID",
        required = false,
        isEnabled = false // Auto-populated
    )

    private val caseDate = FormElement(
        id = 9,
        inputType = InputType.DATE_PICKER,
        title = "Case Date",
        required = true,
        isEnabled = false, // Auto-populated as today's date
        max = System.currentTimeMillis()
    )

    private val caseStatus = FormElement(
        id = 10,
        inputType = InputType.EDIT_TEXT,
        title = "Case Status",
        entries = arrayOf("Suspected", "Confirmed", "Not Confirmed", "Treatment Given"),
        required = true
    )

    private val symptoms = FormElement(
        id = 11,
        inputType = InputType.EDIT_TEXT,
        title = "Symptoms",
        required = false
    )

    private val malariaCaseCount = FormElement(
        id = 12,
        inputType = InputType.EDIT_TEXT,
        title = "Malaria Case Count (Variable)",
        etInputType = android.text.InputType.TYPE_CLASS_NUMBER,
        required = false
    )

    private val referredTo = FormElement(
        id = 13,
        inputType = InputType.EDIT_TEXT,
        title = "Referred To",
        entries = arrayOf(
            "Primary Health Centre",
            "Community Health Centre",
            "District Hospital",
            "Medical College and Hospital",
            "Referral Hospital",
            "Other Private Hospital",
            "Other",
            "None"
        ),
        required = false
    )

    private val otherReferred = FormElement(
        id = 14,
        inputType = InputType.EDIT_TEXT,
        title = "Other",
        required = false,
        isEnabled = false // Enabled only if "Referred To" = "Other"
    )

    private val malariaCaseStatusDate = FormElement(
        id = 15,
        inputType = InputType.DATE_PICKER,
        title = "Malaria Case Status Date",
        required = false,
        isEnabled = false
    )

     override suspend fun handleListOnValueChanged(formId: Int, index: Int): Int {
         TODO("Not yet implemented")
     }

     override fun mapValues(cacheModel: FormDataModel, pageNumber: Int) {
         TODO("Not yet implemented")
     }
 }