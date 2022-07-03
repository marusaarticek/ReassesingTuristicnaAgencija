import java.io.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;

public class Krizarjenje extends Pocitnice {
	
	private String tip;
	private String krizarke;
	
	public Krizarjenje () {
		super();
		this.tip="";
		this.krizarke="";
	}
	
	public Krizarjenje (int id, int maxSteviloOseb, 
	String drzava, int cena, ArrayList<Termin> termin,
	String tip, String krizarke) {
		super(id, maxSteviloOseb, drzava, cena, termin);
		this.tip=tip;
		this.krizarke=krizarke;
	}
	
	public Krizarjenje (int id, int maxSteviloOseb, 
	String drzava, int cena, ArrayList<Termin> termin,ArrayList<Rezervacija> rezervacija,
	String tip, String krizarke) {
		super(id, maxSteviloOseb, drzava, cena, termin, rezervacija);
		this.tip=tip;
		this.krizarke=krizarke;
	}
	
	public void setTip(String tip) {
		this.tip = tip;
	}
	public void setKrizarke(String krizarke) {
		this.krizarke=krizarke;
	}
	public String getTip() {
		return this.tip;
	}
	public String getKrizarke() {
		return this.krizarke;
	}
	
	@Override
    public String toString(boolean admin) {
		String podatki = "";
		
		podatki += "*****   Podatki o krizarjenju   *****\r\n";
		podatki += "--------------------------------\r\n";
		podatki += super.toString(admin);
		podatki += "Tip potovanja: " + this.tip + "\r\n";
		podatki += "Krizarka: " + this.krizarke + "\r\n";
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
		String zapis = "*K\r\n";
		zapis += this.getId()+ "\r\n";		
		zapis += this.getmaxSteviloOseb() + "\r\n";			
		zapis += this.getDrzava() + "\r\n";		
		zapis += this.getCena() + "\r\n";
		zapis += this.tip + "\r\n";		
		zapis += this.krizarke + "\r\n";
		
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
	
	public static Krizarjenje preberiIzNiza(ArrayList<String> zapis)
	{
		Krizarjenje k = new Krizarjenje(); 
		try
		{
			k.setId(Integer.parseInt(zapis.get(0)));
			k.setMaxSt(Integer.parseInt(zapis.get(1)));
			k.setDrzava(zapis.get(2));
			k.setCena(Integer.parseInt(zapis.get(3)));
			k.setTip(zapis.get(4));
			k.setKrizarke(zapis.get(5));
			
			ArrayList<String> terminPodatki;
			//ArrayList<String> rezervacijaPodatki;
			for(int i=6; i < zapis.size(); i++)
			{
				if(zapis.get(i).trim().equals("*T"))	// Ce vrstica vsebuje *S, imamo zapis o statusu
				{
					terminPodatki = new ArrayList<String>();	// Pripravimo nov seznam, v katerega bomo dodajali podatke o trenutnem statusu
					i++;
					while(!zapis.get(i).trim().equals("#"))	// Dokler se zapis o statusu ne konca (dokler se ne pojavi #), dodajamo podatke v seznam
					{
						terminPodatki.add(zapis.get(i));
						i++;
					}
					Termin termin = Termin.preberiIzNiza(terminPodatki);

					k.dodajTermin(termin);
				}
			}
			return k;
		}
		catch(Exception ex)
		{
			System.out.println("Prišlo je do napake v zapisu!");
			System.out.println();
			throw ex;
		}
	}
	
	public static Krizarjenje ustvariKrizarjenje() throws Exception {
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		
		System.out.println("***   Vnos novih pocitnic - krizarjenje  ***");
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
	
		System.out.println("Vnesi tip krizarjenja(mini, eksoticno, cezoceansko,..): ");
		String tip = br.readLine().trim();
		System.out.println();
		System.out.println("Vnesi krizarko(Costa, Royal, Holland,..) : ");
		String krizarke = br.readLine().trim();
		System.out.println();
		
		Krizarjenje k = new Krizarjenje(id, maxSteviloOseb, drzava, cena, seznamTerminov, tip, krizarke);
		return k;
	}
}