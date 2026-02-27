import me.modmuss50.mpp.PublishModTask
import org.gradle.util.internal.VersionNumber

plugins {
    java
    id("me.modmuss50.mod-publish-plugin")
    id("com.vanniktech.maven.publish")
}

afterEvaluate {
    publishMods {
        val disableObfuscation = properties.getOrDefault("fabric.loom.disableObfuscation", false).toString().toBoolean()
        val minecraftVersion = properties["minecraft.version"].toString()
        val modern = VersionNumber.parse(minecraftVersion) >= VersionNumber.parse("1.14")
        file = tasks.named<AbstractArchiveTask>(if (disableObfuscation) "jar" else "remapJar").get().archiveFile.get()
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
            if (!modern) {
                requires("moehreag-legacy-lwjgl3")
            }
        }

        github {
            repository = "Enaium/fabric-mod-ImGui"
            accessToken = providers.gradleProperty("github.token")
            commitish = "master"
        }

        tasks.withType<PublishModTask>().configureEach {
            dependsOn(tasks.named(if (disableObfuscation) "jar" else "remapJar"))
        }
    }

    mavenPublishing {

        publishToMavenCentral(automaticRelease = true)

        signAllPublications()

        coordinates(
            groupId = project.group.toString(),
            artifactId = rootProject.name,
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
}