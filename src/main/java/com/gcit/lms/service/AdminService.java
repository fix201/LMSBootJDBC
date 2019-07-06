package com.gcit.lms.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.BookLoan;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.Genre;
import com.gcit.lms.entity.LibraryBranch;
import com.gcit.lms.entity.Publisher;

@RestController
public class AdminService extends GeneralService {

	@Transactional
	@RequestMapping(value = "/admin/readAuthors", method = RequestMethod.GET, produces = "application/json")
	public List<Author> readAuthors(@RequestParam(value = "authorName") String authorName) {
		return readAuthorsGS(authorName);
	}

	@Transactional
	@RequestMapping(value = "/admin/readBooks", method = RequestMethod.GET, produces = "application/json")
	public List<Book> readBooks(@RequestParam(value = "title") String title) {
		return readBooksGS(title);
	}

	@Transactional
	@RequestMapping(value = "/admin/readLibraryBranches", method = RequestMethod.GET, produces = "application/json")
	public List<LibraryBranch> readLibraryBranches(@RequestParam(value = "branchName") String branchName) {
		return readBranchesGS(branchName);
	}

	@Transactional
	@RequestMapping(value = "/admin/readGenres", method = RequestMethod.GET, produces = "application/json")
	public List<Genre> readGenres(@RequestParam(value = "genreName") String genreName) {
		return readGenresGS(genreName);
	}

	@Transactional
	@RequestMapping(value = "/admin/readBorrowers", method = RequestMethod.GET, produces = "application/json")
	public List<Borrower> readBorrowers(@RequestParam(value = "name") String name) {
		return readBorrowersGS(name);
	}

	@Transactional
	@RequestMapping(value = "/admin/readPublishers", method = RequestMethod.GET, produces = "application/json")
	public List<Publisher> readPublishers(@RequestParam(value = "publisherName") String publisherName) {
		return readPublishersGS(publisherName);
	}

