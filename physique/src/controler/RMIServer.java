package controler;

import java.rmi.RemoteException;

import java.rmi.registry.LocateRegistry;

import java.rmi.registry.Registry;

import java.rmi.server.UnicastRemoteObject;

import java.util.ArrayList;

import interf.ExportableInterf;

import model.Computer;

import model.Equipment;

import model.Network;

import model.Switch;

public class RMIServer implements ExportableInterf{

	private Network network;
	
	public RMIServer() throws RemoteException{
		
		this.network = new Network();
		
	}

	@Override
	public boolean addSwitch(String macAddress, int numberOfPorts) throws RemoteException {
	
		return this.network.addSwitch(macAddress, numberOfPorts);
		
	}


	@Override
	public boolean addComputer(String macAddress) throws RemoteException {
	
		try {

			return this.network.addComputer(macAddress);
			
		} catch (Exception e) {

			e.printStackTrace();

		}
		
		return true;
		
	}
	
	

	@Override
	public boolean removeEquipment(String equipement) throws RemoteException {

		Equipment realEquipment = this.getEquipmentByMac(equipement);
		
		return this.network.removeEquipment(realEquipment);

	}

	@Override
	public boolean addLink(String commutateur, String machine) throws RemoteException {
		
		Switch realSwitch = (Switch)this.getEquipmentByMac(commutateur);
		
		Computer realComputer= (Computer)this.getEquipmentByMac(machine);
			
		return this.network.addLink(realSwitch, realComputer);
		
	}

	
	@Override
	public boolean removeLink(String commutateur, String machine) throws RemoteException {
	
		Switch realSwitch = (Switch)this.getEquipmentByMac(commutateur);
		
		Computer realComputer= (Computer)this.getEquipmentByMac(machine);
		
		return this.network.removeLink(realSwitch, realComputer);
	}
	
	@Override
	public ArrayList<Equipment> getListEquipments() throws RemoteException{

		return this.network.getListEquipments();
	}
	
	private Equipment getEquipmentByMac(String mac) throws RemoteException {
		
		ArrayList<Equipment> equipments = this.network.getListEquipments();
		
		Equipment found = null;
		
		for (Equipment equipment : equipments) {
			
			if(equipment.getMacAddress().equals(mac)) {
				
				found = (Equipment)equipment;
				
				System.out.println(found.getMacAddress());
				
				break;
				
			}
			
		}

		return found;
		
	}

	
	public static void main(String[] args) {
	
		RMIServer server;
		try {
			server = new RMIServer();
	
			ExportableInterf stub = (ExportableInterf) UnicastRemoteObject.exportObject(server,0);
		
			Registry registry = LocateRegistry.createRegistry(1098);
			
			registry.bind("server", stub);
			
			System.out.println("Server running...");
			
		} catch (Exception e) {
		
			e.printStackTrace();
		}
	
		
	}

}
