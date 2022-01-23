import static org.junit.Assert.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
 
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

import service.ElevServlet;

public class ElevServletTest {
	@Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    HttpSession session;
    @Mock
    List<Integer> mockedList = new ArrayList<Integer>();
    
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }
    
	@Test
	public void testDoGetOk() throws IOException, ServletException{
		when(request.getParameter("AflareNumeElev")).thenReturn("AflareNumeElev");
		StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        
        when(request.getSession()).thenReturn(session);
        
        when(session.getAttribute("prenume")).thenReturn("Catalina");
        when(session.getAttribute("nume")).thenReturn("Bejan");
         
        when(response.getWriter()).thenReturn(pw);
 
        ElevServlet elevServlet = new ElevServlet();
        elevServlet.doGet(request, response);
        String result = sw.getBuffer().toString().trim();
        
        assertEquals(new String("Numele elevului: Catalina Bejan"), result);
	}
	
	@Test
	public void testDoGetSessionAttributesNull() throws IOException, ServletException{
		when(request.getParameter("AflareNumeElev")).thenReturn("AflareNumeElev");
		StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        
        when(request.getSession()).thenReturn(session);
        
        when(session.getAttribute("prenume")).thenReturn(null);
        when(session.getAttribute("nume")).thenReturn(null);
     
        when(response.getWriter()).thenReturn(pw);
 
        ElevServlet elevServlet = new ElevServlet();
        elevServlet.doGet(request, response);
        String result = sw.getBuffer().toString().trim();
        
        assertNull(session.getAttribute("prenume"));
        assertNull(session.getAttribute("nume"));
        assertEquals(new String("Atributele prenume sau nume nu sunt setate in sesiune"), result);
	}
	
	@Test
	public void testDoGetAflareNumeElevNull() throws IOException, ServletException{
		when(request.getParameter("AflareNumeElev")).thenReturn(null);
		
		StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        when(response.getWriter()).thenReturn(pw);
 
        ElevServlet elevServlet = new ElevServlet();
        elevServlet.doGet(request, response);
        String result = sw.getBuffer().toString().trim();
        
        assertNull(request.getParameter("AflareNumeElev"));
        assertEquals(new String("Nu se doreste aflarea numelui elevului."), result);
	}
	
	@Test
	public void testList() {
		List<Integer> list = new ArrayList<Integer>();
		assertEquals(0, list.size());
		
		list.add(1);
        assertEquals(1, list.size());
        assertEquals(1, list.get(0).intValue());
        
        list.add(2);
        assertEquals(2, list.size());
        
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(7);
        list.add(8);
        list.add(9);
        list.add(10);
        assertEquals(10, list.size());
        
        list.remove(0);
        assertEquals(9, list.size());
        
        list.clear();
        assertEquals(0, list.size());
        
        Answer<String> answer = new Answer<String>() {
            public String answer(InvocationOnMock invocation) throws Throwable {
                String message = "Metoda get() a fost invocata.";
                System.out.println(message);
            	return message;
            }
        };
        when(mockedList.get(0)).thenAnswer(answer);
        
        mockedList.get(0);
        mockedList.get(0);
        mockedList.get(0);
        
        Collection<Invocation> invocations = Mockito.mockingDetails(mockedList).getInvocations();
        int numberOfCalls = invocations.size();
        if(numberOfCalls > 0 ){
        	System.out.println("Metoda get a fost invocata de "+ numberOfCalls + " ori");
        }else{
        	System.out.println("Metoda get nu a fost invocata niciodata.");
        }
	}
}
