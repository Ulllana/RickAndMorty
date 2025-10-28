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

### Начальный экран приложения
<img width="360" height="808" alt="Screenshot_20251028_162723" src="https://github.com/user-attachments/assets/f41272d2-f1a8-48ea-b539-3f579221bff9" />

### Поиск по имени персонажей
<img width="360" height="808" alt="Screenshot_20251028_162912" src="https://github.com/user-attachments/assets/cdad8554-d7bb-431a-9b77-de4a11ad0168" />

### Выбор фильтров
<img width="360" height="808" alt="Screenshot_20251028_162931" src="https://github.com/user-attachments/assets/669c1fd9-68f6-4e77-b554-f75cc636927d" />

### Установка фильтров
<img width="360" height="808" alt="Screenshot_20251028_162954" src="https://github.com/user-attachments/assets/491835a5-78b7-475e-a4f6-761f14846ee8" />

### Список персонажей по выбранным фильтрам
<img width="360" height="808" alt="Screenshot_20251028_163005" src="https://github.com/user-attachments/assets/eb6d4a60-f666-4ad1-89c6-111f2e6423d3" />

### Детальная информация о персонаже 
<img width="360" height="808" alt="Screenshot_20251028_163023" src="https://github.com/user-attachments/assets/19704b50-096b-45fb-9b61-4639a26821e4" />

### Pull-to-Refresh
<img width="360" height="808" alt="Screenshot_20251028_163058" src="https://github.com/user-attachments/assets/b8a7e2d0-65e7-4d1a-b68f-adf3582e5fcb" />

### Вывод сообщения при введении несуществующего имени персонажа
<img width="360" height="808" alt="Screenshot_20251028_163234" src="https://github.com/user-attachments/assets/a00da709-bbe2-401e-af55-46221b254e04" />


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
