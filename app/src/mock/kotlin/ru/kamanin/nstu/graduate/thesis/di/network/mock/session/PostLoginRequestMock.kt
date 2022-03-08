package ru.kamanin.nstu.graduate.thesis.di.network.mock.session

import com.example.retrofitmockinterceptor.MockResponse
import com.example.retrofitmockinterceptor.PostRequestMock
import ru.kamanin.nstu.graduate.thesis.R
import java.util.regex.Pattern

class PostLoginRequestMock : PostRequestMock {

	override fun response(): MockResponse = Mock()

	override fun urlPattern(): Pattern = Pattern.compile("http://nstu\\.mock\\.ru/login")

	private class Mock : MockResponse {

		override fun fileResId(): Int = R.raw.post_login_mock_response

		override fun statusCode(): Int = 200
	}
}