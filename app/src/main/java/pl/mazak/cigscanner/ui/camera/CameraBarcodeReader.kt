package pl.mazak.cigscanner.ui.camera

import android.Manifest
import android.app.Activity
import androidx.annotation.OptIn
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import pl.mazak.cigscanner.R
import pl.mazak.cigscanner.ui.navigation.BasicRoute
import java.util.concurrent.Executors

object CameraBarcodeReaderRoute : BasicRoute {
    override val route: String = "cam"
    const val redirectParam: String = "redirect"
    val routeWithParam: String = "$route?$redirectParam={$redirectParam}"
    override val titleRes: Int = R.string.camera_menu_title
}

@Composable
fun CameraBarcodeReader(
    imageProcessedCallback: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }

    ActivityCompat.requestPermissions(
        context as Activity,
        arrayOf(Manifest.permission.CAMERA), 100
    )

    Box {
        AndroidView(
            factory = { ctx ->
                PreviewView(ctx).apply {
                    post {
                        cameraProviderFuture.addListener({
                            val cameraProvider = cameraProviderFuture.get()
                            bindPreview(
                                cameraProvider,
                                lifecycleOwner,
                                this,
                                imageProcessedCallback
                            )
                        }, ContextCompat.getMainExecutor(context))
                    }
                }
            },
            modifier = Modifier.fillMaxSize()
        )
//          TODO: WYPIERDOL JAK UZNASZ ZE NIEPOTRZEBNE!!!
//        Canvas(modifier = Modifier.fillMaxSize()) {
//
//            drawRect(
//                color = Color.Blue,
//                size = Size(500f, 100f),
//                topLeft = Offset(300f, 500f)
//            )
//        }
    }
    DisposableEffect(Unit) {
        onDispose {
            val cameraProvider = cameraProviderFuture.get()
            cameraProvider.unbindAll() // Zatrzymanie kamery
        }
    }
}

fun bindPreview(
    cameraProvider: ProcessCameraProvider,
    lifecycleOwner: LifecycleOwner,
    previewView: PreviewView,
    imageProcessedCallback: (String) -> Unit
) {
    val preview: Preview = Preview.Builder()
        .build()

    val cameraSelector: CameraSelector = CameraSelector.Builder()
        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
        .build()

    val imageAnalysis = ImageAnalysis.Builder()
        .build()
        .also { imageAnalysis ->
            imageAnalysis.setAnalyzer(Executors.newSingleThreadExecutor()) {
                analyzeImage(it, imageProcessedCallback)
            }
        }


    preview.surfaceProvider = (previewView.surfaceProvider)
    cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, preview, imageAnalysis)
}

@OptIn(ExperimentalGetImage::class)
@Synchronized
fun analyzeImage(image: ImageProxy, imageProcessedCallback: (String) -> Unit) {
    val options = BarcodeScannerOptions.Builder()
        .setBarcodeFormats(
            Barcode.FORMAT_EAN_8,
            Barcode.FORMAT_EAN_13
        )
        .build()

    val client = BarcodeScanning.getClient(options)
    val image1 = image.image

    if (image1 != null) {
        client.process(
            InputImage.fromMediaImage(image1, image.imageInfo.rotationDegrees)
        ).addOnSuccessListener { barcodes ->
            barcodes.forEach { barcode ->
                run {
                    val rawValue = barcode.rawValue?.let{
                        imageProcessedCallback(it)
                    }
                }
            }
        }
    }


    Thread.sleep(1000)
    image.close()
}
