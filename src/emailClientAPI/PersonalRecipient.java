package emailClientAPI;

public class PersonalRecipient implements Recipient,BirthdayWishReciever{
 
	private String name;
	private String nickName;
	private String birthday;
	private String email;
	private String recipientType;
	private String recipientInfo;
	
	public PersonalRecipient(String recipientInfo) {
		recipientType= recipientInfo.split(":")[0].trim();
		name=(recipientInfo.split(":")[1].trim()).split(",")[0].trim();
		nickName=(recipientInfo.split(":")[1].trim()).split(",")[1].trim();
		email=(recipientInfo.split(":")[1].trim()).split(",")[2].trim();
		birthday=(recipientInfo.split(":")[1].trim()).split(",")[3].trim();
		
		this.recipientInfo=getRecipientType()+": "+getName()+","+getNickName()+","+getEmail()+","+getBirthday();
	}
	
	
	public String getName() {
		return this.name;
	}
	public String getEmail() {
		return this.email;
	}
	public String getNickName() {
		return this.nickName;
	}
	public String getBirthday() {
		return this.birthday;
	}
	
	public String getRecipientType() {
		return recipientType;
	}
	public String getRecipientInfo() {
		return this.recipientInfo;
	}
	
}
