# WatchThis (Android)

Мобільний застосунок для Android Studio, який шукає фільми за словами/фразами для вивчення мови.

## 3-рівнева архітектура

У проєкті реалізовано 3 окремі модулі:

1. `data` - доступ до БД (Room), DAO, репозиторій, тестові дані
2. `business` - бізнес-логіка та валідація
3. `ui` - Android UI (Activity, меню, контекстне меню, toolbar)

## Що реалізовано по вимогах

1. Розділення логіки на 3 рівні (`data`, `business`, `ui`)
2. Графічний інтерфейс:
   - головне меню (Toolbar options menu)
   - контекстне меню (long press по фільму)
   - панель інструментів (Toolbar + кнопки дій)
   - валідація введення (`User ID`, `Search`, `Save word`)
3. Підключення до бази даних:
   - для Android-емулятора використовується локальна БД Room
   - схема PostgreSQL збережена у `database/schema.sql`

## Екрани

1. Login (`LoginActivity`)
2. Home (`MainActivity`)
3. Movie Details (`MovieDetailsActivity`)
4. Favorites (`FavoritesActivity`)
5. Profile (`ProfileActivity`)

## Як запустити в Android Studio (емулятор)

1. Відкрий папку проєкту `WatchThis` в Android Studio.
2. Дочекайся Gradle Sync.
3. Створи або запусти емулятор (API 26+).
4. Обери run configuration модуля `ui` (applicationId: `com.watchthis`).
5. Натисни Run.

## Нотатка по БД

- На Android-клієнті використано Room для коректного запуску в емуляторі.
- PostgreSQL-скрипти залишені в `database/schema.sql` та `database/seed.sql` як серверна/академічна схема.

## Стек

- Kotlin
- Android SDK (API 26+)
- Room
- Coroutines
- Material Components
