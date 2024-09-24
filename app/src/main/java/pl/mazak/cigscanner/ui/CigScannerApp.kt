package pl.mazak.cigscanner.ui

import android.Manifest
import android.app.Activity
import android.util.Log
import androidx.annotation.OptIn
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pl.mazak.cigscanner.ui.navigation.CigScannerNavHost
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Composable
fun CigScannerApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    CigScannerNavHost(navController, modifier)
}


@kotlin.OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CigScannerTopBar(title: String, modifier: Modifier = Modifier) {
    TopAppBar(
        title = {
            Text(
                text = title,
            )
        },
        modifier = modifier,
    )
}


@Composable
fun CameraPreview() {
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
                            )
                        }, ContextCompat.getMainExecutor(context))
                    }
                }
            },
            modifier = Modifier.fillMaxSize()
        )

        Canvas(modifier = Modifier.fillMaxSize()) {

            drawRect(
                color = Color.Blue,
                size = Size(500f, 100f),
                topLeft = Offset(300f, 500f)
            )
        }
    }
//    DisposableEffect(Unit) {
//        onDispose {
//            val cameraProvider = cameraProviderFuture.get()
//            cameraProvider.unbindAll() // Zatrzymanie kamery
//            Log.i("X", "XDXXXXDSD")
//        }
//    }
}

fun bindPreview(
    cameraProvider: ProcessCameraProvider,
    lifecycleOwner: LifecycleOwner,
    previewView: PreviewView,
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
                analyzeImage(it)
            }
        }


    preview.surfaceProvider = (previewView.surfaceProvider)
    cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, preview, imageAnalysis)
}

@OptIn(ExperimentalGetImage::class)
@Synchronized
fun analyzeImage(image: ImageProxy) {
    val options = BarcodeScannerOptions.Builder()
        .setBarcodeFormats(
            Barcode.FORMAT_EAN_8,
            Barcode.FORMAT_EAN_13)
        .build()

    val client = BarcodeScanning.getClient(options)
    val image1 = image.image

    if (image1 != null) {
        val process = client.process(
            InputImage.fromMediaImage(image1, image.imageInfo.rotationDegrees)
        ).addOnSuccessListener { barcodes ->
            barcodes.forEach{barcode ->
                run {
                    barcode.rawValue?.let { Log.i("XXXX", "ZESKANOWANY KOD: $it") }
                }
            }
        }
    }


    Thread.sleep(1000)
    image.close()
}
