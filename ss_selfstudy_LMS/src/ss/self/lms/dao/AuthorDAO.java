package ss.self.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ss.self.lms.entity.Author;

public class AuthorDAO extends BaseDAO<Author> {

	public AuthorDAO(Connection conn) {
		super(conn);

	}
	public void addAuthor(Author author) throws ClassNotFoundException, SQLException {
		save("insert into tbl_author values (?, ?)", new Object[] {author.getId(), author.getName()});
	}
	
	public void updateAuthor(Author author) throws ClassNotFoundException, SQLException {
		save("update tbl_author set authorName = ? where authorId = ?", new Object[] {author.getName(), author.getId()});
	}
	
	public void deleteAuthor(Author author) throws ClassNotFoundException, SQLException {
		save("delete from tbl_author where authorId = ?", new Object[] {author.getId()});
	}
	
	public List<Author> readAllAuthors() throws ClassNotFoundException, SQLException {
		return read("select * from tbl_author", null);
	}
	
	public List<Author> readAuthorFromId(Integer id) throws ClassNotFoundException, SQLException {
		return read("select * from tbl_author where authorId = ?", new Object[] {id});
	}
	
	@Override
	public List<Author> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		List<Author> authors = new ArrayList<>();
		while(rs.next()) {
			Author a = new Author();
			a.setId(rs.getInt("authorId"));
			a.setName(rs.getString("authorName"));
			authors.add(a);
		}
		return authors;
		
	}

}
