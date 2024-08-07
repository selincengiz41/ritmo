package com.selincengiz.ritmo.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.selincengiz.ritmo.data.local.DownloadDao
import com.selincengiz.ritmo.data.local.RitmoDao
import com.selincengiz.ritmo.data.local.entities.DownloadedSong
import com.selincengiz.ritmo.data.mapper.Mappers.toAlbumUI
import com.selincengiz.ritmo.data.mapper.Mappers.toPlaylistUI
import com.selincengiz.ritmo.data.mapper.Mappers.toTrackEntity
import com.selincengiz.ritmo.data.mapper.Mappers.toTrackUI
import com.selincengiz.ritmo.data.remote.RitmoApi
import com.selincengiz.ritmo.data.remote.SearchPagingSource
import com.selincengiz.ritmo.data.remote.dto.ListPlaylistUI
import com.selincengiz.ritmo.domain.model.AlbumUI
import com.selincengiz.ritmo.domain.model.PlaylistUI
import com.selincengiz.ritmo.domain.model.TrackUI
import com.selincengiz.ritmo.domain.repository.RitmoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class RitmoRepositoryImpl(
    private val api: RitmoApi,
    private val dao: RitmoDao,
    private val daoDownloaded: DownloadDao,
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : RitmoRepository {
    override  fun search(q: String):  Flow<PagingData<TrackUI>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                SearchPagingSource(
                    ritmoApi = api,
                    searchQuery = q
                )
            }
        ).flow
    }

    override suspend fun getAlbum(id: String): AlbumUI {
        return api.getAlbum(id).toAlbumUI()
    }

    override suspend fun getTrack(id: String): TrackUI {
        return api.getTrack(id).toTrackUI()
    }

    override suspend fun insertTrack(track: TrackUI) {
        dao.insert(track.toTrackEntity())
    }

    override suspend fun deleteTrack(track: TrackUI) {
        dao.delete(track.toTrackEntity())
    }

    //TODO:Uygulama patlıyor bazen flowdan
    override fun getTracksLocal(): Flow<List<TrackUI>> {
        return dao.getTracks().map { it -> it.map { it.toTrackUI() } }
    }

    override suspend fun getTrackLocal(id: String): TrackUI? {
        return dao.getTrack(id)?.toTrackUI()
    }

    override suspend fun getPlaylist(id: String): PlaylistUI? {
        val playlistsRef = firestore
            .collection("users")
            .document(auth.currentUser!!.uid)
            .collection("playlists")
            .document(id)
        try {
            val playlistsSnapshot = playlistsRef.get().await()
            if (playlistsSnapshot != null && playlistsSnapshot.exists()) {
                return playlistsSnapshot.toObject(ListPlaylistUI::class.java)?.toPlaylistUI()
            }
        } catch (e: Exception) {
            Log.i("getPlaylists", e.message.toString())
        }
        return null
    }

    override suspend fun getPlaylists(): List<PlaylistUI?> {
        val playlistsRef = firestore
            .collection("users")
            .document(auth.currentUser!!.uid)
            .collection("playlists")
        return try {
            val playlistsSnapshot = playlistsRef.get().await()
            val tempList = playlistsSnapshot.documents.map { doc ->
                doc.toObject(ListPlaylistUI::class.java)
            }.toMutableList()

            for (i in 1..playlistsSnapshot.documents.size) {
                tempList[i - 1]?.id = playlistsSnapshot.documents[i - 1].id
            }
            tempList.map { it?.toPlaylistUI() }

        } catch (e: Exception) {
            Log.i("getPlaylists", e.message.toString())
            emptyList()
        }
    }

    override fun createPlaylist(name: String) {
        val playlistsRef = firestore
            .collection("users")
            .document(auth.currentUser!!.uid)
            .collection("playlists")

        val playlistData = mapOf(
            "name" to name,
            "tracks" to listOf<TrackUI>()
        )
        playlistsRef.add(playlistData)
            .addOnSuccessListener {
                println("Playlist successfully added!")
            }.addOnFailureListener { e ->
                println("Error adding playlist: $e")
            }
    }

    override fun deletePlaylist(id: String) {
        firestore
            .collection("users")
            .document(auth.currentUser!!.uid)
            .collection("playlists")
            .document(id)
            .delete()
    }

    override suspend fun addTrackToPlaylist(playlistId: String, track: TrackUI) {
        val playlistsRef = firestore
            .collection("users")
            .document(auth.currentUser!!.uid)
            .collection("playlists")
            .document(playlistId)

        val newList = getPlaylists().filter { it?.id == playlistId }[0]?.tracks
        newList?.add(track)
        playlistsRef.update(
            "tracks",
            newList
        ).addOnSuccessListener {
            println("Playlist successfully added!")
        }.addOnFailureListener { e ->
            println("Error adding playlist: $e")
        }
    }

    override suspend fun insertDownloaded(id: String) {
        daoDownloaded.insert(DownloadedSong(songId = id))
    }

    override suspend fun getDownloaded(): List<String> {
        return daoDownloaded.getAllDownloadedSongs().map { it.songId }
    }
}