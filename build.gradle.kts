plugins {
    java
    application
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("org.danilopianini.gradle-java-qa") version "1.96.0"
    id("org.openjfx.javafxplugin") version "0.1.0"
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

javafx {
    modules= listOf("javafx.controls", "javafx.media", "javafx.fxml")
}

val javaFXModules = listOf(
    "base",
    "controls",
    "fxml",
    "swing",
    "graphics",
    "media"
)

val supportedPlatforms = listOf("linux", "mac", "win") // All required for OOP

dependencies {
    //JavaFX
    val javaFxVersion = "23.0.2"
    implementation("org.openjfx:javafx:$javaFxVersion")
    for (platform in supportedPlatforms) {
        for (module in javaFXModules) {
            implementation("org.openjfx:javafx-$module:$javaFxVersion:$platform")
        }
    }

    //SLF4J
    val slf4jVersion = "2.0.17"
    implementation("org.slf4j:slf4j-api:$slf4jVersion")
    runtimeOnly("ch.qos.logback:logback-classic:1.5.17")

    /// The BOM (Bill of Materials) synchronizes all the versions of Junit coherently.
    testImplementation(platform("org.junit:junit-bom:5.13.2"))
    // The annotations, assertions and other elements we want to have access when compiling our tests.
    testImplementation("org.junit.jupiter:junit-jupiter")
    // The engine that must be available at runtime to run the tests.
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // testfx
    testImplementation("org.testfx:testfx-junit5:4.0.16-alpha")

    // Suppressions for SpotBugs
    compileOnly("com.github.spotbugs:spotbugs-annotations:4.9.3")
    testImplementation("com.github.spotbugs:spotbugs-annotations:4.9.3")

    //lyudaio:jcards
    implementation("com.github.lyudaio:jcards:main-SNAPSHOT")

    // OMDb API
    implementation("com.omertron:API-OMDB:1.5")

    //jOOL
    implementation("org.jooq:jool:0.9.15")

    //Batik
    implementation("org.apache.xmlgraphics:batik-transcoder:1.17")
    implementation("org.apache.xmlgraphics:batik-codec:1.17")
    implementation("org.apache.xmlgraphics:batik-svg-dom:1.17")
    implementation("xml-apis:xml-apis:1.4.01")
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

tasks.named<JavaExec>("run") {
    val modules = listOf(
        "javafx.base",
        "javafx.controls",
        "javafx.fxml",
        "javafx.graphics",
        "javafx.media",
        "javafx.swing"
    ).joinToString(",")

    val javafxLibs = configurations.runtimeClasspath.get()
        .filter { it.name.startsWith("javafx") }
        .joinToString(System.getProperty("path.separator")) { it.absolutePath }

    jvmArgs = listOf(
        "--module-path", javafxLibs,
        "--add-modules", modules
    )
}