	@Transactional
	@RequestMapping(value = "/admin/addBook", method = RequestMethod.POST, consumes = "application/json")
	public void addBook(@RequestBody Book book) {

		try {
			Integer bookId = bDao.addBookGetPK(book);
			// add authors
			for (Author a : book.getAuthors()) {
				Integer authorId = aDao.addAuthorGetPK(a);
				bDao.addBookAuthors(bookId, authorId);
			}
			// add genres
			for (Genre g : book.getGenres()) {
				Integer genreId = gDao.addGenreGetPK(g);
				bDao.addGenres(genreId, bookId);
			}
			// add publisher
			Publisher p = book.getPublisher();
			Integer publisherId = pDao.addPublisherGetPK(p);
			bDao.addPublisher(publisherId, bookId);
			System.out.println("\n[+] Book Added Successfully!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	@RequestMapping(value = "/admin/addAuthor", method = RequestMethod.POST, consumes = "application/json")
	public void addAuthor(@RequestBody Author author) {

		try {
			Integer authorId = aDao.addAuthorGetPK(author);
			for (Book b : author.getBooks()) {
				aDao.addBook(b.getBookId(), authorId);
			}
			System.out.println("\n[+] Author Added Successfully!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	@RequestMapping(value = "/admin/addGenre", method = RequestMethod.POST, consumes = "application/json")
	public void addGenre(@RequestBody Genre genre) {

		try {
			gDao.addGenre(genre);
			System.out.println("\n[+] Genre Added Successfully!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	@RequestMapping(value = "/admin/addPublisher", method = RequestMethod.POST, consumes = "application/json")
	public void addPublisher(@RequestBody Publisher publisher) {

		try {
			Integer publisherId = pDao.addPublisherGetPK(publisher);
			pDao.addPublisherAddress(publisherId, publisher.getPublisherAddress());
			pDao.addPublisherPhone(publisherId, publisher.getPublisherPhone());
			System.out.println("\n[+] Publisher Added Successfully!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	@RequestMapping(value = "/admin/addLibraryBranch", method = RequestMethod.POST, consumes = "application/json")
	public void addLibraryBranch(@RequestBody LibraryBranch libraryBranch) {

		try {
			Integer branchId = lbDao.addLibraryBranchGetPK(libraryBranch);
			lbDao.addLibraryBranchAddress(branchId, libraryBranch.getBranchAddress());
			System.out.println("\n[+] Library Branch Added Successfully!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	@RequestMapping(value = "/admin/addBorrower", method = RequestMethod.POST, consumes = "application/json")
	public void addBorrower(@RequestBody Borrower borrower) {

		try {
			Integer borrowerId = brwDao.addBorrowerGetPK(borrower);
			brwDao.addBorrowerAddress(borrowerId, borrower.getAddress());
			brwDao.addBorrowerPhone(borrowerId, borrower.getAddress());
			System.out.println("\n[+] Borrower Added Successfully!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	@RequestMapping(value = "/admin/addBookLoan", method = RequestMethod.POST, consumes = "application/json")
	public void addBookLoan(@RequestBody BookLoan bookLoan) {

		try {
			blDao.addBookLoan(bookLoan);
			System.out.println("\n[+] Book Loan Added Successfully!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	@RequestMapping(value = "/admin/updateBook", method = RequestMethod.POST, consumes = "application/json")
	public void updateBook(@RequestBody Book book) {

		try {
			bDao.updateBook(book);
			System.out.println("\n[+] Book Updated Successfully!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	@RequestMapping(value = "/admin/updateAuthor", method = RequestMethod.POST, consumes = "application/json")
	public void updateAuthor(@RequestBody Author author) {

		try {
			aDao.updateAuthor(author);
			System.out.println("\n[+] Author Updated Successfully!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	@RequestMapping(value = "/admin/updateGenre", method = RequestMethod.POST, consumes = "application/json")
	public void updateGenre(@RequestBody Genre genre) {

		try {
			gDao.updateGenre(genre);
			System.out.println("\n[+] Genre Updated Successfully!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	@RequestMapping(value = "/admin/updatePublisher", method = RequestMethod.POST, consumes = "application/json")
	public void updatePublisher(@RequestBody Publisher publisher) {

		try {
			pDao.updatePublisher(publisher);
			System.out.println("\n[+] Publisher Updated Successfully!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	@RequestMapping(value = "/admin/updateLibraryBranch", method = RequestMethod.POST, consumes = "application/json")
	public void updateLibraryBranch(@RequestBody LibraryBranch libraryBranch) {

		try {
			lbDao.updateLibraryBranch(libraryBranch);
			System.out.println("\n[+] Library Branch Updated Successfully!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	@RequestMapping(value = "/admin/updateBorrower", method = RequestMethod.POST, consumes = "application/json")
	public void updateBorrower(@RequestBody Borrower borrower) {

		try {
			brwDao.updateBorrower(borrower);
			System.out.println("\n[+] Borrower Updated Successfully!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public void deleteBook(@RequestBody Book book) {

		try {
			bDao.deleteBook(book);
			System.out.println("\n[+] Book Deleted Successfully!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	@RequestMapping(value = "/admin/deleteAuthor", method = RequestMethod.POST, consumes = "application/json")
	public void deleteAuthor(@RequestBody Author author) {

		try {
			aDao.deleteAuthor(author);
			System.out.println("\n[+] Author Deleted Successfully!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	@RequestMapping(value = "/admin/deleteGenre", method = RequestMethod.POST, consumes = "application/json")
	public void deleteGenre(@RequestBody Genre genre) {

		try {
			gDao.deleteGenre(genre);
			System.out.println("\n[+] Genre Deleted Successfully!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	@RequestMapping(value = "/admin/deletePublisher", method = RequestMethod.POST, consumes = "application/json")
	public void deletePublisher(@RequestBody Publisher publisher) {

		try {
			pDao.deletePublisher(publisher);
			System.out.println("\n[+] Publisher Deleted Successfully!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	@RequestMapping(value = "/admin/deleteLibraryBranch", method = RequestMethod.POST, consumes = "application/json")
	public void deleteLibraryBranch(@RequestBody LibraryBranch libraryBranch) {

		try {
			lbDao.deleteLibraryBranch(libraryBranch);
			System.out.println("\n[+] Library Branch Deleted Successfully!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	@RequestMapping(value = "/admin/deleteBorrower", method = RequestMethod.POST, consumes = "application/json")
	public void deleteBorrower(@RequestBody Borrower borrower) {

		try {
			brwDao.deleteBorrower(borrower);
			System.out.println("\n[+] Borrower Deleted Successfully!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	@RequestMapping(value = "/admin/overrideBL", method = RequestMethod.POST, consumes = "application/json")
	public boolean overrideBookLoan(Integer bookId, Integer branchId, Integer cardNo) {
		BookLoan bookLoan = new BookLoan();
		bookLoan.setBookId(bookId);
		bookLoan.setBranchId(branchId);
		bookLoan.setCardNo(cardNo);
		try {
			blDao.override(bookLoan);
			System.out.println("\n[+] Over-riden Successfully!");
			return true;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Transactional
	public BookLoan getBookLoan(Integer bookId, Integer branchId, Integer cardNo) {
		BookLoan bookLoan = new BookLoan();
		bookLoan.setBookId(bookId);
		bookLoan.setBranchId(branchId);
		bookLoan.setCardNo(cardNo);
		try {
			bookLoan = blDao.readBookLoan(bookLoan);
			return bookLoan;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * @RequestMapping(value = "/admin/add", method = RequestMethod.POST, produces =
	 * "application/json") public void add(@RequestBody Object obj) {
	 * System.out.println("hwere: " + obj.getClass()); try { if (obj instanceof
	 * Book) { Book book = (Book) obj; Integer bookId = bDao.addBookGetPK(book); //
	 * add authors for (Author a : book.getAuthors()) { Integer authorId =
	 * aDao.addAuthorGetPK(a); bDao.addBookAuthors(bookId, authorId); } // add
	 * genres for (Genre g : book.getGenres()) { Integer genreId =
	 * gDao.addGenreGetPK(g); bDao.addGenres(genreId, bookId); } // add publisher
	 * Publisher p = book.getPublisher(); Integer publisherId =
	 * pDao.addPublisherGetPK(p); bDao.addPublisher(publisherId, bookId);
	 * System.out.println("\n[+] Book Added Successfully!"); } else if (obj
	 * instanceof Author) { Author author = ((Author) obj); Integer authorId =
	 * aDao.addAuthorGetPK(author); for (Book b : author.getBooks()) {
	 * aDao.addBook(b.getBookId(), authorId); } } else if (obj instanceof Genre) {
	 * gDao.addGenre((Genre) obj);
	 * System.out.println("\n[+] Genre Added Successfully!"); } else if (obj
	 * instanceof Publisher) { Integer publisherId =
	 * pDao.addPublisherGetPK((Publisher) obj);
	 * pDao.addPublisherAddress(publisherId, ((Publisher)
	 * obj).getPublisherAddress()); pDao.addPublisherPhone(publisherId, ((Publisher)
	 * obj).getPublisherPhone());
	 * System.out.println("\n[+] Publisher Added Successfully!"); } else if (obj
	 * instanceof LibraryBranch) { Integer branchId =
	 * lbDao.addLibraryBranchGetPK((LibraryBranch) obj);
	 * lbDao.addLibraryBranchAddress(branchId, ((LibraryBranch)
	 * obj).getBranchAddress());
	 * System.out.println("\n[+] Library Branch Added Successfully!"); } else if
	 * (obj instanceof Borrower) { Integer borrowerId =
	 * brwDao.addBorrowerGetPK((Borrower) obj);
	 * brwDao.addBorrowerAddress(borrowerId, ((Borrower) obj).getAddress());
	 * brwDao.addBorrowerPhone(borrowerId, ((Borrower) obj).getAddress());
	 * System.out.println("\n[+] Borrower Added Successfully!"); } else if (obj
	 * instanceof BookLoan) { blDao.addBookLoan((BookLoan) obj);
	 * System.out.println("\n[+] Book Loan Added Successfully!"); } } catch
	 * (ClassNotFoundException | SQLException e) { e.printStackTrace();
	 * System.out.println("Something went wrong. Failed to get all authors"); } }
	 * 
	 * 
	 * public void update(Object obj) {
	 * 
	 * try { if (obj instanceof Book) { Book book = (Book) obj;
	 * bDao.updateBook(book); } else if (obj instanceof Author) { Author author =
	 * (Author) obj; aDao.updateAuthor(author); } else if (obj instanceof Genre) {
	 * Genre genre = (Genre) obj; gDao.updateGenre(genre); } else if (obj instanceof
	 * Publisher) { Publisher publisher = (Publisher) obj;
	 * pDao.updatePublisher(publisher); } else if (obj instanceof LibraryBranch) {
	 * LibraryBranch libraryBranch = (LibraryBranch) obj;
	 * lbDao.updateLibraryBranch(libraryBranch); } else if (obj instanceof Borrower)
	 * { Borrower borrower = (Borrower) obj; brwDao.updateBorrower(borrower); } else
	 * if (obj instanceof BookLoan) { BookLoan bookLoan = (BookLoan) obj;
	 * blDao.updateBookLoan(bookLoan); }
	 * 
	 * } catch (ClassNotFoundException | SQLException e) { e.printStackTrace(); } }
	 * 
	 * public void delete(Object obj) {
	 * 
	 * try { if (obj instanceof Book) { Book book = (Book) obj;
	 * bDao.deleteBook(book); } else if (obj instanceof Author) { Author author =
	 * (Author) obj; aDao.deleteAuthor(author); } else if (obj instanceof Genre) {
	 * Genre genre = (Genre) obj; gDao.deleteGenre(genre); } else if (obj instanceof
	 * Publisher) { Publisher publisher = (Publisher) obj;
	 * pDao.deletePublisher(publisher); } else if (obj instanceof LibraryBranch) {
	 * LibraryBranch libraryBranch = (LibraryBranch) obj;
	 * lbDao.deleteLibraryBranch(libraryBranch); } else if (obj instanceof Borrower)
	 * { Borrower borrower = (Borrower) obj; brwDao.deleteBorrower(borrower); } else
	 * if (obj instanceof BookLoan) { BookLoan bookLoan = (BookLoan) obj;
	 * blDao.deleteBookLoan(bookLoan); }
	 * 
	 * } catch (ClassNotFoundException | SQLException e) { e.printStackTrace();
	 * System.out.println("Something went wrong. Failed to get all authors"); }
	 * 
	 * }
	 * 
	 * public Author getAuthor(Integer authorId) {
	 * 
	 * Author author = null; try { author = aDao.readAuthorById(authorId); } catch
	 * (ClassNotFoundException | SQLException e) { e.printStackTrace(); } return
	 * author; }
	 * 
	 * public Genre getGenre(Integer genreId) {
	 * 
	 * Genre genre = null; try { genre = gDao.readGenreById(genreId); } catch
	 * (ClassNotFoundException | SQLException e) { e.printStackTrace(); } return
	 * genre; }
	 * 
	 * public Publisher getPublisher(Integer publisherId) { Publisher publisher =
	 * null; try { publisher = pDao.readPublisher(publisherId); } catch
	 * (ClassNotFoundException | SQLException e) { e.printStackTrace(); } return
	 * publisher; }
	 */

}
