package com.example.playerapp.utils

import com.example.playerapp.R
import com.example.playerapp.ui.model.Album
import com.example.playerapp.ui.model.Music
import com.example.playerapp.ui.model.MusicEvent
import com.example.playerapp.ui.model.MusicEventType
import com.example.playerapp.ui.model.SocialNetwork

object DataHelper {
    val recentlyPlayedMusicList =
        listOf(
            Music("mega hit mix", imageDrawable = R.drawable.img_music_2),
            Music("la bede - remi", imageDrawable = R.drawable.img_music_5),
            Music("un x 100to", imageDrawable = R.drawable.img_music_6),
            Music("orxan zeynalli", imageDrawable = R.drawable.img_music_7),
        )

    val events = listOf(
        MusicEvent(
            title = "About the artist",
            desc = "24,419,528 monthly listeners",
            text = "An internet based vocalist, producer, writer, direct and performance artist,based vocalist, producer, writer, director and performance artist, Oliver Tree",
            type = MusicEventType.ABOUT,
            imageDrawable = R.drawable.img_about_artist
        ),
        MusicEvent(
            title = "Jun 9 - Aug 25",
            desc = "4 events on tour",
            type = MusicEventType.EVENT,
            imageDrawable = R.drawable.img_event
        )
    )

    val album = Album(
        artistName = "Oliver Tree",
        posterDrawable = R.drawable.img_about_artist,
        monthlyListenersCount = 24_419_528,
        worldPlace = 181,
        bio = "A Santa Cruz, California native, Tree has emerged as a polymath from many different projects and iterations over the last 10 years. As unpredictable as one artist can be, no one can seem to put their finger on what Oliver Tree will do next. Unafraid to make you laugh, cry, think profoundly or feel completely uncomfortable for the length of a 4 minute music video, he is on the road to developing his own blueprint for packaging and marketing pop culture in the internet era. Versatile in every sense of the word, Tree not only explores every type of entertainment but also every type of genre in his music alike. The box he puts himself in is limitless. It has no boundaries. Oliver Tree has built a multimedia project designed to challenge people's perspective of what art is, and he's not the slightest bit concerned what anyone has to say about it!",
        artistDrawable = R.drawable.img_artist
    )

    val socialNetworks =
        listOf(
            SocialNetwork(name = "Instagram", iconDrawable = R.drawable.ic_instagram),
            SocialNetwork(name = "Twitter", iconDrawable = R.drawable.ic_twitter),
            SocialNetwork(name = "Facebook", iconDrawable = R.drawable.ic_facebook),
        )
}