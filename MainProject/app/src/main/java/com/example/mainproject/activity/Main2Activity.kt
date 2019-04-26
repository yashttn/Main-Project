package com.example.mainproject.activity

import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import com.example.mainproject.R
import com.example.mainproject.views.albums.AlbumsFragment
import com.example.mainproject.views.timeline.TimelineFragment
import com.example.mainproject.views.user_profile.UserProfileFragment
import kotlinx.android.synthetic.main.activity_main2.*

class Main2Activity : AppCompatActivity() {

    private lateinit var fragmentManager: FragmentManager
    private lateinit var albumsFragment: AlbumsFragment
    private lateinit var timelineFragment: TimelineFragment
    private lateinit var userProfileFragment: UserProfileFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        fragmentManager = supportFragmentManager

        albumsFragment = AlbumsFragment()
        timelineFragment = TimelineFragment()
        userProfileFragment = UserProfileFragment()

        fragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_second, albumsFragment)
            .commit()

        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_albums -> loadAlbumFragment()
                R.id.navigation_timeline -> loadTimelineFragment()
                R.id.navigation_profile -> loadUserProfileFragment()
                else -> loadAlbumFragment()
            }
        }
    }

    private fun loadAlbumFragment(): Boolean {
        fragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_second, albumsFragment)
            .commit()
        return true
    }

    private fun loadTimelineFragment(): Boolean {
        fragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_second, timelineFragment)
            .commit()
        return true
    }

    private fun loadUserProfileFragment(): Boolean {
        fragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_second, userProfileFragment)
            .commit()
        return true
    }


}
