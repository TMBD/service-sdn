package model;

import java.rmi.RemoteException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import interf.CommutateurInterf;

public class Switch extends Equipment implements CommutateurInterf{

	// ATTRIBUTES

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int numberOfPorts;
	
	private Map <Integer, CommunicationMode> ports;
	
	private ArrayList <String> filteredSourceAddresses;
	
	private ArrayList <String> filteredDestinationAddresses;
	
	private Map <Integer, Equipment> commutationTable;
	
	
	
	// GETTERS
	
	public int getNumberOfPorts() {
	
		return numberOfPorts;
	
	}

	public ArrayList<String> getFilteredSourceAddresses()  throws RemoteException{

		return filteredSourceAddresses;
	
	}


	public ArrayList<String> getFilteredDestinationAddresses()  throws RemoteException{
	
		return filteredDestinationAddresses;
	
	}


	public Map<Integer, Equipment> getCommutationTable()  throws RemoteException{
	
		return commutationTable;
	
	}
	
	
		
	public Map<Integer, CommunicationMode> getPorts()  throws RemoteException{
	
		return ports;
	
	}

	//SETTERS
	

	public void setNumberOfPorts(int numberOfPorts)  throws RemoteException {
	
		this.numberOfPorts = numberOfPorts;
	
	}


	public void setCommutationTable(Map<Integer, Equipment> commutationTable)  throws RemoteException{
	
		this.commutationTable = commutationTable;
	
	}

	
	// CONSTRUCTOR
	
	public Switch(String macAddress, int numberOfPorts)  throws RemoteException{
	
		super(macAddress);
		
		ports = new HashMap<Integer, CommunicationMode>();
		
		filteredSourceAddresses = new ArrayList<String>();
		
		filteredDestinationAddresses = new ArrayList<String>();
		
		commutationTable = new HashMap<Integer, Equipment>();
		
		this.numberOfPorts = numberOfPorts;

	}
	
	public Switch() throws RemoteException{
		
	}
	
	// CLASS METHODS
	
	
		// REBOOT
		
		public boolean init(){
			
			this.ports.clear();
			
			filteredSourceAddresses.clear();
			
			filteredDestinationAddresses.clear();

			this.commutationTable.clear();
			
			return true;
			
		}

	
	
	// ADDING FILTERS
	
	
	public boolean addFilter(String macAddress, boolean mode) throws RemoteException { // true for fitering source - false for dest

		if(mode) {
			
			filteredSourceAddresses.add(macAddress);
		
		}else {
			
			filteredDestinationAddresses.add(macAddress);
			
		}

		
		return true;
		
	}
	

	
	// REMOVING FILTERS

	public void setPorts(Map<Integer, CommunicationMode> ports) throws RemoteException {
	
		this.ports = ports;
	
	}

	public boolean removeFilter(String macAddress, boolean mode)  throws RemoteException{ // true for fitering source - false for dest
		
		if(mode) {
		
			if(this.filteredSourceAddresses.contains(macAddress)) {
				
				this.filteredSourceAddresses.remove(macAddress);
				
				return true;
				
			}
			
		}else {
		
			if(filteredDestinationAddresses.contains(macAddress)) {
				
				filteredDestinationAddresses.remove(macAddress);
				
				return true;
				
			}
			
		}
			
		return false;	
		
	}
	
	
	// CONFIGURING COMMUNICATION MODE
	
		public boolean configureDuplex(int port, CommunicationMode mode)  throws RemoteException{
		
			this.ports.put(port, mode);
			
			return true;
			
		}
	
	
	

	//ADDING AN ENTRY
	
	public boolean addEntry(Equipment equipment)  throws RemoteException {
	
		int numberOfUsedEntries = commutationTable.size();
		
		if(numberOfUsedEntries < this.numberOfPorts) {
			
			commutationTable.put(numberOfUsedEntries, equipment);
				
		}
		

		for (Integer key:commutationTable.keySet()) {
			
			System.out.println("equipment with mac address " + commutationTable.get(key).getMacAddress() +" is connected on port " + key);
			
		}
		
		return false;
		
	}
	
	
	// REMOVING AN ENTRY
	
	public boolean removeEntry(Equipment equipment)  throws RemoteException{
		
		for (Integer key:commutationTable.keySet()) {
			
			if(commutationTable.get(key).equals(equipment)) {
				
				commutationTable.remove(key);
				
				System.out.println("Link removed between switch and Computer with mac address " + equipment.getMacAddress());
				
			}
			
		}
		
		
		return true;
		
	}
	
	
	// PACKET FORWARDING
	
	
	public boolean forwardPacket(Packet packet)  throws RemoteException{
		
		for (Equipment equipment : commutationTable.values()) {
			
			if(equipment instanceof Computer && !filteredDestinationAddresses.contains(equipment.getMacAddress())) {
				
				((Computer)equipment).getReceivedPackets().push(packet);
								
			}
			
		}
		
		return true;
	
	}
	
	public boolean forwardPacket(Packet packet, String macAddress)  throws RemoteException{
		
		for (Equipment equipment : commutationTable.values()) {
			
			if( (equipment instanceof Computer) && equipment.getMacAddress().equals(macAddress) && !filteredDestinationAddresses.contains(equipment.getMacAddress()))  {
				
				((Computer)equipment).getReceivedPackets().push(packet);
				
				return true;
				
			}
			
		}
		
		return false;
	
	}

}
