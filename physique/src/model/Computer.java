package model;

import interf.*;

import java.rmi.RemoteException;
import java.util.Stack;

public class Computer extends Equipment implements MachineInterf {

	// ATTRIBUTES
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Switch relatedSwitch;
	
	private Stack <Packet> receivedPackets;
	
	// GETTERS
	
	
	public Switch getRelatedSwitch()  throws RemoteException{
		
		return relatedSwitch;
	
	}


	public Stack <Packet> getReceivedPackets()  throws RemoteException{
	
		return receivedPackets;
	
	}

	// SETTERS

	public void setRelatedSwitch(Switch relatedSwitch) throws RemoteException {
	
		this.relatedSwitch = relatedSwitch;
	
	}
	
	// CONSTRUCTORS
	

	public Computer(String macAddress)  throws RemoteException{

		super(macAddress);
		
		receivedPackets = new Stack<Packet>();
	
	}
	

	public Computer() throws RemoteException{
		
	}
	
	// CLASS METHODS
	
	public boolean sendPacket(Packet packet)  throws RemoteException{
		
		if(!this.relatedSwitch.getFilteredSourceAddresses().contains(this.getMacAddress()))
		
			return this.relatedSwitch.forwardPacket(packet);
		
		return false;
		
	}
	

	public boolean sendPacket(Packet packet, String macAddress) throws RemoteException {
		
		return this.relatedSwitch.forwardPacket(packet, macAddress);		
	}

}
