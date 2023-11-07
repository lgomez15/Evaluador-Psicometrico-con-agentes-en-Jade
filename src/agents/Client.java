package agents;

import behaviours.ClientBehaviour;
import jade.core.Agent;

public class Client extends Agent{
	
	protected void setup() {
		
		System.out.println("Client-agent "+getAID().getName()+" is ready.");
		addBehaviour(new ClientBehaviour(this));

	}
	
	protected void takeDown() {
		System.out.println("Client-agent "+getAID().getName()+" terminating.");
	}

}
