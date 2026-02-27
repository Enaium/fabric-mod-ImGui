# Fabric GUI ImGui

[![Version](https://img.shields.io/github/v/tag/Enaium/fabric-mod-ImGui?label=version&style=flat-square&logo=github)](https://github.com/Enaium/fabric-mod-ImGui/releases)
[![CurseForge Downloads](https://img.shields.io/curseforge/dt/1423038?style=flat-square&logo=curseforge)](https://www.curseforge.com/minecraft/mc-mods/fabric-gui-imgui)
[![Modrinth Downloads](https://img.shields.io/modrinth/dt/M78HuV3L?style=flat-square&logo=modrinth)](https://modrinth.com/mod/fabric-gui-imgui)

A powerful Fabric mod library that brings the modern ImGui interface framework to Minecraft, enabling developers to
create beautiful, responsive UIs with minimal code.

> **⚠️ This is a dependency mod** — it doesn't add content to the game itself. Other mods use this library to provide
> enhanced user interfaces. For players: install this if a mod you're using requires it.

## Overview

Fabric GUI ImGui provides seamless integration between Minecraft and the Dear ImGui framework, allowing mod developers
to:

- Create modern, professional-looking UI panels and windows
- Leverage ImGui's powerful immediate-mode GUI paradigm
- Build complex interfaces with minimal boilerplate code
- Maintain version compatibility across multiple Minecraft versions

The library is **version-agnostic** and only requires Fabric Loader, making it easy to use across different Minecraft
releases.

## Installation

### For Mod Developers

Add this dependency to your `build.gradle.kts`:

```kotlin
modImplementation("cn.enaium:fabric-gui-imgui:1.21.11-1.0.0+imgui.1.90.0")
```

### For Players

Download from:

- [CurseForge](https://www.curseforge.com/minecraft/mc-mods/fabric-gui-imgui)
- [Modrinth](https://modrinth.com/mod/fabric-gui-imgui)

## Quick Start

* First, create a class that extends `DefaultImGui`:

```java
public class ExampleImGui extends DefaultImGui {
    public ExampleImGui() {
        super("example");
    }
}
```

* Next, create a file named `cn.enaium.fabric.imgui.ImGuiService` in the `resources/META-INF/services/` directory
  containing the fully qualified name of your class:

```
com.example.ExampleImGui
```

* Finally, create a screen that implements `ImGuiRenderable` to define your ImGui interface:

```java
public final class ExampleScreen extends Screen implements ImGuiRenderable {

    private static final ImBoolean showDemoWindow = new ImBoolean(false);

    public ExampleScreen() {
        super(Text.literal("Example Screen"));
    }

    @Override
    public void render(ImGuiIO io) {
        if (ImGui.begin("Hello, ImGui!")) {
            ImGui.setWindowSize(800, 600);
            ImGui.checkbox("Show Demo Window", showDemoWindow);
            ImGui.end();
        }

        ImGui.showDemoWindow(showDemoWindow);
    }
}
```

## Version Compatibility

This library supports multiple Minecraft versions with corresponding ImGui versions:

| Minecraft | ImGui   |
|-----------|---------|
| 1.14.4    | 1.86.12 |
| 1.15.2    | 1.86.12 |
| 1.16.5    | 1.86.12 |
| 1.17.1    | 1.86.12 |
| 1.18.2    | 1.86.12 |
| 1.19.4    | 1.90.0  |
| 1.20.6    | 1.90.0  |
| 1.21.11   | 1.90.0  |
| 26.1      | 1.90.0  |