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
│   │   │   │   ├── model/               # Data models (User, Post, Comment, etc.)
│   │   │   │   ├── network/             # API services and HTTP client
│   │   │   │   └── repository/          # Repository pattern implementation
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
- **Navigation Compose** 2.9.0-beta01 - Type-safe navigation
- **Lifecycle ViewModel Compose** 2.9.3 - Lifecycle-aware ViewModels

### Networking & Data
- **Ktor Client** 3.3.0 - HTTP client for API calls
- **Kotlinx Serialization** 1.9.0 - JSON serialization
- **Kotlinx Coroutines** 1.10.2 - Asynchronous programming

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

## 🚀 Getting Started

### Prerequisites
- **Android Studio** Giraffe or later
- **Xcode** 14+ (for iOS development)
- **JDK** 11 or later
- **Kotlin** 2.2.10+

### Setup Instructions

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd compose-multiplatform-base
   ```

2. **Open in Android Studio**
   - Open the project in Android Studio
   - Sync Gradle files
   - Wait for indexing to complete

3. **Run on Android**
   ```bash
   ./gradlew composeApp:assembleDebug
   ```
   Or use the "Run" button in Android Studio

4. **Run on iOS**
   - Open `iosApp/iosApp.xcodeproj` in Xcode
   - Select target device/simulator
   - Build and run

### Build Commands

```bash
# Android Debug Build
./gradlew composeApp:assembleDebug

# Android Release Build
./gradlew composeApp:assembleRelease

# iOS Framework
./gradlew composeApp:embedAndSignAppleFrameworkForXcode

# Clean Project
./gradlew clean
```

## 🏛️ Architecture Details

### MVVM Pattern
- **Model**: Data classes with kotlinx.serialization
- **View**: Compose UI screens
- **ViewModel**: StateFlow-based state management with BaseViewModel

### BaseViewModel Features
- Automatic loading state management
- Global error handling
- Success message display
- Coroutine-based operation execution
- Comprehensive logging

### Data Layer
- **Repository Pattern**: Centralized data management
- **API Service**: Ktor-based HTTP client
- **Models**: Serializable data classes for Users, Posts, Comments, Albums, Photos, and Todos

### Navigation
- **Type-safe routes** using sealed classes
- **Kotlinx Serialization** for route parameters
- **Navigation Compose** for declarative navigation

## 📱 Features Overview

### Screens
1. **Main Screen**: Navigation hub with options to view Users and Posts
2. **User List Screen**: Display list of users from JSONPlaceholder API
3. **Posts List Screen**: Display list of posts with full CRUD operations

### Core Functionality
- **Network calls** with proper error handling
- **Loading states** with BaseViewModel
- **Cross-platform logging** with structured output
- **Material 3 theming** with light/dark mode support
- **Type-safe navigation** between screens

## 🎨 UI/UX Features

- **GitHub-inspired color scheme** for both light and dark themes
- **Material 3 components** throughout the app
- **Responsive design** for different screen sizes
- **Loading indicators** and error states
- **Clean, modern interface**

## 🧪 Testing

```bash
# Run common tests
./gradlew composeApp:testDebugUnitTest

# Run Android tests
./gradlew composeApp:connectedAndroidTest
```

## 📦 Dependencies

Key dependencies are managed through Gradle Version Catalogs:
- Compose Multiplatform BOM
- Ktor for networking
- Kotlinx Serialization for JSON
- Napier for logging
- Navigation Compose for routing
- Material 3 for theming

## 🚀 Deployment

### Android
1. Configure signing in `android` block
2. Build release APK: `./gradlew assembleRelease`
3. Deploy to Google Play Store

### iOS
1. Configure provisioning profiles in Xcode
2. Archive and export IPA
3. Deploy to App Store Connect

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- [Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform)
- [Ktor](https://ktor.io/)
- [JSONPlaceholder](https://jsonplaceholder.typicode.com/) for demo API
- [Napier](https://github.com/AAkira/Napier) for cross-platform logging
- Material Design 3 and GitHub design inspiration
