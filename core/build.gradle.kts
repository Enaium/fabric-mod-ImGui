plugins {
    java
    `java-library`
}

dependencies {
    implementation(libs.imgui.java.binding)
    implementation(libs.imgui.java.natives.windows)
    implementation(libs.imgui.java.natives.linux)
    implementation(libs.imgui.java.natives.macos)
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}