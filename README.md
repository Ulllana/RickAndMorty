# Rick and Morty Android App

<p align="center">
  <img src="https://static.wikia.nocookie.net/rickandmorty/images/c/c8/Rick_and_Morty_logo.png" width="400" alt="Rick and Morty Logo">
</p>

Современное Android-приложение для просмотра персонажей вселенной Rick and Morty, построенное с использованием лучших практик Android разработки.

## 🚀 Особенности

- **Полный каталог персонажей** - просмотр всех персонажей Rick and Morty
- **Пагинация** - плавная загрузка данных с поддержкой пагинации
- **Поиск** - поиск персонажей по имени
- **Фильтрация** - фильтрация по статусу, виду и полу
- **Оффлайн-режим** - кэширование данных для работы без интернета
- **Material Design 3** - современный и интуитивный интерфейс
- **Pull-to-refresh** - обновление данных жестом

## 🛠 Технологический стек

### Архитектура
- **Clean Architecture** - четкое разделение на слои
- **MVVM** - Model-View-ViewModel для presentation слоя
- **Repository Pattern** - абстракция доступа к данным

### Jetpack Components
- **Compose** - современный декларативный UI
- **Navigation Compose** - навигация между экранами
- **Room** - локальная база данных
- **Paging 3** - пагинация данных
- **Hilt** - dependency injection
- **ViewModel** - управление UI состоянием

### Networking & Async
- **Retrofit** - HTTP клиент
- **Coil** - загрузка и кэширование изображений
- **Kotlin Coroutines** - асинхронные операции

## 📱 Скриншоты

*(Здесь могут быть добавлены скриншоты приложения)*

## 🏗 Структура проекта

app/

├── data/

│ ├── local/ # Локальная база данных (Room)

│ │ ├── dao/

│ │ ├── entity/

│ │ ├── converter/

│ │ └── RickAndMortyDatabase.kt

│ ├── remote/ # Удаленные данные (API)

│ │ ├── dto/

│ │ ├── paging/

│ │ └── RickAndMortyApi.kt

│ ├── mapper/ # Мапперы между слоями

│ └── repository/ # Реализации репозиториев

├── domain/

│ ├── model/ # Domain модели

│ ├── repository/ # Интерфейсы репозиториев

│ └── usecase/ # Use cases (бизнес-логика)

├── presentation/

│ ├── characters/ # Экран списка персонажей

│ ├── character_detail/ # Экран деталей персонажа

│ ├── components/ # Переиспользуемые компоненты

│ └── navigation/ # Навигация

├── di/ # Dependency Injection (Hilt)

└── util/ # Утилиты и хелперы

## 🎯 Ключевые компоненты

### Data Layer
- **CharacterDao** - операции с базой данных
- **RickAndMortyApi** - API endpoints
- **CharactersPagingSource** - пагинация с fallback на кэш
- **CharacterRepositoryImpl** - реализация репозитория

### Domain Layer
- **Use Cases** - инкапсуляция бизнес-логики
- **Repository Interfaces** - контракты для data слоя
- **Domain Models** - чистые модели данных

### Presentation Layer
- **CharactersScreen** - главный экран с сеткой персонажей
- **CharacterDetailScreen** - экран детальной информации
- **Composable Components** - переиспользуемые UI компоненты

## 📦 Установка и запуск

1. **Откройте проект в Android Studio**
2. **Дождитесь завершения синхронизации Gradle**
3. **Запустите приложение на эмуляторе или устройстве**

### Требования
- **Android SDK 21+**
- **Kotlin 1.9+**
- **Android Studio Giraffe или выше**

## 🎨 UI/UX Особенности

- **Адаптивный дизайн** - оптимизация для разных размеров экранов
- **Индикаторы статуса** - цветовые индикаторы для статуса персонажей
- **Плавные анимации** - загрузка изображений с crossfade
- **Обратная связь** - snackbars для показа ошибок
- **Swipe to refresh** - интуитивное обновление контента

## 🔧 Настройка и кастомизация

### Фильтрация
Приложение поддерживает фильтрацию по:
- **Статус**: Alive, Dead, Unknown
- **Вид**: Human, Alien, Robot и др.
- **Пол**: Male, Female, Genderless, Unknown

### Поиск
Поиск работает в реальном времени с debounce для оптимизации запросов.

## 📡 API

Приложение использует [The Rick and Morty API](https://rickandmortyapi.com/):
- **Базовый URL**: `https://rickandmortyapi.com/api/`
- **Полная документация**: [https://rickandmortyapi.com/documentation](https://rickandmortyapi.com/documentation)

## 📄 Лицензия

Этот проект создан для образовательных целей. Все данные принадлежат Rick and Morty API.

## 📞 Контакты

Ульяна - [ulyanalana2003@gmail.com](ulyanalana2003@gmail.com)

Project Link: [https://github.com/your-username/rick-and-morty-app](https://github.com/Ulllana/RickAndMorty)
