/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto2.bank.presentacion;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import proyecto2.bank.datos.ClienteDAO;
import proyecto2.bank.datos.ClienteDAOImplHibernate;
import proyecto2.bank.negocio.Cliente;

/**
 *
 * @author alumno
 */
@Controller
public class SessionController {
    
    @Autowired
    ClienteDAO clientesDAO = new ClienteDAOImplHibernate();
    
    @RequestMapping(value = {"/Login"}, method = RequestMethod.POST, produces = "application/json")
    public void readByLogin(HttpServletRequest httpRequest, HttpServletResponse httpServletResponse, @RequestBody String json) {
        try {
            httpServletResponse.setContentType("application/json; charset=UTF-8");
            ObjectMapper objectMapper = new ObjectMapper();
            Credenciales credenciales = objectMapper.readValue(json, Credenciales.class);
            Cliente cliente = clientesDAO.readByLogin(credenciales.getLogin());
        if (credenciales != null) {
                httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            

           /* if (cliente.checkPassword(credenciales.getPassword())) {

                httpServletResponse.setStatus(HttpServletResponse.SC_OK);
                httpServletResponse.setContentType("application/json; charset=UTF-8");
                ObjectMapper objectMappers = new ObjectMapper();

                json = objectMappers.writeValueAsString(cliente);
                httpServletResponse.getWriter().println(json);

            }
            else {
                httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }*/
            }
 
        } catch (Exception ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            noCache(httpServletResponse);
            httpServletResponse.setContentType("text/plain; charset=UTF-8");
            try {
                ex.printStackTrace(httpServletResponse.getWriter());
            } catch (IOException ex1) {
            }
        }

    }
    
    
      private void noCache(HttpServletResponse httpServletResponse) {
        httpServletResponse.setHeader("Cache-Control", "no-cache");
    }
    
}
