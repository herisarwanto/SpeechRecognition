package com.englishlearningwithngram.model.api

import com.englishlearningwithngram.model.RegisterResponse
import com.englishlearningwithngram.model.User
import com.englishlearningwithngram.model.UserItem
import com.englishlearningwithngram.model.WordsItem
import com.example.retrofittest.model.Post
import retrofit2.Response
import retrofit2.http.*

interface ApiServices {
    /**
     * Start example DAO
     */
    @GET("posts/1")
    suspend fun getPost(): Response<Post>

    @GET("posts/{postNumber}")
    suspend fun getPost2(
        @Path("postNumber") number: Int
    ): Response<Post>

    @GET("posts")
    suspend fun getCustomPosts(
        @Query("userId") userId: Int,
        @Query("_sort") sort: String,
        @Query("_order") order: String
    ): Response<List<Post>>

    @GET("posts")
    suspend fun getCustomPosts2(
        @Query("userId") userId: Int,
        @QueryMap options: Map<String, String>
    ): Response<List<Post>>

    @POST("posts")
    suspend fun pushPost(
        @Body post: Post
    ): Response<Post>

    @FormUrlEncoded
    @POST("posts")
    suspend fun pushPost2(
        @Field("userId") userId: Int,
        @Field("id") id: Int,
        @Field("title") title: String,
        @Field("body") body: String
    ): Response<Post>

    /**
     * End example DAO
     */
    @FormUrlEncoded
    @POST("index.php/Authentication/register")
    suspend fun registerUser(
            @Field("nama") nama: String,
            @Field("email") email: String,
            @Field("username") username: String,
            @Field("password") password: String
    ) : Response<RegisterResponse>

    @FormUrlEncoded
    @POST("index.php/Authentication/login")
    suspend fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<UserItem>

    @GET("index.php/Word")
    suspend fun getWordsPerLevel(
        @Query("level") level: Int
    ): Response<WordsItem>
}




















