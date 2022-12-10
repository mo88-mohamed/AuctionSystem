import Models.Bid;
import Models.Message;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ServerConnection {
    private static ServerConnection serverConnection;
    private static Socket socket;
    private static OutputStream outputStream;
    private static ObjectOutputStream objectOutputStream;
    private static InputStream inputStream;
    private static ObjectInputStream objectInputStream;
    private static List<Bid> bidsList;
    private boolean isListUpdated;
    private Message message = new Message();

    private ServerConnection() throws IOException, InterruptedException, ClassNotFoundException {
        createConnection();
        bidsList = getBidsList();
        createBidsListUpdater();
    }

    public static ServerConnection getInstance() throws IOException, InterruptedException, ClassNotFoundException {
        if (serverConnection == null)
            serverConnection = new ServerConnection();
        return serverConnection;
    }

    public void startSession() {

    }

    private void createConnection() throws IOException {
        socket = new Socket("localhost", 12345);
        socket.setKeepAlive(true);
        outputStream = socket.getOutputStream();
        objectOutputStream = new ObjectOutputStream(outputStream);
        inputStream = socket.getInputStream();
        objectInputStream = new ObjectInputStream(inputStream);
    }

    public Message sendMessage(Message message) throws IOException, ClassNotFoundException {
        synchronized (objectOutputStream) {
            objectOutputStream.writeObject(message);
        }
        synchronized (objectInputStream) {
            message = (Message) objectInputStream.readObject();
        }
        return message;
    }

    private void createBidsListUpdater() throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Message message1 = new Message();
                try {
                    while (true) {
                        try {
                            synchronized (objectInputStream) {
                                message1 = (Message) objectInputStream.readObject();
                            }
//                            message1 = (Message) objectInputStream.readObject();
                            try {
                                if (message1.getFunctionName().equals("getAllBidsLive")) {
                                    bidsList = (List<Bid>) message1.getObject();
                                    setIsListUpdated(true);
                                }
                            } catch (Exception e) {
                                continue;
                            }
                        } catch (Exception e) {
                            continue;
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    public List<Bid> getBidsList() throws IOException, ClassNotFoundException {
        if (bidsList == null) {
            message = new Message("getAllBids");
            objectOutputStream.writeObject(message);
            message = (Message) objectInputStream.readObject();
            bidsList = (List<Bid>) message.getObject();
        }
        return bidsList;
    }

    public synchronized void setIsListUpdated(boolean state) {
        this.isListUpdated = state;
    }

    public synchronized boolean getIsListUpdated() {
        return isListUpdated;
    }
}
