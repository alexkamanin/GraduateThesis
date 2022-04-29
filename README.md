## Выпускная квалификационная работа бакалавра по направлению 09.03.04 "Программная инженерия" [![developDebug](https://github.com/alexkamanin/GraduateThesis/actions/workflows/assembleDevelopDebug.yml/badge.svg)](https://github.com/alexkamanin/GraduateThesis/actions/workflows/assembleDevelopDebug.yml) [![liveDebug](https://github.com/alexkamanin/GraduateThesis/actions/workflows/assembleLiveDebug.yml/badge.svg)](https://github.com/alexkamanin/GraduateThesis/actions/workflows/assembleLiveDebug.yml)

[Как скачать приложение?](HELP.md)  
[Ссылка на дизайн в Фигме](https://www.figma.com/file/FRGFhp2grpxPrL2b0rdtXy/Дизайн-Романов-Диплом)  
[Ссылка на сервер](http://217.71.129.139:4502/swagger-ui/index.html)

### О чем это приложение?

Необходимо реализовать мобильный клиент для аттестации студентов посредством тестовых заданий открытого типа.  
Студент регистрируется в приложении посредством ввода **Корпоративной почты** и **Проверочного кода**, который пришел на указанную почту.  
Далее он устанавливает придуманный пароль, после при **Авторизации** вход осуществляется через указание **Корпоративной почты** и **Придуманного пароля**.  
Студент может:

* просматривать назначенные и прошедшие экзамены;
* проходить тестирование по дисциплине;
    * Тестирование заключается в прохождении билета, содержащего вопросы и задачи.
    * Каждый вопрос может оцениться `[-2, 0, 2, 2]`.
    * Задача оценивается от `0` до `20` баллов.
* задавать вопросы преподавателю в чате.

### Демонстрация

#### Скриншоты

##### Светлая тема

<p>
<kbd><img src="/snapshots/sign_in_light.jpg" width="100"/></kbd>
<kbd><img src="/snapshots/sign_up_light.jpg" width="100"/></kbd>
<kbd><img src="/snapshots/exam_light.jpg" width="100"/></kbd>
<kbd><img src="/snapshots/ticket_light.jpg" width="100"/></kbd>
<kbd><img src="/snapshots/task_light.jpg" width="100"/></kbd>
<kbd><img src="/snapshots/chat_light.jpg" width="100"/></kbd>
<kbd><img src="/snapshots/share_light.jpg" width="100"/></kbd>
</p>

##### Темная тема

<p>
<kbd><img src="/snapshots/sign_in_night.jpg" width="100"/></kbd>
<kbd><img src="/snapshots/sign_up_night.jpg" width="100"/></kbd>
<kbd><img src="/snapshots/exam_night.jpg" width="100"/></kbd>
<kbd><img src="/snapshots/ticket_night.jpg" width="100"/></kbd>
<kbd><img src="/snapshots/task_night.jpg" width="100"/></kbd>
<kbd><img src="/snapshots/chat_night.jpg" width="100"/></kbd>
<kbd><img src="/snapshots/share_night.jpg" width="100"/></kbd>
</p>

#### Видео
https://user-images.githubusercontent.com/51982941/166413317-747994b6-0017-4f98-a462-3e85be10f1e7.mp4

https://user-images.githubusercontent.com/51982941/166413339-09e4c1e1-d6cf-474a-ab9b-5efee4e83fad.mp4

### Используемые технологии

* Kotlin
* Kotlin Coroutines
* MVVM
* Retrofit + OkHttp3
* Hilt + Dagger
* Gradle