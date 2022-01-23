package test;

import org.junit.Assert;
import org.junit.Test;

import api.ElevDao;
import api.LiceuDao;
import api.LiceuProfilDao;
import api.ProfilDao;
import model.Elev;
import model.Examen;
import model.Liceu;
import model.LiceuProfil;
import model.Profil;

public class ElevDaoTest {
	ElevDao elevDao = new ElevDao();
	LiceuDao liceuDao = new LiceuDao();
	ProfilDao profilDao = new ProfilDao();
	LiceuProfilDao liceuProfilDao = new LiceuProfilDao();

	@Test
	public void saveElevTest() {
		String denumireLiceu = "Colegiul National \"Al. I. Cuza\"";
		Liceu liceu = liceuDao.getLiceuByDenumire(denumireLiceu);
		String denumireProfil = "Matematica-Informatica";
		Profil profil = profilDao.getProfilByDenumire(denumireProfil);
		long codLiceuProfil = liceuProfilDao.getCodLiceuProfilByLiceuAndProfil(liceu, profil);
		Elev elev = new Elev();
		String nume = "Popescu";
		String initialaTata = "C";
		String prenume = "Ion";
		String cnp = "1234378943562";
		elev.setNume(nume);
		elev.setInitialaTata(initialaTata);
		elev.setPrenume(prenume);
		elev.setCnp(cnp);
		elev.setLiceuProfil(liceuProfilDao.findById(codLiceuProfil));
		String result = elevDao.saveElev(elev);
		
		String cnpDB = elev.getCnp();
        Assert.assertNotNull(cnpDB);

        Elev newElev = elevDao.getElevByCnp(cnpDB);

        Assert.assertEquals(nume, newElev.getNume());
        Assert.assertEquals(initialaTata, newElev.getInitialaTata());
        Assert.assertEquals(prenume, newElev.getPrenume());
        Assert.assertEquals(denumireLiceu, newElev.getLiceuProfil().getLiceu().getDenumire());
        Assert.assertEquals(denumireProfil, newElev.getLiceuProfil().getProfil().getDenumire());
        Assert.assertEquals("Tranzactie efectuata cu succes", result);
	}
	
	@Test
	public void saveElevTestDoubledCNP() {
		String denumireLiceu = "Colegiul National \"Al. I. Cuza\"";
		Liceu liceu = liceuDao.getLiceuByDenumire(denumireLiceu);
		String denumireProfil = "Matematica-Informatica";
		Profil profil = profilDao.getProfilByDenumire(denumireProfil);
		long codLiceuProfil = liceuProfilDao.getCodLiceuProfilByLiceuAndProfil(liceu, profil);
		Elev elev = new Elev();
		String nume = "Mihailescu";
		String initialaTata = "B";
		String prenume = "George";
		String cnp = "1234378943563";
		elev.setNume(nume);
		elev.setInitialaTata(initialaTata);
		elev.setPrenume(prenume);
		elev.setCnp(cnp);
		elev.setLiceuProfil(liceuProfilDao.findById(codLiceuProfil));
		elevDao.saveElev(elev);
		String result = elevDao.saveElev(elev);
		
		Assert.assertEquals("Tranzactie esuata", result);
	}
	
	@Test
	public void updateElevTest() {
		String denumireLiceu = "Colegiul National \"Al. I. Cuza\"";
		Liceu liceu = liceuDao.getLiceuByDenumire(denumireLiceu);
		String denumireProfil = "Matematica-Informatica";
		Profil profil = profilDao.getProfilByDenumire(denumireProfil);
		long codLiceuProfil = liceuProfilDao.getCodLiceuProfilByLiceuAndProfil(liceu, profil);
		Elev elev = new Elev();
		String nume = "Vasilescu";
		String initialaTata = "T";
		String prenume = "Otilia";
		String cnp = "1234378943564";
		elev.setNume(nume);
		elev.setInitialaTata(initialaTata);
		elev.setPrenume(prenume);
		elev.setCnp(cnp);
		elev.setLiceuProfil(liceuProfilDao.findById(codLiceuProfil));
		elevDao.saveElev(elev);
		
		prenume = "Sorina";
		cnp = "1234378943564";
		elev.setPrenume(prenume);
		String result = elevDao.updateElev(elev);
		
		String cnpDB = elev.getCnp();
        Assert.assertNotNull(cnpDB);

        Elev newElev = elevDao.getElevByCnp(cnpDB);

        Assert.assertEquals(prenume, newElev.getPrenume());
        Assert.assertEquals("Tranzactie efectuata cu succes", result);
	}
	
