import java.io.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;


public class TuristicnaAgencija {
	
	private ArrayList<Pocitnice> seznamPocitnic;
	
	public TuristicnaAgencija() {
		this.seznamPocitnic = new ArrayList<Pocitnice>();
		}
	
	public TuristicnaAgencija(ArrayList<Pocitnice> seznamPocitnic) {
		this.seznamPocitnic = seznamPocitnic;
		}
	
	//---------------------------------------
	
	
	public ArrayList<Pocitnice> getSeznamPocitnic() {
		return this.seznamPocitnic;
	}
	

	// metoda set
	public void setSeznamPocitnic(ArrayList<Pocitnice> seznamPocitnic) {
		this.seznamPocitnic = seznamPocitnic;
	}
	
	public void dodajPocitnice(Pocitnice p) {
		this.seznamPocitnic.add(p);
		
	}
	
	
	@Override
	public String toString() {
		String podatki = "";
		
		podatki += "\r\n";
		podatki += "-----------------------------------\r\n";
		podatki += "**********   TURISTICNA AGENCIJA   **********\r\n";
		podatki += "-----------------------------------\r\n";
		podatki += "\r\n";
		
		podatki += "\r\n";
		podatki += "**********   POCITNICE   **********\r\n";
		podatki += "\r\n";
		
		for(Pocitnice p : this.seznamPocitnic) {
			podatki += p.toString();
			podatki += "\r\n";
		}
		
		return podatki;
	}
	
	// ....................DATOTEKE
	
	public void shraniVDatotekoP(String imeDatoteke) throws IOException
	{
		
		FileWriter fw = new FileWriter(imeDatoteke, true);
		PrintWriter dat = new PrintWriter(fw);

		for(Pocitnice p : this.seznamPocitnic)
		{
			dat.print(p.shraniKotNiz());
		}
		dat.println("###");

		dat.close();
	} 
	
	
	public void shraniVDatoteko(String imeDatoteke) throws IOException
	{
		
		FileWriter fw = new FileWriter(imeDatoteke, false); 
		PrintWriter dat = new PrintWriter(fw);

		
		for(Pocitnice p : this.seznamPocitnic)
		{
			dat.print(p.shraniKotNiz());
		}
		dat.println("###");

		dat.close();
	} 
	
	
	public void dodajIzDatotekeP(String imeDatoteke) throws Exception
	{
		FileReader fr = new FileReader(imeDatoteke);
		BufferedReader dat = new BufferedReader(fr);

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm");

		ArrayList<String> pocitnicePodatki;
		
		while(dat.ready())
		{
			String vrstica = dat.readLine().trim();
			if(vrstica.equals("*S"))
			{
				pocitnicePodatki = new ArrayList<String>();
				while(dat.ready() && !vrstica.equals("##"))
				{
					vrstica = dat.readLine().trim();
					pocitnicePodatki.add(vrstica);
				}

				Smucanje s = Smucanje.preberiIzNiza(pocitnicePodatki);
				this.seznamPocitnic.add(s);
			}
			if(vrstica.equals("*K"))
			{
				pocitnicePodatki = new ArrayList<String>();
				while(dat.ready() && !vrstica.equals("##"))
				{
					vrstica = dat.readLine().trim();
					pocitnicePodatki.add(vrstica);
				}

				Krizarjenje k = Krizarjenje.preberiIzNiza(pocitnicePodatki);
				this.seznamPocitnic.add(k);
			}
			if(vrstica.equals("*C"))
			{
				pocitnicePodatki = new ArrayList<String>();
				while(dat.ready() && !vrstica.equals("##"))
				{
					vrstica = dat.readLine().trim();
					pocitnicePodatki.add(vrstica);
				}

				Kampiranje kamp = Kampiranje.preberiIzNiza(pocitnicePodatki);
				this.seznamPocitnic.add(kamp);
			}
			if(vrstica.equals("*M"))
			{
				pocitnicePodatki = new ArrayList<String>();
				while(dat.ready() && !vrstica.equals("##"))
				{
					vrstica = dat.readLine().trim();
					pocitnicePodatki.add(vrstica);
				}

				Potovanje p = Potovanje.preberiIzNiza(pocitnicePodatki);
				this.seznamPocitnic.add(p);
			}
			if(vrstica.equals("*I"))
			{
				pocitnicePodatki = new ArrayList<String>();
				while(dat.ready() && !vrstica.equals("##"))
				{
					vrstica = dat.readLine().trim();
					pocitnicePodatki.add(vrstica);
				}

				IzletVGore izlet = IzletVGore.preberiIzNiza(pocitnicePodatki);
				this.seznamPocitnic.add(izlet);
			}
		}
		dat.close();
	}
	
