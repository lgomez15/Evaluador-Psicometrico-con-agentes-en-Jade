package agents;

import behaviours.ManagerBehaviour;
import jade.core.Agent;

public class Manager extends Agent {
	
	public void setup() {
		
		Object[] args = getArguments();

		System.out.println("Manager-agent "+getAID().getName()+" is ready.");
		addBehaviour(new ManagerBehaviour(this, (String) args[0], (String) args[1]));

	}
	
	public void takeDown() {
		System.out.println("Manager-agent "+getAID().getName()+" terminating.");
	}
}
