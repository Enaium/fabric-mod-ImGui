import org.gradle.util.internal.VersionNumber

plugins {
    id("cn.enaium.fabric-multi-game")
}

fmg {
    common.set(project(":core"))
}

val imguiVersion = libs.versions.imgui.get()

subprojects {
    apply(plugin = "mod-publish")

    repositories {
        maven("https://jitpack.io")
    }


    val minecraftVersion = property("minecraft.version")
    version = "$minecraftVersion-${rootProject.version}+imgui.$imguiVersion"

    dependencies {
        add("include", add("api", "io.github.spair:imgui-java-binding:$imguiVersion")!!)
        add("include", add("api", "io.github.spair:imgui-java-lwjgl3:$imguiVersion") {
            exclude(group = "org.lwjgl")
        })
        add("include", add("api", "io.github.spair:imgui-java-natives-windows:$imguiVersion")!!)
        add("include", add("api", "io.github.spair:imgui-java-natives-linux:$imguiVersion")!!)
        add("include", add("api", "io.github.spair:imgui-java-natives-macos:$imguiVersion")!!)

        if (VersionNumber.parse(minecraftVersion.toString()) < VersionNumber.parse("1.14")) {
            add("include", add("api", "com.github.Enaium:ImGui-LWJGL2:e30f6b944c")!!)
        }
    }
}