	//-------------------------------------------------------------------
	public void izbrisId() throws Exception {
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		
		int id = 0;
		while(true) {
			try {
				System.out.println("Vnesi id pocitnic, ki jih zelis izbrisati: ");
				System.out.println();
				System.out.println("id: ");
				id = Integer.parseInt(br.readLine().trim());
				System.out.println();
				break;
			}
			catch (Exception e) {
				System.out.println("Napacen format vnosa!");
				System.out.println();
			}
		}
		
		int i = 0;
		Iterator<Pocitnice> itr = this.seznamPocitnic.iterator();            
		while(itr.hasNext()){
			Pocitnice p = itr.next();
			if(p.getId() == id){
				itr.remove();
				System.out.println("Izbrisali ste pocitnice.");
				i += 1;
			}
		}
		
		
		if(i == 0) {
			System.out.println("Pocitnice s to id ne obstajajo");
		}
	}
	
	
	public void Spremeni(int izbira) throws Exception {
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		
		int id = 0;
		int idtermin = 0;
		String ojoj = "";
		String ojoj2 = "";
		while(true) {
			try {
				System.out.println("Vnesi id pocitnic, ki jih zelis spremeniti: ");
				System.out.println();
				System.out.println("id: ");
				id = Integer.parseInt(br.readLine().trim());
				System.out.println();
				break;
			}
			catch (Exception e) {
				System.out.println("Napacen format vnosa!");
				System.out.println();
			}
		}
		
		boolean check = false;
		
		for(Pocitnice p : this.seznamPocitnic) {
			if(p.getId() == id) {
				check = true;
				System.out.println(p.toString(true));
			}
		}
		
		if(!check) {
			System.out.println("Ni pocitnic s to id!\r\n");
			return;
		}
		
		if(izbira ==  2) {
			int cena = 0;
			while(true) {
				try {
					System.out.println("Vnesi nov cenovni okvir: ");
					System.out.println();
					System.out.println("Cena: ");
					cena = Integer.parseInt(br.readLine().trim());
					System.out.println();
					break;
				}
				catch (Exception e) {
					System.out.println("Napacen format vnosa!");
					System.out.println();
				}
			}
			
			for(Pocitnice p : this.seznamPocitnic) {
				if(p.getId() == id) {
					p.setCena(cena);
					System.out.println("Uspesno spremenjena cena: ");
					return;
				}
			}
		}else if(izbira == 1) {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm");
			String preberiOdhod = "";
			String preberiPrihod = "";
			LocalDateTime odhod = LocalDateTime.now();
			LocalDateTime prihod = LocalDateTime.now();
			System.out.println("***   VNOS TERMINA   ***\r\n");
			
			while(true) {
				try {
					System.out.println("Vnesi id termina, ki ga zelis spremeniti: ");
					System.out.println();
					System.out.println("id: ");
					idtermin = Integer.parseInt(br.readLine().trim());
					System.out.println();
					break;
				}
				catch (Exception e) {
					System.out.println("Napacen format vnosa!");
					System.out.println();
				}
			}
			for(Pocitnice p : this.seznamPocitnic) {
				if(p.getId() == id) {
					for(Termin t : p.getSeznamTerminov()) {
						if(t.getId() == idtermin) {
							while(true) {
								try {
									System.out.println("Vnesi nov termin in cas odhoda (npr: 2022-05-31 10:00):  ");
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
									System.out.println("Vnesi nov termin in cas prihoda (npr: 2022-06-05 10:00):  ");
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
							
							// Ko pretvarjas LocalDateTime v string mors uporabit datetimeformater 
							// da si ne zakompliciras stvari...
							ojoj += prihod;
							ojoj2 += odhod;
							t.setOdhod(ojoj2);
							t.setPrihod(ojoj);
							System.out.println("Uspesno spremenjen termin: ");
							return;
						}else {
							System.out.println("Ni tega termina");
						}
					}
				}
			}
		}
	}
	
	
	public String izpisPoId(boolean admin) throws Exception {
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		
		int id = 0;
		while(true) {
			try {
				System.out.println("Vnesi id: ");
				System.out.println();
				System.out.println("id: ");
				id = Integer.parseInt(br.readLine().trim());
				System.out.println();
				break;
			}
			catch (Exception e) {
				System.out.println("Napacen format vnosa!");
				System.out.println();
			}
		}
		String podatki = "";
		
		for(Pocitnice p : this.seznamPocitnic) {
			if(p.getId() == id) {
				podatki += p.toString(admin);
				podatki += p.zasedenost();
				podatki += "\r\n";
			}
		}
		
		if(podatki.equals("")) {
			return "Ni pocitnic s to id!\r\n";
		}
		
		return podatki;
	}
	
	public String izpisPoTipu(int tip, boolean admin) {
		String podatki = "";
		if(tip == 1) {
			for(int i = 0; i < this.seznamPocitnic.size(); i++) {
				if(this.seznamPocitnic.get(i) instanceof Potovanje) {
					podatki += this.seznamPocitnic.get(i).toString(admin);
					podatki += "\r\n";
				}
			}
		} 
		else if(tip == 2) {
			for(int i = 0; i < this.seznamPocitnic.size(); i++) {
				if(this.seznamPocitnic.get(i) instanceof Kampiranje) {
					podatki += this.seznamPocitnic.get(i).toString(admin);
					podatki += "\r\n";
				}
			}
		} 
		else if(tip == 3) {
			for(int i = 0; i < this.seznamPocitnic.size(); i++) {
				if(this.seznamPocitnic.get(i) instanceof Krizarjenje) {
					podatki += this.seznamPocitnic.get(i).toString(admin);
					podatki += "\r\n";
				}
			}
		}
		else if(tip == 4) {
			for(int i = 0; i < this.seznamPocitnic.size(); i++) {
				if(this.seznamPocitnic.get(i) instanceof Smucanje) {
					podatki += this.seznamPocitnic.get(i).toString(admin);
					podatki += "\r\n";
				}
			}
		} 
		else if(tip == 5) {
			for(int i = 0; i < this.seznamPocitnic.size(); i++) {
				if(this.seznamPocitnic.get(i) instanceof IzletVGore) {
					podatki += this.seznamPocitnic.get(i).toString(admin);
					podatki += "\r\n";
				}
			}
		} 
		return podatki;
	}
	
	public String izpisPoTerminu(boolean admin) throws Exception {
		
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd");
		String preberiOdhod = "";
		String preberiPrihod = "";
		
		System.out.println("***   Iskanje glede na termin pocitnic   ***");
		System.out.println();
		System.out.println("Termin odhoda (npr: 2023-02-01):  ");
		preberiOdhod = br.readLine().trim();
		LocalDate odhod = LocalDate.parse(preberiOdhod, dtf);
		
		System.out.println();
		System.out.println("Termin prihoda (npr: 2023-02-05):  ");
		preberiPrihod = br.readLine().trim();
		LocalDate prihod = LocalDate.parse(preberiPrihod, dtf);
		
		String podatki = "";
		
		System.out.println("Mozne pocitnice v okvirju termina:\r\n");
		
		for(Pocitnice pocitnice : this.seznamPocitnic) {
			for(Termin termin : pocitnice.getSeznamTerminov()) {
				if(termin.getOdhod().isEqual(odhod) && termin.getPrihod().isEqual(prihod)) {
					podatki += pocitnice.toString(admin);
					podatki += termin.toString(admin);
					podatki += pocitnice.zasedenost();
					podatki += "\r\n";
				}
				/*if(termin.getOdhod().isEqual(odhod) || (termin.getOdhod().isAfter(odhod) && termin.getOdhod().isBefore(prihod))
					&& termin.getPrihod().isEqual(prihod) || (termin.getPrihod().isBefore(prihod) && termin.getOdhod().isBefore(prihod) ) ) {
					podatki += pocitnice.toString(admin);
					podatki += pocitnice.zasedenost();
					podatki += "\r\n";
				}
				*/
			}	
		}
		
		if(podatki.equals("")) {
			return "V izbranem terminu ni moznih pocitnic!\r\n";
		}
		
		return podatki;	
	}
	
	public String izpisDrzava(boolean admin) throws Exception {

		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		
		System.out.println("***   Vnesi ime drzave:   ***");
		System.out.println();
		System.out.println("Drzava: ");
		String drzava = br.readLine().trim();
		
		String podatki = "";
		
		for(Pocitnice p : this.seznamPocitnic) {
			if(p.getDrzava().equals(drzava)) {
				podatki += p.toString(admin);
				podatki += p.zasedenost();
				podatki += "\r\n";
			}
		}
		
		if(podatki.equals("")) {
			return "Za izbrano drzavo ni moznih pocitnic!\r\n";
		}
		return podatki;
	}
	
	public String izpisPoCeni(boolean admin) throws Exception {
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		
		int cena = 0;
		while(true) {
			try {
				System.out.println("Vnesi cenovni okvir: ");
				System.out.println();
				System.out.println("Cena: ");
				cena = Integer.parseInt(br.readLine().trim());
				System.out.println();
				break;
			}
			catch (Exception e) {
				System.out.println("Napacen format vnosa!");
				System.out.println();
			}
		}
		String podatki = "";
		
		for(Pocitnice p : this.seznamPocitnic) {
			if(p.getCena() == cena) {
				podatki += p.toString(admin);
				podatki += p.zasedenost();
				podatki += "\r\n";
			}
		}
		
		if(podatki.equals("")) {
			return "V cenovnem okvirju ni moznih pocitnic!\r\n";
		}
		
		return podatki;
	}
	
	//------------------------------------------- 
	
	public String izpisRezervacij(boolean admin) throws Exception {
		
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		
		System.out.println("***   izpis rezervacij:   ***");
		System.out.println();
		System.out.println("Vnesi ime: ");
		String ime = br.readLine().trim();
		System.out.println();
		System.out.println("Vnesi priimek: ");
		String priimek = br.readLine().trim();
		System.out.println();
		
		String podatki = "";
		for(Pocitnice p: this.seznamPocitnic) {
			for(Rezervacija r : p.getSeznamRezervacij()) {
				if(r.getIme().equals(ime) && r.getPriimek().equals(priimek)) {
					podatki += p.toStringPocitnice();
					podatki += r.toString(admin);
					podatki += "\r\n";
				}
			}
		}
		return podatki;
	}
	
	public void novaRezervacija() throws Exception {
		
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		
		System.out.println("***   Vnos nove Rezervacije   ***");
		System.out.println();
		System.out.println("Vnesi ime: ");
		String ime = br.readLine().trim();
		System.out.println();
		System.out.println("Vnesi priimek: ");
		String priimek = br.readLine().trim();
		System.out.println();
		
		
		System.out.println("Vnesi drzavo pocitnic");
		System.out.println();
		System.out.println("Drzava: ");
		String drzava = br.readLine().trim();
		
		String podatki = "";
		
		int stOdraslih = 0;
		while(true) {
			try {
				System.out.println("Vnesi stevilo odraslih: ");
				stOdraslih += Integer.parseInt(br.readLine().trim());
				System.out.println();
				break;
			} 
			catch (Exception e) {
				System.out.println("Napacen format vnosa!");
				System.out.println();
			}
		}
		int stOtrok = 0;
		while(true) {
			try {
				System.out.println("Vnesi stevilo otrok: ");
				stOtrok += Integer.parseInt(br.readLine().trim());
				System.out.println();
				break;
			}
			catch (Exception e) {
				System.out.println("Napacen format vnosa!");
				System.out.println();
			}
		}
		int turisti = stOdraslih+stOtrok;
		/*
		for(int i = 0; i < this.seznamPocitnic.size(); i++) {
			if(this.seznamPocitnic.get(i) instanceof tip && this.seznamPocitnic.get(i).getDrzava().equals(drzava)) {
				if(stevilo + turisti <= this.seznamPocitnic.get(i).getmaxSteviloOseb()) {
					System.out.println("Rezervacija uspesna.");
					Rezervacija r = new Rezervacija(ime, priimek, stOdraslih, stOtrok);
					this.seznamPocitnic.get(i).dodajRezervacijo(r);
					return;
				}
				else if(stevilo + turisti >= this.seznamPocitnic.get(i).getmaxSteviloOseb()) {
					System.out.println("Rezervacija NI mogoca.");
					return;
				}
			}
		}
		*/
		
		
		//DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd");
		String preberiOdhod = "";
		String preberiPrihod = "";
		LocalDate odhod;
		LocalDate prihod;
		// za vsak tip pocitnic so na voljo termini
		// za vsak termin je na voljo maxstoseb - prostih mest
		
		while(true) {
			try {
				System.out.println("Vnesi termin (npr: 2022-05-31):  ");
				preberiOdhod = br.readLine().trim();
				odhod = LocalDate.parse(preberiOdhod);
				break;
				
			}
			catch (Exception e) { 
				System.out.println("Napacen format vnosa!");
				System.out.println("Poskusite ponovno:");
			}
		}
		while(true) {
			try {
				System.out.println("Vnesi termin prihoda (npr: 2022-06-05):  ");
				preberiPrihod = br.readLine().trim();
				prihod = LocalDate.parse(preberiPrihod);
				break;
				
			}
			catch (Exception e) { 
				System.out.println("Napacen format vnosa!");
				System.out.println("Poskusite ponovno:");
				System.out.println();
			}
		}
		
		
		int stevilo = 0;
		
		for(Pocitnice pocitnice : this.seznamPocitnic) {
			if(pocitnice.getDrzava().equals(drzava)) {
				for(Rezervacija r : pocitnice.getSeznamRezervacij()) {
					stevilo += r.getStOdraslih() + r.getStOtrok();
				}
				for(Termin termin : pocitnice.getSeznamTerminov()) {
					LocalDate t1 = termin.getOdhod();
					LocalDate t2 = termin.getPrihod();
					if(t1.isEqual(odhod) && t2.isEqual(prihod) ) {
						System.out.println("termin gucci.");
						if(stevilo + turisti <= pocitnice.getmaxSteviloOseb()) {
							System.out.println("Rezervacija uspesna.");
							Rezervacija r = new Rezervacija(ime, priimek, stOdraslih, stOtrok);
							pocitnice.dodajRezervacijo(r);
							return;
						}
						else if(stevilo + turisti >= pocitnice.getmaxSteviloOseb()) {
							System.out.println("Rezervacija NI mogoca.");
							return;
						}	
					}
					else if(!t1.isEqual(odhod) && !t2.isEqual(prihod) ) {
						break;
					}
					else {
						System.out.println("Za ta termin ni mozne rezervacije");
						return;
					}
					
				}
			}
		}
		
	}
	
	
}

	