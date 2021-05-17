package ss.self.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ss.self.lms.dao.*;
import ss.self.lms.entity.Book;
import ss.self.lms.entity.BookCopies;
import ss.self.lms.entity.BookLoans;
import ss.self.lms.entity.Borrower;

public class BorrowerService {
	
	Util util = new Util();

	public List<Borrower> checkCardNo(Integer cardNo) throws SQLException {
		Connection conn = null;
		List<Borrower> borrowers = new ArrayList<>();
		try {
			conn = util.getConnection();
			BorrowerDAO bdao = new BorrowerDAO(conn);
			
			borrowers = bdao.readBorrowerFromId(cardNo);
			
			conn.commit();
			return borrowers;
		} catch (Exception e) {
			System.out.println("Error getting borrowers");
			e.printStackTrace();
			conn.rollback();
			return null;
		} finally {
			if (conn!=null ) {
				conn.close();
			}
		}
		
	}
	
	public List<Book> getBooksWithCopies(Integer branchId) throws SQLException {
		Connection conn = null;
		List<Book> books = new ArrayList<>();
		try {
			conn = util.getConnection();
			BookDAO bdao = new BookDAO(conn);
			
			books = bdao.readBooksWithCopiesFromBranch(branchId);
			
			conn.commit();
			return books;
		} catch (Exception e) {
			System.out.println("Error getting borrowers");
			e.printStackTrace();
			conn.rollback();
			return null;
		} finally {
			if (conn!=null ) {
				conn.close();
			}
		}
		
	}
	
	public List<Book> getBooksBorrowed(Integer cardNo, Integer branchId) throws SQLException {
		Connection conn = null;
		List<Book> books = new ArrayList<>();
		try {
			conn = util.getConnection();
			BookDAO bdao = new BookDAO(conn);
			
			books = bdao.readBooksBorrowedFromCardNo(cardNo, branchId);
			
			conn.commit();
			return books;
		} catch (Exception e) {
			System.out.println("Error getting borrowers");
			e.printStackTrace();
			conn.rollback();
			return null;
		} finally {
			if (conn!=null ) {
				conn.close();
			}
		}
		
	}
	
	public String checkOutBook(BookLoans bookLoan, BookCopies bookCopies) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			BookLoansDAO bldao = new BookLoansDAO(conn);
			BookCopiesDAO bcdao = new BookCopiesDAO(conn);
			
			bldao.addBookLoans(bookLoan);
			bcdao.updateBookCopies(bookCopies);
			
			conn.commit();
			return "Book loan added successfully";
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			return "Book loan failed";
		} finally {
			if (conn!=null ) {
				conn.close();
			}
		}
		
	}
	
	public String returnBook(BookLoans bookLoan, BookCopies bookCopies) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			BookLoansDAO bldao = new BookLoansDAO(conn);
			BookCopiesDAO bcdao = new BookCopiesDAO(conn);
			
			bldao.deleteBookLoans(bookLoan);
			bcdao.updateBookCopies(bookCopies);
			
			conn.commit();
			return "Book returned successfully";
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			return "Book return failed";
		} finally {
			if (conn!=null ) {
				conn.close();
			}
		}
	}
}
