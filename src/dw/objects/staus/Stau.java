package dw.objects.staus;

public class Stau {

	//Initialize the variables
	
	public String Datum, Uhrzeit,Autobahn,Richtung_Start,Richtung_Ende,Streckenabschnitt_Start,
	Streckenabschnitt_Ende,Art,	Laenge, Beschreibung;

	
	
	public Stau(String Datum,String Uhrzeit,String Autobahn,String Richtung_Start,String Richtung_Ende,
			String Streckenabschnitt_Start,String Streckenabschnitt_Ende,String Art,
			String Laenge) {
		// TODO Auto-generated constructor stub
		
		this.Datum=Datum;
		this.Uhrzeit=Uhrzeit;
		this.Autobahn=Autobahn;
		this.Richtung_Start = Richtung_Start;
		this.Richtung_Ende = Richtung_Ende;
		this.Streckenabschnitt_Start=Streckenabschnitt_Start;
		this.Streckenabschnitt_Ende=Streckenabschnitt_Ende;
		this.Art=Art;
		this.Laenge=Laenge;
	//	this.Beschreibung=Beschreibung;
		
		
	}
	
	public String getEigenschaft(String s){
		return s;
	}

	@Override
	public boolean equals(Object o){
		
		
		if ( o == null ){
		    return false;
		}
		
		if ( o == this ){
		    return true;
		}
		
		Stau that = (Stau) o;
		
		if ( !(o instanceof Stau) ){
			  return false;
		}
		
		if(this.Datum.equals(that.Datum) &&
		this.Uhrzeit.equals(that.Uhrzeit) &&
		this.Autobahn.equals(that.Autobahn) &&
		this.Richtung_Start.equals(that.Richtung_Start) &&
		this.Richtung_Ende.equals(that.Richtung_Ende) &&
		this.Streckenabschnitt_Start.equals(that.Streckenabschnitt_Start) &&
		this.Streckenabschnitt_Ende.equals(that.Streckenabschnitt_Ende )
		&&	this.Art.equals(that.Art) 
		&& this.Laenge.equals(that.Laenge)
		){
			///System.out.println("MATCH!!!!!!!!!!!!!!!!!!!!!");
			return true;
		}

	return false;
		
	}
	

}
