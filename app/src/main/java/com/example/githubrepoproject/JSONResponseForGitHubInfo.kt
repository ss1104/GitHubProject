package com.example.githubrepoproject

import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Path

interface JSONResponseForGitHubInfo{

    @GET("orgs/{ORG}/repos")
    fun getGitHubInfo(@Path("ORG") ORG : String) : Call<List<GitHubInfo>>
}