package com.selincengiz.ritmo.data.remote.dto


class ListPlaylistUI {
    var id: String = ""
    var name: String = ""
    var tracks: MutableList<ListTrackUI> = mutableListOf()
}

class ListTrackUI {
    var album: ListTrackAlbumUI? = ListTrackAlbumUI()
    var artist: ListArtistUI? = ListArtistUI()
    var duration: String? = ""
    var explicitContentCover: Int? = 0
    var explicitContentLyrics: Int = 0
    var explicitLyrics: Boolean? = false
    var id: String? = ""
    var link: String? = ""
    var md5Image: String? = ""
    var preview: String? = ""
    var rank: String? = ""
    var readable: Boolean? = false
    var title: String? = ""
    var titleShort: String? = ""
    var titleVersion: String? = ""
    var type: String = ""
}

class ListArtistUI {
    var id: String? = ""
    var name: String? = ""
    var picture: String? = ""
    var pictureBig: String? = ""
    var pictureMedium: String? = ""
    var pictureSmall: String? = ""
    var pictureXl: String? = ""
    var tracklist: String? = ""
    var type: String? = ""
}

class ListTrackAlbumUI {
    var cover: String? = ""
    var coverBig: String? = ""
    var coverMedium: String? = ""
    var coverSmall: String? = ""
    var coverXl: String? = ""
    var id: String? = ""
    var md5Image: String? = ""
    var title: String? = ""
    var tracklist: String? = ""
    var type: String? = ""
}