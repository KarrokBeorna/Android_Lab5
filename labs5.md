# Цели

- Ознакомиться с принципами и получить практические навыки разработки UI тестов для Android приложений.

## Задача 1 - Простейший UI тест

- Просмотрел 2 первые страницы туториала по Espresso.
- Создал приложение с кнопкой и изменяемым текстовым полем.
- Прописал в модульном билде зависимости для подключения Espresso.
- Синхронизировал проект.

Теперь напишем простенькие тесты:
1. Для начала возьмем готовый код с Espresso и чуть-чуть поменяем его:

        @LargeTest
        class EspressoTest {
            @get:Rule
            val activityRule = ActivityScenarioRule(MainActivity::class.java)
            ...
  - `@LargeTest` - аннотация для назначения тесту квалификатора большого размера теста. Такие тесты должны быть сосредоточены на тестировании интеграции всех компонентов приложения.
  - `@get:Rule` - получаем правило.
  - В сценарий мы передаем LAUNCHER-activity из манифеста.
2. Теперь напишем наши тесты:

        @Test
        fun mainActTest() {
            val button = onView(withId(R.id.button))
            val editText = onView(withId(R.id.editText))

            editText.check(matches(withText("Hello World!")))
            button.check(matches(withText("Button")))

            button.perform(click()).check(matches(withText("Ну, мы нажали...")))
            editText.perform(replaceText("Привет, мир!"))

            activityRule.scenario.onActivity{
                it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            }

            editText.check(matches(withText("Привет, мир!")))
            button.check(matches(withText("Button")))
        }
  - первые 2 строчки: создаем переменные, так как только к этим элементам мы и будем обращаться;
  - следующие 2 строчки - чекаем на правильность начальных слов в элементах;
  - дальше нажимаем на кнопку и изменяем текст в `EditText` с помощью методов `click()` и `replaceText()`;
  - поворачиваем экран (готовый код дали в тексте задания);
  - проверяем, что у нас теперь написано на кнопке и в текстовом поле - поле осталось без изменений, кнопка вернулась в исходное состояние.

Максимально легкое и понятное задание. Такие всегда приятно делать. Пришлось лишь поискать, как изменить текст в EditText, но это заняло не больше 3 минут. Всё остальное нашлось как раз-таки на первых 2 страницах туториала.

## Задача 2 - Тестирование навигации

Пойдём и скопипастим свой же код из 3 лабораторной работы. Я выбрал Task3, где мы использовали флаги или атрибуты (мне понравились флаги, потому что всё, что легко понимается - нравится). Не буду вставлять сюда 4 Activity и 4 xml-файла, пусть они просто полежат на GitHub'e.

Давайте пойдем по порядку:
1. Сначала, как и в первой задаче, пропишем правило, при этом необходимо изменить в манифесте запускающую Activity.

        val activityRuleTask2 = ActivityTestRule(Activity1::class.java)

2. Предварительно напишем проверки для каждой Activity - что кнопка(-и), которая(-ые) у неё есть, действительно отображается(-ются), а которые не должны - нет.

        private fun first() {
            onView(withId(R.id.button)).check(matches(isDisplayed()))
            onView(withId(R.id.button2)).check(doesNotExist())
            onView(withId(R.id.button3)).check(doesNotExist())
            onView(withId(R.id.button4)).check(doesNotExist())
            onView(withId(R.id.button5)).check(doesNotExist())
        }

        private fun second() {
            onView(withId(R.id.button)).check(doesNotExist())
            onView(withId(R.id.button2)).check(matches(isDisplayed()))
            onView(withId(R.id.button3)).check(matches(isDisplayed()))
            onView(withId(R.id.button4)).check(doesNotExist())
            onView(withId(R.id.button5)).check(doesNotExist())
        }

        private fun third() {
            onView(withId(R.id.button)).check(doesNotExist())
            onView(withId(R.id.button2)).check(doesNotExist())
            onView(withId(R.id.button3)).check(doesNotExist())
            onView(withId(R.id.button4)).check(matches(isDisplayed()))
            onView(withId(R.id.button5)).check(matches(isDisplayed()))
        }
3. Напишем первый тест - проверим, что кнопки перехода работают:

        @Test
        fun task2Test() {
            first()
            onView(withId(R.id.button)).perform(click())
            second()
            onView(withId(R.id.button2)).perform(click())
            first()
            onView(withId(R.id.button)).perform(click())
            onView(withId(R.id.button3)).perform(click())
            third()
            onView(withId(R.id.button5)).perform(click())
            second()
            onView(withId(R.id.button2)).perform(click())
        }
   - Сначала проверяем, находимся ли мы в первой Activity.
   - Переходим во вторую и удостоверимся, что мы во второй.
   - Вернемся в первую и проверим.
   - Перейдем во вторую, оттуда в третью и сверимся.
   - Переходим во вторую и проверяем.
   - Переходим в первую.
4. Напишем еще 2 теста - проверим наш backstack:

        @Test
        fun backstackTest() {
            first()
            onView(withId(R.id.button)).perform(click())
            onView(withId(R.id.button3)).perform(click())
            third()
            onView(withId(R.id.button4)).perform(click())
            first()
            try {
                pressBack()
            } catch (e: NoActivityResumedException) {
                // приложение закрылось
            }
            assertTrue(activityRuleTask2.activity.isDestroyed)
        }

        @Test
        fun backstackTest2() {
            onView(withId(R.id.button)).perform(click())
            onView(withId(R.id.button3)).perform(click())
            onView(withId(R.id.button5)).perform(click())
            onView(withId(R.id.button2)).perform(click())
            try {
                pressBack()
            } catch (e: NoActivityResumedException) {
                // вышли из приложения
            }
            assertTrue(activityRuleTask2.activity.isDestroyed)
        }

   - Проверяем, в первой ли мы Activity.
   - Переходим во вторую, оттуда в третью и сверяемся.
   - Переходим в первую и проверяем.
   - Пробуем нажать "Назад", однако мы знаем, что поймаем исключение, поэтому обратим нажатие в блок `try-catch`.
   - Проверяем, разрушена ли наша активити.
   - Тесты разнесены в разные функции, так как я не нашёл, как мы можем перезапустить наше приложение из теста, чтобы после выхода по `pressBack` мы опять смогли зайти в `Activity1`
   - Опять переходим 1-2-3-2-1 и после чего опять нажимаем "Назад" с поимкой исключения.
5. Предсказуемо, что у нас ничего не будет меняться после поворота экрана, но давайте удостоверимся в этом и напишем последний тест:

        @Test
        fun landscapeTest() {
            first()
            onView(withId(R.id.button)).perform(click())
            activityRuleTask2.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            second()
            onView(withId(R.id.button2)).perform(click())
            task2Test()
            backstackTest()
        }

   - Сначала опять смотрим, в первой ли мы Activity.
   - Переходим во вторую Activity.
   - Поворачиваем экран.
   - Смотрим, во второй ли мы Activity до сих пор.
   - Возвращаемся в первую Activity и проделываем 2 прошлых теста уже для альбомной ориентации.

# Выводы

Лабораторная оказалась очень приятной, буквально 10 минут почитать документацию и уже можно выполнить работу.

Все написанные тесты конечно же прошли. Тестировал на физическом устройстве, причем предварительно выключал анимацию переходов, как это советуют в Espresso туториале, однако и после их включения всё выполнилось.

Писать тесты, конечно, интересно, но всё же клацать и тестировать приложение вживую интереснее. Суммарно затраченное время - 4-5 часов с учетом отчёта. 

P.S. Использование `androidx.test.espresso.action.ViewActions.pressBack` **НЕ** дает исключение при выходе из приложения!!!
А вот `androidx.test.espresso.Espresso.pressBack` как раз даёт.