package pl.mazak.cigscanner.ui.camera

import android.Manifest
import android.app.Activity
import android.graphics.Bitmap
import android.util.Log
import androidx.annotation.OptIn
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.core.resolutionselector.ResolutionSelector
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalGraphicsContext
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
import kotlin.math.log

private const val barcodeAreaSize: Float = 200f

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
        Canvas(modifier = Modifier.fillMaxSize()) {
            val barcodeAreaSizeCanvas = (size.height * barcodeAreaSize) / 640f
            drawRect(
                color = Color.Red,
                style = Stroke(4f),
                size = Size(barcodeAreaSizeCanvas, barcodeAreaSizeCanvas),
                topLeft = Offset(
                    x = (size.width / 2) - (barcodeAreaSizeCanvas / 2),
                    y = (size.height / 2) - (barcodeAreaSizeCanvas / 2)
                )
            )
        }
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
        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
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

    if (image.image != null) {
        val bitmap = Bitmap.createBitmap(image.toBitmap(),
            (image.width - barcodeAreaSize).toInt() / 2,
            (image.height - barcodeAreaSize).toInt() / 2,
            barcodeAreaSize.toInt(),
            barcodeAreaSize.toInt())

        client.process(
            InputImage.fromBitmap(bitmap, image.imageInfo.rotationDegrees)
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
