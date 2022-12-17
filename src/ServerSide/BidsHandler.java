package ServerSide;

import Database.Database;
import Models.Bid;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class BidsHandler {
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.0");
    private ResultSet resultSet;
    private String query;

    public BidsHandler() {
    }

    public boolean createBid(Bid bid) throws SQLException {
        query = "INSERT INTO BIDS(title, description, image_path, price, duration, creation_date, seller) values('" + bid.getTitle() + "','" + bid.getDescription() + "','" + bid.getImage_path() + "', '" + bid.getPrice() + "', '" + bid.getDuration() + "', '" + bid.getCreation_date() + "', '" + bid.getSeller() + "')";
        return Database.getInstance().update(query);
    }

    public List<Bid> getAllBids() throws SQLException {
        List<Bid> bidsList = new ArrayList<Bid>();
        List<Integer> expiredBidsIds = new ArrayList<Integer>();
        query = "select * from bids";
        resultSet = Database.getInstance().getRecords(query);
        while (resultSet.next()) {
            LocalDateTime creation_date = LocalDateTime.parse(resultSet.getString("creation_date"), dateTimeFormatter);
            LocalDateTime now = LocalDateTime.now();
            long minutes = ChronoUnit.MINUTES.between(creation_date, now);

            if (minutes < resultSet.getInt("duration")*60)
                bidsList.add(new Bid(resultSet.getInt("id"),resultSet.getInt("price"),resultSet.getInt("duration"),resultSet.getString("title"),resultSet.getString("description"),resultSet.getString("image_path"),resultSet.getString("creation_date"),resultSet.getString("seller")));
            else
                expiredBidsIds.add(resultSet.getInt("id"));
        }
        deleteExpiredBids(expiredBidsIds);
        return bidsList;
    }

    private void deleteExpiredBids(List<Integer> expiredBidsIds) throws SQLException {
        for (int id: expiredBidsIds) {
            query = "DELETE FROM bids WHERE id= '" + id + "';";
            Database.getInstance().update(query);
        }
    }

    public boolean updatePrice(int id, int newPrice, String winner) throws SQLException {
        if (newPrice > getCurrentPrice(id)) {
            query = "UPDATE bids SET price = '" + newPrice + "', winner = '" + winner + "' WHERE id = '" + id + "';";
            return Database.getInstance().update(query);
        } else
            return false;
    }

    private int getCurrentPrice(int id) throws SQLException {
        query = "select price FROM bids WHERE id= '" + id + "';";
        resultSet = Database.getInstance().getRecords(query);
        resultSet.next();
        return resultSet.getInt("price");
    }
}
