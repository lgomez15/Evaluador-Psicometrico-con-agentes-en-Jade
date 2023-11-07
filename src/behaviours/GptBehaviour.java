package behaviours;

import jade.core.behaviours.OneShotBehaviour;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.core.Agent;
import utils.Utils;

public class GptBehaviour extends OneShotBehaviour{
	
	public String adress;

	public GptBehaviour(Agent a, String adress){
		super(a);
		this.adress = adress;
	}

	public void action(){

		String request = "Closest hospital to this ZIP CODE in spain: " + adress;
		String response = Utils.chatGPT(request);
		ACLMessage msg = new ACLMessage(ACLMessage.INFORM);

		String reciever = "Manager" + myAgent.getLocalName().substring(8, 9);
		msg.addReceiver(new AID(reciever, jade.core.AID.ISLOCALNAME));
		msg.setContent(response);
		myAgent.send(msg);
		System.out.println("Agent " + myAgent.getLocalName() + " sent: " + msg.getContent() + " to " + reciever);
	}

	public int onEnd() {
        myAgent.doDelete();
        return super.onEnd();
    }
}
