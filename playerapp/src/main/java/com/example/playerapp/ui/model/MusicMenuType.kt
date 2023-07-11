package com.example.playerapp.ui.model


sealed class MusicMoreMenu(val musicMoreMenuItem: MusicMoreMenuItem) {
    class AddFree(item: MusicMoreMenuItem) : MusicMoreMenu(item)
    class Like(item: MusicMoreMenuItem) : MusicMoreMenu(item)
    class Hide(item: MusicMoreMenuItem) : MusicMoreMenu(item)
    class AddToPlaylist(item: MusicMoreMenuItem) : MusicMoreMenu(item)
    class AddToQueue(item: MusicMoreMenuItem) : MusicMoreMenu(item)
    class ViewAlbum(item: MusicMoreMenuItem) : MusicMoreMenu(item)
    class ViewArtist(item: MusicMoreMenuItem) : MusicMoreMenu(item)
    class Share(item: MusicMoreMenuItem) : MusicMoreMenu(item)
    class ShowCredits(item: MusicMoreMenuItem) : MusicMoreMenu(item)
    class ShowSpotifyCode(item: MusicMoreMenuItem) : MusicMoreMenu(item)
}