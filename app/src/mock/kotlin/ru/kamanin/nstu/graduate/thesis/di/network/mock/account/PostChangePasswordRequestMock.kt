package ru.kamanin.nstu.graduate.thesis.di.network.mock.account

import com.example.retrofitmockinterceptor.MockResponse
import com.example.retrofitmockinterceptor.PostRequestMock
import ru.kamanin.nstu.graduate.thesis.R
import java.util.regex.Pattern

class PostChangePasswordRequestMock : PostRequestMock {

	override fun response(): MockResponse = Mock()

	override fun urlPattern(): Pattern = Pattern.compile("http://nstu\\.mock\\.ru/account/change-password")

	private class Mock : MockResponse {

		override fun fileResId(): Int = R.raw.empty_response

		override fun statusCode(): Int = 200
	}
}