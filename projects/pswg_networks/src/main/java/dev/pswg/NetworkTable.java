package dev.pswg;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class NetworkTable {
	private NetworkTable() {}

	@NotNull
	static ConcurrentHashMap<@NotNull UUID, @NotNull Network> _Networks = new ConcurrentHashMap<>();

	public static Map<@NotNull UUID, @NotNull Network> GetNetworks(){
		return Map.copyOf(_Networks);
	}

	private static void CloseNetworks(){
		ArrayList<Network> snapshot = new ArrayList<>(_Networks.values());

		for (Network network : snapshot){
			try{
				network.close();
			} catch (Exception ex){
				ex.printStackTrace(); // TODO: however MC handles logging and exceptions.
			}
		}

		// If close() removed correctly, this should now be empty.
		_Networks.clear();
	}

	static ConcurrentHashMap<@NotNull UUID, @NotNull NetworkNode> _Nodes = new ConcurrentHashMap<>();

	public static Map<@NotNull UUID, @NotNull NetworkNode> GetNodes(){
		return Map.copyOf(_Nodes);
	}
	private static void CloseNodes(){
		ArrayList<NetworkNode> snapshot = new ArrayList<>(_Nodes.values());

		for (NetworkNode node : snapshot){
			try{
				node.close();
			} catch (Exception ex){
				ex.printStackTrace(); // TODO: however MC handles logging and exceptions.
			}
		}

		// If close() removed correctly, this should now be empty.
		_Nodes.clear();
	}

	public static void CloseAll(){
		CloseNetworks();
		CloseNodes();
	}
}
