package com.selincengiz.ritmo.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.selincengiz.ritmo.data.local.RitmoDao
import com.selincengiz.ritmo.data.mapper.Mappers.toAlbumUI
import com.selincengiz.ritmo.data.mapper.Mappers.toPlaylistUI
import com.selincengiz.ritmo.data.mapper.Mappers.toTrackEntity
import com.selincengiz.ritmo.data.mapper.Mappers.toTrackUI
import com.selincengiz.ritmo.data.remote.RitmoApi
import com.selincengiz.ritmo.data.remote.dto.ListPlaylistUI
import com.selincengiz.ritmo.domain.model.AlbumUI
import com.selincengiz.ritmo.domain.model.PlaylistUI
import com.selincengiz.ritmo.domain.model.TrackUI
import com.selincengiz.ritmo.domain.repository.RitmoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import okhttp3.internal.wait

class RitmoRepositoryImpl(
    private val api: RitmoApi,
    private val dao: RitmoDao,
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : RitmoRepository {
    override suspend fun search(q: String): List<TrackUI?>? {
        return api.search(q).data?.map { it?.toTrackUI() }
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

    override fun getTracksLocal(): Flow<List<TrackUI>> {
        return dao.getTracks().map { it -> it.map { it.toTrackUI() } }
    }

    override suspend fun getTrackLocal(id: String): TrackUI? {
        return dao.getTrack(id)?.toTrackUI()
    }


    override suspend fun getPlaylists(): List<PlaylistUI?> {
        val playlistsRef = firestore
            .collection("users")
            .document(auth.currentUser!!.uid)
            .collection("playlists")
        var returnList = mutableListOf<ListPlaylistUI?>()
        try {
            playlistsRef.addSnapshotListener { playlistsSnapshot, _ ->
                val tempList = mutableListOf<ListPlaylistUI?>()
                playlistsSnapshot?.forEach { doc ->
                    tempList.add(
                        doc.toObject(ListPlaylistUI::class.java)
                    )
                }
                returnList=tempList
            }

        } catch (e: Exception) {
            Log.i("getPlaylists", e.message.toString())
        }
        Log.i("getPlaylists", returnList.get(0)?.name.toString())

        return returnList.map { it?.toPlaylistUI() }
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

}