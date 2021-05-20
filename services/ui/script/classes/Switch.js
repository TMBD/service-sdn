class Switch{
    constructor(htmlElement, htmlId, macAddress, portNumber, moveSwitchEventListener){
        this.isOn = false;
        this.htmlElement = htmlElement;
        this.htmlId = htmlId;
        this.macAddress = macAddress;
        this.portNumber = portNumber;
        this.screenContent = null;
        //=======
        this.moveSwitchEventListener = moveSwitchEventListener;
        //========
        var baseUrl = "http://localhost:9991";



        this.addMessage = function(message) {
            let $messagesBox = $("#messagesBox_"+this.htmlId);
            $messagesBox.append('<div class="message-container switch-message-container"><div class="sysmessage"><p>'+message+'</p></div></div>');
        }

        this.setOnclickListerner = function () {
            this.htmlElement.addEventListener("click", function (event) {
                
                $.when($('#log_'+htmlId).fadeToggle(500)).then(function (){
                    let displayState = $('#log_'+htmlId).css("display");
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
            this.screenContent.setAttribute("id", 'log_'+htmlId);
            this.screenContent.classList.add('chat');
            this.screenContent.innerHTML += '<div class="messages-box clearfix" id='+"messagesBox_"+this.htmlId+'>'+
                                                '<div class="message-container"><div class="sysmessage"><p>'+this.macAddress+'</p></div></div>'+
                                            '</div>';

            document.body.appendChild(this.screenContent);
        }


        this.setUpIndicator = function(){
            $("#switchIndicator_"+htmlId).click(function(event) {
                event.preventDefault();
                event.stopPropagation();
                let reqBody = {
                    switchMac: macAddress
                }

                let onToggleSuccess = (function(data){
                    this.isOn = !this.isOn;
                    if(this.isOn){
                        $("#switchIndicator_"+htmlId).css({"background-color": "green"});
                        this.isOn = false;
                    }else{
                        $("#switchIndicator_"+htmlId).css({"background-color": "red"});
                        this.isOn = true;
                    }
                }).bind(this);
    
                let onToggleError = (function(jqXHR, textStatus, errorThrown){
                    console.log(jqXHR);
                    console.log(textStatus);
                    console.log(errorThrown);
                    alert("Une erreur s'est produite lors du changement d'état du commutateur : "+errorThrown.message);
                }).bind(this);
    
                $.ajax({
                    url: baseUrl+'/switch/toggle',
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
                    jsonData.messages.forEach(message => {
                        this.addMessage(message);
                    });
                    // $("#messagesBox_"+htmlId).scrollTop(messagesBoxHeight);
                }
            }).bind(this);

            let onGetError = (function(jqXHR, textStatus, errorThrown){
                console.log("erreur lors de la reception du message - switch");
                console.log(jqXHR);
                console.log(textStatus);
                console.log(errorThrown);
            }).bind(this);
            
            setInterval(function() {
                if(!exit){
                $.ajax({
                    url: baseUrl+'/switch',
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