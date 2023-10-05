package emailClientAPI;

public abstract class OfficialRecipient implements Recipient {
	
	@Override
	public abstract String getName();
	
	@Override
	public abstract String getEmail();
	
	@Override
	public abstract String getRecipientType();
	
	@Override
	public abstract String getRecipientInfo();

}
