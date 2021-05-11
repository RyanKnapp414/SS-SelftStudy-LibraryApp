/**
 * 
 */
package ss.self.lms.jdbc;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import ss.self.lms.entity.Author;
import ss.self.lms.entity.Book;
import ss.self.lms.entity.BookCopies;
import ss.self.lms.entity.BookLoans;
import ss.self.lms.entity.Borrower;
import ss.self.lms.entity.Branch;
import ss.self.lms.entity.Publisher;
import ss.self.lms.service.AdminService;
import ss.self.lms.service.BorrowerService;
import ss.self.lms.service.LibrarianService;

/**
 * @author Ryan
 *
 */
public class LibraryApp {
	static Scanner sc = new Scanner(System.in);

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		userSelection();
	}

	// select user type
	static void userSelection() {
		int selection;

		System.out.println("Welcome to the GCIT Library Management System. Which category of a user are you \n");
		System.out.println("1. Librarian");
		System.out.println("2. Administrator");
		System.out.println("3. Borrower \n");

		selection = sc.nextInt();

		switch (selection) {
		case 1:
			libStart();
			break;
		case 2:
			adminStart();
			break;
		case 3:
			borStart();
			break;
		default:
			System.out.println("Invalid Selection");
			break;
		}
	}

	// START OF LIBRARY MENUS
	static void libStart() {
		int selection;

		System.out.println("Librarian Selected \n");
		System.out.println("1. Branch Selection");
		System.out.println("2. Quit to previous \n");

		selection = sc.nextInt();

		switch (selection) {
		case 1:
			libBranchSelect();
			break;
		case 2:
			userSelection();
			break;
		default:
			System.out.println("Invalid Selection");
			break;
		}
	}

	// select branch
	static void libBranchSelect() {
		LibrarianService ls = new LibrarianService();
		int selection;
		int count = 1;

		List<Branch> branches = new ArrayList<>();
		try {
			branches = ls.listOfBranches();
		} catch (SQLException e) {
			System.out.println("Error getting list of branches");

		}

		System.out.println("Branch Selection \n");
		// loop through list of available branches from database
		for (Branch branch : branches) {
			System.out.println(count + ". " + branch.getBranchName() + ", " + branch.getBranchAddress());
			count++;
		}

		System.out.println((branches.size() + 1) + ". Quit to previous \n");
		selection = sc.nextInt(); // list position of selected branch

		// if selection is within the array
		if (selection >= 1 && selection <= branches.size()) {
			Branch selectBranch = branches.get(selection - 1);
			libModifyBranch(selectBranch); // pass id of selection
		} else if (selection == (branches.size() + 1)) {
			libStart();
		} else {
			System.out.println("Invalid Selection");
		}

	}

	// select update or add copies to selected branch
	static void libModifyBranch(Branch branch) {
		int selection;

		System.out.println("Branch Modification \n");
		System.out.println("1. Update the details of the Library ");
		System.out.println("2. Add copies of Book to the Branch");
		System.out.println("3. Quit to previous \n");

		selection = sc.nextInt();

		switch (selection) {
		case 1:
			libUpdateBranch(branch);
			break;
		case 2:
			libAddCopies(branch);
			break;
		case 3:
			libBranchSelect();
			break;
		default:
			System.out.println("Invalid Selection");
			break;
		}
	}

	// update branch table with inputs
	static void libUpdateBranch(Branch branch) {
		LibrarianService ls = new LibrarianService();
		Scanner scUpdate = new Scanner(System.in);

		System.out.println(
				"You have chosen to update the Branch with Branch Id: " + branch.getBranchId() + " and Branch Name: "
						+ branch.getBranchName() + ". Enter ‘quit’ at any prompt to cancel operation. \n");
		System.out.println("Please enter new branch name or enter N/A for no change:");
		// quit
		if (scUpdate.hasNext("quit")) {
			System.out.println("Quitting");
			System.exit(0);
		}
		// get input for name
		String newBranchName = scUpdate.nextLine();
		// if N/A set to old name
		if (newBranchName.equals("N/A")) {
			newBranchName = branch.getBranchName();
		}

		System.out.println("Please enter new branch address or enter N/A for no change:");
		// quit
		if (scUpdate.hasNext("quit")) {
			System.out.println("Quitting");
			System.exit(0);
		}
		// get input for address
		String newBranchAddress = scUpdate.nextLine();
		// if N/A set to old address
		if (newBranchAddress.equals("N/A")) {
			newBranchAddress = branch.getBranchAddress();
		}

		Branch updatedBranch = new Branch();

		updatedBranch.setBranchId(branch.getBranchId());
		updatedBranch.setBranchName(newBranchName);
		updatedBranch.setBranchAddress(newBranchAddress);
		// update library_branch and return to LibModifyBranch

		try {
			ls.updateBranch(updatedBranch);
			libModifyBranch(branch);
		} catch (SQLException e) {
			System.out.println("Error updating branch");

		}
	}

	// add copies to book_copies table
	static void libAddCopies(Branch branch) {
		LibrarianService ls = new LibrarianService();
		Scanner scCopies = new Scanner(System.in);
		int selection;
		int noOfCopies;
		int count = 1;
		List<Book> books = new ArrayList<>();
		List<BookCopies> bookCopies = new ArrayList<>();
		BookCopies selectCopies = new BookCopies();

		try {
			books = ls.listOfBooks();
		} catch (SQLException e) {
			System.out.println("Error getting books");

		}

		System.out.println("Pick the Book you want to add copies of to your branch: \n");
		// loop through list of books
		for (Book book : books) {
			System.out.println(count + ". " + book.getTitle());
			count++;
		}
		System.out.println((books.size() + 1) + ". Quit to previous \n");
		selection = scCopies.nextInt(); // list position of selected branch

		// if selection is within the array
		if (selection >= 1 && selection <= books.size()) {
			Book selectBook = books.get(selection - 1);
			try {
				// get current copies of book in branch
				bookCopies = ls.noOfCopies(selectBook.getId(), branch.getBranchId());
				selectCopies = bookCopies.get(0);
			} catch (SQLException e) {
				System.out.println("Error getting book copies");

			}
			System.out.println("Existing number of copies: " + selectCopies.getNoOfCopies());

			System.out.println("Enter new number of copies:");
			noOfCopies = scCopies.nextInt();

			BookCopies updatedCopies = new BookCopies();
			updatedCopies = selectCopies;
			updatedCopies.setNoOfCopies(noOfCopies);
			// update book_copies and return to LibModifyBranch
			try {
				ls.updateCopiesOfBook(updatedCopies);
				libModifyBranch(branch);
			} catch (SQLException e) {
				System.out.println("Error updating book copies");

			}
		} else if (selection == (books.size() + 1)) {
			libModifyBranch(branch);
		} else {
			System.out.println("Invalid Selection");
		}
	}
	// END OF LIBRARY MENUS

	// START OF BORROWER MENUS
	static void borStart() {
		BorrowerService bs = new BorrowerService();
		List<Borrower> borrowers = new ArrayList<>();
		Borrower borrower = new Borrower();
		int cardNo;

		System.out.println("Borrower Selected \n");
		System.out.println("Enter your Card Number:");

		cardNo = sc.nextInt();
		// search for card number if invalid try again
		try {
			borrowers = bs.checkCardNo(cardNo);
			if (borrowers.size() > 0) {
				borrower = borrowers.get(0);
				borTask(borrower);
			} else {
				System.out.println("Invalid Card Number");
			}
		} catch (SQLException e) {
			System.out.println("Error with checking card number");

		}
	}

	// select operation for borrower
	static void borTask(Borrower borrower) {
		int selection;

		System.out.println("Card Number Valid \n");
		System.out.println("1. Check out a book");
		System.out.println("2. Return a Book");
		System.out.println("3. Quit to previous \n");

		selection = sc.nextInt();

		switch (selection) {
		case 1:
			borCheckOutBranch(borrower);
			break;
		case 2:
			borReturnBranch(borrower);
			break;
		case 3:
			userSelection();
			break;
		default:
			System.out.println("Invalid Selection");
			break;
		}
	}

	// select branch to checkout from
	static void borCheckOutBranch(Borrower borrower) {
		LibrarianService ls = new LibrarianService();
		int selection;
		int count = 1;

		List<Branch> branches = new ArrayList<>();
		try {
			branches = ls.listOfBranches();
		} catch (SQLException e) {
			System.out.println("Error getting list of branches");

		}

		System.out.println("Pick the Branch you want to check out from: \n");
		// loop through list of available branches from database
		for (Branch branch : branches) {
			System.out.println(count + ". " + branch.getBranchName() + ", " + branch.getBranchAddress());
			count++;
		}
		System.out.println((branches.size() + 1) + ". Quit to previous \n");
		selection = sc.nextInt();

		// if selection is within the array
		if (selection >= 1 && selection <= branches.size()) {
			Branch selectBranch = branches.get(selection - 1);
			borCheckOutBook(borrower, selectBranch);
		} else if (selection == (branches.size() + 1)) {
			borTask(borrower);
		} else {
			System.out.println("Invalid Selection");
		}
	}

	// checkout book selected under borrower cardNo and set dateOut to now and
	// dueDate to one week later, reduce noOfCopies in branch by one
	static void borCheckOutBook(Borrower borrower, Branch branch) {
		LibrarianService ls = new LibrarianService();
		BorrowerService bs = new BorrowerService();
		int selection;
		int count = 1;
		List<Book> books = new ArrayList<>();
		List<BookCopies> bookCopies = new ArrayList<>();
		Book selectBook = new Book();
		BookLoans bookLoan = new BookLoans();
		BookCopies updatedBookCopies = new BookCopies();

		// find books that have copies in the branch selected
		try {
			books = bs.getBooksWithCopies(branch.getBranchId());
		} catch (SQLException e) {
			System.out.println("Error getting books");

		}

		System.out.println("Pick the Book you want to check out: \n");
		// loop through list of available books that have at least 1 copy in selected
		// branch from database
		for (Book book : books) {
			System.out.println(count + ". " + book.getTitle());
			count++;
		}
		System.out.println((books.size() + 1) + ". Quit to previous \n");
		selection = sc.nextInt(); // list position of selected branch

		// if selection is within the array
		if (selection >= 1 && selection <= books.size()) {
			// add to book loans, date out is today, due date is 1 week from today
			selectBook = books.get(selection - 1);
			bookLoan.setBookId(selectBook.getId());
			bookLoan.setBranchId(branch.getBranchId());
			bookLoan.setCardNo(borrower.getCardNo());
			bookLoan.setDateOut(LocalDate.now());
			bookLoan.setDueDate(LocalDate.now().plusDays(7));

			// get current number of copies of book in branch
			try {
				bookCopies = ls.noOfCopies(selectBook.getId(), branch.getBranchId());
			} catch (SQLException e1) {
				System.out.println("Error getting copies");
				e1.printStackTrace();
			}
			// set updatedCopies to current copies and reduce copies by 1
			updatedBookCopies = bookCopies.get(0);
			updatedBookCopies.setNoOfCopies(updatedBookCopies.getNoOfCopies() - 1);
			// checkout book and update copies
			try {
				String result = bs.checkOutBook(bookLoan, updatedBookCopies);
				System.out.println(result);
			} catch (SQLException e) {
				System.out.println("Error checking out book");

			}
		} else if (selection == (books.size() + 1)) {
			borTask(borrower);
		} else {
			System.out.println("Invalid Selection");
		}
	}

	// select branch to return book to
	static void borReturnBranch(Borrower borrower) {
		LibrarianService ls = new LibrarianService();
		int selection;
		int count = 1;

		List<Branch> branches = new ArrayList<>();
		try {
			branches = ls.listOfBranches();
		} catch (SQLException e) {
			System.out.println("Error getting list of branches");

		}

		System.out.println("Pick the Branch you want to return to: \n");
		// loop through list of available branches from database
		for (Branch branch : branches) {
			System.out.println(count + ". " + branch.getBranchName() + ", " + branch.getBranchAddress());
			count++;
		}
		System.out.println((branches.size() + 1) + ". Quit to previous \n");
		selection = sc.nextInt();

		// if selection is within the array
		if (selection >= 1 && selection <= branches.size()) {
			Branch selectBranch = branches.get(selection - 1);
			borReturnBook(borrower, selectBranch);
		} else if (selection == (branches.size() + 1)) {
			borTask(borrower);
		} else {
			System.out.println("Invalid Selection");
		}
	}

	// delete book loan from table where borrower cardNo, branchId, and bookId are
	// selected and increment copies by one
	static void borReturnBook(Borrower borrower, Branch branch) {
		LibrarianService ls = new LibrarianService();
		BorrowerService bs = new BorrowerService();
		int selection;
		int count = 1;
		List<Book> books = new ArrayList<>();
		List<BookCopies> bookCopies = new ArrayList<>();
		Book selectBook = new Book();
		BookLoans bookLoan = new BookLoans();
		BookCopies updatedBookCopies = new BookCopies();

		// find books loaned to borrower cardNo
		try {
			books = bs.getBooksBorrowed(borrower.getCardNo());
		} catch (SQLException e) {
			System.out.println("Error getting books");

		}

		System.out.println("Pick the Book you want to return: \n");
		// loop through list of available books from cardNo from book_loans database
		for (Book book : books) {
			System.out.println(count + ". " + book.getTitle());
			count++;
		}
		System.out.println((books.size() + 1) + ". Quit to previous \n");
		selection = sc.nextInt();

		// if selection is within the array
		if (selection >= 1 && selection <= books.size()) {
			// set bookLoan bookId, branchId, and cardNo
			selectBook = books.get(selection - 1);
			bookLoan.setBookId(selectBook.getId());
			bookLoan.setBranchId(branch.getBranchId());
			bookLoan.setCardNo(borrower.getCardNo());

			// find copies of book in branch
			try {
				bookCopies = ls.noOfCopies(selectBook.getId(), branch.getBranchId());
			} catch (SQLException e1) {
				System.out.println("Error getting copies");
				e1.printStackTrace();
			}
			// set updatedBookCopies to current copies and increment by one
			updatedBookCopies = bookCopies.get(0);
			updatedBookCopies.setNoOfCopies(updatedBookCopies.getNoOfCopies() + 1);

			// remove from book_loans
			try {
				String result = bs.returnBook(bookLoan, updatedBookCopies);
				System.out.println(result);
			} catch (SQLException e) {
				System.out.println("Error returning book");

			}

		} else if (selection == 6) {
			borTask(borrower);
		} else {
			System.out.println("Invalid Selection");
		}
	}
	// END OF BORROWER MENUS

	// START OF ADMIN MENUS
	static void adminStart() {
		int selection;

		System.out.println("Administrator Selected\nWhich table would you like to modify: \n");
		System.out.println("1. Book");
		System.out.println("2. Author");
		System.out.println("3. Publisher");
		System.out.println("4. Library Branch");
		System.out.println("5. Borrower");
		System.out.println("6. Book Loan");
		System.out.println("7. Quit to previous \n");

		selection = sc.nextInt();

		switch (selection) {
		case 1:
			adminBook();
			break;
		case 2:
			adminAuthor();
			break;
		case 3:
			adminPublisher();
			break;
		case 4:
			adminBranch();
			break;
		case 5:
			adminBorrower();
			break;
		case 6:
			adminBookLoan();
			break;
		case 7:
			userSelection();
			break;
		default:
			System.out.println("Invalid Selection");
			break;
		}
	}

	// selection menu for book table
	static void adminBook() {
		int selection;

		System.out.println("Book Table Selected\nWhich action would you like to perform: \n");
		System.out.println("1. Add");
		System.out.println("2. Update");
		System.out.println("3. Delete");
		System.out.println("4. Quit to previous \n");

		selection = sc.nextInt();

		switch (selection) {
		case 1:
			adminBookAdd();
			break;
		case 2:
			adminBookUpdate();
			break;
		case 3:
			adminBookDelete();
			break;
		case 4:
			adminStart();
			break;
		default:
			System.out.println("Invalid Selection");
			break;
		}
	}

	// add book to book table
	static void adminBookAdd() {
		LibrarianService ls = new LibrarianService();
		AdminService as = new AdminService();
		Scanner scBookAdd = new Scanner(System.in);
		int selection;
		Book bookAdd = new Book();
		List<Author> authors = new ArrayList<>();
		List<Publisher> publishers = new ArrayList<>();
		Publisher selectPublisher = new Publisher();
		Author selectAuthor = new Author();
		int bookId = 1;
		int count = 1;

		// set bookId to number of books in table + 1
		try {
			bookId = ls.listOfBooks().size() + 1;
		} catch (SQLException e) {
			System.out.println("Error getting bookId");

		}
		bookAdd.setId(bookId);

		// get book title from input
		System.out.println("Book Title:");
		String title = scBookAdd.nextLine();
		bookAdd.setTitle(title);

		// get list of authors
		try {
			authors = as.listOfAuthors();
		} catch (SQLException e) {
			System.out.println("Error getting authors");

		}
		// select author from list
		System.out.println("Select Author: \n");
		// loop through list of authors
		for (Author author : authors) {
			System.out.println(count + ". " + author.getName());
			count++;
		}
		selection = scBookAdd.nextInt();

		// if selection is in array
		if (selection >= 1 && selection <= authors.size()) {
			selectAuthor = authors.get(selection - 1);
		} else {
			System.out.println("Invalid Selection");
		}

		// get list of publishers
		try {
			publishers = as.listOfPublishers();
		} catch (SQLException e) {
			System.out.println("Error getting publishers");

		}
		// select publisher from list
		System.out.println("Select Publisher:  \n");
		// loop through list of publishers
		count = 1;
		for (Publisher publisher : publishers) {
			System.out.println(count + ". " + publisher.getName());
			count++;
		}
		selection = scBookAdd.nextInt();

		// if selection is in array
		if (selection >= 1 && selection <= publishers.size()) {
			selectPublisher = publishers.get(selection - 1);
		} else {
			System.out.println("Invalid Selection");
		}

		// set bookAdd values
		bookAdd.setAuthorId(selectAuthor.getId());
		bookAdd.setPublisherId(selectPublisher.getId());

		// add book to table
		try {
			System.out.println(as.addBook(bookAdd));
		} catch (SQLException e) {
			System.out.println("Error adding book");

		}
	}

	// update book table
	static void adminBookUpdate() {
		LibrarianService ls = new LibrarianService();
		AdminService as = new AdminService();
		Scanner scBookUpdate = new Scanner(System.in);
		int selection;
		int count = 1;
		String title;
		Book bookUpdate = new Book();
		List<Book> books = new ArrayList<>();
		List<Author> authors = new ArrayList<>();
		List<Publisher> publishers = new ArrayList<>();
		Publisher selectPublisher = new Publisher();
		Author selectAuthor = new Author();

		// select book from list of books
		System.out.println("Select which book to update: \n");

		// get list of books
		try {
			books = ls.listOfBooks();
		} catch (SQLException e) {
			System.out.println("Error getting books");

		}
		for (Book book : books) {
			System.out.println(count + ". " + book.getTitle());
			count++;
		}

		selection = sc.nextInt();

		// if selection is in array
		if (selection >= 1 && selection <= books.size()) {
			bookUpdate = books.get(selection - 1);
		} else {
			System.out.println("Invalid Selection");
		}

		// get title from input
		System.out.println("Book Title:");
		title = scBookUpdate.nextLine();
		bookUpdate.setTitle(title);

		// get list of authors
		try {
			authors = as.listOfAuthors();
		} catch (SQLException e) {
			System.out.println("Error getting authors");

		}
		System.out.println("Select Author: \n");
		// loop through list of authors
		count = 1;
		for (Author author : authors) {
			System.out.println(count + ". " + author.getName());
			count++;
		}
		selection = sc.nextInt();

		// if selection is in array
		if (selection >= 1 && selection <= authors.size()) {
			selectAuthor = authors.get(selection - 1);
		} else {
			System.out.println("Invalid Selection");
		}

		// get list of publishers
		try {
			publishers = as.listOfPublishers();
		} catch (SQLException e) {
			System.out.println("Error getting publishers");

		}
		System.out.println("Select Publisher:  \n");
		// loop through list of publishers
		count = 1;
		for (Publisher publisher : publishers) {
			System.out.println(count + ". " + publisher.getName());
			count++;
		}
		selection = sc.nextInt();

		// if selection is in array
		if (selection >= 1 && selection <= publishers.size()) {
			selectPublisher = publishers.get(selection - 1);
		} else {
			System.out.println("Invalid Selection");
		}

		// set bookUpdate values
		bookUpdate.setAuthorId(selectAuthor.getId());
		bookUpdate.setPublisherId(selectPublisher.getId());

		// update book table
		try {
			System.out.println(as.updateBook(bookUpdate));
		} catch (SQLException e) {
			System.out.println("Error updating book");

		}

	}

	// delete from book table
	static void adminBookDelete() {
		LibrarianService ls = new LibrarianService();
		AdminService as = new AdminService();
		int selection;
		int count = 1;
		List<Book> books = new ArrayList<>();
		Book bookDelete = new Book();

		System.out.println("Select which book to delete: \n");

		// get list of books
		try {
			books = ls.listOfBooks();
		} catch (SQLException e) {
			System.out.println("Error getting books");

		}
		for (Book book : books) {
			System.out.println(count + ". " + book.getTitle());
			count++;
		}

		selection = sc.nextInt();

		// if selection is in array
		if (selection >= 1 && selection <= books.size()) {
			bookDelete = books.get(selection - 1);
		} else {
			System.out.println("Invalid Selection");
		}

		// delete from book table
		try {
			System.out.println(as.deleteBook(bookDelete));
		} catch (SQLException e) {
			System.out.println("Error deleting book");

		}

	}

	// selection menu for author table
	static void adminAuthor() {
		int selection;

		System.out.println("Author Table Selected\nWhich action would you like to perform: \n");
		System.out.println("1. Add");
		System.out.println("2. Update");
		System.out.println("3. Delete");
		System.out.println("4. Quit to previous \n");

		selection = sc.nextInt();

		switch (selection) {
		case 1:
			adminAuthorAdd();
			break;
		case 2:
			adminAuthorUpdate();
			break;
		case 3:
			adminAuthorDelete();
			break;
		case 4:
			adminStart();
			break;
		default:
			System.out.println("Invalid Selection");
			break;
		}
	}

	// add author to author table
	static void adminAuthorAdd() {
		AdminService as = new AdminService();
		Scanner scAuthorAdd = new Scanner(System.in);
		Author authorAdd = new Author();
		int authorId = 1;

		// set authorId to number of authors in table + 1
		try {
			authorId = as.listOfAuthors().size() + 1;
		} catch (SQLException e) {
			System.out.println("Error getting AuthorId");

		}
		authorAdd.setId(authorId);

		// get author name from input
		System.out.println("Author Name:");
		String name = scAuthorAdd.nextLine();
		authorAdd.setName(name);

		// add to author table
		try {
			System.out.println(as.addAuthor(authorAdd));
		} catch (SQLException e) {
			System.out.println("Error adding author");

		}
	}

	// update author table
	static void adminAuthorUpdate() {
		AdminService as = new AdminService();
		Scanner scAuthorUpdate = new Scanner(System.in);
		int selection;
		int count = 1;
		String name;
		Author authorUpdate = new Author();
		List<Author> authors = new ArrayList<>();

		System.out.println("Select which author to update: \n");

		// get list of authors
		try {
			authors = as.listOfAuthors();
		} catch (SQLException e) {
			System.out.println("Error getting authors");

		}
		for (Author author : authors) {
			System.out.println(count + ". " + author.getName());
			count++;
		}

		selection = sc.nextInt();

		// if selection is in array
		if (selection >= 1 && selection <= authors.size()) {
			authorUpdate = authors.get(selection - 1);
		} else {
			System.out.println("Invalid Selection");
		}

		// get author name from input
		System.out.println("Author Name:");
		name = scAuthorUpdate.nextLine();
		authorUpdate.setName(name);

		// update author table
		try {
			System.out.println(as.updateAuthor(authorUpdate));
		} catch (SQLException e) {
			System.out.println("Error updating author");

		}

	}

	// delete from author table
	static void adminAuthorDelete() {
		LibrarianService ls = new LibrarianService();
		AdminService as = new AdminService();
		int selection;
		int count = 1;
		List<Author> authors = new ArrayList<>();
		Author authorDelete = new Author();

		System.out.println("Select which author to delete: \n");

		// get list of authors
		try {
			authors = as.listOfAuthors();
		} catch (SQLException e) {
			System.out.println("Error getting authors");

		}
		for (Author author : authors) {
			System.out.println(count + ". " + author.getName());
			count++;
		}

		selection = sc.nextInt();

		// if selection is in array
		if (selection >= 1 && selection <= authors.size()) {
			authorDelete = authors.get(selection - 1);
		} else {
			System.out.println("Invalid Selection");
		}

		// delete from author table
		try {
			System.out.println(as.deleteAuthor(authorDelete));
		} catch (SQLException e) {
			System.out.println("Error deleting author");

		}

	}

	// selection menu for publisher table
	static void adminPublisher() {
		int selection;

		System.out.println("Publisher Table Selected\nWhich action would you like to perform: \n");
		System.out.println("1. Add");
		System.out.println("2. Update");
		System.out.println("3. Delete");
		System.out.println("4. Quit to previous \n");

		selection = sc.nextInt();

		switch (selection) {
		case 1:
			adminPublisherAdd();
			break;
		case 2:
			adminPublisherUpdate();
			break;
		case 3:
			adminPublisherDelete();
			break;
		case 4:
			adminStart();
			break;
		default:
			System.out.println("Invalid Selection");
			break;
		}
	}

	// add publisher to publisher table
	static void adminPublisherAdd() {
		AdminService as = new AdminService();
		Scanner scPublisherAdd = new Scanner(System.in);
		Publisher publisherAdd = new Publisher();
		int publisherId = 1;

		// set publisherId to number of publishers in table + 1
		try {
			publisherId = as.listOfPublishers().size() + 1;
		} catch (SQLException e) {
			System.out.println("Error getting PublisherId");

		}
		publisherAdd.setId(publisherId);

		// get inputs
		System.out.println("Publisher Name:");
		String name = scPublisherAdd.nextLine();
		publisherAdd.setName(name);

		System.out.println("Publisher Address:");
		String address = scPublisherAdd.nextLine();
		publisherAdd.setAddress(address);

		System.out.println("Publisher Phone:");
		String phone = scPublisherAdd.nextLine();
		publisherAdd.setPhone(phone);

		// add to publisher table
		try {
			System.out.println(as.addPublisher(publisherAdd));
		} catch (SQLException e) {
			System.out.println("Error adding publisher");

		}
	}

	// update publisher table
	static void adminPublisherUpdate() {
		AdminService as = new AdminService();
		Scanner scPublisherUpdate = new Scanner(System.in);
		int selection;
		int count = 1;
		String name;
		Publisher publisherUpdate = new Publisher();
		List<Publisher> publishers = new ArrayList<>();

		System.out.println("Select which publisher to update: \n");

		// get list of publishers
		try {
			publishers = as.listOfPublishers();
		} catch (SQLException e) {
			System.out.println("Error getting publishers");

		}
		for (Publisher publisher : publishers) {
			System.out.println(count + ". " + publisher.getName());
			count++;
		}

		selection = sc.nextInt();

		// if selection is in array
		if (selection >= 1 && selection <= publishers.size()) {
			publisherUpdate = publishers.get(selection - 1);
		} else {
			System.out.println("Invalid Selection");
		}

		// get inputs
		System.out.println("Publisher Name:");
		name = scPublisherUpdate.nextLine();
		publisherUpdate.setName(name);

		System.out.println("Publisher Address:");
		String address = scPublisherUpdate.nextLine();
		publisherUpdate.setAddress(address);

		System.out.println("Publisher Phone:");
		String phone = scPublisherUpdate.nextLine();
		publisherUpdate.setPhone(phone);

		// update publisher table
		try {
			System.out.println(as.updatePublisher(publisherUpdate));
		} catch (SQLException e) {
			System.out.println("Error updating publisher");

		}

	}

	// delete from publisher table
	static void adminPublisherDelete() {
		AdminService as = new AdminService();
		int selection;
		int count = 1;
		List<Publisher> publishers = new ArrayList<>();
		Publisher publisherDelete = new Publisher();

		System.out.println("Select which publisher to delete: \n");

		// get list of publishers
		try {
			publishers = as.listOfPublishers();
		} catch (SQLException e) {
			System.out.println("Error getting publishers");

		}
		for (Publisher publisher : publishers) {
			System.out.println(count + ". " + publisher.getName());
			count++;
		}

		selection = sc.nextInt();

		// if selection is in array
		if (selection >= 1 && selection <= publishers.size()) {
			publisherDelete = publishers.get(selection - 1);
		} else {
			System.out.println("Invalid Selection");
		}

		// delete from publisher table
		try {
			System.out.println(as.deletePublisher(publisherDelete));
		} catch (SQLException e) {
			System.out.println("Error deleting publisher");

		}
	}

	// selection menu for branch table
	static void adminBranch() {
		int selection;

		System.out.println("Branch Table Selected\nWhich action would you like to perform: \n");
		System.out.println("1. Add");
		System.out.println("2. Update");
		System.out.println("3. Delete");
		System.out.println("4. Quit to previous \n");

		selection = sc.nextInt();

		switch (selection) {
		case 1:
			adminBranchAdd();
			break;
		case 2:
			adminBranchUpdate();
			break;
		case 3:
			adminBranchDelete();
			break;
		case 4:
			adminStart();
			break;
		default:
			System.out.println("Invalid Selection");
			break;
		}
	}

	// add branch to branch table
	static void adminBranchAdd() {
		LibrarianService ls = new LibrarianService();
		AdminService as = new AdminService();
		Scanner scBranchAdd = new Scanner(System.in);
		Branch branchAdd = new Branch();
		int branchId = 1;

		// set branchId to number of branches in table + 1
		try {
			branchId = ls.listOfBranches().size() + 1;
		} catch (SQLException e) {
			System.out.println("Error getting BranchId");

		}
		branchAdd.setBranchId(branchId);

		// get inputs
		System.out.println("Branch Name:");
		String name = scBranchAdd.nextLine();
		branchAdd.setBranchName(name);

		System.out.println("Branch Address:");
		String address = scBranchAdd.nextLine();
		branchAdd.setBranchAddress(address);

		// add to branch table
		try {
			System.out.println(as.addBranch(branchAdd));
		} catch (SQLException e) {
			System.out.println("Error adding branch");

		}
	}

	// update branch in branch table
	static void adminBranchUpdate() {
		LibrarianService ls = new LibrarianService();
		AdminService as = new AdminService();
		Scanner scBranchUpdate = new Scanner(System.in);
		int selection;
		int count = 1;
		String name;
		Branch branchUpdate = new Branch();
		List<Branch> branches = new ArrayList<>();

		System.out.println("Select which branch to update: \n");

		// get list of branches
		try {
			branches = ls.listOfBranches();
		} catch (SQLException e) {
			System.out.println("Error getting branches");

		}
		for (Branch branch : branches) {
			System.out.println(count + ". " + branch.getBranchName());
			count++;
		}

		selection = sc.nextInt();

		// if selection is in array
		if (selection >= 1 && selection <= branches.size()) {
			branchUpdate = branches.get(selection - 1);
		} else {
			System.out.println("Invalid Selection");
		}

		// get inputs
		System.out.println("Branch Name:");
		name = scBranchUpdate.nextLine();
		branchUpdate.setBranchName(name);

		System.out.println("Branch Address:");
		String address = scBranchUpdate.nextLine();
		branchUpdate.setBranchAddress(address);

		// update branch table
		try {
			System.out.println(as.updateBranch(branchUpdate));
		} catch (SQLException e) {
			System.out.println("Error updating branch");

		}

	}

	// delete from branch table
	static void adminBranchDelete() {
		LibrarianService ls = new LibrarianService();
		AdminService as = new AdminService();
		int selection;
		int count = 1;
		List<Branch> branches = new ArrayList<>();
		Branch branchDelete = new Branch();

		System.out.println("Select which branch to delete: \n");

		// get list of branches
		try {
			branches = ls.listOfBranches();
		} catch (SQLException e) {
			System.out.println("Error getting branches");

		}
		for (Branch branch : branches) {
			System.out.println(count + ". " + branch.getBranchName());
			count++;
		}

		selection = sc.nextInt();

		// if selection is in array
		if (selection >= 1 && selection <= branches.size()) {
			branchDelete = branches.get(selection - 1);
		} else {
			System.out.println("Invalid Selection");
		}

		// delete from branch table
		try {
			System.out.println(as.deleteBranch(branchDelete));
		} catch (SQLException e) {
			System.out.println("Error deleting branch");

		}

	}

	// selection menu for borrower table
	static void adminBorrower() {
		int selection;

		System.out.println("Borrower Table Selected\nWhich action would you like to perform: \n");
		System.out.println("1. Add");
		System.out.println("2. Update");
		System.out.println("3. Delete");
		System.out.println("4. Quit to previous \n");

		selection = sc.nextInt();

		switch (selection) {
		case 1:
			adminBorrowerAdd();
			break;
		case 2:
			adminBorrowerUpdate();
			break;
		case 3:
			adminBorrowerDelete();
			break;
		case 4:
			adminStart();
			break;
		default:
			System.out.println("Invalid Selection");
			break;
		}
	}

	// add borrower to borrower table
	static void adminBorrowerAdd() {
		AdminService as = new AdminService();
		Scanner scBorrowerAdd = new Scanner(System.in);
		Borrower borrowerAdd = new Borrower();

		// get inputs
		System.out.println("Borrower CardNo:");
		int cardNo = sc.nextInt();
		borrowerAdd.setCardNo(cardNo);

		System.out.println("Borrower Name:");
		String name = scBorrowerAdd.nextLine();
		borrowerAdd.setName(name);

		System.out.println("Borrower Address:");
		String address = scBorrowerAdd.nextLine();
		borrowerAdd.setAddress(address);

		System.out.println("Borrower Phone:");
		String phone = scBorrowerAdd.nextLine();
		borrowerAdd.setPhone(phone);

		// add to borrower table
		try {
			System.out.println(as.addBorrower(borrowerAdd));
		} catch (SQLException e) {
			System.out.println("Error adding borrower");

		}
	}

	// update borrower table
	static void adminBorrowerUpdate() {
		AdminService as = new AdminService();
		Scanner scBorrowerUpdate = new Scanner(System.in);
		int selection;
		int count = 1;
		Borrower borrowerUpdate = new Borrower();
		List<Borrower> borrowers = new ArrayList<>();

		System.out.println("Select which borrower to update: \n");

		// get list of borrowers
		try {
			borrowers = as.listOfBorrowers();
		} catch (SQLException e) {
			System.out.println("Error getting borrowers");

		}
		for (Borrower borrower : borrowers) {
			System.out.println(count + ". " + borrower.getName());
			count++;
		}

		selection = sc.nextInt();

		// if selection is in array
		if (selection >= 1 && selection <= borrowers.size()) {
			borrowerUpdate = borrowers.get(selection - 1);
		} else {
			System.out.println("Invalid Selection");
		}

		// get inputs
		System.out.println("Borrower Name:");
		String name = scBorrowerUpdate.nextLine();
		borrowerUpdate.setName(name);

		System.out.println("Borrower Address:");
		String address = scBorrowerUpdate.nextLine();
		borrowerUpdate.setAddress(address);

		System.out.println("Borrower Phone:");
		String phone = scBorrowerUpdate.nextLine();
		borrowerUpdate.setPhone(phone);

		// update borrower table
		try {
			System.out.println(as.updateBorrower(borrowerUpdate));
		} catch (SQLException e) {
			System.out.println("Error updating borrower");

		}

	}

	// delete from borrower table
	static void adminBorrowerDelete() {
		AdminService as = new AdminService();
		int selection;
		int count = 1;
		List<Borrower> borrowers = new ArrayList<>();
		Borrower borrowerDelete = new Borrower();

		System.out.println("Select which borrower to delete: \n");

		// get list of borrowers
		try {
			borrowers = as.listOfBorrowers();
		} catch (SQLException e) {
			System.out.println("Error getting borrowers");

		}
		for (Borrower borrower : borrowers) {
			System.out.println(count + ". " + borrower.getName());
			count++;
		}

		selection = sc.nextInt();

		// if selection is in array
		if (selection >= 1 && selection <= borrowers.size()) {
			borrowerDelete = borrowers.get(selection - 1);
		} else {
			System.out.println("Invalid Selection");
		}

		// delete from borrower table
		try {
			System.out.println(as.deleteBorrower(borrowerDelete));
		} catch (SQLException e) {
			System.out.println("Error deleting borrower");

		}

	}

	// update due date of book loan
	static void adminBookLoan() {
		AdminService as = new AdminService();
		Scanner scBookLoan = new Scanner(System.in);
		int selection;
		int count = 1;
		List<BookLoans> bookLoans = new ArrayList<>();
		BookLoans bookLoan = new BookLoans();

		System.out.println("Book Loan Table Selected\nOver-ride due date for which loan: \n");

		// get list of book loans
		try {
			bookLoans = as.listOfBookLoans();
		} catch (SQLException e) {
			System.out.println("Error getting book loans");
		}

		for (BookLoans loan : bookLoans) {
			System.out.println(count + ". CardNo:" + loan.getCardNo() + ", OUT: " + loan.getDateOut() + ", DUE: "
					+ loan.getDueDate());
			count++;
		}
		selection = sc.nextInt();

		// if selection is in array
		if (selection >= 1 && selection <= bookLoans.size()) {
			bookLoan = bookLoans.get(selection - 1);
		} else {
			System.out.println("Invalid Selection");
		}
		// get input of new due date
		System.out.println("Enter new due date in yyyy-MM-dd format:");
		String dateString = scBookLoan.nextLine();
		LocalDate date = LocalDate.parse(dateString);
		bookLoan.setDueDate(date);

		// update book loans table
		try {
			System.out.println(as.updateBookLoans(bookLoan));
		} catch (SQLException e) {
			System.out.println("Error updating book loan");

		}

	}
	// END OF ADMIN MENUS
}
