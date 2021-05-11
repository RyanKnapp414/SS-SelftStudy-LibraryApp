package ss.self.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ss.self.lms.dao.*;
import ss.self.lms.entity.Book;
import ss.self.lms.entity.BookCopies;
import ss.self.lms.entity.Branch;

public class LibrarianService {
	
	Util util = new Util();

	public String updateBranch(Branch branch) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			BranchDAO brdao = new BranchDAO(conn);
			
			brdao.updateBranch(branch);
			
			conn.commit();
			return "Branch updated successfully!";
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			return "Branch could not be updated.";
		} finally {
			if (conn!=null ) {
				conn.close();
			}
		}
	}
	
	public String updateCopiesOfBook(BookCopies bookCopies) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			BookCopiesDAO bcdao = new BookCopiesDAO(conn);
			
			bcdao.updateBookCopies(bookCopies);
			
			conn.commit();
			return "Copies updated successfully!";
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			return "Copies could not be updated.";
		} finally {
			if (conn!=null ) {
				conn.close();
			}
		}
	}
	
	public List<BookCopies> noOfCopies(Integer bookId, Integer branchId) throws SQLException {
		Connection conn = null;
		List<BookCopies> bookCopies = new ArrayList<>();
		try {
			conn = util.getConnection();
			BookCopiesDAO bcdao = new BookCopiesDAO(conn);
			
			bookCopies = bcdao.readBookCopiesFromBookAndBranchId(bookId, branchId);
			
			conn.commit();
			return bookCopies;
		} catch (Exception e) {
			System.out.println("Copies could not be found.");
			e.printStackTrace();
			conn.rollback();
			return null;
		} finally {
			if (conn!=null ) {
				conn.close();
			}
		}
	}
	
	public List<Book> listOfBooks() throws SQLException{
		Connection conn = null;
		List<Book> books = new ArrayList<>();
		try {
			conn = util.getConnection();
			BookDAO bdao = new BookDAO(conn);
			
			books = bdao.readAllBooks();
			
			conn.commit();
			return books;
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			System.out.println("Books could not be found.");
			return null;
		} finally {
			if (conn!=null ) {
				conn.close();
			}
		}
	}
	
	public List<Branch> listOfBranches() throws SQLException{
		Connection conn = null;
		List<Branch> branches = new ArrayList<>();
		try {
			conn = util.getConnection();
			BranchDAO brdao = new BranchDAO(conn);
			
			branches = brdao.readAllBranches();
			
			conn.commit();
			return branches;
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			System.out.println("Branches could not be found.");
			return null;
		} finally {
			if (conn!=null ) {
				conn.close();
			}
		}
	}
	
}
