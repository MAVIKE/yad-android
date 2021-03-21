# YAD Android

<!-- ToC start -->
## Содержание

1. [Ссылки](#Ссылки)
1. [Запуск](#Запуск)
1. [Структура проекта](#Структура-проекта)
1. [Правила](#Правила)
<!-- ToC end -->

## Ссылки

:bookmark_tabs: [Kanban](https://github.com/orgs/MAVIKE/projects/2)

:notebook: [Документация](https://github.com/MAVIKE/yad-docs)

:iphone: [Android](https://github.com/MAVIKE/yad-android)

:phone: [iOS](https://github.com/MAVIKE/yad-ios)

## Запуск

Запуск осуществляется с помощью встроенный средств [Android-studio](https://developer.android.google.cn/studio?hl=en)

## Структура проекта

```
├── app
│   ├── build.gradle
│   ├── proguard-rules.pro
│   └── src
│       ├── androidTest
│       │   └── java
│       │       └── ru
│       │           └── m2d
│       │               └── yad
│       │                   └── ExampleInstrumentedTest.java
│       ├── main
│       │   ├── AndroidManifest.xml
│       │   ├── java
│       │   │   └── ru
│       │   │       └── m2d
│       │   │           └── yad
│       │   │               ├── view
│       │   │               │   ├── adapter
│       │   │               │   │   └── CustomBindingAdapter.java
│       │   │               │   └── ui
│       │   │               │       ├── dashboard
│       │   │               │       │   ├── DashboardFragment.java
│       │   │               │       │   └── DashboardViewModel.java
│       │   │               │       ├── fragments
│       │   │               │       │   └── UserDetails.java
│       │   │               │       ├── home
│       │   │               │       │   ├── HomeFragment.java
│       │   │               │       │   └── HomeViewModel.java
│       │   │               │       ├── MainActivity.java
│       │   │               │       └── notifications
│       │   │               │           ├── NotificationsFragment.java
│       │   │               │           └── NotificationsViewModel.java
│       │   │               └── viewmodel
│       │   │                   ├── UserViewModelFactory.java
│       │   │                   └── UserViewModel.java
│       │   └── res
│       │       ├── drawable
│       │       │   ├── ic_dashboard_black_24dp.xml
│       │       │   ├── ic_home_black_24dp.xml
│       │       │   ├── ic_launcher_background.xml
│       │       │   └── ic_notifications_black_24dp.xml
│       │       ├── drawable-v24
│       │       │   └── ic_launcher_foreground.xml
│       │       ├── layout
│       │       │   ├── activity_main.xml
│       │       │   ├── fragment_dashboard.xml
│       │       │   ├── fragment_home.xml
│       │       │   ├── fragment_notifications.xml
│       │       │   └── fragment_user_details.xml
│       │       ├── menu
│       │       │   └── bottom_nav_menu.xml
│       │       ├── mipmap-anydpi-v26
│       │       │   ├── ic_launcher_round.xml
│       │       │   └── ic_launcher.xml
│       │       ├── mipmap-hdpi
│       │       │   ├── ic_launcher.png
│       │       │   └── ic_launcher_round.png
│       │       ├── mipmap-mdpi
│       │       │   ├── ic_launcher.png
│       │       │   └── ic_launcher_round.png
│       │       ├── mipmap-xhdpi
│       │       │   ├── ic_launcher.png
│       │       │   └── ic_launcher_round.png
│       │       ├── mipmap-xxhdpi
│       │       │   ├── ic_launcher.png
│       │       │   └── ic_launcher_round.png
│       │       ├── mipmap-xxxhdpi
│       │       │   ├── ic_launcher.png
│       │       │   └── ic_launcher_round.png
│       │       ├── navigation
│       │       │   └── mobile_navigation.xml
│       │       ├── values
│       │       │   ├── colors.xml
│       │       │   ├── dimens.xml
│       │       │   ├── strings.xml
│       │       │   └── themes.xml
│       │       └── values-night
│       │           └── themes.xml
│       └── test
│           └── java
│               └── ru
│                   └── m2d
│                       └── yad
│                           └── ExampleUnitTest.java
├── build.gradle
├── gradle
│   └── wrapper
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── gradle.properties
├── gradlew
├── gradlew.bat
├── LICENCE
├── README.md
├── settings.gradle
├── yad_core -- Библиотечные классы для доступа к данным
│   ├── build.gradle
│   ├── consumer-rules.pro
│   ├── proguard-rules.pro
│   └── src
│       ├── androidTest
│       │   └── java
│       │       └── ru
│       │           └── m2d
│       │               └── yad_core
│       │                   └── ExampleInstrumentedTest.java
│       ├── main
│       │   ├── AndroidManifest.xml
│       │   └── java
│       │       └── ru
│       │           └── m2d
│       │               └── yad_core
│       │                   ├── core
│       │                   │   ├── models -- Модели уровня системы
│       │                   │   │   ├── CategoryItem.java
│       │                   │   │   ├── Category.java
│       │                   │   │   ├── Courier.java
│       │                   │   │   ├── Location.java
│       │                   │   │   ├── MenuItem.java
│       │                   │   │   ├── OrderItem.java
│       │                   │   │   ├── Order.java
│       │                   │   │   ├── Restaurant.java
│       │                   │   │   └── User.java
│       │                   │   └── repos -- Интерфейсы доступа к данным
│       │                   │       └── UsersRepo.java
│       │                   └── services -- Сервисы доступа к данным
│       │                       ├── mock -- Мок реализация доступа к данным
│       │                       │   └── repos
│       │                       │       └── MockUsersRepo.java
│       │                       └── remote -- Доступ к данным backend'а
│       │                           ├── models -- Модели, получаемые от внешнего сервиса
│       │                           │   ├── RawCategoryItem.java
│       │                           │   ├── RawCategory.java
│       │                           │   ├── RawCourier.java
│       │                           │   ├── RawLocation.java
│       │                           │   ├── RawMenuItem.java
│       │                           │   ├── RawOrderItem.java
│       │                           │   ├── RawOrder.java
│       │                           │   ├── RawRestaurant.java
│       │                           │   └── RawUser.java
│       │                           └── repos -- Реализация доступа к данным
│       │                               ├── RemoteService.java
│       │                               └── RemoteUsersRepo.java
│       └── test
│           └── java
│               └── ru
│                   └── m2d
│                       └── yad_core
│                           └── ExampleUnitTest.java
└── yaddeliveryboy -- Приложение для курьера
    ├── build.gradle
    ├── proguard-rules.pro
    └── src

```

### Ветки

Каждый новый тикет (issue) следует выполнять в отдельной ветке с префиксом **fb-N-**,
где **N** - номер тикета. После в названии следует краткая информация о задаче.

Например,
тикет [#1 Проектирование БД](https://github.com/MAVIKE/yad-backend/issues/1),
ветка [fb-1-db-schema](https://github.com/MAVIKE/yad-backend/tree/fb-1-db-schema).

### Коммиты

Коммиты в ветке должны начинаться с **#N**.

Пример для ветки выше - "#1 Update DB schema picture".

### Запросы на слияние

После выполнения задания надо назначить Pull Request (PR) в ветку **develop**.

PR содержит название тикета, в описании указывается
[связь с ним](https://docs.github.com/en/github/managing-your-work-on-github/linking-a-pull-request-to-an-issue).

