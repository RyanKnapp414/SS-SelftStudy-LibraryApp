package ss.self.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ss.self.lms.entity.Author;
import ss.self.lms.entity.Book;
import ss.self.lms.entity.BookCopies;

public class BookDAO extends BaseDAO<Book> {

	public BookDAO(Connection conn) {
		super(conn);
	}

	public void addBook(Book book) throws ClassNotFoundException, SQLException {
		save("insert into tbl_book values (?, ?, ?, ?)", new Object[] {book.getId(), book.getTitle(), book.getAuthorId(), book.getPublisherId()});
	}
	
	public void updateBook(Book book) throws ClassNotFoundException, SQLException {
		save("update tbl_book set title = ?, authId = ?, pubId = ? where bookId = ?", new Object[] {book.getTitle(), book.getAuthorId(), book.getPublisherId(), book.getId()});
	}
	
	public void deleteBook(Book book) throws ClassNotFoundException, SQLException {
		save("delete from tbl_book where bookId = ?", new Object[] {book.getId()});
	}
	
	public List<Book> readAllBooks() throws ClassNotFoundException, SQLException {
		return read("select * from tbl_book", null);
	}
	
	public List<Book> readBookFromId(Integer id) throws ClassNotFoundException, SQLException {
		return read("select * from tbl_book where bookId = ?", new Object[] {id});
	}
	
	public List<Book> readBooksWithCopiesFromBranch(Integer branchId) throws ClassNotFoundException, SQLException {
		return read("select tbl_book.bookId, tbl_book.title, tbl_book.authId, tbl_book.pubId from tbl_book inner join tbl_book_copies on tbl_book.bookId = tbl_book_copies.bookId where tbl_book_copies.branchId = ? AND noOfCopies > 0", new Object[] {branchId});
	}
	
	public List<Book> readBooksBorrowedFromCardNo(Integer cardNo) throws ClassNotFoundException, SQLException {
		return read("select tbl_book.bookId, tbl_book.title, tbl_book.authId, tbl_book.pubId from tbl_book inner join tbl_book_loans on tbl_book.bookId = tbl_book_loans.bookId where tbl_book_loans.cardNo = ?", new Object[] {cardNo});
	}
	
	public List<Book> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		List<Book> books = new ArrayList<>();
		while(rs.next()) {
			Book b = new Book();
			b.setId(rs.getInt("bookId"));
			b.setTitle(rs.getString("title"));
			b.setAuthorId(rs.getInt("authId"));
			b.setPublisherId(rs.getInt("pubId"));
			books.add(b);
		}
		return books;
		
	}
}
