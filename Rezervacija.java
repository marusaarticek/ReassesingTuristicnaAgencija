import java.io.*;
import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Rezervacija {
	
	private String ime;
	private String priimek;
	private int stOdraslih;
	private int stOtrok;
	
	public Rezervacija() {
		ime = "";
		priimek = "";
		stOdraslih = 0;
		stOtrok = 0;	
	}
	
	public Rezervacija(String ime, String priimek, int stOdraslih, int stOtrok) {
		this.ime = ime;
		this.priimek = priimek;
		this.stOdraslih = stOdraslih;
		this.stOtrok = stOtrok;	
	}
	public String getIme() {
		return this.ime;
	}
	public String getPriimek() {
		return this.priimek;
	}
	
	public int getStOtrok() {
		return this.stOdraslih;
	}
	
	public int getStOdraslih() {
		return this.stOdraslih;
	}
	
	public String shraniKotNiz() {
		String zapis = "*R\r\n";			
		zapis += this.ime + "\r\n";
		zapis += this.priimek + "\r\n";
		zapis += this.stOdraslih + "\r\n";
		zapis += this.stOtrok+ "\r\n";
		zapis += "#\r\n";
		return zapis;
	}
	
	
    public String toString(boolean admin) {
		String podatki = "";
		
		podatki += "***   Podatki o rezervaciji  ***\r\n";
		podatki += "Ime: " + this.ime + "\r\n";
		podatki += "Priimek: " + this.priimek + "\r\n";
		int st = this.stOdraslih + this.stOtrok;
		if(admin) {
			podatki += "Stevilo oseb:  " + st + "\r\n";
		}
		return podatki;
	}
	
}