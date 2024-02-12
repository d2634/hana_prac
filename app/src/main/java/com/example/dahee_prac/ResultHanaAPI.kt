package com.example.dahee_prac

data class ResultHanaAPI(
    var headerData: HeaderData,
    var data: HanaData
)

data class HeaderData(
    var status: Int,
    var errorCode: String?,
    var errorMessage: String?,
    var errorMessageId: String?,
    var guid: String,
    var url: String,
    var timestamp: String,
    var actionErrorList: List<Any>,
    var other: Map<String, Any>,
    var returnUrl: String?
)

data class HanaData(
    var secretBData: SecretBData,
    var bnkMenuUpdateYn: String,
    var cmsWidget: CmsWidget,
    // 나머지 필드들을 추가
)

data class SecretBData(
    var PatternPrintAuthYn: String,
    var FaceAuthYn: String,
    var FingerPrintAuthYn: String
)

data class CmsWidget(
    var modDate: String?,
    var data: List<CmsWidgetData>
)

data class CmsWidgetData(
    var img: String,
    var content: String,
    var linkUrl: String
)