package ru.kamanin.nstu.graduate.thesis.di.network.mock.exam

import com.example.retrofitmockinterceptor.GetRequestMock
import com.example.retrofitmockinterceptor.MockResponse
import ru.kamanin.nstu.graduate.thesis.R
import java.util.regex.Pattern

class GetExamListRequestMock : GetRequestMock {

	override fun response(): MockResponse = Mock()

	override fun urlPattern(): Pattern = Pattern.compile("http://nstu\\.mock\\.ru/student/ticket")

	private class Mock : MockResponse {

		override fun fileResId(): Int = R.raw.get_exam_list_mock_response

		override fun statusCode(): Int = 200
	}
}