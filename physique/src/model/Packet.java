package model;

import java.rmi.RemoteException;


import java.rmi.server.UnicastRemoteObject;

import interf.PacketInterf;

public class Packet extends UnicastRemoteObject implements PacketInterf{

	// ATTRIBUTES
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String message;
	
	private String emitter;
	
	// GETTERS
	
	public String getMessage()  throws RemoteException{

		return message;

	}
	

	public String getEmitter()  throws RemoteException{
	
		return emitter;
	
	}

	
	// SETTERS
	
	private void setMessage(String message)  throws RemoteException{
	
		this.message = message;
	
	}

	private void setEmitter(String emitter)  throws RemoteException{
	
		this.emitter = emitter;
	
	}

	// CONSTRUCTOR
	
	public Packet(String message, String emitter)  throws RemoteException{
		
		this.setMessage(message);
		
		this.setEmitter(emitter);
		
	}
	
	public Packet() throws RemoteException{
		
	}
	
}
