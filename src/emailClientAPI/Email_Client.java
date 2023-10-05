package emailClientAPI;

import java.util.InputMismatchException;
import java.util.Scanner;
public class Email_Client {

	public static void main(String[] args) throws Exception {
		
		System.out.println("application ON!");
		
		API_Controller.restoreEmails();
		API_Controller.loadRecipientDetailsFromFile();
		API_Controller.sendingBirthdayWishEmailsAuto();
		Scanner scanner = new Scanner(System.in);
		
		boolean applicationOn =true;
		
		while(applicationOn) {
			try {
		        System.out.println("Enter option type: \n"
		              + "1 - Adding a new recipient\n"
		              + "2 - Sending an email\n"
		              + "3 - Printing out all the recipients who have birthdays\n"
		              + "4 - Printing out details of all the emails sent\n"
		              + "5 - Printing out the number of recipient objects in the application\n"
		              + "6 - Exit the application\n");
		
		        int option = scanner.nextInt();
		        scanner.nextLine();
		        
		        switch(option){
		              case 1:
		                  // input format - Official: nimal,nimal@gmail.com,ceo
		                  // input format - Office_friend: kamal,kamal@gmail.com,clerk,2000/12/12
		            	  // input format - Personal: sunil,<nick-name>,sunil@gmail.com,2000/10/10
		            	  while(true) {
		            		  System.out.println("Enter new recipient here or, enter 0 go back to the main menu.");
		            		  String emailInfo = scanner.nextLine();
		            		  
		            		  if(emailInfo.equals("0")) {
		            			  break;
		                	  }
		            		  
		            		  String recipientType = emailInfo.split(":")[0].trim();  
		            		  Recipient newRecipient;
		            		  
		        			  switch(recipientType) {
		        			  	case "Office_friend":
		        			  		String emailAddress1 = emailInfo.split(":")[1].trim().split(",")[1].trim();  
		        			  		if(!API_Controller.checkAddingEmailAddressAlreadyHasAUser(emailAddress1)) {
		        			  			newRecipient = new OfficialFriend(emailInfo);
			        			  		API_Controller.addRecipientToFile(newRecipient);
			        			  		API_Controller.getRecipientsList().add(newRecipient);
			        			  		
			        			  		//send birthday wish email if the recipient's birthday is today
			        			  		API_Controller.sendingBirthdayWishEmailForSinglePerson(newRecipient);
		        			  		}
		        			  		else {
		        			  			System.out.println("Can not add the new recipient. Email address is already used by another recipient in the app.");
		        			  		}
		        			  		
		        			  		break;
		        			  	case "Personal":
		        			  		String emailAddress2 = emailInfo.split(":")[1].trim().split(",")[2].trim(); 
		        			  		if(!API_Controller.checkAddingEmailAddressAlreadyHasAUser(emailAddress2)) {
		        			  			newRecipient = new PersonalRecipient(emailInfo);
			        			  		API_Controller.addRecipientToFile(newRecipient);
			        			  		API_Controller.getRecipientsList().add(newRecipient);
			        			  		
			        			  		//send birthday wish email if the recipient's birthday is today
			        			  		API_Controller.sendingBirthdayWishEmailForSinglePerson(newRecipient);
		        			  		}
		        			  		else {
		        			  			System.out.println("Can not add the new recipient. Email address is already used by another recipient in the app.");
		        			  		}
		        			  		
		        			  		break;
		        			  	case "Official":
		        			  		String emailAddress3 = emailInfo.split(":")[1].trim().split(",")[1].trim();  
		        			  		if(!API_Controller.checkAddingEmailAddressAlreadyHasAUser(emailAddress3)) {
		        			  			newRecipient = new Official(emailInfo);
			        			  		API_Controller.addRecipientToFile(newRecipient);
			        			  		API_Controller.getRecipientsList().add(newRecipient);
		        			  		}
		        			  		else {
		        			  			System.out.println("Can not add the new recipient. Email address is already used by another recipient in the app.");
		        			  		}
		        			  		
		        			  		break;
		        			  	default:
		        			  		System.out.println("Invalid input recipient type!");
		        			  		break;
		        			  		
		        			  }
		        			  
		            	  }
		            	  
		            	   break;
		            	   
		              case 2:
		                  // input format - email, subject, content
		                  // code to send an email
		            	  String emailDetails = scanner.nextLine();
		            	  String mailAddress = emailDetails.split(",")[0].trim();
		            	  String subject = emailDetails.split(",")[1].trim();
		            	  String content =  emailDetails.split(",")[2].trim();
		            	  Email email = new Email(mailAddress,subject,content);
		            	  API_Controller.sendEmail(email);
		            	  break;
		              case 3:
		                  // input format - yyyy/MM/dd (ex: 2018/09/17)
		                  // get recipients who have birthdays on the given date
		            	  String date1 = scanner.nextLine();
		            	  System.out.println("Birthday recipients on "+date1+" :");
		            	  API_Controller.getBirthdayRecipients(date1);
		                  break;
		              case 4:
		                  // input format - yyyy/MM/dd (ex: 2022/08/12)
		                  // get the details of all the emails sent on the input date
		            	  String date2 = scanner.nextLine();
		            	  System.out.println("Emails details, sent on "+date2+" :");
		            	  API_Controller.EmailsDetailsOnDate(date2);
		                  break;
		              case 5:
		                  // get the number of recipient objects in the application
		            	  int numberOfRecipients = API_Controller.getRecipientsList().size();
		            	  System.out.println("Number of saved recipients: "+numberOfRecipients);
		            	  for(Recipient recipient:API_Controller.getRecipientsList()) {
		            		  System.out.println(recipient.getName()+" - "+recipient.getEmail());
		            	  }
		            	  System.out.println("");
		            	  break;
		              case 6:
		            	  // OFF the application
		            	  applicationOn=false;
		            	  System.out.println("application OFF!");
		            	  break;
		            	  
		              default :
		            	  System.out.println("Invalid Input. Try again!");
		            	  break;
		        }
		        
		            	  
			}
			catch(InputMismatchException ex) {
				System.out.println(ex);
				System.out.println("Invalid Input. Try again!");
				scanner.nextLine();
				}
		    catch(Exception ex) {
		    	System.out.println(ex);
		    	System.out.println("Invalid Input. Try again!");
		    }
			
		}
		scanner.close();
		        
	}	
}	    
 
