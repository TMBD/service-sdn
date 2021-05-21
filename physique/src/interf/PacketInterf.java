package interf;

import java.rmi.Remote;

import java.rmi.RemoteException;

public interface PacketInterf extends Remote{
	
	public String getMessage () throws RemoteException;
	public String getEmitter() throws RemoteException;

}
