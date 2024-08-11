package com.spase_y.playlistmaker05022024.mediateka.favorites.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.spase_y.playlistmaker05022024.search.domain.model.Track
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteTrackDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrack(track: Track)

    @Delete
    suspend fun deleteTrack(track: Track)

    @Query("SELECT * FROM favorite_tracks ORDER BY trackId DESC")
    fun getAllFavoriteTracks(): Flow<List<Track>>

}