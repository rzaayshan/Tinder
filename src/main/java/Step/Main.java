package Step;

import Step.Filters.CanLogin;
import Step.Filters.IsLogin;
import Step.Helpers.TemplateEngine;
import Step.Servlets.*;
import Step.db.ConnDetails;
import Step.db.DbSetup;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.DispatcherType;
import java.sql.Connection;
import java.util.EnumSet;


public class Main {
    public static void main(String[] args) throws Exception {

        DbSetup.prepare(ConnDetails.url, ConnDetails.username, ConnDetails.password);

        Server server = new Server(8087);

        ServletContextHandler handler = new ServletContextHandler();

        TemplateEngine engine = TemplateEngine.folder("content");

        handler.addServlet(new ServletHolder(new Start(engine)),"/start");
        handler.addServlet(new ServletHolder(new Like(engine)),"/users");
        handler.addServlet(new ServletHolder(new Choice()),"/choice");
        handler.addServlet(new ServletHolder(new List(engine)),"/liked");
        handler.addServlet(new ServletHolder(new Login()),"/login");
        handler.addServlet(new ServletHolder(new Logout()),"/logout");
        handler.addServlet(new ServletHolder(new Chat(engine)),"/message");
        handler.addServlet(new ServletHolder(new StaticServlet("css")), "/css/*");

        handler.addFilter(new FilterHolder(new CanLogin()),"/start", EnumSet.of(DispatcherType.REQUEST));
        handler.addFilter(new FilterHolder(new IsLogin()),"/users", EnumSet.of(DispatcherType.REQUEST));
        handler.addFilter(new FilterHolder(new IsLogin()),"/liked", EnumSet.of(DispatcherType.REQUEST));
        handler.addFilter(new FilterHolder(new IsLogin()),"/choice", EnumSet.of(DispatcherType.REQUEST));
        handler.addFilter(new FilterHolder(new IsLogin()),"/message",EnumSet.of(DispatcherType.REQUEST));



        server.setHandler(handler);

        server.start();
        server.join();


    }
}
