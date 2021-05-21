package interf;

import model.*;

import java.rmi.Remote;

import java.rmi.RemoteException;


public interface ReseauInterf extends Remote {
	
	public boolean addSwitch(String macAddress, int numberOfPorts) throws RemoteException;

	public boolean addComputer (String macAddress) throws RemoteException;
	
	public boolean removeEquipment (Equipment equipement) throws RemoteException;

	public boolean addLink (Switch commutateur, Computer machine) throws RemoteException;

	public boolean removeLink (Switch commutateur, Computer machine) throws RemoteException;
	
	
}
