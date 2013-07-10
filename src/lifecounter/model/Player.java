package lifecounter.model;

import java.io.Serializable;

public class Player implements Serializable{

	private static final long serialVersionUID = -6892980559438663182L;
	
	private String name;
	
	public Player(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;		
	}
	
	public void setName(String name) {
		if(name == null || name.length() < 3)
			return;
		
		this.name = name;
	}
}
