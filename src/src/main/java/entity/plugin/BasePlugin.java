package entity.plugin;

import java.util.*;
import java.io.IOException;
import entity.*;

public abstract class BasePlugin {
    private Map<String, String> keyToValueMap = new HashMap<>();

    public BasePlugin() {
        this.keyToValueMap.put("SIRIP_HIU", "Shark Fin");    
        this.keyToValueMap.put("Shark Fin", "SIRIP_HIU");    
        this.keyToValueMap.put("SUSU", "Milk");    
        this.keyToValueMap.put("Milk", "SUSU");    
        this.keyToValueMap.put("DAGING_DOMBA", "Sheep Meat");    
        this.keyToValueMap.put("Sheep Meat", "DAGING_DOMBA");    
        this.keyToValueMap.put("DAGING_KUDA", "Horse Meat");    
        this.keyToValueMap.put("Horse Meat", "DAGING_KUDA");    
        this.keyToValueMap.put("DAGING_BERUANG", "Bear Meat");    
        this.keyToValueMap.put("Bear Meat", "DAGING_BERUANG");    
        this.keyToValueMap.put("TELUR", "Egg");    
        this.keyToValueMap.put("Egg", "TELUR");    
        this.keyToValueMap.put("STROBERI", "Strawberry");    
        this.keyToValueMap.put("Strawberry", "STROBERI");    
        this.keyToValueMap.put("LABU", "Pumpkin");    
        this.keyToValueMap.put("Pumpkin", "LABU");    
        this.keyToValueMap.put("JAGUNG", "Corn");    
        this.keyToValueMap.put("Corn", "JAGUNG");    
    }
}
