import org.gradle.internal.os.OperatingSystem

plugins {
    `fabric-loom`
    `legacy-looming`
    id("common-fabric")
}

dependencies {
    mappings("net.legacyfabric:yarn:${property("fabric.yarn.version")}:v2")
    modImplementation("net.legacyfabric.legacy-fabric-api:legacy-fabric-api:${property("fabric.api.version")}")
}