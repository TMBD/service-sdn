class Network{
    constructor(){
        this.laptopList = [];
        this.switchList = [];
        var baseUrl = "http://localhost:9991";

        

        this.addLaptop = function(laptopDiv, id, moveLaptopEventListener, addConnector){
            let onAddLaptopSuccess = (function(data){
                this.laptopList.push(new Laptop(laptopDiv, id, data.laptopMac, moveLaptopEventListener, addConnector));
            }).bind(this);

            let onAddLaptopError = (function(jqXHR, textStatus, errorThrown){
                console.log(jqXHR);
                console.log(textStatus);
                console.log(errorThrown);
                // this.laptopList.push(new Laptop(laptopDiv, id, id, moveLaptopEventListener, addConnector)); // a supprimer
                laptopDiv.remove(); //a decommenter
                alert("Une erreur s'est produite lors de la création de la machine : "+errorThrown.message); //a decommenter
            }).bind(this);

            $.ajax({
                url: baseUrl+'/laptop',
                type: 'POST',
                cache: false,
                timeout: 10000,
                success: onAddLaptopSuccess,
                error: onAddLaptopError
            });

        }


        this.addSwitch = function(switchDiv, id, portNumber, moveSwitchEventListener){
            let reqBody = {
                portNumber: portNumber
            }
            let onAddSwitchSuccess = (function(data){
                this.switchList.push(new Switch(switchDiv, id, data.switchMac, portNumber, moveSwitchEventListener));
            }).bind(this);

            let onAddSwitchError = (function(jqXHR, textStatus, errorThrown){
                console.log(jqXHR);
                console.log(textStatus);
                console.log(errorThrown);
                switchDiv.remove(); //a decommenter
                // this.switchList.push(new Switch(switchDiv, id, id, portNumber, moveSwitchEventListener)); //a commenter
                alert("Une erreur s'est produite lors de la création du commutateur : "+errorThrown.message); //a decommenter
            }).bind(this);

            $.ajax({
                url: baseUrl+'/switch',
                data: JSON.stringify(reqBody),
                type: 'POST',
                cache: false,
                timeout: 10000,
                success: onAddSwitchSuccess,
                error: onAddSwitchError
            });
        }


        this.getLaptop = function(laptopMac){
            for (let index = 0; index < this.laptopList.length; index++) {
                const laptop = this.laptopList[index];
                if(laptop.getMac() == laptopMac){
                    return laptop;
                }
            }
        }


        this.getSwitch = function(switchMac){
            for (let index = 0; index < this.switchList.length; index++) {
                const _switch = this.switchList[index];
                if(_switch.getMac() == switchMac){
                    return _switch;
                }
            }
        }

        this.getLaptopMacByHtmlId = function(htmlId){
            for (let index = 0; index < this.laptopList.length; index++) {
                const laptop = this.laptopList[index];
                if(laptop.getHtmlId() == htmlId){
                    return laptop.getMac();
                }
            }
        }

        this.getSwitchMacByHtmlId = function(htmlId){
            for (let index = 0; index < this.switchList.length; index++) {
                const _switch = this.switchList[index];
                if(_switch.getHtmlId() == htmlId){
                    return _switch.getMac();
                }
            }
        }


        this.getLaptopHtmlElementByMac = function(laptopMac){
            for (let index = 0; index < this.laptopList.length; index++) {
                const laptop = this.laptopList[index];
                if(laptop.getMac() == laptopMac){
                    return laptop.getHtmlElement();
                }
            }
        }

        this.getSwitchHtmlElementByMac = function(switchMac){
            for (let index = 0; index < this.switchList.length; index++) {
                const _switch = this.switchList[index];
                if(_switch.getMac() == switchMac){
                    return _switch.getHtmlElement();
                }
            }
        }

        this.getLaptopByMac = function(laptopMac){
            for (let index = 0; index < this.laptopList.length; index++) {
                const laptop = this.laptopList[index];
                if(laptop.getMac() == laptopMac){
                    return laptop;
                }
            }
        }

        this.getSwitchByMac = function(switchMac){
            for (let index = 0; index < this.switchList.length; index++) {
                const _switch = this.switchList[index];
                if(_switch.getMac() == switchMac){
                    return _switch;
                }
            }
        }

        this.createCrossLink = function(switchMac, laptopMac, switchPortNumber, lastX, lastY, currentX, currentY){
            let reqBody = {
                switchMac: switchMac,
                laptopMac: laptopMac,
                portNumber: switchPortNumber
            }
            let onCreateCrossLinkSuccess = (function(data){
                line(lastX, lastY, currentX, currentY, "networkContainer");
                let laptopHtmlElement = this.getLaptopHtmlElementByMac(laptopMac);
                let laptopObj = this.getLaptop(laptopMac);
                laptopHtmlElement.removeEventListener("mousedown", laptopObj.moveLaptopEventListener);
                laptopHtmlElement.removeEventListener('contextmenu', laptopObj.addConnector);

                let switchHtmlElement = this.getSwitchHtmlElementByMac(switchMac);
                let switchObj = this.getSwitch(switchMac);
                switchHtmlElement.removeEventListener("mousedown", switchObj.moveSwitchEventListener);

            }).bind(this);

            let onCreateCrossLinkError = (function(jqXHR, textStatus, errorThrown){
                console.log(jqXHR);
                console.log(textStatus);
                console.log(errorThrown);
                alert("Une erreur s'est produite lors de la création de la connexion : "+errorThrown.message); // a decommenter
            }).bind(this);

            $.ajax({
                url: baseUrl+'/connection/switch',
                data: JSON.stringify(reqBody),
                type: 'POST',
                cache: false,
                timeout: 10000,
                success: onCreateCrossLinkSuccess,
                error: onCreateCrossLinkError
            });
        }




        this.createDirectLink = function(firstMacAddress, secondMacAddress, firstSwitchPortNumber, secondSwitchPortNumber, lastX, lastY, currentX, currentY){
            let reqBody = {
                switchMac1: firstMacAddress,
                switchMac2: secondMacAddress,
                switchPort1: firstSwitchPortNumber,
                switchPort2: secondSwitchPortNumber
            }
            let onCreateDirectLinkSuccess = (function(data){
                line(lastX, lastY, currentX, currentY, "networkContainer");
                let switchHtmlElement = this.getSwitchHtmlElementByMac(firstMacAddress);
                let switchObj = this.getSwitch(firstMacAddress);
                switchHtmlElement.removeEventListener("mousedown", switchObj.moveSwitchEventListener);

                switchHtmlElement = this.getSwitchHtmlElementByMac(secondMacAddress);
                switchObj = this.getSwitch(secondMacAddress);
                switchHtmlElement.removeEventListener("mousedown", switchObj.moveSwitchEventListener);

            }).bind(this);

            let onCreateDirectLinkError = (function(jqXHR, textStatus, errorThrown){
                console.log(jqXHR);
                console.log(textStatus);
                console.log(errorThrown);
                alert("Une erreur s'est produite lors de la création de la connexion : "+errorThrown.message); // a decommenter
            }).bind(this);

            $.ajax({
                url: baseUrl+'/connection/cross',
                data: JSON.stringify(reqBody),
                type: 'POST',
                cache: false,
                timeout: 10000,
                success: onCreateDirectLinkSuccess,
                error: onCreateDirectLinkError
            });
        }


    }
    
}