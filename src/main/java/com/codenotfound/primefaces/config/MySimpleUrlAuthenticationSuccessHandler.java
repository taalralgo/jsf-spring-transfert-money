package com.codenotfound.primefaces.config;

import com.codenotfound.primefaces.model.Utilisateur;
import com.codenotfound.primefaces.repository.UtilisateurRepository;
import com.codenotfound.primefaces.service.CustumUserDetailsService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;

@SuppressWarnings("unused")
public class MySimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler
{

    protected Log logger = LogFactory.getLog(this.getClass());

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    private Utilisateur u;
    @Autowired
    private Utils utils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication)
            throws IOException
    {

        handle(request, response, authentication);
        clearAuthenticationAttributes(request);
    }

    protected void handle(HttpServletRequest request,
                          HttpServletResponse response, Authentication authentication)
            throws IOException
    {

        String targetUrl = determineTargetUrl(authentication);

        request.getSession().setAttribute("connectedUser", u);
        if (response.isCommitted())
        {
            logger.debug(
                    "Response has already been committed. Unable to redirect to "
                            + targetUrl);
            return;
        }


        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    @Autowired
    private UtilisateurRepository iUtilisateur;

    @Autowired
    private CustumUserDetailsService userDetailsService;

    protected String determineTargetUrl(Authentication authentication)
    {
        boolean isSuper = false;
        boolean isAdmin = false;
        boolean isCaissier = false;
        Collection<? extends GrantedAuthority> authorities
                = authentication.getAuthorities();
        String role = "";
        for (GrantedAuthority grantedAuthority : authorities)
        {
            if (grantedAuthority.getAuthority().equals("ROLE_ADMIN"))
            {
                role = grantedAuthority.getAuthority();
                isAdmin = true;
                break;
            }
            else if (grantedAuthority.getAuthority().equals("ROLE_SUPER"))
            {
                role = grantedAuthority.getAuthority();
                isSuper = true;
                break;
            }
            else if (grantedAuthority.getAuthority().equals("ROLE_CAISSIER"))
            {
                role = grantedAuthority.getAuthority();
                isCaissier= true;
                break;
            }
            System.out.println("role : " + role);
        }


        u = new Utilisateur();

        u = iUtilisateur.findByLogin(utils.getConnectedUser());

        if(!u.isChanged())
        {
            return "/reset.xhtml";
        }
        else
            return "/helloworld.xhtml";
//        if (isAdmin) {
//            return "home";
//        }
//        if (isCaissier) {
//            return "home";
//        }
//        if (isSuper) {
//            return "home";
//        }
//        else {
//            throw new IllegalStateException();
//        }
//
//        if (isAdmin)
//            return "/cars.xhtml";
//        else
//            return "/helloworld.xhtml";


    }

    protected void clearAuthenticationAttributes(HttpServletRequest request)
    {
        HttpSession session = request.getSession(false);
        if (session == null)
        {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

    public void setRedirectStrategy(RedirectStrategy redirectStrategy)
    {
        this.redirectStrategy = redirectStrategy;
    }

    protected RedirectStrategy getRedirectStrategy()
    {
        return redirectStrategy;
    }

}