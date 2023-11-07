package agents;

import jade.core.Agent;
import behaviours.GptBehaviour;


public class GptAgent extends Agent {
	
	public void setup() {
		
		Object[] args = getArguments();


		System.out.println("Gpt-agent "+getAID().getName()+" is ready.");
		addBehaviour(new GptBehaviour(this, (String) args[0]));

	}
	
	public void takeDown() {
		System.out.println("Gpt-agent "+getAID().getName()+" terminating.");
	}	
}
