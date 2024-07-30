package com.selincengiz.ritmo.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.selincengiz.ritmo.data.local.entities.ArtistEntity
import com.selincengiz.ritmo.data.local.entities.TrackAlbumEntity

@ProvidedTypeConverter
class RitmoTypeConverter {

    @TypeConverter
    fun albumToString(album: TrackAlbumEntity): String {
        return "${album.cover},${album.coverBig},${album.coverMedium},${album.coverSmall},${album.coverXl},${album.id},${album.md5Image},${album.title},${album.tracklist},${album.type}"
    }

    @TypeConverter
    fun stringToAlbum(album: String): TrackAlbumEntity {
        return album.split(',').let { sourceArray ->
            TrackAlbumEntity(
                cover = sourceArray[0],
                coverBig = sourceArray[1],
                coverMedium = sourceArray[2],
                coverSmall = sourceArray[3],
                coverXl = sourceArray[4],
                id = sourceArray[5],
                md5Image = sourceArray[6],
                title = sourceArray[7],
                tracklist = sourceArray[8],
                type = sourceArray[9]
            )
        }
    }

    @TypeConverter
    fun artistToString(artist: ArtistEntity): String {
        return "${artist.id},${artist.name},${artist.picture},${artist.pictureBig},${artist.pictureMedium},${artist.pictureSmall},${artist.pictureXl},${artist.tracklist},${artist.type}"
    }

    @TypeConverter
    fun stringToArtist(artist: String): ArtistEntity {
        return artist.split(',').let { sourceArray ->
            ArtistEntity(
                id = sourceArray[0],
                name = sourceArray[1],
                picture = sourceArray[2],
                pictureBig = sourceArray[3],
                pictureMedium = sourceArray[4],
                pictureSmall = sourceArray[5],
                pictureXl = sourceArray[6],
                tracklist = sourceArray[7],
                type = sourceArray[8]
            )
        }
    }
}