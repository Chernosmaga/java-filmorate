# Приложение для оценки просмотренных фильмов и рекомендаций
<details>
 <summary>Техническое задание 9-го спринта</summary>

Представьте, что после изучения сложной темы и успешного выполнения всех заданий вы решили отдохнуть и провести вечер за просмотром фильма. Вкусная еда уже готовится, любимый плед уютно свернулся на кресле — а вы всё ещё не выбрали, что же посмотреть!

Фильмов много — и с каждым годом становится всё больше. Чем их больше, тем больше разных оценок. Чем больше оценок, тем сложнее сделать выбор. Однако не время сдаваться! Вы напишете бэкенд для сервиса, который будет работать с фильмами и оценками пользователей, а также возвращать топ-5 фильмов, рекомендованных к просмотру. Теперь ни вам, ни вашим друзьям не придётся долго размышлять, что посмотреть вечером.

В этом спринте вы начнёте с малого, но очень важного: создадите каркас Spring Boot приложения `Filmorate` (от англ. film — «фильм» и rate — «оценивать»). В дальнейшем сервис будет обогащаться новым функционалом и с каждым спринтом становиться лучше благодаря вашим знаниям о Java. Скорее вперёд!
## Предварительная настройка проекта
В репозитории создайте ветку `controllers-films-users`. Разработку решения для первого спринта нужно вести в ней. Репозиторий при этом должен быть публичным.

Создайте заготовку проекта с помощью Spring Initializr. Некоторые параметры вы найдёте в этой таблице, остальные заполните самостоятельно.

### Параметр/Значение
 * Group (организация)/ru.yandex.practicum
 * Artifact (артефакт)/filmorate
 * Name (название проекта)/filmorate
 * Dependencies (зависимости)/Spring Web

Ура! Проект сгенерирован. Теперь можно шаг за шагом реализовать приложение.

## Модели данных

Создайте пакет `model`. Добавьте в него два класса — `Film` и `User`. Это классы — модели данных приложения.

У `model.Film` должны быть следующие свойства:
 * целочисленный идентификатор — `id`;
 * название — `name`;
 * описание — `description`;
 * дата релиза — `releaseDate`;
 * продолжительность фильма — `duration`.

Свойства `model.User`:
 * целочисленный идентификатор — `id`;
 * электронная почта — `email`;
 * логин пользователя — `login`;
 * имя для отображения — `name`;
 * дата рождения — `birthday`.

### Подсказка: про аннотацию @Data

Используйте аннотацию `@Data` библиотеки Lombok — с ней будет меньше работы по созданию сущностей.

## Хранение данных
Сейчас данные можно хранить в памяти приложения — так же, как вы поступили в случае с менеджером задач. Для этого используйте контроллер.

В следующих спринтах мы расскажем, как правильно хранить данные в долговременном хранилище, чтобы они не зависели от перезапуска приложения.

## REST-контроллеры

Создайте два класса-контроллера. `FilmController` будет обслуживать фильмы, а `UserController` — пользователей. Убедитесь, что созданные контроллеры соответствуют правилам REST.

Добавьте в классы-контроллеры эндпоинты с подходящим типом запроса для каждого из случаев.

Для `FilmController`:
 * добавление фильма;
 * обновление фильма;
 * получение всех фильмов.

Для `UserController`:
 * создание пользователя;
 * обновление пользователя;
 * получение списка всех пользователей.

Эндпоинты для создания и обновления данных должны также вернуть созданную или изменённую сущность.

### Подсказка: про аннотацию @RequestBody
Используйте аннотацию `@RequestBody`, чтобы создать объект из тела запроса на добавление или обновление сущности.

## Валидация

Для `Film`:
 * название не может быть пустым;
 * максимальная длина описания — 200 символов;
 * дата релиза — не раньше 28 декабря 1895 года;
 * продолжительность фильма должна быть положительной.

Для `User`:
 * электронная почта не может быть пустой и должна содержать символ @;
 * логин не может быть пустым и содержать пробелы;
 * имя для отображения может быть пустым — в таком случае будет использован логин;
 * дата рождения не может быть в будущем.

### Подсказка: как обработать ошибки

Для обработки ошибок валидации напишите новое исключение — например, `ValidationException`.

## Логирование

Добавьте логирование для операций, которые изменяют сущности — добавляют и обновляют их. Также логируйте причины ошибок — например, если валидация не пройдена. Это считается хорошей практикой.

### Подсказка: про логирование сообщений

Воспользуйтесь библиотекой `slf4j` для логирования и объявляйте логер для каждого класса — так будет сразу видно, где в коде выводится та или иная строка.

```
private final static Logger log = LoggerFactory.getLogger(Example.class);
```

Вы также можете применить аннотацию `@Slf4j` библиотеки Lombok, чтобы не создавать логер вручную.

## Тестирование

