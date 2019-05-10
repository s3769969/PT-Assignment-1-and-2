package userInterface;

public class InvalidId extends Exception{

	String date;
	
	public InvalidId(String date) {
		this.date = date;
	}

	public String toString() {
		return "Invalid Id: Id needs to be in the form *AAA123* or *aaa123*\n";
	}
}
