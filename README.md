# Compose Multiplatform Base

A modern, production-ready Kotlin Multiplatform project template built with Compose Multiplatform, targeting Android and iOS platforms. This project demonstrates best practices for cross-platform mobile development with shared business logic and UI.

## 🚀 Features

- ✅ **Cross-platform UI** with Compose Multiplatform
- ✅ **MVVM Architecture** with ViewModel and StateFlow
- ✅ **Network API integration** with Ktor client
- ✅ **JSON serialization** with kotlinx.serialization
- ✅ **Logging** with Napier (cross-platform logging)
- ✅ **Coroutines** for asynchronous programming
- ✅ **Modern Material 3 design**
- ✅ **Lifecycle-aware components**
- ✅ **Clean Architecture** with separation of concerns

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
│   │   │   │   ├── model/               # Data models
│   │   │   │   ├── network/             # API services
│   │   │   │   └── repository/          # Repository pattern
│   │   │   ├── presentation/            # Presentation layer
│   │   │   │   ├── screen/              # Compose screens
│   │   │   │   └── viewmodel/           # ViewModels
│   │   │   ├── App.kt                   # Main App composable
│   │   │   ├── Logger.kt                # Logging utilities
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

### Dependencies
- **Ktor** 3.3.0 - HTTP client for API calls
- **kotlinx.serialization** 1.9.0 - JSON serialization
- **kotlinx.coroutines** 1.10.2 - Asynchronous programming
- **Napier** 2.7.1 - Cross-platform logging
- **AndroidX Lifecycle** 2.9.3 - Lifecycle-aware components
- **Material 3** - Modern Android design system

## 🚀 Getting Started

### Prerequisites

- **Android Studio** Hedgehog (2023.1.1) or newer
- **Xcode** 15.0+ (for iOS development)
- **JDK** 17 or higher
- **Kotlin Multiplatform Plugin** for Android Studio

### Setup

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd compose-multiplatform-base
   ```

2. **Open in Android Studio**
   - Open Android Studio
   - Select "Open an existing project"
   - Navigate to the cloned directory and open it

3. **Sync the project**
   - Android Studio will automatically sync Gradle
   - Wait for the sync to complete

### Building and Running

#### Android

**Option 1: Using Android Studio**
- Select the `composeApp` run configuration
- Click the "Run" button or press `Ctrl+R` (Windows/Linux) or `Cmd+R` (macOS)

**Option 2: Using Command Line**
```bash
# Debug build
./gradlew :composeApp:assembleDebug

# Install on connected device/emulator
./gradlew :composeApp:installDebug
```

#### iOS

**Option 1: Using Android Studio**
- Select the `iosApp` run configuration
- Click the "Run" button

**Option 2: Using Xcode**
- Open `iosApp/iosApp.xcodeproj` in Xcode
- Select a simulator or device
- Press `Cmd+R` to run

## 🏛️ Architecture

This project follows **Clean Architecture** principles with clear separation of concerns:

### Data Layer
- **Models**: Data classes representing API responses
- **Network**: Ktor HTTP client configuration and API services
- **Repository**: Data repository implementing business logic

### Presentation Layer
- **ViewModels**: Business logic and state management
- **Screens**: Compose UI screens
- **State Management**: StateFlow for reactive UI updates

### Key Components

#### UserViewModel
Manages user data state and handles user interactions:
- Fetches users from API
- Manages loading states
- Handles user selection
- Provides error handling

#### UserRepository
Centralized data management:
- API calls using Ktor client
- Data caching and state management
- Error handling and retry logic

#### Logger
Cross-platform logging utility using Napier:
- Unified logging across Android and iOS
- Different log levels (Debug, Info, Warning, Error)
- Platform-specific log output

## 🔧 Configuration

### API Configuration
The project uses [JSONPlaceholder](https://jsonplaceholder.typicode.com/) as a demo API. To change the API:

1. Update the base URL in your API service
2. Modify data models to match your API response
3. Update repository methods accordingly

### Logging Configuration
Logging is initialized in the App composable and provides cross-platform logging:
- **Android**: Logs appear in Logcat
- **iOS**: Logs appear in Xcode console

## 🧪 Testing

### Running Tests
```bash
# Run all tests
./gradlew test

# Run Android tests specifically
./gradlew :composeApp:testDebugUnitTest

# Run iOS tests
./gradlew :composeApp:iosSimulatorArm64Test
```

## 📦 Dependencies Management

Dependencies are managed in `gradle/libs.versions.toml` using Gradle Version Catalogs:

- All versions are centralized in the `[versions]` section
- Libraries are defined in the `[libraries]` section
- Plugins are defined in the `[plugins]` section

To update dependencies:
1. Update version numbers in `libs.versions.toml`
2. Sync the project
3. Test thoroughly on both platforms

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🔗 Resources

- [Kotlin Multiplatform Documentation](https://kotlinlang.org/docs/multiplatform.html)
- [Compose Multiplatform Documentation](https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-multiplatform-getting-started.html)
- [Ktor Documentation](https://ktor.io/docs/)
- [kotlinx.serialization Guide](https://kotlinlang.org/docs/serialization.html)
- [Napier GitHub](https://github.com/AAkira/Napier)

## 📞 Support

For questions, issues, or contributions, please:
- Open an issue on GitHub
- Check existing documentation
- Review the codebase for examples

---

**Happy coding! 🎉**
