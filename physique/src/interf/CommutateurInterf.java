package interf;

import model.*;

import java.rmi.Remote;

import java.rmi.RemoteException;

import java.util.ArrayList;

import java.util.Map;


public interface CommutateurInterf extends Remote {
	
	public boolean init () throws RemoteException;
	public boolean addFilter (String adresseMac, boolean mode) throws RemoteException;
	public boolean removeFilter (String adresseMac, boolean mode) throws RemoteException;
	public boolean configureDuplex(int port, CommunicationMode mode) throws RemoteException;
	public int getNumberOfPorts() throws RemoteException;
	public Map <Integer, Equipment> getCommutationTable() throws RemoteException;
	public ArrayList <String> getFilteredSourceAddresses() throws RemoteException;
	public ArrayList <String> getFilteredDestinationAddresses() throws RemoteException;
	

}
