package clueGame;

public enum CardType {
	
	PERSON("PERSON"), WEAPON("WEAPON"), ROOM("ROOM");
	

	private String value;
	
	CardType(String avalue){
		value = avalue;
	
}
	
	public String toString(){return value;}
	
	

	public static CardType fromString(String text) {
	    if (text != null) {
	      for (CardType b : CardType.values()) {
	        if (text.equalsIgnoreCase(b.value)) {
	          return b;
	        }
	      }
	    }
	    return null;
	  }

}


