package sn.esp.dic3.model;

public class Commutateur {

    private int portNumber;
    private String switchMac;

    public Commutateur(int portNumber, String switchMac) {
        this.portNumber = portNumber;
        this.switchMac = switchMac;
    }

    public Commutateur() {}

    public int getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }

    public String getSwitchMac() {
        return switchMac;
    }

    public void setSwitchMac(String switchMac) {
        this.switchMac = switchMac;
    }
}
