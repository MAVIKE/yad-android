# YAD Android

<!-- ToC start -->
## Содержание

1. [Ссылки](#Ссылки)
1. [Запуск](#Запуск)
1. [Структура проекта](#Структура-проекта)
1. [Правила](#Правила)
<!-- ToC end -->

## Ссылки

:bookmark_tabs: [Kanban](https://github.com/orgs/MAVIKE/projects/1)

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
│       ├── main
│       │   ├── AndroidManifest.xml
│       │   ├── java
│       │   │   └── ru
│       │   │       └── m2d
│       │   │           └── yad
│       │   │               ├── view
│       │   │               │   ├── adapter
│       │   │               │   └── ui
│       │   │               │       ├── dashboard
│       │   │               │       ├── fragments
│       │   │               │       ├── home
│       │   │               │       ├── MainActivity.java
│       │   │               │       └── notifications
│       │   │               └── viewmodel
│       │   └── res -- Ресурсные файлы
│       │       ├── drawable
│       │       ├── layout
│       │       ├── values
│       │       └── values-night
│       └── test -- Тесты
│           └── java
│               └── ru
│                   └── m2d
│                       └── yad
│                           └── ExampleUnitTest.java
├── yad_core -- Библиотечные классы для доступа к данным
│   └── src
│       ├── main
│       │   ├── AndroidManifest.xml
│       │   └── java
│       │       └── ru
│       │           └── m2d
│       │               └── yad_core
│       │                   ├── core
│       │                   │   ├── models -- Модели уровня системы
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

