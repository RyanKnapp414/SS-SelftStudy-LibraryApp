package ss.self.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ss.self.lms.dao.*;
import ss.self.lms.entity.Author;
import ss.self.lms.entity.Book;
import ss.self.lms.entity.BookLoans;
import ss.self.lms.entity.Borrower;
import ss.self.lms.entity.Branch;
import ss.self.lms.entity.Publisher;

public class AdminService {
	
	Util util = new Util();

	public String addBook(Book book) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			BookDAO bdao = new BookDAO(conn);
			
			bdao.addBook(book);
			
			conn.commit();
			return "Book added successfully!";
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			return "Book could not be added.";
		} finally {
			if (conn!=null ) {
				conn.close();
			}
		}
		
	}
	
	public String updateBook(Book book) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			BookDAO bdao = new BookDAO(conn);
			
			bdao.updateBook(book);
			
			conn.commit();
			return "Book updated successfully!";
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			return "Book could not be updated.";
		} finally {
			if (conn!=null ) {
				conn.close();
			}
		}
		
	}
	
	public String deleteBook(Book book) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			BookDAO bdao = new BookDAO(conn);
			
			bdao.deleteBook(book);
			
			conn.commit();
			return "Book deleted successfully!";
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			return "Book could not be deleted.";
		} finally {
			if (conn!=null ) {
				conn.close();
			}
		}
		
	}
	
	public String addAuthor(Author author) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			AuthorDAO adao = new AuthorDAO(conn);
			
			adao.addAuthor(author);
			
			conn.commit();
			return "Author added successfully!";
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			return "Author could not be added.";
		} finally {
			if (conn!=null ) {
				conn.close();
			}
		}
		
	}
	
	public String updateAuthor(Author author) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			AuthorDAO adao = new AuthorDAO(conn);
			
			adao.updateAuthor(author);
			
			conn.commit();
			return "Author updated successfully!";
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			return "Author could not be updated.";
		} finally {
			if (conn!=null ) {
				conn.close();
			}
		}
		
	}
	
	public String deleteAuthor(Author author) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			AuthorDAO adao = new AuthorDAO(conn);
			
			adao.deleteAuthor(author);
			
			conn.commit();
			return "Author deleted successfully!";
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			return "Author could not be deleted.";
		} finally {
			if (conn!=null ) {
				conn.close();
			}
		}
		
	}
	
	public String addPublisher(Publisher publisher) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			PublisherDAO pdao = new PublisherDAO(conn);
			
			pdao.addPublisher(publisher);
			
			conn.commit();
			return "Publisher added successfully!";
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			return "Publisher could not be added.";
		} finally {
			if (conn!=null ) {
				conn.close();
			}
		}
		
	}
	
	public String updatePublisher(Publisher publisher) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			PublisherDAO pdao = new PublisherDAO(conn);
			
			pdao.updatePublisher(publisher);
			
			conn.commit();
			return "Publisher updated successfully!";
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			return "Publisher could not be updated.";
		} finally {
			if (conn!=null ) {
				conn.close();
			}
		}
		
	}
	
	public String deletePublisher(Publisher publisher) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			PublisherDAO pdao = new PublisherDAO(conn);
			
			pdao.deletePublisher(publisher);
			
			conn.commit();
			return "Publisher deleted successfully!";
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			return "Publisher could not be deleted.";
		} finally {
			if (conn!=null ) {
				conn.close();
			}
		}
		
	}

	public String addBranch(Branch branch) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			BranchDAO brdao = new BranchDAO(conn);
			
			brdao.addBranch(branch);
			
			conn.commit();
			return "Branch added successfully!";
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			return "Branch could not be added.";
		} finally {
			if (conn!=null ) {
				conn.close();
			}
		}
		
	}
	
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
	
	public String deleteBranch(Branch branch) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			BranchDAO brdao = new BranchDAO(conn);
			
			brdao.deleteBranch(branch);
			
			conn.commit();
			return "Branch deleted successfully!";
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			return "Branch could not be deleted.";
		} finally {
			if (conn!=null ) {
				conn.close();
			}
		}
		
	}
	
	public String addBorrower(Borrower borrower) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			BorrowerDAO bodao = new BorrowerDAO(conn);
			
			bodao.addBorrower(borrower);
			
			conn.commit();
			return "Borrower added successfully!";
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			return "Borrower could not be added.";
		} finally {
			if (conn!=null ) {
				conn.close();
			}
		}
		
	}
	
	public String updateBorrower(Borrower borrower) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			BorrowerDAO bodao = new BorrowerDAO(conn);
			
			bodao.updateBorrower(borrower);
			
			conn.commit();
			return "Borrower updated successfully!";
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			return "Borrower could not be updated.";
		} finally {
			if (conn!=null ) {
				conn.close();
			}
		}
		
	}
	
	public String deleteBorrower(Borrower borrower) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			BorrowerDAO bodao = new BorrowerDAO(conn);
			
			bodao.deleteBorrower(borrower);
			
			conn.commit();
			return "Borrower deleted successfully!";
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			return "Borrower could not be deleted.";
		} finally {
			if (conn!=null ) {
				conn.close();
			}
		}
		
	}
	
	public String updateBookLoans(BookLoans bookLoans) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			BookLoansDAO bldao = new BookLoansDAO(conn);
			
			bldao.updateBookLoans(bookLoans);
			
			conn.commit();
			return "BookLoans updated successfully!";
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			return "BookLoans could not be updated.";
		} finally {
			if (conn!=null ) {
				conn.close();
			}
		}
		
	}
	
	public List<Author> listOfAuthors() throws SQLException{
		Connection conn = null;
		List<Author> authors = new ArrayList<>();
		try {
			conn = util.getConnection();
			AuthorDAO adao = new AuthorDAO(conn);
			
			authors = adao.readAllAuthors();
			
			conn.commit();
			return authors;
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			System.out.println("Authors could not be found.");
			return null;
		} finally {
			if (conn!=null ) {
				conn.close();
			}
		}
	}
	
	public List<Publisher> listOfPublishers() throws SQLException{
		Connection conn = null;
		List<Publisher> publishers = new ArrayList<>();
		try {
			conn = util.getConnection();
			PublisherDAO pdao = new PublisherDAO(conn);
			
			publishers = pdao.readAllPublishers();
			
			conn.commit();
			return publishers;
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			System.out.println("Publishers could not be found.");
			return null;
		} finally {
			if (conn!=null ) {
				conn.close();
			}
		}
	}
	
	public List<Borrower> listOfBorrowers() throws SQLException{
		Connection conn = null;
		List<Borrower> borrowers = new ArrayList<>();
		try {
			conn = util.getConnection();
			BorrowerDAO bodao = new BorrowerDAO(conn);
			
			borrowers = bodao.readAllBorrowers();
			
			conn.commit();
			return borrowers;
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			System.out.println("Borrowers could not be found.");
			return null;
		} finally {
			if (conn!=null ) {
				conn.close();
			}
		}
	}
	
	public List<BookLoans> listOfBookLoans() throws SQLException{
		Connection conn = null;
		List<BookLoans> bookLoans = new ArrayList<>();
		try {
			conn = util.getConnection();
			BookLoansDAO bldao = new BookLoansDAO(conn);
			
			bookLoans = bldao.readAllBookLoans();
			
			conn.commit();
			return bookLoans;
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			System.out.println("BookLoans could not be found.");
			return null;
		} finally {
			if (conn!=null ) {
				conn.close();
			}
		}
	}
}
