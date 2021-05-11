package ss.self.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ss.self.lms.entity.Publisher;

public class PublisherDAO extends BaseDAO<Publisher> {

	public PublisherDAO(Connection conn) {
		super(conn);

	}
	public void addPublisher(Publisher publisher) throws ClassNotFoundException, SQLException {
		save("insert into tbl_publisher values (?, ?, ?, ?)", new Object[] {publisher.getId(), publisher.getName(), publisher.getAddress(), publisher.getPhone()});
	}
	
	public void updatePublisher(Publisher publisher) throws ClassNotFoundException, SQLException {
		save("update tbl_publisher set publisherName = ?, publisherAddress = ?, publisherPhone = ? where publisherId = ?", new Object[] {publisher.getName(), publisher.getAddress(), publisher.getPhone(), publisher.getId()});
	}
	
	public void deletePublisher(Publisher publisher) throws ClassNotFoundException, SQLException {
		save("delete from tbl_publisher where publisherId = ?", new Object[] {publisher.getId()});
	}
	
	public List<Publisher> readAllPublishers() throws ClassNotFoundException, SQLException {
		return read("select * from tbl_publisher", null);
	}
	
	public List<Publisher> readPublisherFromId(Integer id) throws ClassNotFoundException, SQLException {
		return read("select * from tbl_publisher where publisherId = ?", new Object[] {id});
	}
	
	@Override
	public List<Publisher> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		List<Publisher> publishers = new ArrayList<>();
		while(rs.next()) {
			Publisher p = new Publisher();
			p.setId(rs.getInt("publisherId"));
			p.setName(rs.getString("publisherName"));
			p.setAddress(rs.getString("publisherAddress"));
			p.setPhone(rs.getString("publisherPhone"));
			publishers.add(p);
		}
		return publishers;
		
	}

}
