package interf;

import model.*;

import java.rmi.Remote;

import java.rmi.RemoteException;

import java.util.Stack;


public interface MachineInterf extends Remote {
	
	public boolean sendPacket (Packet stubPacket) throws RemoteException ;
	
	public boolean sendPacket (Packet packet, String adresseMac) throws RemoteException;
	
	public Switch getRelatedSwitch () throws RemoteException;
	
	public Stack <Packet> getReceivedPackets() throws RemoteException;

}
