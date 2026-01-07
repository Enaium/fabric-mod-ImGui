import me.modmuss50.mpp.PublishModTask

plugins {
    id("me.modmuss50.mod-publish-plugin")
}

afterEvaluate {
    publishMods {
        file = tasks.named<AbstractArchiveTask>("remapJar").get().archiveFile.get()
        type = STABLE
        displayName = "fabric-gui-imgui ${project.version}"
        changelog = rootProject.file("changelog.md").readText(Charsets.UTF_8)
        modLoaders.add("fabric")

        curseforge {
            projectId = "1423038"
            accessToken = providers.gradleProperty("curseforge.token")
            minecraftVersions.add(property("minecraft.version").toString())
        }

        modrinth {
            projectId = "M78HuV3L"
            accessToken = providers.gradleProperty("modrinth.token")
            minecraftVersions.add(property("minecraft.version").toString())
        }

        github {
            repository = "Enaium/fabric-mod-ImGui"
            accessToken = providers.gradleProperty("github.token")
            commitish = "master"
        }

        tasks.withType<PublishModTask>().configureEach {
            dependsOn(tasks.named("remapJar"))
        }
    }
}