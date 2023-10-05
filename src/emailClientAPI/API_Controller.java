package emailClientAPI;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class API_Controller {
	private static ArrayList<Recipient> clients=new ArrayList<Recipient>();
	private static HashMap<LocalDate, ArrayList<Email>> sentEmails= new HashMap<>();
	
	/*
	 * This method is for adding a new recipient to the clientList text file in the following formats;
	 * Official: <name>,<email>,<designation>,
	 * Official_friend: <name>,<email>,<designation>,<birthday>
	 * Personal: <name>,<nickname>,<email>,<birthday>
	 * Accepts one parameter which is type of Recipient
	 * @param recipient : Recipient object
	 * This method does not return anything
	 */

	public static void addRecipientToFile(Recipient recipient) {
		try {
			//write details to the file

			 File file = new File("clientList.txt");
	         if(!file.exists()) {
	        	 file.createNewFile();
	         }

	         FileWriter fileWritter = new FileWriter(file.getName(),true);
	         BufferedWriter bwDetails = new BufferedWriter(fileWritter);
	         bwDetails.write(recipient.getRecipientInfo()+"\n");
	         bwDetails.close();
			
			System.out.println("Successfully added a new recipient.\n");
			
		}
		//run if there is an error while adding a recipient
		catch (IOException e) {
			System.out.println(e);
			System.out.println("Error, new recipient can't be saved!\n");
		}
		
	}
	/*
	 * This is the method for loading the recipient details from the text file into the application
	 * Does not accept any parameter
	 * Recipient objects are read from the file, and add into the clients list
	 * Does not return any attributes
	 * @throws IOException: exception which use to throw a failure in Input & Output operations
	 */
	
	public static void loadRecipientDetailsFromFile() throws IOException {
		 	 
			 FileReader detailsFile = new FileReader("clientList.txt"); //read the file
			 BufferedReader bufferedReader = new BufferedReader(detailsFile); //create a buffering character input stream
			 String currentRecipient=null;
			 
			 while ((currentRecipient = bufferedReader.readLine()) != null) {
				 if (currentRecipient.equals("")) { //check empty lines
					 continue;
				 }
				 String RecipientType = currentRecipient.split(":")[0].trim(); //get the type of the recipient
				 
				 switch (RecipientType) {
				 	case "Official": //Type of the recipient is "Official"
				 		clients.add(new Official(currentRecipient));
				 		break;
				 	case "Office_friend": //Type of the recipient is "Official_friend"
				 		clients.add(new OfficialFriend(currentRecipient));
				 		break;
				 	case "Personal": //Type of the recipient is "Personal"
				 		clients.add(new PersonalRecipient(currentRecipient));
				 		break;
				 	default:
				 		break;
				 }
			 }
			 bufferedReader.close(); //close the file
	}
		 
	
	
	/*
	 * This method is for printing out all the names of recipients who have their birthday set to given date
	 * Accepts one String parameter
	 * @param givenDate : The date, input by the user
	 * Does not return anything
	 */
	
	public static void getBirthdayRecipients(String selectedDate){
		 try{
			 int birthdayRecipients_count = 0; // counting birthday recipients on selectedDate
			 
			 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd"); //format the given date into the format of "yyyy/MM/dd"
			 LocalDate formattedDate = LocalDate.parse(selectedDate, formatter);
			 
			 //check whether the clients list is empty
			 if (clients != null){ 
				 //loop through the clients list
				 for(Recipient recipient: clients){ 
					 
					//check whether the recipient object implement the BirthdayWishReciever interface
					 if (recipient instanceof BirthdayWishReciever){ 
						 
						 String birthdayStr = ((BirthdayWishReciever) recipient).getBirthday();
						 LocalDate birthday = LocalDate.parse(birthdayStr, formatter);
						 
						 if ((birthday.getMonth() == formattedDate.getMonth()) && (birthday.getDayOfMonth() == formattedDate.getDayOfMonth())){
							 
							//print the name of the recipient who has his/her birthday on given date
							 System.out.println(recipient.getName()+" - "+recipient.getEmail()); 
							 birthdayRecipients_count++;
						 }
					 }
				 }
				 if (birthdayRecipients_count==0){
					 System.out.println("No person has birthday on " + formattedDate.toString());
				 }
			 }
		 }
		 catch (Exception ex) {
			 System.out.println(ex);
		 }
	}
	
	/*
	 * This method is for sending an email and for recording the details of the sent e-mails 
	 * Accepts one parameter, type of Email
	 * @param email : Email object
	 * Does not return anything
	 */
	
	public static void sendEmail(Email email) throws Exception {
		 email.sendMail(email.getEmailAddress()); //sending the email (sendMmail method is in Email class)
		 
		 //check whether there is any email which was sent on the same date before
		 if (sentEmails.containsKey(email.getDate())){
			 (sentEmails.get(email.getDate())).add(email); //add the email object to the same ArrayList, under the key, that was previously created
		 }
		 else{
			 ArrayList<Email> newEmailsArrayOnDate = new ArrayList<Email>(); //create a new ArrayList object
			 newEmailsArrayOnDate.add(email); //add the email object to the created ArrayList
		 	sentEmails.put(email.getDate(), newEmailsArrayOnDate); //create a new key and add the ArrayList under the key
		 }
		 
		//serializing sentEmails HashMap
		 storeEmails(); 
	}
	
	/*
	* This method is for printing out details of all the e-mails sent on a date specified by user input
	* Accepts on parameter in String type
	* @param givenDate : The date which was entered by the user
	* Does not return anything
	*/
	
	public static void EmailsDetailsOnDate(String selectedDate) {
		
		//format the given date into the format of "yyyy/MM/dd"
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd"); 
		LocalDate formatedDate = LocalDate.parse(selectedDate, formatter);
		
		try {
			//check whether the sentEmails is empty
			if(sentEmails != null && sentEmails.containsKey(formatedDate)){ 
				if(!sentEmails.get(formatedDate).isEmpty()){ 
					//Loop through the ArrayList under the date
					 for(Email emails: sentEmails.get(formatedDate)) { 
							System.out.println("Recipient: " + emails.getEmailAddress() + ",  Subject: " + emails.getSubject());
							}
					}
				//e-mails were not sent on given date
				else{ 
					System.out.println("Emails were not sent on " + formatedDate.toString());
				}
			}
			else{
				System.out.println("Emails were not sent on " + formatedDate.toString());
			}
			System.out.println();
		}
		catch (Exception ex){
			System.out.println(ex);
		}
	}
	
	/*
	 * This method is for serializing the HashMap which contains the details of the sent e-mails
	 * Does not accept any parameter
	 * Does not return any anything
	 */
	 private static void storeEmails() {
		 try {
			 //Object Serialization
			 FileOutputStream fileStream = new FileOutputStream("emails.ser");
			 ObjectOutputStream os = new ObjectOutputStream(fileStream);
			 os.writeObject(sentEmails);
			 os.close();
			 System.out.println("Sent email is successfully saved!\n");
		 }
		 catch (Exception e) {
			 System.out.println(e);
			 System.out.println("Error, Sent email is not saved!\n");
		 }
	 }
	 
	 /*
	 * This method is for deserializing the emails.ser file
	 * Does not accept any parameter
	 * Does not return any anything
	 */
	 
	@SuppressWarnings("unchecked")
	public static void restoreEmails() throws Exception {
		 try {
			 //Deserialization
			 
			 FileInputStream fileStream=new FileInputStream("emails.ser");
			 ObjectInputStream hashmapObject=new ObjectInputStream(fileStream);
			 sentEmails = (HashMap<LocalDate, ArrayList<Email>>) hashmapObject.readObject();
			 hashmapObject.close();
			 fileStream.close();
		 }
		 catch (EOFException e){
			 System.out.println(e);
			 System.out.println("No emails sent yet. You can send emails!");
			 return;
		 }
	}
	
	 /*
	 * This method is for sending birthday wishes e-mails automatically to the recipients who have birthday today
	 * Does not accept any parameter
	 * Does not return any anything
	 */
	 public static void sendingBirthdayWishEmailsAuto(){
		 try{
			 LocalDate today = LocalDate.now(); //get the today's date
			 
			 if((int)getRecipientsList().size()!=0) {
				 
				//check whether the birthday e-mails have already sent
				 ArrayList<String> emailAddressesOfSentBirthdayWishToday=new ArrayList<String>();
				 if (sentEmails.containsKey(today)){ 
					 for(Email email:sentEmails.get(today)) {
						 if(email.getSubject().equalsIgnoreCase("Birthday wish")) {
							 emailAddressesOfSentBirthdayWishToday.add(email.getEmailAddress());
						 }
					 }
					 
				  } 
				 int sentEmailsCount = 0;
				 //Loop through the clients
				 for (Recipient recipient : clients) {
					 if(!(emailAddressesOfSentBirthdayWishToday.contains(recipient.getEmail()))) {
						 sentEmailsCount+=sendingBirthdayWishEmailForSinglePerson(recipient);
					 }
				 }
				 if(emailAddressesOfSentBirthdayWishToday.size()==0) {
					//check whether there is any recipient who has his/her birthday today
					 if (sentEmailsCount == 0) { 
						 System.out.println("No one has birthdays today ("+today.toString()+") !");
					 }
				 }
				else {
					System.out.println("Birthday wishing emails have already been sent for today ("+today.toString()+") successfully!");
				}
			}
			else {
				 System.out.println("No recipient has been saved in the application. You can add new recipients!");
			 }
			 
		 }
		 catch (Exception e) {
			 System.out.println(e);
		 }
	 }
	 
	 /*
		 * This method is for sending birthday wish e-mail to a one person who has birthday today
		 * Accept one parameter as Recipient object
		 * @param recipient
		 * Return 0 or 1 
	*/
	 public static int sendingBirthdayWishEmailForSinglePerson(Recipient recipient){
		 try{
			 LocalDate today = LocalDate.now();
			 
			//check whether the recipient object implement the BirthdayWishReciever interface
			 if ((recipient instanceof BirthdayWishReciever)) { 
				 String birthdayStr = ((BirthdayWishReciever) recipient).getBirthday(); //get the birthday of the recipient
				 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd"); //format the given date into the format of "yyyy/MM/dd"
				 LocalDate birthday = LocalDate.parse(birthdayStr, formatter);
				 
				 //check whether the birthday of the recipient is today
				 if ((birthday.getMonth() == today.getMonth()) && (birthday.getDayOfMonth() == today.getDayOfMonth())) {
					 //check whether the type of the recipient object is Person
					 if (recipient instanceof PersonalRecipient) {
						 String emailAddress = recipient.getEmail(); //get the email address of the recipient
						 String subject = "Birthday wish";
						 String content = "Hugs and love on your birthday. Lahiru Kavinda";
						 Email mail = new Email(emailAddress, subject, content); //create an Email object
						 sendEmail(mail); //send the birthday wish
						 return 1;
					 }
					 //check whether the type of the recipient object is Official_friend
					 else if (recipient instanceof OfficialFriend) {
						 String emailAddress = recipient.getEmail(); //get the email address of the recipient
						 String subject = "Birthday wish";
						 String content = "Wish you a Happy Birthday. Lahiru Kavinda";
						 Email mail = new Email(emailAddress, subject, content); //create an email object
						 sendEmail(mail); //send the birthday wish
						 return 1;
					 }
				 }
			}
		}	
		catch (Exception e){
			 System.out.println(e);
		}
		return 0;
	}
	 
	 /*
	  * This method is to check newly adding recipient's e-mail address is already used by another recipients in the app. If so does not add the new recipient.
	  * Accept one String parameter as emailAddress
	  * @param emailAddress
	  * Return true or false
	  */
	 public static boolean checkAddingEmailAddressAlreadyHasAUser(String emailAddress) {
		 for (Recipient recipient : clients) {
			 if(recipient.getEmail().equalsIgnoreCase(emailAddress)) {
				 return true;
			 }
		 }
		 return false;
		 
	 }
	 
	 /*
	  * This method is to get the clients list
	  * return clients ArrayList
	  */
	  public static ArrayList<Recipient> getRecipientsList() {
		  return clients;
	  }
	  
}
