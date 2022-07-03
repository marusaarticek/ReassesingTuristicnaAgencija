import java.io.*;
import java.util.*;


public class Uporabnik {
	
	private String ime;
	private String priimek;
	private String geslo;
	private boolean administrator;
	
	public Uporabnik() {
		ime = "";
		priimek = "";
		geslo = "";
		administrator = false;
	}
	
	public Uporabnik(String ime, String priimek, String geslo, boolean administrator) {
	
		this.ime = ime;
		this.priimek=priimek;
		this.geslo = geslo;
		this.administrator = administrator;	
	}	
	
	public void setIme(String ime) {
		this.ime = ime;
	}
	public void setPriimek(String priimek) {
		this.priimek = priimek;
	}
	public void setGeslo(String geslo) {
		this.geslo = geslo;
	}
	public void setAdmin(boolean administrator) {
		this.administrator = administrator;
	}	
	
	public String getIme() {
		return this.ime;
	}
	public String getPriimek() {
		return this.priimek;
	}
	public String getGeslo() {
		return this.geslo;
	}
	boolean getAdmin() {
		return this.administrator;
	}
	
	public static Uporabnik preberiIzNiza(ArrayList<String> zapis)
	{
		Uporabnik uporabnik = new Uporabnik();  // Najprej ustvarimo objekt, kateremu bomo nastavili podane lastnosti
		try
		{
			uporabnik.setIme(zapis.get(0));
			uporabnik.setPriimek(zapis.get(1));
			uporabnik.setGeslo(zapis.get(2));
			boolean str = Boolean.parseBoolean(zapis.get(3));
			uporabnik.setAdmin(str);
			
			return uporabnik;
		}
		catch(Exception ex)
		{
			System.out.println("Prišlo je do napake v zapisu!");
			System.out.println();
			throw ex;
		}
	}

	
	public String shraniKotNiz()
	{
		String zapis = "*U\r\n";			// Zapišemo kodo "G", ki oznacuje gradivo
		zapis += this.ime + "\r\n";		// Zapišemo avtorja
		zapis += this.priimek + "\r\n";
		zapis += this.geslo + "\r\n";	
		zapis += this.administrator + "\r\n";// Zapišemo naslov
		zapis += "##\r\n";					// Oznacimo konec branja
		return zapis;
	}
}