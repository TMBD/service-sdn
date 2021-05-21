package interf;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import model.Equipment;

public interface ExportableInterf extends Remote{

	public boolean addSwitch(String macAddress, int numberOfPorts) throws RemoteException;
	
	public boolean addComputer(String macAddress) throws RemoteException;
	
	public boolean removeEquipment(String equipementMacAdress) throws RemoteException;
	
	public boolean addLink(String commutateurMac, String machineMac) throws RemoteException;
	
	public boolean removeLink(String commutateurMac, String machineMac) throws RemoteException;

	public ArrayList<Equipment> getListEquipments() throws RemoteException;
	
}
