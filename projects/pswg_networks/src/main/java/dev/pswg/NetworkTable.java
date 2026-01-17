package dev.pswg;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class NetworkTable {
	private NetworkTable() {}

	@NotNull
	static ConcurrentHashMap<@NotNull UUID, @NotNull Network> reg = new ConcurrentHashMap<>();

	public static void CloseAll(){
		// Snapshot to avoid iteration weirdness while close() removes entries.
		ArrayList<Network> snapshot = new ArrayList<>(reg.values());

		for (Network network : snapshot){
			try{
				network.close();
			} catch (Exception ex){
				// Keep going; we want best-effort cleanup.
				// Replace with your logger as needed.
				ex.printStackTrace();
			}
		}

		// If close() removed correctly, this should now be empty.
		reg.clear();
	}
}
