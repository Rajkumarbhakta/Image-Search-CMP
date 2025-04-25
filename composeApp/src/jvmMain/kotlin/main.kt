import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import java.awt.Dimension
import com.rkbapps.imagesearch.App
import com.rkbapps.imagesearch.di.initKoin
import org.jetbrains.compose.reload.DevelopmentEntryPoint

fun main(){
    initKoin()
    application {
        Window(
            title = "Image Search",
            state = rememberWindowState(width = 800.dp, height = 600.dp),
            onCloseRequest = ::exitApplication,
        ) {
            window.minimumSize = Dimension(350, 600)
            DevelopmentEntryPoint {
                App()
            }
        }
    }
}

@Preview
@Composable
fun AppPreview() { App() }
