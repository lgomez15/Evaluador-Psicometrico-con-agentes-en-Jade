package behaviours;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import utils.Utils;
import jade.core.AID;

public class ClientBehaviour extends CyclicBehaviour{

	public int step = 0;
	public int psycometrics;
	public String adress;

	
	public ClientBehaviour(Agent a) {
		super(a);
	}

	public void action() {
		switch(this.step){
			case 0:
				sendMsg();
			case 1:
				receiveMsg();
		}
	}

	public void receiveMsg()
	{
		ACLMessage msg = myAgent.receive();
		if(msg != null)
		{
			System.out.println(msg.getContent());
			this.step = 0;
		}
		else
		{
			block();
		}
	}

	public void sendMsg()
	{

		survey();
		String request = psycometrics + "/" + adress;
		ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
		AID[] receivers = Utils.searchAgents(myAgent,"PsycometricService");

		if(receivers != null && receivers.length > 0)
		{
			for(int i=0; i<receivers.length; i++)
			{
				msg.addReceiver(receivers[i]);
			}
			msg.setContent(request);
			myAgent.send(msg);
			this.step = 1;
		}
		else
		{
			System.out.println("No PsycometricService found.");
			this.step = 0;
		}
		
		
	}

	public int controlAnswers()
	{
		int control = 0;
		while(control == 0)
		{
			System.out.println("Please answer with a yes or no answer. (y/n)");
			String answer = System.console().readLine();
			if(answer.equals("y") || answer.equals("n"))
			{
				control = 1;
				if(answer.equals("y"))
				{
					return 1;
				}
				else
				{
					return 0;
				}
			}
		}
		return 0;
	}

	public int survey ()
	{
		System.out.println("\nWelcome to the Pyscometric Service!\nThe aim of this psychometric evaluation program is to provide a comprehensive insight into the emotional and psychological state of affected individuals, enabling mental health professionals and aid teams to focus their efforts more effectively and in a personalized manner.");
		System.out.println("Please answer the following questions with the most accurate answer possible.\n");
		//adress from the patient info:
		System.out.println("Please enter your adress:");
		adress = System.console().readLine();
		//10 questions for the psychometric evaluation after an earthquake.
		System.out.println("\n1. Do you feel nervous, anxious or on edge?");
		psycometrics += controlAnswers();
		System.out.println("2. Do you feel that you are unable to stop or control worrying?");
		psycometrics += controlAnswers();
		System.out.println("3. Do you worry too much about different things?");
		psycometrics += controlAnswers();
		System.out.println("4. Do you have trouble relaxing?");
		psycometrics += controlAnswers();
		System.out.println("5. Do you have trouble concentrating on things, such as reading the newspaper or watching television?");
		psycometrics += controlAnswers();
		System.out.println("6. Do you become easily annoyed or irritable?");
		psycometrics += controlAnswers();
		System.out.println("7. Do you feel afraid as if something awful might happen?");
		psycometrics += controlAnswers();
		System.out.println("8. Do you feel that you are going crazy?");
		psycometrics += controlAnswers();
		System.out.println("9. Do you feel that you might pass out?");
		psycometrics += controlAnswers();
		System.out.println("10. Do you feel that you are trapped or caught?");
		psycometrics += controlAnswers();
		System.out.println("\nThank you for your time.\n");
		return psycometrics;
	}

}
