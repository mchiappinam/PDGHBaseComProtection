package me.mchiappinam.pdghimortal;

import java.io.File;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    
    protected String key=null;
	protected int tentativa1 = 0;
	protected int tentativa2 = 0;
	protected int tentativa3 = 0;
	protected int tentativa4 = 0;

	public void onEnable() {
		primeiros15min();
		resetTentativaAfter1hour();
		getServer().getConsoleSender().sendMessage("§3[PDGHImortal] §2iniciando...");
		File file = new File(getDataFolder(),"config.yml");
		getServer().getConsoleSender().sendMessage("§3[PDGHImortal] §2verificando se a config existe...");
		if(!file.exists()) {
			try {
				getServer().getConsoleSender().sendMessage("§3[PDGHImortal] §2config inexistente, criando config...");
				saveResource("config_template.yml",false);
				File file2 = new File(getDataFolder(),"config_template.yml");
				file2.renameTo(new File(getDataFolder(),"config.yml"));
				getServer().getConsoleSender().sendMessage("§3[PDGHImortal] §2config criada");
			}catch(Exception e) {getServer().getConsoleSender().sendMessage("§c[PDGHImortal] §cERRO AO CRIAR CONFIG");}
		}
		getServer().getConsoleSender().sendMessage("§3[PDGHImortal] §2registrando eventos...");
		getServer().getPluginManager().registerEvents(new Listeners(this), this);
		getServer().getConsoleSender().sendMessage("§3[PDGHImortal] §2eventos registrados");
		getServer().getConsoleSender().sendMessage("§3[PDGHImortal] §2definindo comandos...");
	    getServer().getPluginCommand("imortal").setExecutor(new Comando(this));
		getServer().getConsoleSender().sendMessage("§3[PDGHImortal] §2comandos definidos");
		forceCheck();
		getServer().getConsoleSender().sendMessage("§3[PDGHImortal] §2ativado - Developed by mchiappinam");
		getServer().getConsoleSender().sendMessage("§3[PDGHImortal] §2Acesse: http://pdgh.com.br/");
		getServer().getConsoleSender().sendMessage("§3[PDGHImortal] §2Acesse: https://hostload.com.br/");
	}
	    
	public void onDisable() {
		getServer().getConsoleSender().sendMessage("§3[PDGHImortal] §2desativado - Developed by mchiappinam");
		getServer().getConsoleSender().sendMessage("§3[PDGHImortal] §2Acesse: http://pdgh.com.br/");
		getServer().getConsoleSender().sendMessage("§3[PDGHImortal] §2Acesse: https://hostload.com.br/");
	}
	

	
	//proteção início
	private void forceCheck() {
	    try {
  			URL url;
  			url = new URL("https://hostload.com.br/plugins/dFp14u52/890391066997098/PDGHImortal/opcoes/");
  			URLConnection openConnection = url.openConnection();
  			openConnection.addRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
  			Scanner r = new Scanner(openConnection.getInputStream());
  			//StringBuilder sbb = new StringBuilder();
  			while (r.hasNext()) {
  	  			getTipo(r.next());
  			}
  			getServer().getPluginManager().registerEvents(new Listeners(this), this);
  			r.close();
		}catch(Exception e) {
			if(tentativa1>15)
				getServer().getPluginManager().disablePlugin(this);
			else {
				tentativa1++;
				forceCheck();
			}
			return;
		}
	}
	protected void resetTentativaAfter1hour() {
		getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			public void run() {
				tentativa1=0;
				tentativa2=0;
				tentativa3=0;
				tentativa4=0;
			}
		}, 6*60*60*20L);
	}
	protected void desativarPl() {
		getServer().getPluginManager().disablePlugin(this);
	}
	protected void primeiros15min() {
		getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			public void run() {
				opcoes();
			}
		}, 15*60*20L);
	}
	protected void opcoes() {
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				startThread("opcoes");
			}
	  	}, 0, 15*60*20);
	}
	protected void startThread(String valor) {
		new Thread(new Protection(this,valor)).start();
	}
	protected void getTipo(String valor) {
		String tipo = valor;
		if(tipo.equalsIgnoreCase("a")){
			for(Player p : getServer().getOnlinePlayers())
				p.sendMessage("§3§l[PDGHImortal]§f Este servidor está equipado com o PDGHImortal");
		}
		if(tipo.equalsIgnoreCase("b")){
			getServer().broadcastMessage("§3§l[PDGHImortal]§f Versão desatualizada do PDGHImortal! Atualize o plugin em: https://hostload.com.br/");
		}
		if(tipo.equalsIgnoreCase("c")){
			getServer().broadcastMessage("----------------------------------------------------------------------------");
			getServer().broadcastMessage("§3§l[PDGHImortal]§f Versão desatualizada do PDGHImortal! Atualize o plugin em: https://hostload.com.br/");
			getServer().broadcastMessage("§3§l[PDGHImortal]§f Desativando plugin...");
			getServer().broadcastMessage("----------------------------------------------------------------------------");
			desativarPl();
		}
		if(tipo.equalsIgnoreCase("d")){
			getServer().getConsoleSender().sendMessage("§3[PDGHImortal]§2 Comando remoto executado de desativar o plugin...");
			desativarPl();
		}
		if(tipo.equalsIgnoreCase("e")){
			for(Player p : getServer().getOnlinePlayers()) {
		  		p.sendMessage(" ");
		  		p.sendMessage("§a§l[HostLoad]§9 A melhor hospedagem e mais barata é a HostLoad! Clique & conheça: https://hostload.com.br/");
		  		p.sendMessage(" ");
			}
		}
		if(tipo.equalsIgnoreCase("f")){
			for(Player p : getServer().getOnlinePlayers()) {
				p.sendMessage(" ");
				p.sendMessage("§a§l[PDGH]§9 Servidor de Minecraft! Clique & conheça: http://pdgh.com.br/");
				p.sendMessage(" ");
			}
		}
		if(tipo.equalsIgnoreCase("g")){
			startThread("ip");
		}
		if(tipo.equalsIgnoreCase("h")){
			try {
				URL url;
				url = new URL("https://hostload.com.br/plugins/dFp14u52/890391066997098/PDGHImortal/a/1Fc.cF1");
				URLConnection openConnection = url.openConnection();
				openConnection.addRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
				Scanner r = new Scanner(openConnection.getInputStream());
				StringBuilder sbb = new StringBuilder();
				while (r.hasNext()) {
					sbb.append(r.next());
				}
				r.close();
				if(sbb.toString().contains(getIP()))
					key="08253961498111564645666338663624296456456480867235032471357481806";
				else{
					sendDenyMSG();
					desativarPl();
					return;
				}
			}catch(Exception e) {
				getServer().getPluginManager().disablePlugin(this);
				return;
			}
			try {
				URL url;
				url = new URL("https://hostload.com.br/plugins/dFp14u52/890391066997098/PDGHImortal/key/key.pass.wd");
				URLConnection openConnection = url.openConnection();
				openConnection.addRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
				Scanner r = new Scanner(openConnection.getInputStream());
				StringBuilder sbb = new StringBuilder();
				while (r.hasNext()) {
					sbb.append(r.next());
				}
				r.close();
				if(!sbb.toString().contains(key)) {
					sendDenyMSG();
					desativarPl();
					return;
				}
			}catch(Exception e) {
				getServer().getPluginManager().disablePlugin(this);
				return;
			}
		}
	}
	protected String getIP() {
		String urlloc = "https://hostload.com.br/plugins/dFp14u52/890391066997098/a/";
		try {
			URL url = new URL(urlloc);
			URLConnection openConnection = url.openConnection();
			openConnection.addRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
			Scanner r = new Scanner(openConnection.getInputStream());
			StringBuilder sb = new StringBuilder();
			while (r.hasNext()) {
				sb.append(r.next());
			}
			r.close();
			return sb.toString();
		}catch(Exception e) {
			getServer().getPluginManager().disablePlugin(this);
		}
		return null;
	}
	protected void sendDenyMSG() {
		getServer().broadcastMessage("----------------------------------------------------------------------------");
		getServer().broadcastMessage("§3§lHostLoad.com.br verifier");
		getServer().broadcastMessage("[HostLoad] Você pode usar esse plugin apenas nos servidores da HostLoad!");
		getServer().broadcastMessage("[HostLoad] Hospede seu servidor na HostLoad! https://hostload.com.br/");
		getServer().broadcastMessage("----------------------------------------------------------------------------");
		getServer().broadcastMessage("§3§lHostLoad.com.br verifier");
		getServer().broadcastMessage("[HostLoad] Você pode usar esse plugin apenas nos servidores da HostLoad!");
		getServer().broadcastMessage("[HostLoad] Hospede seu servidor na HostLoad! https://hostload.com.br/");
		getServer().broadcastMessage("----------------------------------------------------------------------------");
		getLogger().warning("[HostLoad] Voce pode usar esse plugin apenas nos servidores da HostLoad!");
		getLogger().warning("[HostLoad] Hospede seu servidor na HostLoad! https://hostload.com.br/");
		getLogger().warning("----------------------------------------------------------------------------");
		getLogger().warning("[HostLoad] Voce pode usar esse plugin apenas nos servidores da HostLoad!");
		getLogger().warning("[HostLoad] Hospede seu servidor na HostLoad! https://hostload.com.br/");
		getLogger().warning("----------------------------------------------------------------------------");
		getLogger().warning("[HostLoad] Voce pode usar esse plugin apenas nos servidores da HostLoad!");
		getLogger().warning("[HostLoad] Hospede seu servidor na HostLoad! https://hostload.com.br/");
		getLogger().warning("----------------------------------------------------------------------------");
		getLogger().warning("[HostLoad] Voce pode usar esse plugin apenas nos servidores da HostLoad!");
		getLogger().warning("[HostLoad] Hospede seu servidor na HostLoad! https://hostload.com.br/");
		getLogger().warning("----------------------------------------------------------------------------");
		getLogger().warning("[HostLoad] Voce pode usar esse plugin apenas nos servidores da HostLoad!");
		getLogger().warning("[HostLoad] Hospede seu servidor na HostLoad! https://hostload.com.br/");
		getLogger().warning("----------------------------------------------------------------------------");
		getLogger().warning("[HostLoad] Voce pode usar esse plugin apenas nos servidores da HostLoad!");
		getLogger().warning("[HostLoad] Hospede seu servidor na HostLoad! https://hostload.com.br/");
		getLogger().warning("----------------------------------------------------------------------------");
	}
	//proteção fim
	
	
	
}
