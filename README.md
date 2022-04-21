## Выпускная квалификационная работа бакалавра по направлению 09.03.04 "Программная инженерия"

[![developDebug](https://github.com/alexkamanin/GraduateThesis/actions/workflows/assembleDevelopDebug.yml/badge.svg)](https://github.com/alexkamanin/GraduateThesis/actions/workflows/assembleDevelopDebug.yml)
[![liveDebug](https://github.com/alexkamanin/GraduateThesis/actions/workflows/assembleLiveDebug.yml/badge.svg)](https://github.com/alexkamanin/GraduateThesis/actions/workflows/assembleLiveDebug.yml)

### Краткое содержание

#### Авторизация/Регистрация

Необходимо реализовать мобильный клиент студента для дистанционной сдачи экзамена.  
Студент регистрируется в приложении посредством ввода **Корпоративной почты** и **Проверочного кода**, который пришел на указанную почту.  
Далее он устанавливает придуманный пароль, после при **Авторизации** вход осуществляется через указание **Корпоративной почты** и **Придуманного пароля**

#### Тестирование

Студент выполняет билет. Билет представляет собой набор вопросов с ответом в открытой форме и одной задачей. Каждый ответ на вопрос оценивается (-2, 0, 1, 2)
баллами. Задача оценивается от 0 до 20 баллов.

#### Обратная связь

В процессе экзамена студент может общаться с перподавателем в чате.

### Разработка

#### Варианты сборок

* **developDebug** - сборка приложения с моками.
* **liveDebug** - сборка приложения с запросами на [сервер](http://217.71.129.139:4502/swagger-ui/index.html).

### Демонстрация

[Ссылка на дизайн в Фигме](https://www.figma.com/file/FRGFhp2grpxPrL2b0rdtXy/Дизайн-Романов-Диплом)

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

### Используемые технологии

* Kotlin
* Kotlin Coroutines
* MVVM
* Retrofit + OkHttp3
* Hilt + Dagger
* Gradle