package ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.delegate

import android.net.Uri
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.entity.ArtefactMetaData
import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.entity.FileInfo
import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.entity.Openable
import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.usecase.*
import ru.kamanin.nstu.graduate.thesis.utils.coroutines.exception.launch
import ru.kamanin.nstu.graduate.thesis.utils.coroutines.flow.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

interface ArtefactViewModelDelegate {

	val attachFileEvent: LiveState<FileInfo?>
	val attachUnsupportedFileEvent: LiveState<FileInfo>
	val openFileEvent: LiveState<Openable>
	val storagePermissionEvent: LiveEvent
	val selectDocumentTreeEvent: LiveEvent

	val attachedFile: Uri?

	fun clearAttachedFile()

	fun attachContent(uri: Uri)
	fun detachContent()

	fun selectArtefact(artefact: ArtefactMetaData)
	fun clearSelectedArtefact()

	fun afterStoragePermissionGranted()
	fun selectDocumentTreeDirectory(uri: Uri)
}

//todo доработать делегат
//todo добавить обработку ошибок

class ArtefactViewModelDelegateImpl @Inject constructor(
	private val downloadArtefactUseCase: DownloadArtefactUseCase,
	private val getFileInfoUseCase: GetFileInfoUseCase,
	private val setDocumentTreeUseCase: SetDocumentTreeUseCase,
	private val shouldAskWriteStoragePermission: ShouldAskWriteStoragePermission,
	private val shouldSelectDocumentTreeUseCase: ShouldSelectDocumentTreeUseCase,
) : ArtefactViewModelDelegate, CoroutineScope {

	override val coroutineContext: CoroutineContext = Dispatchers.Main.immediate + SupervisorJob()

	private var selectedArtefact: ArtefactMetaData? = null
	private var attachedFileUri: Uri? = null

	private val _attachFileEvent = MutableLiveState<FileInfo?>()
	override val attachFileEvent: LiveState<FileInfo?> get() = _attachFileEvent.asLiveState()

	private val _attachUnsupportedFileEvent = MutableLiveState<FileInfo>()
	override val attachUnsupportedFileEvent: LiveState<FileInfo> get() = _attachUnsupportedFileEvent.asLiveState()

	private val _openFileEvent = MutableLiveState<Openable>()
	override val openFileEvent: LiveState<Openable> get() = _openFileEvent.asLiveState()

	private val _storagePermissionEvent = MutableLiveEvent()
	override val storagePermissionEvent: LiveEvent get() = _storagePermissionEvent.asLiveEvent()

	private val _selectDocumentTreeEvent = MutableLiveEvent()
	override val selectDocumentTreeEvent: LiveEvent get() = _selectDocumentTreeEvent.asLiveEvent()

	override val attachedFile: Uri? get() = attachedFileUri

	override fun clearAttachedFile() {
		attachedFileUri = null
	}

	override fun attachContent(uri: Uri) {
		attachedFileUri = uri

		launch(::handleAttachFileError) {
			val file = getFileInfoUseCase(uri)
			if (file.isAllowed) {
				_attachFileEvent(file)
			} else {
				_attachUnsupportedFileEvent(file)
			}
		}
	}

	private fun handleAttachFileError(throwable: Throwable) {
		Log.e("TEST_TECH", throwable.stackTraceToString())
	}

	override fun detachContent() {
		_attachFileEvent(null)
	}

	override fun selectArtefact(artefact: ArtefactMetaData) {
		selectedArtefact = artefact

		launch {
			when {
				shouldAskWriteStoragePermission()                        -> _storagePermissionEvent.invoke()
				artefact.isDocument && shouldSelectDocumentTreeUseCase() -> _selectDocumentTreeEvent.invoke()
				else                                                     -> onStoragePermissionGranted()
			}
		}
	}

	private fun onStoragePermissionGranted() {
		launch(::handleDownloadArtefactError) {
			val artefact = requireNotNull(selectedArtefact)
			val uri = downloadArtefactUseCase(artefact)

			val openable = Openable(artefact.mimeType, uri)
			_openFileEvent.invoke(openable)

			selectedArtefact = null
		}
	}

	private fun handleDownloadArtefactError(throwable: Throwable) {
		Log.d("TEST_TECH", throwable.stackTraceToString())
	}

	override fun clearSelectedArtefact() {
		selectedArtefact = null
	}

	override fun afterStoragePermissionGranted() {
		launch {
			val artefact = requireNotNull(selectedArtefact)

			if (artefact.isDocument && shouldSelectDocumentTreeUseCase()) {
				_selectDocumentTreeEvent.invoke()
			} else {
				onStoragePermissionGranted()
			}
		}
	}

	override fun selectDocumentTreeDirectory(uri: Uri) {
		launch {
			setDocumentTreeUseCase(uri)

			onStoragePermissionGranted()
		}
	}
}