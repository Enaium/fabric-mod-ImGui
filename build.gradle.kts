allprojects {
    group = "cn.enaium"
    version = rootProject.property("version").toString()

    repositories {
        mavenCentral()
        maven {
            name = "Fabric"
            url = uri("https://maven.fabricmc.net/")
        }
        maven {
            name = "legacy-fabric"
            url = uri("https://repo.legacyfabric.net/repository/legacyfabric/")
        }
        maven { url = uri("https://jitpack.io") }
    }
}