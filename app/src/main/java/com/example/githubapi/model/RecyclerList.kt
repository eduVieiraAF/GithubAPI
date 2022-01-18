package com.example.githubapi.model

import com.google.gson.annotations.SerializedName


class RecyclerList(val items: List<RecyclerData>)
class RecyclerData(
    @SerializedName("name") val name: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("owner") val owner: Owner?,
    @SerializedName("forks_count") val forksCount: String?,
    @SerializedName("stargazers_count") val stargazersCount: String?)

class Owner(val login: String?, @SerializedName("avatar_url") val avatarUrl: String?)