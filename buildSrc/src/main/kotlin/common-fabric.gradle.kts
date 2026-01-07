plugins {
    java
    id("fabric-loom")
    id("mod-publish")
    id("com.vanniktech.maven.publish")
}

val modName: String by rootProject
base {
    archivesName.set(modName)
}

dependencies {
    compileOnly(project(":core"))
    minecraft("com.mojang:minecraft:${property("minecraft.version")}")
    modImplementation("net.fabricmc:fabric-loader:${property("fabric.loader.version")}")
}

val modVersion: String by rootProject

val imguiVersion = property("imgui.version")
version = "${property("minecraft.version")}-${modVersion}+imgui.$imguiVersion"

dependencies {
    include(api("io.github.spair:imgui-java-binding:$imguiVersion")!!)
    include(api("io.github.spair:imgui-java-lwjgl3:$imguiVersion")!!)
    include(api("io.github.spair:imgui-java-natives-windows:$imguiVersion")!!)
    include(api("io.github.spair:imgui-java-natives-linux:$imguiVersion")!!)
    include(api("io.github.spair:imgui-java-natives-macos:$imguiVersion")!!)
}

mavenPublishing {

    publishToMavenCentral(automaticRelease = true)

    signAllPublications()

    coordinates(
        groupId = project.group.toString(),
        artifactId = base.archivesName.get(),
        version = project.version.toString()
    )

    pom {
        name = "Fabric Gui ImGui"
        description = "Minecraft Gui Library"
        url = "https://github.com/Enaium/fabric-mod-ImGui"
        licenses {
            license {
                name = "The Apache License, Version 2.0"
                url = "http://www.apache.org/licenses/LICENSE-2.0.txt"
            }
        }
        developers {
            developer {
                name = "Enaium"
                url = "https://github.com/Enaium"
            }
        }
        scm {
            connection.set("scm:git:git://github.com/Enaium/fabric-mod-ImGui.git")
            developerConnection.set("scm:git:ssh://github.com/Enaium/fabric-mod-ImGui.git")
            url.set("https://github.com/Enaium/fabric-mod-ImGui")
        }
    }
}

tasks.processResources {
    from(project(":core").sourceSets.main.get().output)
    inputs.property("currentTimeMillis", System.currentTimeMillis())

    filesMatching("fabric.mod.json") {
        expand(
            mapOf(
                "version" to project.version.toString(),
                "minecraft_version" to properties["minecraft.version"].toString()
                    .let { "${it.subSequence(0, it.lastIndexOf("."))}.x" },
                "java_version" to properties["java.version"].toString(),
            )
        )
    }

    filesMatching("fabric-gui-imgui.mixins.json") {
        expand(
            mapOf(
                "java_version" to properties["java.version"],
                "mixin_list" to file("src/main/java/cn/enaium/fabric/imgui/mixin").listFiles()
                    ?.joinToString(", ", "[", "]") { """"${it.name.substringBeforeLast(".")}"""" }
            )
        )
    }
}

property("java.version").toString().toInt().let {
    tasks.withType<JavaCompile> {
        options.release.set(it)
    }

    java.sourceCompatibility = JavaVersion.toVersion(it)
    java.targetCompatibility = JavaVersion.toVersion(it)
}

afterEvaluate {
    configurations.runtimeClasspath.get().forEach {
        if (it.name.startsWith("sponge-mixin")) {
            tasks.named<JavaExec>("runClient") {
                jvmArgs("-javaagent:${it.absolutePath}")
            }
            tasks.named<JavaExec>("runServer") {
                jvmArgs("-javaagent:${it.absolutePath}")
            }
        }
    }
}