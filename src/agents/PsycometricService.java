package agents;

import behaviours.PsycometricServiceBehaviour;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class PsycometricService  extends Agent{

	public void setup()
	{
		System.out.println("Psycometric Service " + getAID().getName() + " is ready.");

		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription(); 
		sd.setType("PsycometricService"); 
		sd.setName("PsycometricService"); 
		dfd.addServices(sd); 

		try {
			DFService.register(this, dfd); 
		}
		catch (Exception e) {
			System.out.println("Error registering the Psycometric Service.");
		}

		addBehaviour(new PsycometricServiceBehaviour(this));

	}
	
	

}
