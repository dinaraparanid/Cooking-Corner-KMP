import SwiftUI
import ComposeApp

@main
struct iOSApp: App {
    var rootComponent = KodeinIOS.shared.initializeWithRootComponent()

	var body: some Scene {
		WindowGroup {
			ComposeView().ignoresSafeArea(.keyboard)
		}
	}
}