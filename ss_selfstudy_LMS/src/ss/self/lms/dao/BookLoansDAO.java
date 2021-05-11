package ss.self.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ss.self.lms.entity.BookLoans;

public class BookLoansDAO extends BaseDAO<BookLoans> {

	public BookLoansDAO(Connection conn) {
		super(conn);

	}
	public void addBookLoans(BookLoans bookLoans) throws ClassNotFoundException, SQLException {
		save("insert into tbl_book_loans values (?, ?, ?, ?, ?)", new Object[] {bookLoans.getBookId(), bookLoans.getBranchId(), bookLoans.getCardNo(), bookLoans.getDateOut(), bookLoans.getDueDate()});
	}
	
	public void updateBookLoans(BookLoans bookLoans) throws ClassNotFoundException, SQLException {
		save("update tbl_book_loans set dateOut = ?, dueDate = ? where bookId = ? AND branchId = ? AND cardNo = ?", new Object[] {bookLoans.getDateOut(), bookLoans.getDueDate(), bookLoans.getBookId(), bookLoans.getBranchId(), bookLoans.getCardNo()});
	}
	
	public void deleteBookLoans(BookLoans bookLoans) throws ClassNotFoundException, SQLException {
		save("delete from tbl_book_loans where bookId = ? AND branchId = ? AND cardNo = ?", new Object[] {bookLoans.getBookId(), bookLoans.getBranchId(), bookLoans.getCardNo()});
	}
	
	public List<BookLoans> readAllBookLoans() throws ClassNotFoundException, SQLException {
		return read("select * from tbl_book_loans", null);
	}
	
	public List<BookLoans> readBookLoansFromBookId(Integer id) throws ClassNotFoundException, SQLException {
		return read("select * from tbl_book_loans where bookId = ?", new Object[] {id});
	}
	
	public List<BookLoans> readBookLoansFromBranchId(Integer id) throws ClassNotFoundException, SQLException {
		return read("select * from tbl_book_loans where branchId = ?", new Object[] {id});
	}
	
	public List<BookLoans> readBookLoansFromCardNo(Integer id) throws ClassNotFoundException, SQLException {
		return read("select * from tbl_book_loans where cardNo = ?", new Object[] {id});
	}
	
	@Override
	public List<BookLoans> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		List<BookLoans> bookLoans = new ArrayList<>();
		while(rs.next()) {
			BookLoans bl = new BookLoans();
			bl.setBookId(rs.getInt("bookId"));
			bl.setBranchId(rs.getInt("branchId"));
			bl.setCardNo(rs.getInt("cardNo"));
			bl.setDateOut(rs.getDate("dateOut").toLocalDate());
			bl.setDueDate(rs.getDate("dueDate").toLocalDate());
			bookLoans.add(bl);
		}
		return bookLoans;
		
	}

}
