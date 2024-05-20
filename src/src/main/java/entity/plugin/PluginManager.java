package entity.plugin;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

public class PluginManager {
    private Map<String, PluginInterface> plugins = new HashMap<>();

    public void loadPlugin(String jarPath, String className) throws Exception {
        File jarFile = new File(jarPath);
        URL jarURL = jarFile.toURI().toURL();
        URLClassLoader loader = URLClassLoader.newInstance(new URL[]{jarURL});
        Class<?> pluginClass = Class.forName(className, true, loader);
        PluginInterface plugin = (PluginInterface) pluginClass.getDeclaredConstructor().newInstance();
        plugins.put(className, plugin);
    }

    public PluginInterface getPlugin(String className) {
        return plugins.get(className);
    }
}
