plugins {
    java
    application
    id("com.gradleup.shadow") version "8.3.6"
    id("org.danilopianini.gradle-java-qa") version "1.96.0"
    //id("org.openjfx.javafxplugin") version "0.1.0"
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories { // Where to search for dependencies
    mavenCentral()
    gradlePluginPortal()
    maven("https://jitpack.io")
}

val javaFXModules = listOf(
    "base",
    "controls",
    "fxml",
    "swing",
    "graphics"
)

val supportedPlatforms = listOf("linux", "mac", "win") 

// javafx {
//     version = "21"
//     modules("javafx.controls", "javafx.media", "javafx.fxml")
// }

val osName = when {
    org.gradle.internal.os.OperatingSystem.current().isWindows -> "win"
    org.gradle.internal.os.OperatingSystem.current().isMacOsX -> "mac"
    else -> "linux"
}

dependencies {
    // testfx
    testImplementation("org.testfx:testfx-junit5:4.0.16-alpha")
    val slf4jVersion = "2.0.17"
    implementation("org.slf4j:slf4j-api:$slf4jVersion")
    runtimeOnly("ch.qos.logback:logback-classic:1.5.17")
    // JUnit 5
    val jUnitVersion = "5.11.4"
    testImplementation("org.junit.jupiter:junit-jupiter-api:$jUnitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$jUnitVersion")
    // Suppressions for SpotBugs
    compileOnly("com.github.spotbugs:spotbugs-annotations:4.9.3")
    //lyudaio:jcards
    implementation("com.github.lyudaio:jcards:main-SNAPSHOT")

    // OMDb API
    implementation("com.omertron:API-OMDB:1.5")

    //jOOL
    implementation("org.jooq:jool:0.9.15")

    implementation("org.openjfx:javafx-media:21:$osName")

    //Batik
    implementation("org.apache.xmlgraphics:batik-transcoder:1.17")
    implementation("org.apache.xmlgraphics:batik-codec:1.17")
    implementation("org.apache.xmlgraphics:batik-svg-dom:1.17")
    implementation("xml-apis:xml-apis:1.4.01")

    val javaFxVersion = "23.0.2"
    implementation("org.openjfx:javafx:$javaFxVersion")
    for (platform in supportedPlatforms) {
        for (module in javaFXModules) {
            implementation("org.openjfx:javafx-$module:$javaFxVersion:$platform")
        }
    }
}

application {
    // Define the main class for the application.
    mainClass.set("ludomania.core.Ludomania")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events(*org.gradle.api.tasks.testing.logging.TestLogEvent.values())
        showStandardStreams = true
    }
}