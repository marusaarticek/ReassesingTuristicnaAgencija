import java.io.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;


public class UporabniskiVmesnik {

	private ArrayList<Uporabnik> seznamUporabnikov;
	
	public UporabniskiVmesnik() {
		this.seznamUporabnikov = new ArrayList<Uporabnik>();
	}
	
	public UporabniskiVmesnik(ArrayList<Uporabnik> seznamUporabnikov) {
		this.seznamUporabnikov = seznamUporabnikov;
	}
	
	public ArrayList<Uporabnik> getSeznamUporabnikov() {
		return this.seznamUporabnikov;
	}
	
	public void setSeznamUporabnikov(ArrayList<Uporabnik> seznamUporabnikov) {
		this.seznamUporabnikov = seznamUporabnikov;
	}
	
	public void registracijaUporabnika(boolean admin) throws Exception {
		
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		
		System.out.println("***   Registracija novega uporabnika   ***");
		System.out.println();
		System.out.println("Vnesi ime: ");
		String ime = br.readLine().trim();
		System.out.println();
		System.out.println("Vnesi priimek: ");
		String priimek = br.readLine().trim();
		System.out.println();
		System.out.println("Vnesi geslo: ");
		String geslo = br.readLine().trim();
		System.out.println();
		
		
		boolean pogoj = false;
		for(Uporabnik uporabnik : this.seznamUporabnikov) {
			if(uporabnik.getIme().equals(ime) && uporabnik.getPriimek().equals(priimek)) {
				pogoj = true;
				System.out.println("Uporabnik s tem imenom je ze registriran.");
				break;
			}
		}
		if(!pogoj && !admin) {
			Uporabnik u = new Uporabnik(ime, priimek, geslo, false);
			this.seznamUporabnikov.add(u);
			System.out.println("Registracija uspesna.");
		
		}
		
		if(admin) {
			Uporabnik u = new Uporabnik(ime, priimek, geslo, true);
			this.seznamUporabnikov.add(u);
			System.out.println("Registracija admina uspesna.");
		}
	}
	
	public void dodajUporabnika(Uporabnik u) {
		boolean pogoj = false;
		for(Uporabnik uporabnik : this.seznamUporabnikov) {
			if(uporabnik.getIme().equals(u.getIme()) && uporabnik.getPriimek().equals(u.getPriimek())) {
				pogoj = true;
				break;
			}
		}
		if(!pogoj) {
			this.seznamUporabnikov.add(u);
			System.out.println("Registracija uspesna.");
		}
		else {
		System.out.println("Uporabnik je ze registriran.");
		System.out.println();
		}
	}
	
	public boolean prijavaUporabnika(boolean admin) throws Exception {
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		
		System.out.println("***   Prijava v sistem.   ***");
		System.out.println();
		System.out.println("Vnesi ime: ");
		String ime = br.readLine().trim();
		System.out.println();
		System.out.println("Vnesi priimek: ");
		String priimek = br.readLine().trim();
		System.out.println();
		System.out.println("Vnesi geslo: ");
		String geslo = br.readLine().trim();
		System.out.println();
		
		boolean flag = false;
		firstLoop: for(Uporabnik u : this.seznamUporabnikov) {
			if(u.getIme().equals(ime) && u.getPriimek().equals(priimek) && u.getGeslo().equals(geslo)) {
				if(admin && u.getAdmin()) {
					System.out.println("Prijava v sistem uspesna.\r\n");
					flag = true;
					return flag;
				}
				else if(admin && !u.getAdmin()) {
					System.out.println("Prijavi se lahko samo admin!\r\n");
					return flag;
				}
				else if(!admin && !u.getAdmin()) {
					System.out.println("Prijava v sistem uspesna.\r\n");
					flag = true;
					return flag;
				}
			}
		}
		System.out.println("Uporabnik se ni registriran.");
		return flag;
	}
	
	public void izbrisUporabnika() throws Exception {
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		
		System.out.println("***   Brisanje uporabnikov:   ***");
		System.out.println("Vnesi podatke o uporabniku, ki ga zelis zbrisati:\r\n");
		System.out.println("Vnesi ime: ");
		String ime = br.readLine().trim();
		System.out.println();
		System.out.println("Vnesi priimek: ");
		String priimek = br.readLine().trim();
		System.out.println();
		System.out.println("Vnesi geslo: ");
		String geslo = br.readLine().trim();
		System.out.println();
		
		int count = 0;
		
		for(Uporabnik u : this.seznamUporabnikov) {
			if(u.getAdmin()) {
				count += 1;
			}
		}
		
		firstLoop: for(Uporabnik u : this.seznamUporabnikov) {
			if(u.getIme().equals(ime) && u.getPriimek().equals(priimek) && u.getGeslo().equals(geslo) && !(u.getAdmin())) {
				this.seznamUporabnikov.remove(u);
				System.out.println("Uporabnik izbrisan.");
				return;
			}
			else if(u.getIme().equals(ime) && u.getPriimek().equals(priimek) && u.getGeslo().equals(geslo) && u.getAdmin()) {
				if(count == 1) {
					System.out.println("Pozor! Admina ne morete zbrisati! ");
					return;
				}
				else {
					this.seznamUporabnikov.remove(u);
					System.out.println("Admin izbrisan.");
					return;
				}
			}
		}
		System.out.println("Uporabnik se ni registriran.");
	}
	
	public void dodajIzDatotekeU(String imeDatoteke) throws Exception
	{
		FileReader fr = new FileReader(imeDatoteke);
		BufferedReader dat = new BufferedReader(fr);

		ArrayList<String> uporabnikiPodatki;
		
		while(dat.ready())
		{
			String vrstica = dat.readLine().trim();
			if(vrstica.equals("*U"))
			{
				uporabnikiPodatki = new ArrayList<String>();
				while(dat.ready() && !vrstica.equals("##"))
				{
					vrstica = dat.readLine().trim();
					uporabnikiPodatki.add(vrstica);
				}

				Uporabnik novUporabnik = Uporabnik.preberiIzNiza(uporabnikiPodatki);
				this.seznamUporabnikov.add(novUporabnik);
			}
		}
		dat.close();
	}
	
	public void shraniVDatotekoU(String imeDatoteke) throws IOException
	{
		
		FileWriter fw = new FileWriter(imeDatoteke, true); 
		PrintWriter dat = new PrintWriter(fw);

		for(Uporabnik u : this.seznamUporabnikov)
		{
			dat.print(u.shraniKotNiz());
		}
		dat.println("***");
		
		dat.close();
	} 
	
}