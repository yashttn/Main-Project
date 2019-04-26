package com.example.mainproject.presenters.albums

import com.example.mainproject.models.firebase.FirebaseInteractorImpl
import com.example.mainproject.models.firebase.IFirebaseInteractor
import com.example.mainproject.models.pojo.Album
import com.example.mainproject.views.albums.AlbumsFragment

class AlbumsPresenterImpl(albumsFragment: AlbumsFragment) : IAlbumsPresenter, IFirebaseInteractor.IGetAlbumsList.IAlbumsListFinished {

    private var getAlbumsListFirebase: IFirebaseInteractor.IGetAlbumsList = FirebaseInteractorImpl()
    private var albumsView: IAlbumsView = albumsFragment

    override fun getAlbumsList() {
        getAlbumsListFirebase.getAlbums(this)
    }

    override fun getAlbumsSuccess(albumsList: ArrayList<Album>) {
        albumsView.updatedAlbumsList(albumsList)
    }

    override fun getAlbumsFailure() {
        albumsView.getAlbumsFailure("Operation Failed!")
    }
}