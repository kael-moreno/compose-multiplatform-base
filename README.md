# Compose Multiplatform Base

A modern, production-ready Kotlin Multiplatform project template built with Compose Multiplatform, targeting Android and iOS platforms. This project demonstrates best practices for cross-platform mobile development with shared business logic, UI, and a clean architecture pattern.

## 🚀 Features

- ✅ **Cross-platform UI** with Compose Multiplatform
- ✅ **MVVM Architecture** with ViewModel and StateFlow
- ✅ **Type-safe Navigation** with Navigation Compose and Kotlinx Serialization
- ✅ **Network API integration** with Ktor client
- ✅ **JSON serialization** with kotlinx.serialization
- ✅ **Cross-platform logging** with Napier
- ✅ **Coroutines** for asynchronous programming
- ✅ **Modern Material 3 design** with GitHub-inspired color scheme
- ✅ **Lifecycle-aware components**
- ✅ **Clean Architecture** with separation of concerns
- ✅ **Base ViewModel** with common loading/error state management
- ✅ **Repository pattern** for data management
- ✅ **Comprehensive error handling** and logging
- ✅ **DataStore** for persistent data storage
- ✅ **Encrypted storage** for sensitive data
- ✅ **Dependency Injection** with Koin
- ✅ **Type-safe input validation** with KSafe

## 📱 Platforms

- **Android** (API 24+)
- **iOS** (iOS 12+)

## 🏗️ Project Structure

```
compose-multiplatform-base/
├── composeApp/                          # Shared application code
│   ├── src/
│   │   ├── commonMain/kotlin/           # Shared Kotlin code
│   │   │   ├── data/                    # Data layer
│   │   │   │   ├── datastore/           # DataStore configuration
│   │   │   │   ├── encryption/          # Encryption services
│   │   │   │   ├── model/               # Data models (User, Post, Comment, etc.)
│   │   │   │   ├── network/             # API services and HTTP client
│   │   │   │   └── repository/          # Repository pattern implementation
│   │   │   ├── di/                      # Dependency injection with Koin
│   │   │   ├── navigation/              # Type-safe navigation setup
│   │   │   │   ├── AppNavigation.kt     # Navigation configuration
│   │   │   │   └── Screen.kt            # Navigation routes
│   │   │   ├── presentation/            # Presentation layer
│   │   │   │   ├── screen/              # Compose screens (Main, UserList, PostsList)
│   │   │   │   └── viewmodel/           # ViewModels with BaseViewModel
│   │   │   ├── ui/                      # UI components and theming
│   │   │   ├── App.kt                   # Main App composable
│   │   │   ├── Logger.kt                # Cross-platform logging utilities
│   │   │   └── Platform.kt              # Platform-specific code
│   │   ├── androidMain/                 # Android-specific code
│   │   └── iosMain/                     # iOS-specific code
├── iosApp/                              # iOS application wrapper
└── gradle/                              # Gradle configuration
```

## 🛠️ Tech Stack

### Core Technologies
- **Kotlin** 2.2.10 - Programming language
- **Compose Multiplatform** 1.8.2 - UI framework
- **Kotlin Multiplatform** - Code sharing across platforms

### Architecture & UI
- **MVVM Architecture** - Clean separation of concerns
- **Material 3** - Modern design system with GitHub-inspired theming
- **Navigation Compose** 2.9.0-rc02 - Type-safe navigation
- **Lifecycle ViewModel Compose** 2.9.3 - Lifecycle-aware ViewModels

### Networking & Data
- **Ktor Client** 3.3.0 - HTTP client for API calls
- **Kotlinx Serialization** 1.9.0 - JSON serialization
- **Kotlinx Coroutines** 1.10.2 - Asynchronous programming
- **DataStore** 1.1.7 - Data persistence with Preferences
- **KSafe** - Type-safe input validation

### Dependency Injection
- **Koin** 4.1.1 - Lightweight dependency injection
- **Koin ViewModel** - ViewModel integration with Compose

### Development & Debugging
- **Napier** 2.7.1 - Cross-platform logging
- **Android Gradle Plugin** 8.10.1
- **Gradle** with Version Catalogs

### API Integration
- **JSONPlaceholder API** - Demo REST API for:
  - Users management
  - Posts and comments
  - Albums and photos
  - Todo items

## 🔐 Security Features

- **Encrypted Storage** - Secure storage for sensitive data
- **Platform-specific Encryption** - Leveraging native security features
- **Token Management** - Secure token storage and retrieval

## 🚀 Getting Started

### Prerequisites
- **Android Studio** Hedgehog or later
- **Xcode** 15+ (for iOS development)
- **JDK** 17 or later
- **Kotlin** 2.2.10+

### Setup Instructions

1. **Clone the repository**
   ```bash
   git clone https://github.com/YOUR-USERNAME/compose-multiplatform-base.git
   cd compose-multiplatform-base
   ```

2. **Open in Android Studio**
   - Open the project in Android Studio
   - Sync Gradle files

3. **Run on Android**
   - Select an Android device/emulator
   - Click "Run"

4. **Run on iOS**
   - Open the `iosApp` directory in Xcode
   - Select an iOS device/simulator
   - Click "Run"

## 📱 Key Features Implementation

### Cross-Platform Architecture
- **Platform** interface with expect/actual implementations for platform-specific code
- **DataStore** for synchronized preferences across platforms
- **Koin** for dependency injection across all platforms

### Navigation
- Type-safe navigation with sealed class routes
- Serializable navigation parameters
- Centralized navigation setup in AppNavigation.kt

### Data Security
- Encrypted data storage with platform-specific implementations
- Secure token management for API authentication
- Cross-platform encryption service

### UI Components
- Material 3 implementation with GitHub-inspired theme
- Responsive layouts that work on both platforms
- Common components library shared between platforms

## 📝 License

This project is licensed under the MIT License - see the LICENSE file for details.
