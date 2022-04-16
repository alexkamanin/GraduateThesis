package ru.kamanin.nstu.graduate.thesis.artefact.data.api

import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.http.*
import ru.kamanin.nstu.graduate.thesis.artefact.data.dto.ArtefactDto

interface ArtefactApi {

	@POST("/artefact/upload")
	@Multipart
	suspend fun upload(@Part part: MultipartBody.Part): ArtefactDto

	@GET("/artefact/{artefactId}/download")
	@Streaming
	suspend fun download(@Path("artefactId") artefactId: Long): ResponseBody

	@GET("/artefact/{artefactId}/info")
	suspend fun getInfo(@Path("artefactId") artefactId: Long): ArtefactDto
}