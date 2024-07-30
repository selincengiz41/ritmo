package com.selincengiz.ritmo.data.mapper

import com.selincengiz.ritmo.data.local.entities.ArtistEntity
import com.selincengiz.ritmo.data.local.entities.TrackAlbumEntity
import com.selincengiz.ritmo.data.local.entities.TrackEntity
import com.selincengiz.ritmo.data.remote.dto.TrackAlbumDto
import com.selincengiz.ritmo.data.remote.dto.AlbumResponse
import com.selincengiz.ritmo.data.remote.dto.ArtistDto
import com.selincengiz.ritmo.data.remote.dto.TrackDto
import com.selincengiz.ritmo.domain.model.AlbumUI
import com.selincengiz.ritmo.domain.model.ArtistUI
import com.selincengiz.ritmo.domain.model.TrackAlbumUI
import com.selincengiz.ritmo.domain.model.TrackUI

object Mappers {

    fun TrackDto.toTrackUI(): TrackUI {
        return TrackUI(
            trackAlbumDto?.toTrackAlbumUI(),
            artistDto?.toArtistUI(),
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

    fun TrackEntity.toTrackUI(): TrackUI {
        return TrackUI(
            albumEntity?.toTrackAlbumUI(),
            artistEntity?.toArtistUI(),
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

    fun TrackUI.toTrackEntity(): TrackEntity {
        return TrackEntity(
            album?.toTrackAlbumEntity(),
            artist?.toArtistEntity(),
            duration,
            explicitContentCover,
            explicitContentLyrics,
            explicitLyrics,
            id?:"",
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

    private fun TrackAlbumDto.toTrackAlbumUI(): TrackAlbumUI {
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

    private fun TrackAlbumEntity.toTrackAlbumUI(): TrackAlbumUI {
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

    private fun TrackAlbumUI.toTrackAlbumEntity(): TrackAlbumEntity {
        return TrackAlbumEntity(
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
            artistDto?.toArtistUI(),
            available,
            contributorDtos,
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
            tracksDto?.data?.map { it.toTrackUI() },
            type,
            upc,
        )
    }

    private fun ArtistDto.toArtistUI(): ArtistUI {
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

    private fun ArtistEntity.toArtistUI(): ArtistUI {
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

    private fun ArtistUI.toArtistEntity(): ArtistEntity {
        return ArtistEntity(
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