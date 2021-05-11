package ss.self.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ss.self.lms.entity.BookCopies;

public class BookCopiesDAO extends BaseDAO<BookCopies> {

	public BookCopiesDAO(Connection conn) {
		super(conn);

	}
	public void addBookCopies(BookCopies bookCopies) throws ClassNotFoundException, SQLException {
		save("insert into tbl_book_copies values (?, ?, ?)", new Object[] {bookCopies.getBookId(), bookCopies.getBranchId(), bookCopies.getNoOfCopies()});
	}
	
	public void updateBookCopies(BookCopies bookCopies) throws ClassNotFoundException, SQLException {
		save("update tbl_book_copies set noOfCopies = ? where bookId = ? AND branchId = ?", new Object[] {bookCopies.getNoOfCopies(), bookCopies.getBookId(), bookCopies.getBranchId()});
	}
	
	public void deleteBookCopies(BookCopies bookCopies) throws ClassNotFoundException, SQLException {
		save("delete from tbl_book_copies where bookId = ? AND branchId = ?", new Object[] {bookCopies.getBookId(), bookCopies.getBranchId()});
	}
	
	public List<BookCopies> readAllBookCopies() throws ClassNotFoundException, SQLException {
		return read("select * from tbl_book_copies", null);
	}
	
	public List<BookCopies> readBookCopiesFromBookId(Integer id) throws ClassNotFoundException, SQLException {
		return read("select * from tbl_book_copies where bookId = ?", new Object[] {id});
	}
	
	public List<BookCopies> readBookCopiesFromBranchId(Integer id) throws ClassNotFoundException, SQLException {
		return read("select * from tbl_book_copies where branchId = ?", new Object[] {id});
	}
	
	public List<BookCopies> readBookCopiesFromBookAndBranchId(Integer bookId, Integer branchId) throws ClassNotFoundException, SQLException {
		return read("select * from tbl_book_copies where bookId = ? AND branchId = ?", new Object[] {bookId, branchId});
	}
	
	
	@Override
	public List<BookCopies> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		List<BookCopies> bookCopies = new ArrayList<>();
		while(rs.next()) {
			BookCopies bc = new BookCopies();
			bc.setBookId(rs.getInt("bookId"));
			bc.setBranchId(rs.getInt("branchId"));
			bc.setNoOfCopies(rs.getInt("noOfCopies"));
			bookCopies.add(bc);
		}
		return bookCopies;
		
	}

}