	@Test
	public void updateElevTestNotFound() {
		String denumireLiceu = "Colegiul National \"Al. I. Cuza\"";
		Liceu liceu = liceuDao.getLiceuByDenumire(denumireLiceu);
		String denumireProfil = "Matematica-Informatica";
		Profil profil = profilDao.getProfilByDenumire(denumireProfil);
		long codLiceuProfil = liceuProfilDao.getCodLiceuProfilByLiceuAndProfil(liceu, profil);
		Elev elev = new Elev();
		String nume = "Capitanescu";
		String initialaTata = "L";
		String prenume = "Rares";
		String cnp = "1234378943569";
		elev.setNume(nume);
		elev.setInitialaTata(initialaTata);
		elev.setPrenume(prenume);
		elev.setCnp(cnp);
		elev.setLiceuProfil(liceuProfilDao.findById(codLiceuProfil));

		String result = elevDao.updateElev(elev);
		
		String cnpDB = elev.getCnp();
        Assert.assertNotNull(cnpDB);

        Assert.assertNull(elevDao.getElevByCnp(cnpDB));
        Assert.assertEquals("Tranzactie esuata", result);
	}
	
	@Test
	public void deleteElevTest() {
		String denumireLiceu = "Colegiul National \"Al. I. Cuza\"";
		Liceu liceu = liceuDao.getLiceuByDenumire(denumireLiceu);
		String denumireProfil = "Matematica-Informatica";
		Profil profil = profilDao.getProfilByDenumire(denumireProfil);
		long codLiceuProfil = liceuProfilDao.getCodLiceuProfilByLiceuAndProfil(liceu, profil);
		Elev elev = new Elev();
		String nume = "Damaschin";
		String initialaTata = "P";
		String prenume = "Liliana";
		String cnp = "1234378943565";
		elev.setNume(nume);
		elev.setInitialaTata(initialaTata);
		elev.setPrenume(prenume);
		elev.setCnp(cnp);
		elev.setLiceuProfil(liceuProfilDao.findById(codLiceuProfil));
		elevDao.saveElev(elev);
		String result = elevDao.deleteElev(elev);
		
		String cnpDB = elev.getCnp();
        Assert.assertNotNull(cnpDB);

        Assert.assertNull(elevDao.getElevByCnp(cnpDB));
        Assert.assertEquals("Tranzactie efectuata cu succes", result);
	}
	
	@Test
	public void deleteElevTestNotFound() {
		String denumireLiceu = "Colegiul National \"Al. I. Cuza\"";
		Liceu liceu = liceuDao.getLiceuByDenumire(denumireLiceu);
		String denumireProfil = "Matematica-Informatica";
		Profil profil = profilDao.getProfilByDenumire(denumireProfil);
		long codLiceuProfil = liceuProfilDao.getCodLiceuProfilByLiceuAndProfil(liceu, profil);
		Elev elev = new Elev();
		String nume = "Damaschin";
		String initialaTata = "P";
		String prenume = "Liliana";
		String cnp = "1234378943566";
		elev.setNume(nume);
		elev.setInitialaTata(initialaTata);
		elev.setPrenume(prenume);
		elev.setCnp(cnp);
		elev.setLiceuProfil(liceuProfilDao.findById(codLiceuProfil));

		String result = elevDao.deleteElev(elev);
		Assert.assertEquals("Tranzactie esuata", result);
	}
	
	
}
