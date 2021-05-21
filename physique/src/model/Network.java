package model;

import java.rmi.RemoteException;

import java.rmi.server.UnicastRemoteObject;

import java.util.ArrayList;

import interf.ReseauInterf;

public class Network extends UnicastRemoteObject implements ReseauInterf {

	public Network() throws RemoteException {
		
		super();
		
		listEquipments = new ArrayList<Equipment>();
		
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Equipment> listEquipments;
	
	public ArrayList<Equipment> getListEquipments() throws RemoteException{
	
		return this.listEquipments;
	
	}
	
	private boolean addEquipment(Equipment equipment) throws RemoteException{
	
		System.out.println(equipment.getMacAddress() + " added ! ");
		
		return this.listEquipments.add(equipment);
	
	}
	
	@Override
	public boolean addSwitch(String macAddress, int numberOfPorts) throws RemoteException {
		
		Switch mySwitch = new Switch(macAddress, numberOfPorts);
		
		return this.addEquipment(mySwitch);
		
	}

	@Override
	public boolean addComputer(String macAddress) throws RemoteException {

		Computer computer = new Computer(macAddress);
		
		return this.addEquipment(computer);
		
	}
	
	public boolean removeEquipment(Equipment equipment) throws RemoteException {
		
		System.out.println("Equipment with mac @ "+ equipment.getMacAddress() + " has been removed successfully");
		
		return this.listEquipments.remove(equipment);
	
	}
	
	
	public boolean addLink(Switch commutateur, Computer machine) throws RemoteException {
				
		return commutateur.addEntry(machine);
	
	}
	
public boolean removeLink(Switch commutateur, Computer machine) throws RemoteException {
		
		return commutateur.removeEntry(machine);
	
	}

}
