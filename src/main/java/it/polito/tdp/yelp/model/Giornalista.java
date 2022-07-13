package it.polito.tdp.yelp.model;

public class Giornalista {

	
	private int ID;
	private int nIntervistati;
	
	public Giornalista(int iD) {
		super();
		ID = iD;
		this.nIntervistati = 0;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public int getnIntervistati() {
		return nIntervistati;
	}

	public void incrementaIntervistati() {
		this.nIntervistati++;
	}
	
	
	
	
}
