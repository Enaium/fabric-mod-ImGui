/*
 * Copyright 2026 Enaium
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.enaium.fabric.imgui.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.ClientCommands;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.network.chat.Component;

/**
 * A client-side {@code /imgui-test} command, used as a scratch pad for testing.
 */
public class TestCommand {
    private TestCommand() {
    }

    /**
     * Listen for the client command registration event.
     */
    public static void register() {
        ClientCommandRegistrationCallback.EVENT.register(TestCommand::onRegister);
    }

    private static void onRegister(final CommandDispatcher<FabricClientCommandSource> dispatcher, final CommandBuildContext context) {
        dispatcher.register(ClientCommands.literal("imgui-test").executes(TestCommand::execute));
    }

    private static int execute(final CommandContext<FabricClientCommandSource> context) {
        final Minecraft client = context.getSource().getClient();
        client.execute(() -> client.setScreenAndShow(new TestWindow(Component.literal("ImGui Test"))));
        return Command.SINGLE_SUCCESS;
    }
}
