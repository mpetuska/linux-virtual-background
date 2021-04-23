package camfaker

import javafx.animation.AnimationTimer
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.HBox
import javafx.stage.Stage
import nu.pattern.OpenCV
import org.opencv.core.Mat
import org.opencv.core.MatOfByte
import org.opencv.core.MatOfRect
import org.opencv.core.Rect
import org.opencv.core.Scalar
import org.opencv.core.Size
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgproc.Imgproc
import org.opencv.imgproc.Imgproc.COLOR_RGB2YUV
import org.opencv.imgproc.Imgproc.cvtColor
import org.opencv.objdetect.CascadeClassifier
import org.opencv.objdetect.Objdetect
import org.opencv.videoio.VideoCapture
import java.io.ByteArrayInputStream
import java.io.FileOutputStream
import java.io.InputStream
import java.io.RandomAccessFile

fun loadImage(imagePath: String?): Mat? {
  return Imgcodecs.imread(imagePath)
}

fun saveImage(imageMatrix: Mat?, targetPath: String?) {
  Imgcodecs()
  Imgcodecs.imwrite(targetPath, imageMatrix)
}

fun mat2Bytes(mat: Mat?): ByteArray {
  val bytes = MatOfByte()
  Imgcodecs()
  Imgcodecs.imencode(".jpg", mat, bytes)
  return bytes.toArray()
}

fun mat2Img(mat: Mat?): Image? {
  val inputStream: InputStream = ByteArrayInputStream(mat2Bytes(mat))
  return Image(inputStream)
}

class VideoApp : Application() {
  override fun start(stage: Stage) {
    OpenCV.loadShared()
    val capture = VideoCapture(0)
    val imageView = ImageView()
    val hbox = HBox(imageView)
    val scene = Scene(hbox)
    stage.scene = scene
    stage.show()

    object : AnimationTimer() {
      val videoDevice: FileOutputStream

      init {
        videoDevice = FileOutputStream(RandomAccessFile("/dev/video20", "rw").fd)
      }

      fun writeToCamera(mat: Mat) {
        cvtColor(mat, mat, COLOR_RGB2YUV)
        val bytesStr = mat2Bytes(mat)
        videoDevice.write(bytesStr)
        println("written to fakecam $bytesStr")
      }

      fun getCapture(): Image? {
        val mat = Mat()
        capture.read(mat)
        writeToCamera(mat.clone())
        return mat2Img(mat)
      }

      override fun handle(l: Long) {
        imageView.image = getCapture()
      }
    }.start()
  }
}

fun start() {
  OpenCV.loadShared()
  val loadedImage = loadImage("TODO")
  val facesDetected = MatOfRect()

  val cascadeClassifier = CascadeClassifier()
  val minFaceSize = Math.round(loadedImage!!.rows() * 0.1f).toDouble()
  cascadeClassifier.load("./src/main/resources/haarcascades/haarcascade_frontalface_alt.xml")
  cascadeClassifier.detectMultiScale(
    loadedImage,
    facesDetected,
    1.1,
    3,
    Objdetect.CASCADE_SCALE_IMAGE,
    Size(minFaceSize, minFaceSize),
    Size()
  )

  val facesArray: Array<Rect> = facesDetected.toArray()
  for (face in facesArray) {
    Imgproc.rectangle(loadedImage, face.tl(), face.br(), Scalar(0.0, 0.0, 255.0), 3)
  }
  saveImage(loadedImage, "./output.jpg")
}

fun startCamera(vararg args: String) {
  Application.launch(VideoApp::class.java, *args)
}
