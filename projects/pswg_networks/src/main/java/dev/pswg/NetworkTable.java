package dev.pswg;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class NetworkTable {
	private NetworkTable() {}

	@NotNull
	static ConcurrentHashMap<@NotNull UUID, @NotNull Network> NetworksPackagePrivate = new ConcurrentHashMap<>();

	public static boolean NetworksContainsKey(UUID key){
		return NetworksPackagePrivate.containsKey(key);
	}

	public static Set<UUID> GetNetworkIds(){
		return Set.copyOf(NetworksPackagePrivate.keySet());
	}

	private static void CloseNetworks(){
		ArrayList<Network> snapshot = new ArrayList<>(NetworksPackagePrivate.values());

		for (Network network : snapshot){
			try{
				network.close();
			} catch (Exception ex){
				ex.printStackTrace(); // TODO: however MC handles logging and exceptions.
			}
		}

		// If close() removed correctly, this should now be empty.
		NetworksPackagePrivate.clear();
	}

	static ConcurrentHashMap<@NotNull UUID, @NotNull NetworkNode> NodesPackagePrivate = new ConcurrentHashMap<>();

	static ConcurrentHashMap<@NotNull NetworkNode, Optional<NetworkComponentBlockEntity>> NodeToComponentPackagePrivate = new ConcurrentHashMap<>();

	public static void ToggleNetworkGlow(@NotNull UUID networkId){
		Objects.requireNonNull(networkId);
		if(NetworksPackagePrivate.containsKey(networkId)){
			Network target = NetworksPackagePrivate.get(networkId);
			Set<NetworkNode> nodes = target.GetNodes();

			for (NetworkNode node : nodes){
				NodeToComponentPackagePrivate
					.get(node)
                    .ifPresent(networkComponentBlockEntity ->
                        networkComponentBlockEntity.Glowing = !networkComponentBlockEntity.Glowing
                    );
			}
		}
	}

	private static void CloseNodes(){
		ArrayList<NetworkNode> snapshot = new ArrayList<>(NodesPackagePrivate.values());

		for (NetworkNode node : snapshot){
			try{
				node.close();
			} catch (Exception ex){
				ex.printStackTrace(); // TODO: however MC handles logging and exceptions.
			}
		}

		// If close() removed correctly, this should now be empty.
		NodesPackagePrivate.clear();
	}

	public static void CloseAll(){
		CloseNetworks();
		CloseNodes();
	}
}
