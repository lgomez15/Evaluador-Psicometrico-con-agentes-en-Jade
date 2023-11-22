package behaviours;

import jade.core.behaviours.OneShotBehaviour;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.core.Agent;

public class PsycometricAgentBehaviour extends OneShotBehaviour {
	public String psycometrics;

	public PsycometricAgentBehaviour(Agent a, String psycometrics) {
		super(a);
		this.psycometrics = psycometrics;
	}

	public void action()
	{
		String response = evaluateSituation(Integer.parseInt(psycometrics));
		ACLMessage msg = new ACLMessage(ACLMessage.INFORM);

		System.out.println("Agent " + myAgent.getLocalName() + " evaluated the situation and sent: " + response);
		String reciever = "Manager" + myAgent.getLocalName().substring(16, 17);
		msg.addReceiver(new AID(reciever, jade.core.AID.ISLOCALNAME));
		msg.setContent(response);
		myAgent.send(msg);
		System.out.println("Agent " + myAgent.getLocalName() + " sent: " + msg.getContent() + " to " + reciever);
	}

	public String evaluateSituation(int psycometrics)
	{
		String response = "";
		if(psycometrics > 5)
		{
			response = "You are not in a good state of mind, you should seek help.";
		}
		else if(psycometrics <= 5 && psycometrics < 7)
		{
			response = "You are in a good state of mind, but you should still seek help.";
		}
		else if(psycometrics <= 7)
		{
			response = "You are in a good state of mind, you don't need help.";
		}
		return response;
	}

	public int onEnd() {
		myAgent.doDelete();
		return super.onEnd();
	}
}
