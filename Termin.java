import java.io.*;
import java.util.*;
import java.time.*;
import java.time.format.*;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;

public class Termin {
	
	private int idTermina;
	private LocalDateTime odhod;
	private LocalDateTime prihod;
	
	
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm");

	
	public Termin() {
		idTermina = 000;
		odhod = LocalDateTime.parse("2023-02-01 10:00", dtf);
		prihod = LocalDateTime.parse("2023-02-01 10:00", dtf);
		
	}
	
	public Termin(LocalDateTime odhod, LocalDateTime prihod, int idTermina) {
		this.idTermina = idTermina;
		this.odhod = odhod;
		this.prihod = prihod;
	}
	public int getId() {
		return this.idTermina;
	}
	public LocalDate getOdhod() {
		return this.odhod.toLocalDate();
	}
	public LocalDate getPrihod() {
		return this.prihod.toLocalDate();
	}
	public void setId(int idTermina) {
		this.idTermina = idTermina;
	}
	public void setOdhod(String odhod) {
		DateTimeFormatter form = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
		this.odhod = LocalDateTime.parse(odhod, form);
	}
	public void setPrihod(String prihod) {
		DateTimeFormatter form = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
		this.prihod = LocalDateTime.parse(prihod, form);
	}
	
	
    public String toString(boolean admin) {
		String podatki = "";
		
		podatki += "***   Podatki o terminu   ***\r\n";
		if(admin) {
			podatki += "Id: " + this.idTermina + "\r\n";
		}
		podatki += "Datum odhoda: " + this.odhod + "\r\n";
		podatki += "Datum prihoda: " + this.prihod + "\r\n";
		
		return podatki;
	}
	
	public String shraniKotNiz() {
		String zapis = "*T\r\n";
		zapis += this.idTermina + "\r\n";
		zapis += this.odhod + "\r\n";
		zapis += this.prihod + "\r\n";
		zapis += "#\r\n";
		return zapis;
	}
	
	public static Termin preberiIzNiza(ArrayList<String> zapis)
	{
		Termin termin = new Termin(); 
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm");
		String p = "";
		String r = "";
		try
		{	
			termin.setId(Integer.parseInt(zapis.get(0)));
			p += zapis.get(1);
			r += zapis.get(2);
			termin.setOdhod(p);
			termin.setPrihod(r);
			// termin.setOdhod(LocalDateTime.parse(zapis.get(1), dtf));
			// termin.setPrihod(LocalDateTime.parse(zapis.get(2), dtf));
			return termin;
		}
		catch(Exception ex)
		{
			System.out.println("Prišlo je do napake v zapisu!");
			System.out.println();
			throw ex;
		}
	}
}