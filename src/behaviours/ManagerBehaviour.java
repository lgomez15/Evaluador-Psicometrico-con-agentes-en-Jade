package behaviours;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.AID;
import jade.lang.acl.ACLMessage;


public class ManagerBehaviour extends Behaviour {
	
	private String uuid;
	private String request;
	public int step = 0;
	public String managerID,r1,r2;


	public ManagerBehaviour(Agent a ,String uuid, String request) {
			super(a);
			this.uuid = uuid;
			this.request = request;

	}

	public void action() {
		
		switch (step)
		{
			//launch the psycometrics agent and gptAgent
			case 0:
				//asing the manager a number
				managerID = this.myAgent.getLocalName().substring(7, 8);

				//separate the adress from the request
				String[] parts = request.split("/");
				String psycometrics = parts[0];
				String adress = parts[1];

				//launch the psycometrics agent and gptAgent 
				String exePsycometricsAgent = "cmd /c start cmd.exe @cmd /k \"java jade.Boot -container PsycometricAgent" + managerID + ":agents.PsycometricAgent(" + psycometrics + ")\"";
				String exeGptAgent = "cmd /c start cmd.exe @cmd /k \"java jade.Boot -container GptAgent" + managerID + ":agents.GptAgent(" + adress + ")\"";
				try{
					Runtime.getRuntime().exec(exePsycometricsAgent);
					Runtime.getRuntime().exec(exeGptAgent);
					step = 1;
				}catch(Exception e){
					System.out.println("ManagerBehaviour: Error while launching psycometrics agent or gpt agent");
				}				
				break;
				
				
			//wait for the response from the psycometrics agent and gptAgent then send the response to the client
			case 1:
				ACLMessage msg = myAgent.receive();
				if (msg!= null)
				{
					//if the message is from the psycometrics agent
					if(msg.getSender().getLocalName().contains("PsycometricAgent" + managerID))
					{
						System.out.println("Agent: " + this.myAgent.getLocalName() + "received: " + msg.getContent() + " \nfrom: " + msg.getSender().getLocalName());
						r1 = msg.getContent();
					} else if(msg.getSender().getLocalName().contains("GptAgent" + managerID))
					{
						System.out.println("Agent: " + this.myAgent.getLocalName() + "received: " + msg.getContent() + " \nfrom: " + msg.getSender().getLocalName());
						r2 = msg.getContent();
					}

					//if both responses are received
					if(r1 != null && r2 != null)
					{
						ACLMessage reply = new ACLMessage(ACLMessage.INFORM);
						reply.addReceiver(new AID("PsycometricService", jade.core.AID.ISLOCALNAME));
						reply.setInReplyTo(this.uuid);
						reply.setContent(r1 + "/n" + r2);
						myAgent.send(reply);
						done();
					}
				}else {
					System.out.println("Agent:" + this.myAgent.getLocalName() + " is waiting for response");
					block();
				}
				break;
		}
		
	}

	public boolean done() {
		return false;
	}
	
}
