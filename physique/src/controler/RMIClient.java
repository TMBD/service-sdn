package controler;

import java.rmi.registry.LocateRegistry;

import java.rmi.registry.Registry;

import java.util.ArrayList;

import interf.EquipmentInterf;

import interf.ExportableInterf;

import model.Equipment;


public class RMIClient {

	public static void main(String[] args) {
		
		try {

			Registry registry = LocateRegistry.getRegistry("192.168.43.108",1098);
			
			ExportableInterf instance = (ExportableInterf) registry.lookup("server");
			
			
			instance.addComputer("4:9f:e3:ff:45:12");
			
			instance.addSwitch("4:9f:e3:45:45:74", 10);
			
			instance.addLink("4:9f:e3:45:45:74", "4:9f:e3:ff:45:12");
			
			instance.removeLink("4:9f:e3:45:45:74", "4:9f:e3:ff:45:12");
			
			instance.removeEquipment("4:9f:e3:ff:45:12");
			
			ArrayList<Equipment> equipments = (ArrayList<Equipment>) instance.getListEquipments();
			
			for (int i = 0; i < equipments.size(); i++) {
				
				EquipmentInterf equipment = equipments.get(i);
				
				System.out.println("--- "+ equipment.getMacAddress() + "--- " + equipment.getState() );				
				
			}
			
			
		} catch (Exception e) {

			e.printStackTrace();
			
		}
		

		
	}
	
}
