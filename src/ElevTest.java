import static org.junit.Assert.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
 
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.Invocation;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import model.Elev;


public class ElevTest {
	private final long COD_ELEV=1;
    public final String NUME="Popescu";
    private final String INITIALA_TATA="A";
    public final String PRENUME="Ion";
    private final String CNP="1234567891234";
    private Elev elev = new Elev();
    
    @Test
    public void testCodElev(){
    	elev.setCodElev(COD_ELEV);
    	assertEquals(COD_ELEV,elev.getCodElev());
    }
    @Test
    public void testNume(){
    	elev.setNume(NUME);
    	assertEquals(NUME,elev.getNume());
    }
    @Test
    public void testInitialaTata(){
    	elev.setInitialaTata(INITIALA_TATA);
    	assertEquals(INITIALA_TATA, elev.getInitialaTata());
    }
    @Test
    public void testPrenume(){
    	elev.setPrenume(PRENUME);
    	assertEquals(PRENUME,elev.getPrenume());
    }
    @Test
    public void testCnp(){
    	elev.setCnp(CNP);
    	assertEquals(CNP,elev.getCnp());
    }
}
