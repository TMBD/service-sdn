class Laptop{
    constructor(htmlElement, htmlId, macAddress, moveLaptopEventListener, addConnector){
        this.isOn = false;
        this.htmlElement = htmlElement;
        this.htmlId = htmlId;
        this.macAddress = macAddress;
        this.screenContent = null;
        //-------
        this.moveLaptopEventListener = moveLaptopEventListener;
        this.addConnector = addConnector;
        //-------
        var baseUrl = "http://localhost:9991";

        this.addMessage = function(message, senderMac) {
            let $messagesBox = $("#messagesBox_"+this.htmlId);
            $messagesBox.append('<div class="message-container"><div class="pmessage"> <p>'+senderMac+'</p></div></div>');
            $messagesBox.append('<div class="message-container"><div class="inmessage"><i class="fa fa-close"></i> <p>'+message+'</p></div></div>');
        }

        this.setOnclickListerner = function () {
            this.htmlElement.addEventListener("click", function (event) {
                
                $.when($('#chat_'+htmlId).fadeToggle(500)).then(function (){
                    let displayState = $('#chat_'+htmlId).css("display");
                    if(displayState == "none"){
                        if(this.isOn) $('#'+htmlId).css({"background-color": "#9cf88e"});
                        else $('#'+htmlId).css({"background-color": "#F3EFEF"});
                    }else{
                        $('#'+htmlId).css({"background-color": "#080075"});
                    }

                });
                
            });
        }

        this.setUpScreenLogic = function(){
            this.screenContent = document.createElement("div");
            this.screenContent.setAttribute("id", 'chat_'+htmlId);
            this.screenContent.classList.add('chat');
            this.screenContent.innerHTML +='<div class="messages-box clearfix" id='+"messagesBox_"+this.htmlId+'>'+
                                        '<div class="message-container"><div class="sysmessage"><p>'+this.macAddress+'</p></div></div>'+
                                        '</div>'+
                                        '<div class="create-massage">'+
                                            '<form id='+"form_"+this.htmlId+'>'+
                                                '<input type="text" class="macTextField" id='+"macAddressValue_"+this.htmlId+' placeholder="Adresse mac...">'+
                                                '<input type="text" id='+"messageValue_"+this.htmlId+' placeholder="Votre message...">'+
                                                '<button type="submit"><i class="fa fa-send"></i></button>'+
                                            '</form>'+
                                        '</div>';

            document.body.appendChild(this.screenContent);

            $("#form_"+this.htmlId).submit(function( event ) {
                event.preventDefault();
                let macAddressValue = $("#macAddressValue_"+htmlId).val();
                let messageValue = $("#messageValue_"+htmlId).val();
                if(messageValue.trim() != ""){
                    /////// http call ///////////
                    let reqBody = {
                        packet : messageValue,
                        senderLaptopMac : macAddress,
                        laptopMac : macAddressValue
                    }
                    let onSendSuccess = (function(data){
                        $("#macAddressValue_"+htmlId).val("");
                        $("#messageValue_"+htmlId).val("");
                        let $messagesBox = $("#messagesBox_"+htmlId);
                        $messagesBox.append('<div class="message-container"><div class="message"><i class="fa fa-close"></i> <p>' + messageValue +'</p></div></div>');
                    }).bind(this);
        
                    let onSendError = (function(jqXHR, textStatus, errorThrown){
                        console.log(jqXHR);
                        console.log(textStatus);
                        console.log(errorThrown);
                        alert("Une erreur s'est produite lors de l'envoi du message : "+errorThrown.message); // a decommenter
                    }).bind(this);
        
                    $.ajax({
                        url: baseUrl+'/send',
                        data: JSON.stringify(reqBody),
                        type: 'POST',
                        cache: false,
                        timeout: 10000,
                        success: onSendSuccess,
                        error: onSendError
                    });
                    /////// end http call ///
                    
                }else alert("Peut pas envoyé un message vide !");
            });
        }

        this.setUpIndicator = function(){
            $("#laptopIndicator_"+htmlId).click(function(event) {
                event.preventDefault();
                event.stopPropagation();
                let reqBody = {
                    laptopMac: macAddress
                }

                let onToggleSuccess = (function(data){
                    this.isOn = !this.isOn;
                    if(this.isOn){
                        $("#laptopIndicator_"+htmlId).css({"background-color": "green"});
                        this.isOn = false;
                    }else{
                        $("#laptopIndicator_"+htmlId).css({"background-color": "red"});
                        this.isOn = true;
                    }
                }).bind(this);
    
                let onToggleError = (function(jqXHR, textStatus, errorThrown){
                    console.log(jqXHR);
                    console.log(textStatus);
                    console.log(errorThrown);
                    alert("Une erreur s'est produite lors du changement d'état de la machine : "+errorThrown.message); 
                }).bind(this);
    
                $.ajax({
                    url: baseUrl+'/laptop/toggle',
                    data: JSON.stringify(reqBody),
                    type: 'PUT',
                    cache: false,
                    timeout: 10000,
                    success: onToggleSuccess,
                    error: onToggleError
                });
    
            });
        }


        this.getMac = function(){
            return this.macAddress;
        }

        this.getHtmlId = function(){
            return this.htmlId;
        }


        this.getHtmlElement = function(){
            return this.htmlElement;
        }


        //////// SCRIPT DE RECUPERATION REGULIERE DES MESSAGES ////////////
        this.setUpGetPaquet = function(){
            let onGetSuccess = (function(data){
                let jsonData = jQuery.parseJSON(data);
                if(jsonData != null){
                    // messagesBoxHeight = $("#messagesBox_"+htmlId).scrollHeight,
                    jsonData.messages.forEach(element => {
                        let senderMac = element.sender;
                        let message = element.content;
                        this.addMessage(message, senderMac);
                    });
                    // $("#messagesBox_"+htmlId).scrollTop(messagesBoxHeight);
                }
            }).bind(this);

            let onGetError = (function(jqXHR, textStatus, errorThrown){
                console.log("erreur lors de la reception du message");
                console.log(jqXHR);
                console.log(textStatus);
                console.log(errorThrown);
            }).bind(this);
            
            setInterval(function() {
                if(!exit){
                $.ajax({
                    url: baseUrl+'/laptop',
                    dataType: "text",
                    type: 'GET',
                    cache: false,
                    timeout: 15000,
                    success: onGetSuccess,
                    error: onGetError
                });
                }
            }, 20000);
        }


        //Appel automatique des methodes 
        this.setUpScreenLogic();
        this.setOnclickListerner();
        this.setUpIndicator();
        this.setUpGetPaquet();



    }

}

