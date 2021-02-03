package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class OthelloServer {

    public static ServerSocket serverSocket;
    public static List<OthelloServerThread> safeList = Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) {

        //Validating port number
        int port = -1;
        try {
            port = Integer.parseInt(args[0]);
            if ((port < 0 || port > 65535)) {
                System.out.println("[SERVER] Error: Invalid port number");
                port = 62000;
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println("[SERVER] Error: Invalid port number");
            port = 62000;
        }

        System.out.println("[SERVER] Using port number " + port);

        //Create server socket and accepting coming socket
        try {
            int i = 1;
            serverSocket = new ServerSocket(port);
            System.out.println("[SERVER] Starting server ...");

            while (true) {
                Socket incoming = serverSocket.accept();
                System.out.println("[SERVER] Inbound connection #" + i);
                OthelloServerThread thread = new OthelloServerThread(incoming);
                safeList.add(thread);
                thread.start();
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void remove(OthelloServerThread thread){
        safeList.remove(thread);
    }

    public static class OthelloServerThread extends Thread{

        private Socket socket;
        private OutputStream outputStream;
        private PrintWriter out;
        private String name;
        private InputStream inputStream;
        private Scanner scanner;

        public OthelloServerThread(Socket incoming) throws IOException {
            socket = incoming;
            outputStream = socket.getOutputStream();
            out = new PrintWriter(outputStream, true);
            inputStream = socket.getInputStream();
            scanner = new Scanner(inputStream);
            this.name = scanner.nextLine();
            System.out.println("[SERVER] " + name + " has connected");
            out.println(name + " has connected");
        }

        @Override
        public void run(){
            try {
                try {
                    boolean isDone = false;
                    while (!isDone && scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        if(line.equals("/help")) {
                            getWriter().println("/help: this message\n/bye: disconnect\n/who: shows the name of all connected players\n/name (name): Rename yourself\n");
                        }
                        else if (line.equals("/bye")){
                            isDone = true;
                            getWriter().println(name + " has disconnected");
                            System.out.println("[SERVER] " + name + " has disconnected");
                        }
                        else {
                            System.out.println("[SERVER] " + name + ": " + line);
                            for (OthelloServerThread item : safeList)
                                item.getWriter().println(name + ": " + line);
                        }
                    }
                } finally{
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public PrintWriter getWriter(){
            return out;
        }
    }
}
