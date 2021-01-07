package wonyong.by.hupyeongasi

import com.google.gson.annotations.SerializedName

data class Summoner(
    @SerializedName("id") var encryptedSummonerId:String,
    @SerializedName("accountId") var accountId:String,
    @SerializedName("puuid") var puuid:String,
    @SerializedName("name") var name:String,
    @SerializedName("profileIconId") var profileIconId:Int,
    @SerializedName("revisionDate") var revisionDate:Long,
    @SerializedName("summonerLevel") var summonerLevel:Int)