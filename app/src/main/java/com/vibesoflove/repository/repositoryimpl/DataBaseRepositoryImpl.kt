package com.vibesoflove.repository.repositoryimpl

import com.vibesoflove.db.*
import com.vibesoflove.repository.repository.DataBaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DataBaseRepositoryImpl @Inject constructor(
        private val dao: DataBaseDao,
        private val videoDao: VideoDao,
        private val photoDao: PhotoDao,
        private val audioDao: AudioDao,
        private val mixContainerDao: MixContainerDao
) : DataBaseRepository {
    override suspend fun insert(model: DataBaseEntity) = withContext(Dispatchers.IO) {
        dao.insert(model)
    }

    override suspend fun delete(model: DataBaseEntity) = withContext(Dispatchers.IO) {
        dao.delete(model)
    }

    override fun getAll(): Flow<List<DataBaseEntity>> = dao.getAll()


    override fun getSavedVideo(): Flow<List<VideoEntity>> = videoDao.getAll()


    override suspend fun insertVideo(model: VideoEntity) = withContext(Dispatchers.IO) {
        videoDao.insertVideo(model)
    }

    override suspend fun deleteVideo(model: VideoEntity) = withContext(Dispatchers.IO) {
        videoDao.delete(model)
    }

    override suspend fun insertPhoto(model: PhotoEntity) = withContext(Dispatchers.IO) {
        photoDao.insertPhoto(model = model)

    }

    override suspend fun deletePhoto(model: PhotoEntity) = withContext(Dispatchers.IO) {
        photoDao.delete(model)
    }

    override fun getSavedPhoto(): Flow<List<PhotoEntity>> = photoDao.getSavedPhoto()

    override suspend fun insertAudio(model: AudioEntity) = withContext(Dispatchers.IO) {
        audioDao.insert(model)
    }

    override suspend fun deleteAudio(model: AudioEntity) = withContext(Dispatchers.IO) {
        audioDao.delete(model)
    }

    override fun getAudioList(): Flow<List<AudioEntity>> = audioDao.getAudioList()

    override suspend fun insertInContainer(model: MixContainer) = withContext(Dispatchers.IO) {
        mixContainerDao.insert(model)
    }

    override suspend fun deleteFromContainer(model: MixContainer) = withContext(Dispatchers.IO) {
        mixContainerDao.delete(model)
    }

    override fun getContainer(): Flow<List<MixContainer>>  = mixContainerDao.getData()
    override suspend fun updateData(model: MixContainer) = withContext(Dispatchers.IO){mixContainerDao.update(model)}

}