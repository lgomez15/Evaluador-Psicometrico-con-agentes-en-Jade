package behaviours;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;


public class PsycometricServiceBehaviour extends CyclicBehaviour {

	Map<String, String> requests;
	int managers[]={0,1,2,3,4,5};
	
	public PsycometricServiceBehaviour(Agent a)
	{
		super(a);
		this.requests = new HashMap<String, String>();
	}

	public void action()
	{
		ACLMessage msg = myAgent.receive();
		if (msg != null)
		{
			if(msg.getPerformative() == ACLMessage.REQUEST)
			{
				System.out.println("REQUEST for "+ msg.getContent() +"received from " + msg.getSender().getName());

				UUID uuid = UUID.randomUUID();
				String uuidString = uuid.toString();
				this.requests.put(uuidString, msg.getSender().getLocalName());

				String exeFormatManager = "cmd /c start cmd.exe @cmd /k \"java jade.Boot -container ";
				for(int i=0; i<this.managers.length; i++)
				{
					if(this.managers[i] != -1)
					{
						exeFormatManager += "manager" + this.managers[i]+ ":agents.Manager(" + uuidString + "," +msg.getContent() + ")";
						this.managers[i] = -1;
						break;
					}
				}
				try
				{
					Runtime.getRuntime().exec(exeFormatManager);
				}
				catch(Exception e)
				{
					System.out.println("Error while executing manager");
				}
			} else
			{
				System.out.println("RESPONSE for "+ msg.getContent() +"received from " + msg.getSender().getName());
				String uuid = (String) msg.getInReplyTo();
				ACLMessage reply = new ACLMessage(ACLMessage.INFORM);
				reply.addReceiver(new AID(this.requests.get(uuid), jade.core.AID.ISLOCALNAME));
				reply.setContent(msg.getContent());
				myAgent.send(reply);
				this.requests.remove(uuid);

				//set manager to available

			}
		}
		else
		{
			block();
		}

	}


}
