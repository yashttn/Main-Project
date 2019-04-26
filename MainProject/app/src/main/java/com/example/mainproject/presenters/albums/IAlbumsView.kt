package com.example.mainproject.presenters.albums

import com.example.mainproject.models.pojo.Album

interface IAlbumsView {
    fun updatedAlbumsList(updatedList: ArrayList<Album>)
    fun getAlbumsFailure(exception: String)
}