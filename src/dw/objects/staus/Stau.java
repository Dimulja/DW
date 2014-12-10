package dw.objects.staus;

public class Stau {

	//Initialize the variables
	
	public String Datum, Uhrzeit,Autobahn,Richtung_Start,Richtung_Ende,Streckenabschnitt_Start,
	Streckenabschnitt_Ende,Art,	Laenge, Beschreibung;

	
	/**
	 * @param String Datum,String Uhrzeit,String Autobahn,String Richtung_Start,String Richtung_Ende,
			String Streckenabschnitt_Start,String Streckenabschnitt_Ende,String Art,
			String Laenge
			
			Creates a new Object Stau
	 */
	public Stau(String Datum,String Uhrzeit,String Autobahn,String Richtung_Start,String Richtung_Ende,
			String Streckenabschnitt_Start,String Streckenabschnitt_Ende,String Art,
			String Laenge, String Beschreibung) {
		
		this.Datum=Datum;
		this.Uhrzeit=Uhrzeit;
		this.Autobahn=Autobahn;
		this.Richtung_Start = Richtung_Start;
		this.Richtung_Ende = Richtung_Ende;
		this.Streckenabschnitt_Start=Streckenabschnitt_Start;
		this.Streckenabschnitt_Ende=Streckenabschnitt_Ende;
		this.Art=Art;
		this.Laenge=Laenge;
		this.Beschreibung=Beschreibung;
		
		
	}
	
	public int getOrtHash (){
		String s=Autobahn +Richtung_Start +Richtung_Ende+ Streckenabschnitt_Start +
		Streckenabschnitt_Ende;	
		return (s.hashCode() & 0x7FFFFFFF) % Integer.MAX_VALUE;
	}
	
	
	public int getZeitHash (){
		String s=Datum+Uhrzeit;	
		return (s.hashCode() & 0x7FFFFFFF) % Integer.MAX_VALUE;
	}
	/**
	 * @param o Stau
	 * Checks if two objects equals
	 * @return true if Stau1.equals(Stau2)
	 */
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
		&& this.Beschreibung.equals(that.Beschreibung)
		){
			///System.out.println("MATCH!!!!!!!!!!!!!!!!!!!!!");
			return true;
		}

	return false;
		
	}
	

}
