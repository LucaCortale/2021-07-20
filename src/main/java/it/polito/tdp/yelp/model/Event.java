package it.polito.tdp.yelp.model;

public class Event implements Comparable<Event>{
	
	public enum EventType{
		DA_INTERVISTARE,
		FERIE;
	}

	private int giorno;
	private EventType type;
	private User intervistato;
	private Giornalista giornlista;
	
	
	


	public Event(int giorno, EventType type, User intervistato, Giornalista giornlista) {
		super();
		this.giorno = giorno;
		this.type = type;
		this.intervistato = intervistato;
		this.giornlista = giornlista;
	}

	

	public EventType getType() {
		return type;
	}



	public int getGiorno() {
		return giorno;
	}


	public User getIntervistato() {
		return intervistato;
	}


	public Giornalista getGiornlista() {
		return giornlista;
	}


	@Override
	public int compareTo(Event o) {
		
		return this.giorno - o.giorno;
	}
	
	
	
}
