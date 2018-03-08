package com.yjkj.chainup.net.api

import com.fengniao.wanandroid.bean.ArticleList
import com.fengniao.wanandroid.bean.BannerData
import com.fengniao.wanandroid.net.api.HttpResult
import com.google.gson.JsonObject
import io.reactivex.Observable
import retrofit2.http.*


interface ApiService {

    @GET("banner/json")
    fun getBanner(): Observable<HttpResult<MutableList<BannerData>>>


    @GET("article/list/{page}/json")
    fun getArticleList(@Path("page") page: Int): Observable<HttpResult<MutableList<ArticleList>>>

}
