import java.io.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;


public class Smucanje extends Pocitnice {
	
	private boolean najemHotela;
	private boolean hisniLjubljencki;
	
	public Smucanje() {
		super();
		this.najemHotela=false;
		this.hisniLjubljencki=false;
	}
	
	public Smucanje(int id, int maxSteviloOseb, 
	String drzava, int cena, ArrayList<Termin> termin,
	boolean najemHotela, boolean hisniLjubljencki) {
		super(id, maxSteviloOseb, drzava, cena, termin);
		this.najemHotela=najemHotela;
		this.hisniLjubljencki=hisniLjubljencki;
	}
	
	public void setNajem(boolean najem) {
		najemHotela = najem;
	}
	public void setPet(boolean pet) {
		hisniLjubljencki = pet;
	}
	public boolean getNajem() {
		return this.najemHotela;
	}
	public boolean getPet() {
		return this.hisniLjubljencki;
	}
	
	@Override
    public String toString(boolean admin) {
		String podatki = "";
		
		podatki += "*****   Podatki o smucanju   *****\r\n";
		podatki += "--------------------------------\r\n";
		podatki += super.toString(admin);
		podatki += "Najem hotela: " + this.najemHotela + "\r\n";
		podatki += "Hisni ljubljencki: " + this.hisniLjubljencki + "\r\n";
		podatki += "\r\n";
		/*
		for(Termin t : this.getSeznamTerminov()) {
			podatki += t.toString();
			podatki += "\r\n";
		}
		*/
		return podatki;
	}
	
	public String shraniKotNiz()
	{
		String zapis = "*S\r\n";
		zapis += this.getId()+ "\r\n";		
		zapis += this.getmaxSteviloOseb() + "\r\n";			
		zapis += this.getDrzava() + "\r\n";		
		zapis += this.getCena() + "\r\n";
		zapis += this.najemHotela + "\r\n";		
		zapis += this.hisniLjubljencki  + "\r\n";
		
		for(Termin t : this.getSeznamTerminov()) // Zapišemo še vsak status posebej
		{
			zapis += t.shraniKotNiz();
		}
		for(Rezervacija r : this.getSeznamRezervacij()) // Zapišemo še vsak status posebej
		{
			zapis += r.shraniKotNiz();
		}
		zapis += "##\r\n";					// Oznacimo konec branja
		return zapis;
	}
	
	
	public static Smucanje preberiIzNiza(ArrayList<String> zapis)
	{
		Smucanje smucaj = new Smucanje(); 
		try
		{
			smucaj.setId(Integer.parseInt(zapis.get(0)));
			smucaj.setMaxSt(Integer.parseInt(zapis.get(1)));
			smucaj.setDrzava(zapis.get(2));
			smucaj.setCena(Integer.parseInt(zapis.get(3)));
			if(zapis.get(4).equals("true")) {
				smucaj.setNajem(true);
			} else {
				smucaj.setNajem(false);
			}
			if(zapis.get(5).equals("true")) {
				smucaj.setPet(true);
			} else {
				smucaj.setPet(false);
			}

			ArrayList<String> terminPodatki;
			//ArrayList<String> rezervacijaPodatki;
			for(int i=6; i < zapis.size(); i++)
			{
				if(zapis.get(i).trim().equals("*T"))	
				{
					terminPodatki = new ArrayList<String>();	
					i++;
					while(!zapis.get(i).trim().equals("#"))	{
						terminPodatki.add(zapis.get(i));
						i++;
					}
					Termin termin = Termin.preberiIzNiza(terminPodatki);

					smucaj.dodajTermin(termin);
				}
			}
			return smucaj;
		}
		catch(Exception ex)
		{
			System.out.println("Prišlo je do napake v zapisu!");
			System.out.println();
			throw ex;
		}
	}
	
	
	
