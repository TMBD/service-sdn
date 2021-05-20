let currentDroppable = null;
var numberOfLaptop = 0;
var numberOfSwitch = 0;
var laptopDivIdList = [];
var switchDivIdList = [];
var lastClickedEquipmentType = null;
var currentClickedEquipmentType = null;
var lastClickedEquipmentId = null;
var firstSwitchPortNumber = null;
var secondSwitchPortNumber = null;
var firstMacAddress = null;
var secondMacAddress = null;
var lastX = null;
var lastY = null;
var currentX = null;
var currentY = null;
var network = null;


$(document).ready(function() {
    network = new Network();
});

// function recevoir(){
//     laptop = network.getLaptop("laptop0");
//     laptop.addMessage("blablababalbal fsdflkj fdsj fjslkfj j fdslkj jfklsdjf lkjf dsk", "fdsfsdfs");

//     _switch = network.getSwitch("switch0");
//     _switch.addMessage("blablababalbal fdsfsdj fsdjklf ej fd s jfkel jkle fas jfklef  jfskldjfelk fjsdklafe ");
    
// }

//------- Laptop ---------
function addLaptop() {
    let laptopDiv = document.createElement("div");

    let id = "laptop"+numberOfLaptop;
    laptopDiv.setAttribute("id", id);
    //laptopDivIdList.push(id);
    numberOfLaptop++;

    laptopDiv.classList.add('equipment');
    laptopDiv.classList.add('laptop-off');
    laptopDiv.innerHTML += '<div class="indicator" id='+"laptopIndicator_"+id+'></div>';
    networkContainer.appendChild(laptopDiv);

    let moveLaptopEventListener = function(event){
        moveLaptop(event, id);
    }

    let addConnector = function(event) {
        event.preventDefault();

        if(lastClickedEquipmentType == "laptop"){
            alert("Vous ne pouvez pas connecter deux ordinateurs !");
        }
        else if(lastClickedEquipmentType == "switch"){
            currentClickedEquipmentType = "laptop";
            currentX = event.clientX;
            currentY = event.clientY;
            secondMacAddress = network.getLaptopMacByHtmlId(id);
            createLink(event, id);


            // laptopDiv.removeEventListener("mousedown", moveLaptopEventListener);
            // this.removeEventListener('contextmenu', addConnector);
            
            // lastClickedEquipmentType = null;
            // lastClickedEquipmentId = null;
        }
        else {
            firstMacAddress = network.getLaptopMacByHtmlId(id);
            lastClickedEquipmentType = "laptop";
            laptopDiv.removeEventListener("mousedown", moveLaptopEventListener);
            this.removeEventListener('contextmenu', addConnector);
            lastClickedEquipmentId = id;
            lastX = event.clientX;
            lastY = event.clientY;
        }
    }

    laptopDiv.addEventListener("mousedown", moveLaptopEventListener);

    laptopDiv.addEventListener("contextmenu", addConnector);

    //-- add the laptop to the netword ---
    result = network.addLaptop(laptopDiv, id, moveLaptopEventListener, addConnector);
    
}

function moveLaptop(event, id){
    moveEquipment(event, id);
}

// function createLinkWithLaptop(event, id){
//     createLink(event, id);
// }

function createLink(event, id){
    
    line(lastX, lastY, currentX, currentY, "networkContainer");
}



//------- Switch ---------
function addSwitch() {
    let switchDiv = document.createElement("div");

    let id = "switch"+numberOfSwitch;
    switchDiv.setAttribute("id", id);
    switchDivIdList.push(id);
    numberOfSwitch++;
    
    switchDiv.classList.add('equipment');
    switchDiv.classList.add('switch-off');
    
    let port = document.createElement("div");
    port.classList.add('port');
    switchDiv.appendChild(port);
    port = document.createElement("div");
    port.classList.add('port');
    switchDiv.appendChild(port);
    port = document.createElement("div");
    port.classList.add('port');
    switchDiv.appendChild(port);
    port = document.createElement("div");
    port.classList.add('port');
    switchDiv.appendChild(port);

    switchDiv.innerHTML += '<div class="indicator" id='+"switchIndicator_"+id+'></div>';
    networkContainer.appendChild(switchDiv);

    let moveSwitchEventListener = function(event){
        moveSwitch(event, id);
    }

    switchDiv.addEventListener("mousedown", moveSwitchEventListener);

    switchDiv.addEventListener("contextmenu", function(event) {
        event.preventDefault();
        let portNumber = prompt("Donnez le numéro de port sur lequel vous souhaiter connecter l'appareil : ");
        if(lastClickedEquipmentType == "switch" || lastClickedEquipmentType == "laptop"){
            secondSwitchPortNumber = portNumber;
            currentClickedEquipmentType = "switch";
            currentX = event.clientX;
            currentY = event.clientY;
            secondMacAddress = network.getSwitchMacByHtmlId(id);
            createLink(event, id);


            switchDiv.removeEventListener("mousedown", moveSwitchEventListener);
            lastClickedEquipmentType = null;
            lastClickedEquipmentId = null;
        }
        else {
            firstMacAddress = network.getSwitchMacByHtmlId(id);
            firstSwitchPortNumber = portNumber;
            lastClickedEquipmentType = "switch";
            switchDiv.removeEventListener("mousedown", moveSwitchEventListener);
            lastClickedEquipmentId = id;
            lastX = event.clientX;
            lastY = event.clientY;
        }
    });

    // Add a switch to the network
    portNumber = prompt("Donnez le nombre de port : ");
    network.addSwitch(switchDiv, id, portNumber, moveSwitchEventListener);
}

// function createLinkWithSwitch(event, id){
//     createLink(event, id);
// }

function moveSwitch(event, id){
    moveEquipment(event, id);
}


function createLink(event, id){
    if(lastClickedEquipmentType != currentClickedEquipmentType){
        let switchMac = null;
        let laptopMac = null;
        if(currentClickedEquipmentType == "laptop"){
            switchMac = firstMacAddress;
            laptopMac = secondMacAddress;
        }else{
            switchMac = secondMacAddress;
            laptopMac = firstMacAddress;
        }
        network.createCrossLink(switchMac, laptopMac, firstSwitchPortNumber, lastX, lastY, currentX, currentY);
    }else{
        network.createDirectLink(firstMacAddress, secondMacAddress, firstSwitchPortNumber, secondSwitchPortNumber, lastX, lastY, currentX, currentY);
    }
    firstMacAddress = null;
    secondMacAddress = null;
    firstSwitchPortNumber = null;
    secondSwitchPortNumber = null;
    lastClickedEquipmentType = null;
    currentClickedEquipmentType = null;
    lastClickedEquipmentId = null; //a supprinmer eventuellement

}