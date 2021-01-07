package wonyong.by.hupyeongasi

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface API {

    @GET("lol/summoner/v4/summoners/by-name/{summonerName}")
    fun getsummoner(
        @Path("summonerName") summonerName : String,
        @Query("api_key") api_key : String
    ): Call<Summoner>

    @GET("lol/spectator/v4/active-games/by-summoner/{encryptedSummonerId}")
    fun getspectator(
        @Path("encryptedSummonerId") encryptedSummonerId : String?,
        @Query("api_key") api_key : String
    ): Call<Spectator>
}