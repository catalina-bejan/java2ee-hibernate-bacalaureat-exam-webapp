import static org.junit.Assert.assertEquals;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.junit.Test;

import model.Elev;
import model.Examen;
import model.Proba;
import model.Rezultat;

public class ExamenTest {
	
	private final long COD_EXAMEN=10;
    private final Elev ELEV=new Elev();
    private final Proba PROBA = new Proba();
	private final Rezultat REZULTAT = new Rezultat();
	private Examen examen = new Examen();
	
	@Test
    public void testCodExamen(){
    	examen.setCodExamen(COD_EXAMEN);
    	assertEquals(COD_EXAMEN,examen.getCodExamen());
    }
	
	@Test
    public void testElev(){
    	examen.setElev(ELEV);
    	assertEquals(ELEV,examen.getElev());
    }
	
	@Test
    public void testProba(){
    	examen.setProba(PROBA);
    	assertEquals(PROBA,examen.getProba());
    }
	
	@Test
    public void testRezultat(){
    	examen.setRezultat(REZULTAT);
    	assertEquals(REZULTAT,examen.getRezultat());
    }
	
	
}
