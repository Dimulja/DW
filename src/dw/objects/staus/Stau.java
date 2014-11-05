package dw.objects.staus;

public class Stau {

	//Initialize the variables
	
	public String Datum, Uhrzeit,Autobahn,Richtung_Start,Richtung_Ende,Streckenabschnitt_Start,
	Streckenabschnitt_Ende,Art,	Laenge, Beschreibung;
	 private String Eigenschaft="";
	
	
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

	
	public void setEigenschaft(String Eigenschaft, String Wert){
		this.Eigenschaft=Eigenschaft;
		
	}
}
