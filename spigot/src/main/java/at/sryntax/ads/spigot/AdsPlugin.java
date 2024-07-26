package at.sryntax.ads.spigot;

import at.srsyntax.ads.api.API;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

/*
 * MIT License
 *
 * Copyright (c) 2024 Marcel Haberl
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
public class AdsPlugin extends JavaPlugin {

    private static final int BSTATS_ID = 20817;

    private static API api;

    private Economy economy;

    @Override
    public void onEnable() {
        try {
            new Metrics(this, BSTATS_ID);
            this.economy = setupEconomy();
        } catch (Exception exception) {
            getLogger().severe("Plugin could not be loaded successfully!");
            exception.printStackTrace();
        }
    }

    @Override
    public void onDisable() {

    }

    private Economy setupEconomy() {
       final var provider = getEconomyProvider();
       if (provider == null) {
           throw new NullPointerException("Vault not found. Install the Vault to be able to use this plugin.");
       }
       return provider;
    }

    private Economy getEconomyProvider() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) return null;
        final RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) return null;
        return rsp.getProvider();
    }

    public static API getApi() {
        if (api == null) {
            api = new APIImpl();
        }
        return api;
    }
}
