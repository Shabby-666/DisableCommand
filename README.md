# DisableCommand 插件 / DisableCommand Plugin

## 简介 / Introduction
一个简单而实用的Minecraft Bukkit/Spigot插件，用于禁用Minecraft原版或其他插件的命令。

A simple and practical Minecraft Bukkit/Spigot plugin for disabling vanilla Minecraft commands or commands from other plugins.

## 功能特点 / Features
- 通过配置文件灵活禁用任意命令，无需修改代码
- 自定义禁用命令时显示的提示消息
- 支持命令重载功能，无需重启服务器即可应用配置更改
- 管理员（OP）和拥有特定权限的玩家可以绕过命令禁用限制

- Flexibly disable any commands through configuration file without modifying code
- Customizable message displayed when a player tries to execute a disabled command
- Support for configuration reloading without restarting the server
- Administrators (OP) and players with specific permissions can bypass command restrictions

## 支持的Minecraft版本 / Supported Minecraft Versions
- 1.21.4

## 安装方法 / Installation
1. 确保你的服务器使用的是Spigot或Bukkit兼容的服务器软件
2. 将编译好的`DisableCommand.jar`文件放入服务器的`plugins`文件夹
3. 启动服务器，插件会自动生成配置文件
4. 编辑配置文件后重启服务器或使用重载命令应用更改

1. Ensure your server is running Spigot or a Bukkit-compatible server software
2. Place the compiled `DisableCommand.jar` file into your server's `plugins` folder
3. Start the server, and the plugin will automatically generate configuration files
4. After editing the configuration file, restart the server or use the reload command to apply changes

## 配置说明 / Configuration
插件首次启动后，会在`plugins/DisableCommand`文件夹中生成`DisableCommands.yml`文件。配置文件内容如下：

When the plugin starts for the first time, it will generate a `DisableCommands.yml` file in the `plugins/DisableCommand` folder. The configuration file contains the following:

```yaml
# DisableCommand配置文件
# 在此处添加你想要禁用的命令，无需包含斜杠
# Example: disabled-commands: ["gamemode", "tp", "give"]
disabled-commands:
  - gamemode
  - tp
  - give

# 当玩家尝试执行禁用命令时显示的消息
# 使用 & 符号作为颜色代码前缀，例如："&eDisable&cCommand &a>> &4这个命令已被禁用！"
# Message displayed when a player attempts to execute a disabled command
# Use & symbol as color code prefix, e.g.: "&eDisable&cCommand &a>> &4This command has been disabled!"
disabled-message: "&eDisable&cCommand &a>> &4这个命令已被禁用！"
```

- **disabled-commands**: 在这里添加你想要禁用的命令列表，无需包含斜杠
- **disabled-message**: 当玩家尝试执行被禁用的命令时显示的提示消息，可以使用`&`符号添加颜色代码

- **disabled-commands**: Add the list of commands you want to disable here, without including the slash
- **disabled-message**: The message displayed when a player tries to execute a disabled command, you can use `&` symbol to add color codes

## 使用方法 / Usage

### 添加禁用命令 / Adding Disabled Commands
编辑`plugins/DisableCommand/DisableCommands.yml`文件，在`disabled-commands`列表中添加你想要禁用的命令。例如：

Edit the `plugins/DisableCommand/DisableCommands.yml` file and add the commands you want to disable to the `disabled-commands` list. For example:

```yaml
disabled-commands:
  - gamemode
  - tp
  - give
  - op
  - kill
```

### 重载配置 / Reloading Configuration
修改配置文件后，可以使用以下命令重载配置，无需重启服务器：

After modifying the configuration file, you can use the following command to reload the configuration without restarting the server:

```
/disablecommand reload
```

或者使用简写版本：

Or use the shorthand version:

```
/dc reload
```

注意：只有拥有`disablecommand.reload`权限的玩家才能使用此命令，默认情况下只有OP拥有此权限。

Note: Only players with the `disablecommand.reload` permission can use this command, which is by default only available to OPs.

## 权限节点 / Permission Nodes
- `disablecommand.use` - 允许使用DisableCommand插件的命令，默认所有人拥有
- `disablecommand.reload` - 允许使用重载命令，默认只有OP拥有
- `disablecommand.bypass` - 允许绕过禁用的命令，默认只有OP拥有

- `disablecommand.use` - Allows using DisableCommand plugin commands, available to everyone by default
- `disablecommand.reload` - Allows reloading the configuration, available only to OPs by default
- `disablecommand.bypass` - Allows bypassing disabled commands, available only to OPs by default

## 常见问题 / Frequently Asked Questions

### Q: 如何禁用带有参数的命令？
A: 目前插件只禁用命令本身，不考虑参数。如果你禁用了tp命令，那么所有以/tp开头的命令都会被禁用。

### Q: How to disable commands with parameters?
A: Currently, the plugin only disables the command itself, not considering parameters. If you disable the tp command, all commands starting with /tp will be disabled.

### Q: 如何禁用其他插件的命令？
A: 与禁用原版命令的方法相同，只需在配置文件中添加插件命令的名称即可。

### Q: How to disable commands from other plugins?
A: The same way as disabling vanilla commands, simply add the name of the plugin command to the configuration file.

### Q: 为什么某些命令无法被禁用？
A: 一些特殊命令可能通过其他方式执行，不受PlayerCommandPreprocessEvent事件的影响。

### Q: Why can't some commands be disabled?
A: Some special commands may be executed through other means and are not affected by the PlayerCommandPreprocessEvent.

### Q: 为什么管理员可以执行被禁用的命令？
A: 为了方便服务器管理，插件默认允许OP和拥有`disablecommand.bypass`权限的玩家执行所有命令。

### Q: Why can administrators execute disabled commands?
A: For server management convenience, the plugin by default allows OPs and players with `disablecommand.bypass` permission to execute all commands.

## 开发者信息 / Developer Information
如果您是开发者，想要修改或扩展此插件，可以使用以下命令编译：

If you are a developer and want to modify or extend this plugin, you can compile it using the following command:

```
mvn clean install
```

编译后的插件文件将位于`target`目录中。

The compiled plugin file will be located in the `target` directory.

## 许可证 / License
此插件采用MIT许可证开源。

This plugin is open-source under the MIT License.
