import com.ncorti.ktfmt.gradle.tasks.KtfmtFormatTask
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    kotlin("jvm") version "2.0.21"
    id("com.ncorti.ktfmt.gradle") version "0.20.1"
    id("io.gitlab.arturbosch.detekt") version "1.23.7"
}

group = "se.fzy.adventofcode"

version = "1.0-SNAPSHOT"

repositories { mavenCentral() }

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
    testImplementation(kotlin("test"))
    testImplementation("io.strikt:strikt-core:0.34.0")
}

tasks.test { useJUnitPlatform() }

kotlin { jvmToolchain(21) }

detekt {
    buildUponDefaultConfig = true
    config.from(rootDir.resolve("detekt.yml"))
}

ktfmt {
    kotlinLangStyle()
    removeUnusedImports.set(true)
    manageTrailingCommas.set(true)
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()

    testLogging { events(TestLogEvent.FAILED, TestLogEvent.PASSED, TestLogEvent.SKIPPED) }
}

tasks.register<KtfmtFormatTask>("ktfmtPrecommit") {
    source = project.fileTree(rootDir)
    include("**/*.kt")
}

tasks.withType<Detekt>().configureEach { jvmTarget = "21" }

tasks.withType<DetektCreateBaselineTask>().configureEach { jvmTarget = "21" }
