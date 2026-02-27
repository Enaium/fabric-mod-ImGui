plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    maven {
        name = "Fabric"
        url = uri("https://maven.fabricmc.net/")
    }
    maven {
        name = "legacy-fabric"
        url = uri("https://repo.legacyfabric.net/repository/legacyfabric/")
    }
    mavenCentral()
}

val kotlinVersion: String by project
val loomVersion: String by project
val modPublishVersion: String by project

dependencies {
    api(libs.loom)
    api(libs.legacy.looming)
    api(libs.publish)
    api(libs.maven)
    api(libs.fmg)
}