Добавьте тесты для валидации. Убедитесь, что она работает на граничных условиях.

### Подсказка: на что обратить внимание при тестировании
Проверьте, что валидация не пропускает пустые или неверно заполненные поля. Посмотрите, как контроллер реагирует на пустой запрос.

## Проверьте себя

Так как у вашего API пока нет интерфейса, вы будете взаимодействовать с ним через веб-клиент. Мы подготовили набор тестовых данных — Postman коллекцию. С её помощью вы сможете протестировать ваше API: postman.json

## Дополнительное задание*

А теперь необязательное задание для самых смелых! Валидация, которую мы предлагаем реализовать в основном задании, — базовая. Она не покрывает всех возможных ошибок. Например, всё ещё можно создать пользователя с такой электронной почтой: `это-неправильный?эмейл@`.

В Java есть инструменты для проверки корректности различных данных. С помощью аннотаций можно задать ограничения, которые будут проверяться автоматически. Для этого добавьте в описание сборки проекта следующую зависимость.

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency> 
```

Теперь вы можете применить аннотацию `@NotNull` к полю класса-модели для проверки на `null`, `@NotBlank` — для проверки на пустую строку, `@Email` — для проверки на соответствие формату электронного адреса. Полный список доступных аннотаций можно найти в документации.

Чтобы Spring не только преобразовал тело запроса в соответствующий класс, но и проверил корректность переданных данных, вместе с аннотацией `@RequestBody` нужно использовать аннотацию `@Valid`.
```
public createUser(@Valid @RequestBody User user) 
```

</details>

### Количество итераций: 3
### Количество замечаний: 6

<details>
<summary>Техническое задание 10-го спринта</summary>

Настало время улучшить `Filmorate`. Чтобы составлять рейтинг фильмов, нужны отзывы пользователей. А для улучшения рекомендаций по просмотру хорошо бы объединить пользователей в комьюнити.

По итогам прошлого спринта у вас получилась заготовка приложения. Программа может принимать, обновлять и возвращать пользователей и фильмы.
В этот раз улучшим API приложения до соответствия REST, а также изменим архитектуру приложения с помощью внедрения зависимостей.

## Наводим порядок в репозитории

Для начала убедитесь в том, что ваша работа за предыдущий спринт слита с главной веткой `main`. Создайте новую ветку, которая будет называться `add-friends-likes`.
Название ветки важно сохранить, потому что оно влияет на запуск тестов в GitHub.

### Подсказка: про работу в Git

Для слияния веток используйте команду `merge`.

## Архитектура

Начнём с переработки архитектуры. Сейчас вся логика приложения спрятана в контроллерах — изменим это. Вынесите хранение данных о фильмах и пользователях в отдельные классы. Назовём их «хранилищами» (англ. storage) — так будет сразу понятно, что они делают.

 * Создайте интерфейсы `FilmStorage` и `UserStorage`, в которых будут определены методы добавления, удаления и модификации объектов.
 * Создайте классы `InMemoryFilmStorage` и `InMemoryUserStorage`, имплементирующие новые интерфейсы, и перенесите туда всю логику хранения, обновления и поиска объектов.
 * Добавьте к `InMemoryFilmStorage` и `InMemoryUserStorage` аннотацию `@Component`, чтобы впоследствии пользоваться внедрением зависимостей и передавать хранилища сервисам.

### Подсказка: про структуру проекта

Чтобы объединить хранилища, создайте новый пакет `storage`. В нём будут только классы и интерфейсы, имеющие отношение к хранению данных. Например, `ru.yandex.filmorate.storage.film.FilmStorage`.

## Новая логика

Пока у приложения нет никакой бизнес-логики, кроме валидации сущностей. Обеспечим возможность пользователям добавлять друг друга в друзья и ставить фильмам лайки.

 * Создайте `UserService`, который будет отвечать за такие операции с пользователями, как добавление в друзья, удаление из друзей, вывод списка общих друзей. Пока пользователям не надо одобрять заявки в друзья — добавляем сразу. То есть если Лена стала другом Саши, то это значит, что Саша теперь друг Лены.
 * Создайте `FilmService`, который будет отвечать за операции с фильмами, — добавление и удаление лайка, вывод 10 наиболее популярных фильмов по количеству лайков. Пусть пока каждый пользователь может поставить лайк фильму только один раз.
 * Добавьте к ним аннотацию `@Service` — тогда к ним можно будет получить доступ из контроллера.

### Подсказка: ещё про структуру

По аналогии с хранилищами, объедините бизнес-логику в пакет `service`.

### Подсказка: про список друзей и лайки

Есть много способов хранить информацию о том, что два пользователя являются друзьями. Например, можно создать свойство `friends` в классе пользователя, которое будет содержать список его друзей. Вы можете использовать такое решение или придумать своё.

Для того чтобы обеспечить уникальность значения (мы не можем добавить одного человека в друзья дважды), проще всего использовать для хранения `Set<Long>` c id друзей. Таким же образом можно обеспечить условие «один пользователь — один лайк» для оценки фильмов.

## Зависимости

Переделайте код в контроллерах, сервисах и хранилищах под использование внедрения зависимостей.

 * Используйте аннотации `@Service`, `@Component`, `@Autowired`. Внедряйте зависимости через конструкторы классов.
 * Классы-сервисы должны иметь доступ к классам-хранилищам. Убедитесь, что сервисы зависят от интерфейсов классов-хранилищ, а не их реализаций. Таким образом в будущем будет проще добавлять и использовать новые реализации с другим типом хранения данных.
 * Сервисы должны быть внедрены в соответствующие контроллеры.

### Подсказка: @Service vs @Component

`@Component` — аннотация, которая определяет класс как управляемый Spring. Такой класс будет добавлен в контекст приложения при сканировании.
`@Service` не отличается по поведению, но обозначает более узкий спектр классов — такие, которые содержат в себе бизнес-логику и, как правило, не хранят состояние.

## Полный REST

Дальше стоит заняться контроллерами и довести API до соответствия REST.

 * С помощью аннотации `@PathVariable` добавьте возможность получать каждый фильм и данные о пользователях по их уникальному идентификатору: `GET .../users/{id}`.
 * Добавьте методы, позволяющие пользователям добавлять друг друга в друзья, получать список общих друзей и лайкать фильмы. Проверьте, что все они работают корректно.
   * `PUT /users/{id}/friends/{friendId}` — добавление в друзья.
   * `DELETE /users/{id}/friends/{friendId}` — удаление из друзей.
   * `GET /users/{id}/friends` — возвращаем список пользователей, являющихся его друзьями.
   * `GET /users/{id}/friends/common/{otherId}` — список друзей, общих с другим пользователем.
   * `PUT /films/{id}/like/{userId}` — пользователь ставит лайк фильму.
   * `DELETE /films/{id}/like/{userId}` — пользователь удаляет лайк.
   * `GET /films/popular?count={count}` — возвращает список из первых `count` фильмов по количеству лайков. Если значение параметра `count` не задано, верните первые 10.
 * Убедитесь, что ваше приложение возвращает корректные HTTP-коды.
   * 400 — если ошибка валидации: `ValidationException`;
   * 404 — для всех ситуаций, если искомый объект не найден;
   * 500 — если возникло исключение.

### Подсказка

Настройте `ExceptionHandler` для централизованной обработки ошибок.

## Тестирование

Убедитесь, что приложение работает, — протестируйте его с помощью Postman.


</details>

### Количество итераций: 2
### Количество замечаний: 2

<details>
 <summary>Техническое задание 11-го спринта</summary>

## Задание для взаимопроверки

Сейчас `Filmorate` хранит все данные в своей памяти.
Это приводит к тому, что при перезапуске приложения его история и настройки сбрасываются. Вряд ли это обрадует пользователей!

Итак, нам нужно, чтобы данные:

 * были доступны всегда,
 * находились в актуальном состоянии.

А ещё важно, чтобы пользователи могли получать их быстро. Для этого вся информация должна храниться в базе данных.

В этом задании вы будете проектировать базу данных для проекта, основываясь на уже существующей функциональности.
Вносить какие-либо изменения в код не потребуется.


Готовое решение отправьте своему партнёру по взаимопроверке из группы.

Если ваша работа не пройдёт проверку одногруппником, то ревьюер потратит одну попытку сдачи финального задания
следующего спринта на проверку ER диаграммы, и у вас будет меньше попыток сдачи проекта `Filmorate`.

## Как проходит взаимопроверка

### Загрузите решение
Начните с загрузки файла с решением в ваш репозиторий на GitHub. Затем пригласите партнёра по взаимопроверке в
приватный репозиторий — сделать это можно через меню Collaboration (англ. «сотрудничество»).
Откройте настройки репозитория и введите логин партнёра: Settings → Repositories → Manage access → Invite a collaborator.
Теперь отправьте ссылку на ваше решение одногруппнику в Пачке.

⚠️ Решение нужно отправить не позднее указанного дедлайна. Когда проверка будет выполнена, не
забудьте исключить одногруппника из репозитория — иначе у него останется полный доступ.

## Проверьте работу одногруппника

Вы получили ссылку на репозиторий одногруппника — теперь можно оставлять комментарии к коду. Убедитесь,
что код отвечает требованиям задания и code style, принятому в Практикуме.

Ревью — ответственная задача. Представьте себя на месте другого студента и подумайте, какая обратная связь была
бы наиболее полезна для него.

Идеальный комментарий содержит:
 1. Мягкие формулировки. Постарайтесь не использовать слово «нужно» (альтернатива — «лучше») и повелительное наклонение
(«сделай»). Лучше не перекладывать работу кода на его автора — «этот код делает» вместо «ты делаешь».
 2. Развёрнутые объяснения.
 3. Обоснование необходимости другого решения.
 4. Встречные предложения — как сделать лучше.
 5. Поясняющие ссылки на статьи и обсуждения.

Например: "Здесь лучше использовать вот это — оно реализует такой-то функционал. А то работает медленнее.
[Пример кода. Поясняющая ссылка.]"

## Оцените обратную связь

По результатам ревью оцените, насколько полезные комментарии вы получили. Это поможет вашему партнёру быть более
конструктивным ревьюером.

## Доработка модели

Прежде чем приступить к созданию схемы базы данных, нужно доработать модель приложения. Сейчас сущности, с которыми
работает `Filmorate`, имеют недостаточно полей, чтобы получилось создать полноценную базу. Исправим это!

## `Film`

 1. Добавьте новое свойство — «жанр». У фильма может быть сразу несколько жанров, а у поля — несколько значений.
Например, таких:
 * Комедия.
 * Драма.
 * Мультфильм.
 * Триллер.
 * Документальный.
 * Боевик.
 2. Ещё одно свойство — рейтинг Ассоциации кинокомпаний (англ. Motion Picture Association, сокращённо МРА).
Эта оценка определяет возрастное ограничение для фильма. Значения могут быть следующими:
 * G — у фильма нет возрастных ограничений,
 * PG — детям рекомендуется смотреть фильм с родителями,
 * PG-13 — детям до 13 лет просмотр не желателен,
 * R — лицам до 17 лет просматривать фильм можно только в присутствии взрослого,
 * NC-17 — лицам до 18 лет просмотр запрещён.

## `User`

 1. Добавьте статус для связи «дружба» между двумя пользователями:
 2. неподтверждённая — когда один пользователь отправил запрос на добавление другого пользователя в друзья,
 3. подтверждённая — когда второй пользователь согласился на добавление.

## Создание схемы базы данных

Начните с таблиц для хранения пользователей и фильмов. При проектировании помните о том, что:
 * Каждый столбец таблицы должен содержать только одно значение. Хранить массивы значений или вложенные записи в столбцах нельзя.
 * Все неключевые атрибуты должны однозначно определяться ключом.
 * Все неключевые атрибуты должны зависеть только от первичного ключа, а не от других неключевых атрибутов.
 * База данных должна поддерживать бизнес-логику, предусмотренную в приложении. Подумайте о том, как будет
происходить получение всех фильмов, пользователей. А как — топ N наиболее популярных фильмов. Или список общих
друзей с другим пользователем.

Теперь нарисуйте схему базы данных. Для этого можно использовать любой из следующих инструментов:

 1. [dbdiagram.io](https://dbdiagram.io/home).
 2. [QuickDBD](https://app.quickdatabasediagrams.com/#/).
 3. [Miro](https://miro.com/ru/).
 4. [Lucidchart](https://www.lucidchart.com/pages/examples/database-design-tool).
 5. [Diagrams.net](https://app.diagrams.net/).

## Последние штрихи

Прежде чем отправлять получившуюся схему на проверку:
 1. Скачайте диаграмму в виде картинки и добавьте в репозиторий. Убедитесь, что на изображении чётко виден текст.
 2. Добавьте в файл `README.md` ссылку на файл диаграммы. Если использовать разметку markdown, то схему будет видно
непосредственно в `README.md`.
 3. Там же напишите небольшое пояснение к схеме: приложите примеры запросов для основных операций вашего приложения.

### Подсказка

Документы по разметке, которая поддерживается GitHub, лежат [здесь](https://docs.github.com/en/get-started/writing-on-github/getting-started-with-writing-and-formatting-on-github/basic-writing-and-formatting-syntax#images).

</details>

<details>
 <summary>Схема базы данных для этого проекта</summary>

### Комментарии

 * файл в формате SVG лежит в папке проекта
 * между таблицами `film` и `genre`, таблица `film_genre` является посредником. В результате получается связь many-to-many
 * та же посредником между таблицами `film` и `users_liked`, является таблица `likes`
 * в таблице `film_mpa` содержатся возрастные рейтинги фильмов
 * связь между таблицами `user`, `user_friends` и `friends` - many-to-many. В таблице `users_friends` хранятся идентификаторы
пользователей, которые являются друзьями друг друга, а `bool` означает подтверждено ли было добавление в друзья


### [Ссылка на файл здесь](file:///D:/Java%20stuff/java-filmorate/data-base-diagram.svg)

</details>

### Количество итераций: 
### Количество замечаний: 

<details>
 <summary>Техническое задание 12-го спринта</summary>

## Пока что тут пусто

</details>

### Количество итераций:
### Количество замечаний: