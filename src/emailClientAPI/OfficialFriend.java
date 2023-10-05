package emailClientAPI;

public class OfficialFriend extends OfficialRecipient implements BirthdayWishReciever {
	private String name;
	private String email;
	private String designation;
	private String birthday;
	private String recipientType;
	private String recipientInfo;
	
	public OfficialFriend(String recipientInfo) {
		recipientType= recipientInfo.split(":")[0].trim();
		name=(recipientInfo.split(":")[1].trim()).split(",")[0].trim();
		email=(recipientInfo.split(":")[1].trim()).split(",")[1].trim();
		designation=(recipientInfo.split(":")[1].trim()).split(",")[2].trim();
		birthday=(recipientInfo.split(":")[1].trim()).split(",")[3].trim();
		
		this.recipientInfo=getRecipientType()+": "+getName()+","+getEmail()+","+getDesignation()+","+getBirthday();
	}
	
	public String getName() {
		return this.name;
	}
	public String getEmail() {
		return this.email;
	}
	public String getDesignation() {
		return this.designation;
	}
	public String getBirthday() {
		return this.birthday;
	}
	public String getRecipientType() {
		return this.recipientType;
	}
	public String getRecipientInfo() {
		return this.recipientInfo;
	}
	
}
