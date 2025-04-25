import androidx.compose.ui.window.ComposeUIViewController
import com.rkbapps.imagesearch.App
import com.rkbapps.imagesearch.di.initKoin
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }
