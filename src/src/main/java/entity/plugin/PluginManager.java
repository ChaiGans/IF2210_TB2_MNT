package entity.plugin;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

public class PluginManager {
    private Map<String, PluginInterface> plugins = new HashMap<>();

    public PluginManager() {
        this.plugins.put("com.plugin.TxtConfigLoader", new TxtConfigLoader());
    }

    public void loadPlugin(String jarPath) throws Exception {
        File file = new File(jarPath);
        URL jarURL = file.toURI().toURL();
        URLClassLoader loader = URLClassLoader.newInstance(new URL[]{jarURL});
        String className = "";
        try (JarFile jarFile = new JarFile(file)) {
            Manifest manifest = jarFile.getManifest();
            if (manifest != null) {
                Attributes attrs = manifest.getMainAttributes();  // Get the main manifest attributes
                System.out.println("Manifest attributes:");
                className = attrs.getValue("Plugin-Class");
            } else {
                throw new Exception("Invalid jar");
            }
        }
        Class<?> pluginClass = Class.forName(className, true, loader);
        PluginInterface plugin = (PluginInterface) pluginClass.getDeclaredConstructor().newInstance();
        plugins.put(className, plugin);
    }

    public PluginInterface getPlugin(String className) {
        return plugins.get(className);
    }
}
