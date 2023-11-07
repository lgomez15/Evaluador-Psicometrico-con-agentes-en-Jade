package agents;

import jade.core.Agent;
import behaviours.PsycometricAgentBehaviour;

public class PsycometricAgent extends Agent{
	
	public void setup(){

		Object[] args = getArguments();

		System.out.println("Psycometric Agent " + getAID().getName() + " is ready.");
		addBehaviour(new PsycometricAgentBehaviour(this, (String) args[0]));
	}

	public void takeDown() {
		System.out.println("Psycometric Agent " + getAID().getName() + " terminating.");
	}
}
