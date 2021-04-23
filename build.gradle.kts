plugins {
  kotlin("jvm")
  id("com.github.jakemarsden.git-hooks")
  id("org.jlleitschuh.gradle.ktlint")
  id("org.openjfx.javafxplugin")
  idea
  application
}

repositories {
  mavenCentral()
  google()
}

idea {
  module {
    isDownloadSources = true
    isDownloadJavadoc = true
  }
}

gitHooks {
  setHooks(
    mapOf(
      "post-checkout" to "ktlintApplyToIdea",
      "pre-commit" to "ktlintFormat",
      "pre-push" to "check"
    )
  )
}

javafx {
  modules("javafx.controls", "javafx.fxml")
}

dependencies {
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:_")
  implementation("org.tensorflow:tensorflow-core-platform:_")
  implementation("org.openpnp:opencv:_")
  implementation("org.bytedeco:javacv-platform:_")
  implementation("org.python:jython:_")

  testImplementation(kotlin("test-junit"))
}

application {
  mainClass.set("camfaker.AppKt")
}
