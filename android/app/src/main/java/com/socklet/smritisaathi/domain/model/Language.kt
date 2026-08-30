package com.socklet.smritisaathi.domain.model

data class Language(
    val code: String,
    val nameInEnglish: String,
    val nativeName: String,
    val region: String,
    val audioSampleResId: Int? = null
)

val SUPPORTED_LANGUAGES = listOf(
    Language(
        code = "en",
        nameInEnglish = "English",
        nativeName = "English",
        region = "Pan-India",
        audioSampleResId = null
    ),
    Language(
        code = "hi",
        nameInEnglish = "Hindi",
        nativeName = "हिंदी",
        region = "Pan-India",
        audioSampleResId = null
    ),
    Language(
        code = "as",
        nameInEnglish = "Assamese",
        nativeName = "অসমীয়া",
        region = "Assam",
        audioSampleResId = null
    ),
    Language(
        code = "brx",
        nameInEnglish = "Bodo",
        nativeName = "बोड़ो",
        region = "Assam",
        audioSampleResId = null
    ),
    Language(
        code = "kha",
        nameInEnglish = "Khasi",
        nativeName = "খাচি",
        region = "Meghalaya",
        audioSampleResId = null
    ),
    Language(
        code = "gar",
        nameInEnglish = "Garo",
        nativeName = "গারো",
        region = "Meghalaya",
        audioSampleResId = null
    ),
    Language(
        code = "lus",
        nameInEnglish = "Mizo",
        nativeName = "মিজো",
        region = "Mizoram",
        audioSampleResId = null
    ),
    Language(
        code = "mni",
        nameInEnglish = "Manipuri",
        nativeName = "মেইতেই",
        region = "Manipur",
        audioSampleResId = null
    ),
    Language(
        code = "nmx",
        nameInEnglish = "Nagamese",
        nativeName = "নাগামিজ",
        region = "Nagaland",
        audioSampleResId = null
    )
)