	public static Smucanje ustvariSmucanje() throws Exception {
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		
		System.out.println("***   Vnos novih pocitnic - smucanje  ***");
		System.out.println();
		System.out.println("Vnesi drzavo: ");
		String drzava = br.readLine().trim();
		System.out.println();
		
		
		int id = 0;
		while(true) {
			try {
				System.out.println("Vnesi id pocitnic: ");
				id = Integer.parseInt(br.readLine().trim());
				System.out.println();
				break;
			} 
			catch (Exception e) {
				System.out.println("Napacen format vnosa!");
				System.out.println();
			}
		}
		
		int stevilo;
		int maxSteviloOseb = 0;
		while(true) {
			try {
				System.out.println("Vnesi max stevilo oseb: ");
				maxSteviloOseb = Integer.parseInt(br.readLine().trim());
				System.out.println();
				break;
			}
			catch (Exception e) {
				System.out.println("Napacen format vnosa!");
				System.out.println();
			}
		}
		
		int cena = 0;
		while(true) {
			try {
				System.out.println("Vnesi ceno Pocitnic: ");
				cena = Integer.parseInt(br.readLine().trim());
				System.out.println();
				break;
			}
			catch (Exception e) {
				System.out.println("Napacen format vnosa!");
				System.out.println();
			}
		}
		
		int n = 0;
		while(true) {
			try {
				System.out.println("Vnesi stevilo moznih terminov: ");
				n = Integer.parseInt(br.readLine().trim());
				System.out.println();
				break;
			}
			catch (Exception e) {
				System.out.println("Napacen format vnosa!");
				System.out.println();
			}
		}
		
		
		ArrayList<Termin> seznamTerminov = new ArrayList<Termin>();
		for(int i=0; i < n; i++) {
			
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm");
			String preberiOdhod = "";
			String preberiPrihod = "";
			LocalDateTime odhod = LocalDateTime.now();
			LocalDateTime prihod = LocalDateTime.now();
			System.out.println("***   VNOS TERMINA   ***\r\n");
			int j = 0;
			while(true) {
				try {
					System.out.println("Vnesi id termina: ");
					j = Integer.parseInt(br.readLine().trim());
					System.out.println();
					break;
				}
				catch (Exception e) {
					System.out.println("Napacen format vnosa!");
					System.out.println();
				}
			}
			
			while(true) {
				try {
					System.out.println("Vnesi termin in cas odhoda (npr: 2022-05-31 10:00):  ");
					preberiOdhod = br.readLine().trim();
					odhod = LocalDateTime.parse(preberiOdhod, dtf);
					break;
					
				}
				catch (Exception e) { 
					System.out.println("Napacen format vnosa!");
					System.out.println("Poskusite ponovno:");
				}
			}
			while(true) {
				try {
					System.out.println();
					System.out.println("Vnesi termin in cas prihoda (npr: 2022-06-05 10:00):  ");
					preberiPrihod = br.readLine().trim();
					prihod = LocalDateTime.parse(preberiPrihod, dtf);
					break;
					
				}
				catch (Exception e) { 
					System.out.println("Napacen format vnosa!");
					System.out.println("Poskusite ponovno:");
					System.out.println();
				}
			}
			System.out.println("Vnos termina koncan. ");
			Termin t = new Termin(odhod, prihod, j);
			seznamTerminov.add(t);
		}
	
		boolean najemHotela = true;
		while (true) {
			System.out.println("Vnesi ali zelite najeti hotelsko sobo (DA/NE): ");
			String niz = br.readLine().trim();
			System.out.println();
			
			if(niz.equals("DA")) {
				break;
			} else if (niz.equals("NE")) {
				najemHotela = false;
				break;
			} else {
				System.out.println("Napacen format vnosa!");
				System.out.println();
			}
		}
		boolean hisniLjubljencki = true;
		while (true) {
			System.out.println("Vnesi ali boste imeli s sabo hisne ljubljencke (DA/NE): ");
			String niz = br.readLine().trim();
			System.out.println();
			
			if(niz.equals("DA")) {
				break;
			} else if (niz.equals("NE")) {
				hisniLjubljencki = false;
				break;
			} else {
				System.out.println("Napacen format vnosa!");
				System.out.println();
			}
		}
		
		Smucanje s = new Smucanje(id, maxSteviloOseb, drzava, cena, seznamTerminov, najemHotela, hisniLjubljencki);
		return s;
	}	
	
}