package com.coors.commoncore.model

import android.os.Parcelable
import androidx.annotation.IntRange
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*


@JsonClass(generateAdapter = true)
data class AnchorWrapper(
    val data : List<AnchorModel>
)

@JsonClass(generateAdapter = true)
data class AnchorModel(
    val awayLogo: String?,
    val awayName: String?,
    val awayScore: String?,
    val gameTime: String?,
    val guestTeamName: String?,
    val half: String?,
    val homeLogo: String?,
    val homeName: String?,
    val homeScore: String?,
    val hostId: Int?,
    val hostName: String?,
    val hostPhotoId: String?,
    val league: String?,
    val liveRoomId: Int?,
    @Json(name = "liveStatus")
    private val liveStatusValue: Int?,
    val liveStreamId: String?,
    val masterTeamName: String?,
    val matchId: String,
    val matchType: String?,
    val multiLanguageMasterTeamName: MultiLanguageModel?,
    val multiLanguageMatchLeague: MultiLanguageModel?,
    val startDate: Long?,
    val videoSource: VideoSource?,
    val voidSourceUrl: String?,
    val hostTags: String?,
    val subscribeStatus: Int?,
    val popularity: String?,
    val backgroundImage: String?,
    val hostLanguage: Int?,
    val hostPictureId: String?,
    val sort: Int?,
    val watcherCount: Int?,
    val activityStatus: Int?,
    // @20/12/2020 新增
    val benefitsAssistantSwitch: Int?,
    val gamePart: String?,
    val redEnvelopeTotalAmount: Int?,
    val liveUserGroupId: Int?,
    val liveHostType: Int?,
    val liveHostTypeShow: String?
) : Serializable {
    /**
     * 是否正在直播
     */
    fun isLiving() = liveStatusValue == LiveStatus.LIVING.status

    fun getPhotoUrl(): String {
        return "https://picsum.photos/id/237/200/300"
//        "http://m.aalgds.com/api/forehead/system/file/get/${hostPhotoId.orEmpty()}"
    }

    /**
     * 是否已訂閱
     */
    fun isBooking(): Boolean = subscribeStatus == 1

    fun getDisplayTime(): String = SimpleDateFormat("MM-dd HH:mm").format(Date(startDate ?: 0) )

    fun getVsTeamText() = "${homeName.orEmpty()} VS ${awayName.orEmpty()}"
}

/**
 * @author gittar
 * 直播状态
 */
enum class LiveStatus(val status: Int, val desc: String) {
    NONE(-1, "暂无主播"),

    READY(0, "即将开始"),

    LIVING(1, "直播中"),

    FINISH(2, "已结束");

    companion object {

        fun valueOf(@IntRange(from = 0, to = 2) status: Int): LiveStatus {
            values().forEach {
                if (it.status == status) {
                    return it
                }
            }
            return NONE
        }
    }
}

/**
 * 视频源地址
 */
data class VideoSource(
    val rtmp: String,
    val flv: String,
    val m3u8: String
) : Serializable

@Parcelize
data class MultiLanguageModel(
    val en: String,
    val vietnam: String,
    val zh_CN: String
) : Parcelable

data class MultipleImages(
    val hasTwoFormatImages: Boolean,
    val hasGifImage: Boolean,
    val noGifImage: String,
    val gifImage: String
) {
    companion object {

        private const val FORMAT_GIT_LOW_CASE = ".gif"
        private const val FORMAT_GIT_UP_CASE = ".GIF"

        /**
         * 轉為 多圖片
         */
        fun convertMultipleImages(multipleImagesUrl: String): MultipleImages {
            val split = multipleImagesUrl.split(",")
            val gifFilter =
                split.filter { it.contains(FORMAT_GIT_LOW_CASE) || it.contains(FORMAT_GIT_UP_CASE) }
            val notGifFilter =
                split.filter { !it.contains(FORMAT_GIT_LOW_CASE) || !it.contains(FORMAT_GIT_UP_CASE) }
            return MultipleImages(
                hasTwoFormatImages = gifFilter.isNotEmpty() && notGifFilter.isNotEmpty(),
                hasGifImage = gifFilter.isNotEmpty(),
                noGifImage = notGifFilter.firstOrNull() ?: "",
                gifImage = gifFilter.firstOrNull() ?: ""
            )
        }
    }
}