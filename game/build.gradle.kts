plugins {
    id("cn.enaium.fabric-multi-game")
}

fmg {
    common.set(project(":core"))
}

subprojects {
    apply(plugin = "mod-publish")

    val imguiVersion = property("imgui.version")
    val minecraftVersion = property("minecraft.version")
    version = "$minecraftVersion-${rootProject.version}+imgui.$imguiVersion"

    dependencies {
        add("include", add("api", "io.github.spair:imgui-java-binding:$imguiVersion")!!)
        add("include", add("api", "io.github.spair:imgui-java-lwjgl3:$imguiVersion")!!)
        add("include", add("api", "io.github.spair:imgui-java-natives-windows:$imguiVersion")!!)
        add("include", add("api", "io.github.spair:imgui-java-natives-linux:$imguiVersion")!!)
        add("include", add("api", "io.github.spair:imgui-java-natives-macos:$imguiVersion")!!)
    }
}