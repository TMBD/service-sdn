package model;

import interf.*;

import java.rmi.RemoteException;

import java.rmi.server.UnicastRemoteObject;

public class Equipment extends UnicastRemoteObject implements EquipmentInterf{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// ATTRIBUTES
	
	private String macAddress;
	
	private boolean state;
	
	

	// GETTERS
	
	public String getMacAddress()  throws RemoteException{
	
		return macAddress;
	
	}


	public boolean getState()  throws RemoteException{
	
		return state;
	
	}


	// SETTERS
	
	private void setMacAddress(String macAddress)  throws RemoteException {
	
		this.macAddress = macAddress;
	
	}
	
	
	// CONSTRUCTOR
	
	

	public Equipment(String macAddress) throws RemoteException{
	
		this.setMacAddress(macAddress);
	
		this.state = false;
		
	}
	
	public Equipment()  throws RemoteException{
		
	}
	
	// CLASS METHODS
	
	public boolean toggleSwitch()  throws RemoteException{
		
		state = !state;
		
		return state;
		
	}

	
}
