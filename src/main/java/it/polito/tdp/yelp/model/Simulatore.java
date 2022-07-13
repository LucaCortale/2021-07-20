package it.polito.tdp.yelp.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.yelp.model.Event.EventType;

public class Simulatore {
	
	
	//DATI INPUT
	private int x1;
	private int x2;
	
	//DATI OUTPUT
	//I GIORNALISTI SONO RAPPRESENTI DA UN NUMERO COMPRESO TRA 0 e x1-1
	private List <Giornalista> giornalisti;
	private int numeroGiorniSimulati;
	
	//MODELLO DEL MONDO
	private Set<User> intervistati;
	private Graph<User,DefaultWeightedEdge> grafo;
	
	//CODA DEGGLI EVENTI
	PriorityQueue<Event> queue;
	
	
	
	
	
	//seleziono uno user dalla lista evitando di selzionare coloro che sono gia in this.intervistati
	private User selezionaIntervistato(Collection <User> lista) {
		
		Set <User> candidati = new HashSet <User> (lista);
		candidati.removeAll(this.intervistati);
		int scelto = (int)(Math.random()*candidati.size());
		
		return (new ArrayList<User>(candidati)).get(scelto);
		
		
	}
	
	private User selezionaAdiacente(User u) {
		List<User> vicini = Graphs.neighborListOf(this.grafo, u);
		vicini.removeAll(this.intervistati);
		if(vicini.size()==0) return null;//vertice isolato o tutti adiacenti gia intervistati
		
		double max =0;
		for(User v : vicini) {
			double peso = this.grafo.getEdgeWeight(this.grafo.getEdge(u, v));
			if(peso > max) {
				max=peso;
			}
		}
		
		
		List<User> migliori = new ArrayList<>();
		for(User v : vicini) {
			double peso = this.grafo.getEdgeWeight(this.grafo.getEdge(u, v));
			if(peso == max) {
				migliori.add(v);
			}
		}
		
		int scelto = (int)Math.random()*migliori.size();
		return migliori.get(scelto);
		
	}
	
	//METODI TIPICI SIMULAZIONE
	
	public Simulatore(Graph<User,DefaultWeightedEdge> grafo) {
		
		this.grafo = grafo;
		
		
	}
	
	public void init(int x1,int x2) {
		
		this.x1=x1;
		this.x2= x2;
		
		//inizializzo i valori di uscita e il mondo
		this.intervistati= new HashSet<User>();
		
		this.numeroGiorniSimulati=0;
		
		this.giornalisti = new ArrayList<>();
		for(int id=0;id<this.x1;id++) {
			this.giornalisti.add(new Giornalista(id));
		}
		
		//carico la coda--
		for(Giornalista g : this.giornalisti) {
			User intervistato = this.selezionaIntervistato(this.grafo.vertexSet());
			this.intervistati.add(intervistato);
			g.incrementaIntervistati();
			this.queue.add(new Event(1,EventType.DA_INTERVISTARE,intervistato,g));
		}
		
	}
	
	public void run() {
		
		while(!this.queue.isEmpty() && this.intervistati.size()< this.x2) {
			
			Event e = this.queue.poll();
			this.numeroGiorniSimulati = e.getGiorno();
			processEvent(e);
			
		}
		
	}
	
	

	private void processEvent(Event e) {
		
		switch(e.getType()) {
		
		case DA_INTERVISTARE:
			
			double caso = Math.random();
			
			if(caso< 0.6) {
				//1 caso
				User vicino = this.selezionaAdiacente(e.getIntervistato());
				if(vicino == null) {
					vicino = selezionaIntervistato(this.grafo.vertexSet());
				}
				
				this.queue.add(new Event(e.getGiorno()+1,EventType.DA_INTERVISTARE,vicino,e.getGiornlista()));
				this.intervistati.add(vicino);
				e.getGiornlista().incrementaIntervistati();
				
			}else if(caso<0.8) {
				//2 caso
				this.queue.add(new Event(e.getGiorno()+1,EventType.FERIE,e.getIntervistato(),e.getGiornlista()));
				
			}else {
				//3 caso:domani continuo con lo stesso
				this.queue.add(new Event(e.getGiorno()+1,EventType.DA_INTERVISTARE,e.getIntervistato(),e.getGiornlista()));
				
				
			}
			
			break;
		
		case FERIE:
			
			break;
			
		}
		
	}

	public int getX1() {
		return x1;
	}

	public void setX1(int x1) {
		this.x1 = x1;
	}

	public int getX2() {
		return x2;
	}

	public void setX2(int x2) {
		this.x2 = x2;
	}

	public List<Giornalista> getGiornalisti() {
		return giornalisti;
	}

	public int getNumeroGiorniSimulati() {
		return numeroGiorniSimulati;
	}
	
	
}
