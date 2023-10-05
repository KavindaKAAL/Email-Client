package emailClientAPI;

public class Official extends OfficialRecipient {
	private String name;
	private String email;
	private String designation;
	private String recipientType;
	private String recipientInfo;
	
	public Official(String recipientInfo) {
		recipientType= recipientInfo.split(":")[0].trim();
		name=(recipientInfo.split(":")[1].trim()).split(",")[0].trim();
		email=(recipientInfo.split(":")[1].trim()).split(",")[1].trim();
		designation=(recipientInfo.split(":")[1].trim()).split(",")[2].trim();
		this.recipientInfo=getRecipientType()+": "+getName()+","+getEmail()+","+getDesignation();
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
	public String getRecipientType() {
		return this.recipientType;
	}
	public String getRecipientInfo() {
		return this.recipientInfo;
	}

}
