package com.selincengiz.ritmo.data.mapper

import com.selincengiz.ritmo.data.mapper.Mappers.toTrackUI
import com.selincengiz.ritmo.data.remote.dto.Album
import com.selincengiz.ritmo.data.remote.dto.AlbumResponse
import com.selincengiz.ritmo.data.remote.dto.Artist
import com.selincengiz.ritmo.data.remote.dto.Track
import com.selincengiz.ritmo.domain.model.AlbumUI
import com.selincengiz.ritmo.domain.model.ArtistUI
import com.selincengiz.ritmo.domain.model.TrackAlbumUI
import com.selincengiz.ritmo.domain.model.TrackUI

object Mappers {

    fun Track.toTrackUI(): TrackUI {
        return TrackUI(
            album?.toTrackAlbumUI(),
            artist?.toArtistUI(),
            duration,
            explicitContentCover,
            explicitContentLyrics,
            explicitLyrics,
            id,
            link,
            md5Image,
            preview,
            rank,
            readable,
            title,
            titleShort,
            titleVersion,
            type
        )
    }

    fun Album.toTrackAlbumUI(): TrackAlbumUI {
        return TrackAlbumUI(
            cover,
            coverBig,
            coverMedium,
            coverSmall,
            coverXl,
            id,
            md5Image,
            title,
            tracklist,
            type,
        )
    }

    fun AlbumResponse.toAlbumUI(): AlbumUI {
        return AlbumUI(
            artist?.toArtistUI(),
            available,
            contributors,
            cover,
            coverBig,
            coverMedium,
            coverSmall,
            coverXl,
            duration,
            explicitContentCover,
            explicitContentLyrics,
            explicitLyrics,
            fans,
            genreId,
            id,
            label,
            link,
            md5Image,
            nbTracks,
            recordType,
            releaseDate,
            share,
            title,
            tracklist,
            tracks?.data?.map { it.toTrackUI() },
            type,
            upc,
        )
    }

    fun Artist.toArtistUI(): ArtistUI {
        return ArtistUI(
            id,
            name,
            picture,
            pictureBig,
            pictureMedium,
            pictureSmall,
            pictureXl,
            tracklist,
            type,
        )
    }
}