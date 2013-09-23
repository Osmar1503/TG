package model;

public enum Action {
	  TURN_ON_LAMP, TURN_OFF_LAMP;

	  public String toString() {
	    switch(this) {
	      case TURN_ON_LAMP:  return "Turn on lamp";
	      case TURN_OFF_LAMP: return "Turn off lamp";
	      default: return "Unspecified";
	    }
	  }
}
