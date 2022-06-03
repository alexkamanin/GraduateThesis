package ru.kamanin.nstu.graduate.thesis.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.squareup.moshi.JsonAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import ru.kamanin.graduate.thesis.shared.notification.di.*
import ru.kamanin.graduate.thesis.shared.notification.domain.entity.AnswerNotification
import ru.kamanin.graduate.thesis.shared.notification.domain.entity.ExamNotification
import ru.kamanin.graduate.thesis.shared.notification.domain.entity.MessageNotification
import ru.kamanin.graduate.thesis.shared.notification.domain.entity.NotificationType
import ru.kamanin.graduate.thesis.shared.notification.domain.usecase.UpdateFirebaseNotificationTokenUseCase
import ru.kamanin.nstu.graduate.thesis.R
import ru.kamanin.nstu.graduate.thesis.component.ui.resources.R.drawable.*
import ru.kamanin.nstu.graduate.thesis.shared.account.domain.usecase.GetAccountById
import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.usecase.GetArtefactMetaDataUseCase
import ru.kamanin.nstu.graduate.thesis.shared.exam.domain.usecase.GetExamListUseCase
import ru.kamanin.nstu.graduate.thesis.shared.ticket.domain.entity.TaskState
import ru.kamanin.nstu.graduate.thesis.shared.ticket.domain.entity.TaskType
import ru.kamanin.nstu.graduate.thesis.shared.ticket.domain.usecase.GetTaskUseCase
import ru.kamanin.nstu.graduate.thesis.utils.time.dateStringFormat
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@AndroidEntryPoint
class FirebaseNotificationService : FirebaseMessagingService(), CoroutineScope {

	override val coroutineContext: CoroutineContext = Dispatchers.Default + SupervisorJob()

	private lateinit var notificationManager: NotificationManager

	@Inject
	lateinit var updateFirebaseNotificationTokenUseCase: UpdateFirebaseNotificationTokenUseCase

	@Inject
	lateinit var getArtefactMetaDataUseCase: GetArtefactMetaDataUseCase

	@Inject
	lateinit var getAccountById: GetAccountById

	@Inject
	lateinit var getTaskUseCase: GetTaskUseCase

	@Inject
	lateinit var getExamListUseCase: GetExamListUseCase

	@Inject
	@MessageChannelId
	lateinit var messageChannelId: String

	@Inject
	@MessageChannelName
	lateinit var messageChannelName: String

	@Inject
	@ExamChannelId
	lateinit var examChannelId: String

	@Inject
	@ExamChannelName
	lateinit var examChannelName: String

	@Inject
	@AnswerChannelId
	lateinit var answerChannelId: String

	@Inject
	@AnswerChannelName
	lateinit var answerChannelName: String

	@Inject
	lateinit var messageAdapter: JsonAdapter<MessageNotification>

	@Inject
	lateinit var examAdapter: JsonAdapter<ExamNotification>

	@Inject
	lateinit var answerAdapter: JsonAdapter<AnswerNotification>

	private companion object {

		const val TOKEN_EVENT = "com.google.firebase.messaging.NEW_TOKEN"
		const val NEW_TOKEN = "token"

		const val NOTIFICATION_ID = "google.message_id"
		const val NOTIFICATION_TITLE = "gcm.notification.title"
		const val NOTIFICATION_BODY = "gcm.notification.body"
	}

	override fun onCreate() {
		super.onCreate()

		initNotificationManager()
		initChannels()
	}

	private fun initNotificationManager() {
		notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
	}

	private fun initChannels() {
		initChannel(messageChannelId, messageChannelName)
		initChannel(answerChannelId, answerChannelName)
		initChannel(examChannelId, examChannelName)
	}

