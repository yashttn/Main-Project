package com.example.mainproject.views.albums

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.mainproject.R
import com.example.mainproject.adapters.AlbumsAdapter
import com.example.mainproject.models.pojo.Album
import com.example.mainproject.presenters.albums.AlbumsPresenterImpl
import com.example.mainproject.presenters.albums.IAlbumsPresenter
import com.example.mainproject.presenters.albums.IAlbumsView
import kotlinx.android.synthetic.main.fragment_albums_screen.*

class AlbumsFragment : Fragment(), IAlbumsView {

    private var albumsAdapter = AlbumsAdapter()
    lateinit var albumsPresenter: IAlbumsPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_albums_screen, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        buildAlbumsGridView()
        albumsPresenter = AlbumsPresenterImpl(this)
        albumsPresenter.getAlbumsList()
    }

    private fun buildAlbumsGridView() {
        rv_grid_albums.layoutManager = GridLayoutManager(this.context, 2)
        val albumsList = ArrayList<Album>()
        /*for (album in 1..10) {
            albumsList.add(Album("album",
                "Empty",
                "album_background"))
        }*/
        albumsAdapter.albumsList = albumsList
        rv_grid_albums.adapter = albumsAdapter
    }

    override fun updatedAlbumsList(updatedList: ArrayList<Album>) {
        albumsAdapter.albumsList = updatedList
        albumsAdapter.notifyDataSetChanged()
    }

    override fun getAlbumsFailure(exception: String) {
        Toast.makeText(activity, exception, Toast.LENGTH_SHORT).show()
    }
}