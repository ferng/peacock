package com.thecrunchycorner.pricebox.server;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.net.ssl.SSLServerSocketFactory;

import org.apache.http.ConnectionClosedException;
import org.apache.http.HttpConnectionFactory;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpServerConnection;
import org.apache.http.impl.DefaultBHttpServerConnection;
import org.apache.http.impl.DefaultBHttpServerConnectionFactory;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpProcessor;
import org.apache.http.protocol.HttpProcessorBuilder;
import org.apache.http.protocol.HttpRequestHandler;
import org.apache.http.protocol.HttpService;
import org.apache.http.protocol.ResponseConnControl;
import org.apache.http.protocol.ResponseDate;
import org.apache.http.protocol.ResponseServer;
import org.apache.http.protocol.UriHttpRequestHandlerMapper;
import org.apache.log4j.Logger;

public class Server
{

  private static Logger logger = Logger.getLogger(Server.class);
  private static boolean terminate = false;

  public Server()
  {
    // TODO Auto-generated constructor stub
  }

  public static void main(String[] args) {
    HttpProcessor httpProc = HttpProcessorBuilder.create()
        .add(new ResponseDate()).add(new ResponseServer("pricebox/1.1"))
        .add(new ResponseConnControl()).build();

    UriHttpRequestHandlerMapper httpMapper = new UriHttpRequestHandlerMapper();
    httpMapper.register("/login/*", getLoginRequestHandler());
    httpMapper.register("/shutdown/*", getShutdownRequestHandler());

    HttpService httpService = new HttpService(httpProc, httpMapper);

    try {
      Thread t = new RequestListenerThread(8080, httpService, null);
      t.setDaemon(false);
      t.start();
    } catch (IOException ex) {

    }

  }

















  static class RequestListenerThread extends Thread {

    private final HttpConnectionFactory<DefaultBHttpServerConnection> connFactory;
    private final ServerSocket serversocket;
    private final HttpService httpService;

    public RequestListenerThread(
            final int port,
            final HttpService httpService,
            final SSLServerSocketFactory sf) throws IOException {
        this.connFactory = DefaultBHttpServerConnectionFactory.INSTANCE;
        this.serversocket = sf != null ? sf.createServerSocket(port) : new ServerSocket(port);
        this.httpService = httpService;
    }

    @Override
    public void run() {
        logger.debug("Listening on port " + this.serversocket.getLocalPort());
        while (!Thread.interrupted()) {
            try {
                // Set up HTTP connection
              if (terminate == true) {
                return;
              }
                Socket socket = this.serversocket.accept();
                logger.debug("Incoming connection from " + socket.getInetAddress());
                HttpServerConnection conn = this.connFactory.createConnection(socket);

                // Start worker thread
                Thread t = new WorkerThread(this.httpService, conn);
                t.setDaemon(true);
                t.start();
            } catch (InterruptedIOException ex) {
                break;
            } catch (IOException e) {
                System.err.println("I/O error initialising connection thread: "
                        + e.getMessage());
                break;
            }
        }
    }
}

static class WorkerThread extends Thread {

    private final HttpService httpservice;
    private final HttpServerConnection conn;

    public WorkerThread(
            final HttpService httpservice,
            final HttpServerConnection conn) {
        super();
        this.httpservice = httpservice;
        this.conn = conn;
    }

    @Override
    public void run() {
        System.out.println("New connection thread");
        HttpContext context = new BasicHttpContext(null);
        try {
            while (!Thread.interrupted() && this.conn.isOpen()) {
                this.httpservice.handleRequest(this.conn, context);
            }
        } catch (ConnectionClosedException ex) {
            System.err.println("Client closed connection");
        } catch (IOException ex) {
            System.err.println("I/O error: " + ex.getMessage());
        } catch (HttpException ex) {
            System.err.println("Unrecoverable HTTP protocol violation: " + ex.getMessage());
        } finally {
            try {
                this.conn.shutdown();
            } catch (IOException ignore) {}
        }
    }

}



















  private static HttpRequestHandler getLoginRequestHandler() {
    return new HttpRequestHandler() {
      @Override
      public void handle(HttpRequest arg0, HttpResponse arg1, HttpContext arg2) throws HttpException, IOException {
        logger.debug("call made");
      }
    };
  }


  private static HttpRequestHandler getShutdownRequestHandler() {
    return new HttpRequestHandler() {
      @Override
      public void handle(HttpRequest arg0, HttpResponse arg1, HttpContext arg2) throws HttpException, IOException {
        logger.debug("Shutting down");
        terminate = true;
      }
    };
  }




}

