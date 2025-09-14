package cn.example;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DisableCommand extends JavaPlugin implements Listener {

    private Set<String> disabledCommands = new HashSet<>();
    private String disabledMessage;
    private FileConfiguration config;
    private File configFile;
    private File configFolder;

    @Override
    public void onEnable() {
        // 注册事件监听器
        getServer().getPluginManager().registerEvents(this, this);
        
        // 创建自定义配置文件夹和配置文件
        setupConfigFile();
        
        // 从配置文件加载禁用的命令和提示消息
        loadDisabledCommands();
        loadDisabledMessage();
        
        getLogger().info("DisableCommand插件已成功启用！");
    }

    @Override
    public void onDisable() {
        getLogger().info("DisableCommand插件已成功禁用！");
    }

    // 设置自定义配置文件
    private void setupConfigFile() {
        // 获取插件数据文件夹（即plugins/DisableCommand）
        configFolder = getDataFolder();
        if (!configFolder.exists()) {
            configFolder.mkdirs();
        }
        
        // 创建或加载自定义配置文件
        configFile = new File(configFolder, "DisableCommands.yml");
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
                // 设置默认配置
                config = YamlConfiguration.loadConfiguration(configFile);
                config.set("disabled-commands", Arrays.asList("gamemode", "tp", "give"));
                config.set("disabled-message", "&eDisable&cCommand &a>> &4这个命令已被禁用！");
                config.save(configFile);
            } catch (IOException e) {
                getLogger().severe("无法创建配置文件！" + e.getMessage());
            }
        } else {
            config = YamlConfiguration.loadConfiguration(configFile);
        }
    }
    
    private void loadDisabledCommands() {
        disabledCommands.clear();
        List<String> commandsList = config.getStringList("disabled-commands");
        for (String command : commandsList) {
            // 去除命令前的斜杠并转为小写
            command = command.replaceFirst("^/", "").toLowerCase();
            disabledCommands.add(command);
        }
    }

    private void loadDisabledMessage() {
        String rawMessage = config.getString("disabled-message");
        if (rawMessage != null) {
            // 替换颜色代码
            disabledMessage = ChatColor.translateAlternateColorCodes('&', rawMessage);
        } else {
            // 默认消息
            disabledMessage = ChatColor.translateAlternateColorCodes('&', "&eDisable&cCommand &a>> &4这个命令已被禁用！");
        }
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        // 获取玩家输入的命令（不含斜杠）
        String command = event.getMessage().split(" ")[0].replaceFirst("^/", "").toLowerCase();
        
        // 检查是否是禁用的命令
        if (disabledCommands.contains(command)) {
            // 检查玩家是否有管理员权限（OP权限或bypass权限）
            if (!event.getPlayer().isOp() && !event.getPlayer().hasPermission("disablecommand.bypass")) {
                event.setCancelled(true);
                event.getPlayer().sendMessage(disabledMessage);
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // 支持disablecommand和dc两个命令
        if (cmd.getName().equalsIgnoreCase("disablecommand") || cmd.getName().equalsIgnoreCase("dc")) {
            if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
                if (sender.hasPermission("disablecommand.reload")) {
                    // 重新加载自定义配置文件
                    config = YamlConfiguration.loadConfiguration(configFile);
                    loadDisabledCommands();
                    loadDisabledMessage();
                    sender.sendMessage(ChatColor.GREEN + "DisableCommand配置已重新加载！");
                } else {
                    sender.sendMessage(ChatColor.RED + "你没有权限执行此命令！");
                }
                return true;
            }
        }
        return false;
    }
}