	private fun initChannel(channelId: String, channelName: String) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
			notificationManager.createNotificationChannel(channel)
		}
	}

	override fun onNewToken(token: String) {
		updateFirebaseNotificationTokenUseCase(token)
	}

	override fun handleIntent(intent: Intent) {
		if (intent.isTokenEvent()) {
			val token = requireNotNull(intent.getStringExtra(NEW_TOKEN))
			onNewToken(token)
			return
		}

		val messageTitle = intent.extras?.getString(NOTIFICATION_TITLE) ?: return
		val messageBody = intent.extras?.getString(NOTIFICATION_BODY) ?: return

		when (val notificationType = NotificationType.valueOf(messageTitle)) {

			NotificationType.EXAM_CREATED,
			NotificationType.EXAM_READY,
			NotificationType.EXAM_STARTED,
			NotificationType.EXAM_FINISHED,
			NotificationType.EXAM_CLOSED    -> renderExamNotification(notificationType, messageBody)

			NotificationType.ANSWER_CHANGED -> renderAnswerNotification(messageBody)

			NotificationType.NEW_MESSAGE    -> renderMessageNotification(messageBody)
		}
	}

	private fun Intent.isTokenEvent(): Boolean =
		TOKEN_EVENT == action

	private fun renderExamNotification(type: NotificationType, body: String) {
		val exam = examAdapter.fromJson(body) as ExamNotification
		val (title, description) = when (type) {
			NotificationType.EXAM_CREATED  -> exam.name to getString(R.string.push_exam_created, exam.start.dateStringFormat)
			NotificationType.EXAM_STARTED  -> exam.name to getString(R.string.push_exam_started)
			NotificationType.EXAM_FINISHED -> exam.name to getString(R.string.push_exam_finished)
			NotificationType.EXAM_CLOSED   -> exam.name to getString(R.string.push_exam_closed)
			else                           -> return
		}

		send(exam.id, title, description, examChannelId)
	}

	private fun renderAnswerNotification(body: String) {
		val answer = answerAdapter.fromJson(body) as AnswerNotification

		when (answer.state) {
			TaskState.RATED -> {
				launch {
					val task = getTaskUseCase(answer.taskId)
					val exam = getExamListUseCase().first { exam -> exam.id == answer.studentRatingId }
					val (taskWithNumber, maxRating) = if (task.taskType == TaskType.QUESTION) {
						getString(R.string.question_title, answer.number) to exam.regulationRating.maxQuestionRating
					} else {
						getString(R.string.exercise_title, answer.number) to exam.regulationRating.maxExerciseRating
					}
					val description = getString(R.string.push_answer_rated, taskWithNumber, answer.rating, maxRating)

					send(answer.id, exam.name, description, answerChannelId)
				}
			}

			else            -> return
		}
	}

	private fun renderMessageNotification(body: String) {
		val message = messageAdapter.fromJson(body) as MessageNotification

		launch {
			val account = getAccountById(message.accountId)
			val title = getString(R.string.format_two_separated_text, account.surname, account.name)

			val description = when {

				message.text != null && message.artefactId != null -> {
					val artefactId = requireNotNull(message.artefactId)
					val artefact = getArtefactMetaDataUseCase(artefactId)
					getString(R.string.format_two_line_text, message.text, artefact.fullName)
				}

				message.text != null                               -> {
					requireNotNull(message.text)
				}

				message.artefactId != null                         -> {
					val artefactId = requireNotNull(message.artefactId)
					val artefact = getArtefactMetaDataUseCase(artefactId)
					artefact.fullName
				}

				else                                               -> throw IllegalStateException("Text and artefact cannot be null at the same time")
			}

			send(message.id, title, description, messageChannelId)
		}
	}

	private fun send(
		id: Long,
		title: String,
		text: String,
		channelId: String
	) {
		val notificationBuilder = NotificationCompat.Builder(this, channelId)
			.setSmallIcon(ic_notification)
			.setContentTitle(title)
			.setStyle(NotificationCompat.BigTextStyle().bigText(text))
			.setContentText(text)
			.setAutoCancel(true)
			.setCategory(NotificationCompat.CATEGORY_MESSAGE)
			.setDefaults(NotificationCompat.DEFAULT_ALL)
			.setPriority(NotificationCompat.PRIORITY_HIGH)

		notificationManager.notify(id.toInt(), notificationBuilder.build())
	}
}