package com.example.playerapp.utils

import com.example.playerapp.R
import com.example.playerapp.ui.model.Music
import com.example.playerapp.ui.model.MusicEvent
import com.example.playerapp.ui.model.MusicEventType

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
}