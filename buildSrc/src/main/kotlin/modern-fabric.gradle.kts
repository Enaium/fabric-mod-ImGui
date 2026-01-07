plugins {
    `fabric-loom`
    id("common-fabric")
}

dependencies {
    mappings("net.fabricmc:yarn:${property("fabric.yarn.version")}:v2")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${property("fabric.api.version")}")
}