package interf;

import java.rmi.Remote;

import java.rmi.RemoteException;

public interface EquipmentInterf extends Remote {

	public boolean toggleSwitch () throws RemoteException;
	
	public boolean getState () throws RemoteException;
	
	public String getMacAddress () throws RemoteException;
}
