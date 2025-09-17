import SwiftUI
import ComposeApp

@main
struct iOSApp: App {

    init () {
        // Initialize ComposeApp or any other setup if needed
        KoinIOSKt.doInitKoin()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}