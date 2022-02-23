package la.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import la.dto.MemberBean;

/**
 * 会員(Member)用Data Access Object(DAO)
 *
 * @since 2021/10/15
 */

public class MemberDAO {
	
private Connection con;
	
	public MemberDAO() throws DAOException {
		con = Common.getConnection();
	}

	/**
	 * このメソッドは、Daoクラスが新しいDTOインスタンスを作成する場合使用する。<br>
	 * プログラマがDTOも拡張したい場合、このメソッドをオーバーライドして、<br>
	 * 拡張されたDTOを返すようにするためのものである。
	 * <p>
	 * 注：DTOクラスを拡張する場合は、clone()メソッドをオーバーライドすること。
	 *
	 * @return DTOオブジェクト
	 */
	public MemberBean createDTO() {
		return new MemberBean();
	}

	/**
	 * 指定された主キーでMemberを検索し、結果をDTOオブジェクトとして返す。<br>
	 * 返されるDTOオブジェクトは、createDTO()メソッドにより作成する。<br>
	 * 該当データが存在しない場合、NotFoundExceptionをスローする。<br>
	 *
	 * @param con   DBコネクション
	 * @param id 主キー
	 * @return Member
	 * @throws NotFoundException 該当レコードなし
	 * @throws SQLException SQLエラー
	 * @throws DAOException 
	 */
	public MemberBean findByPKey(int id) throws NotFoundException, SQLException, DAOException {
		if(con == null)
			con = Common.getConnection();
		MemberBean dto = createDTO();
		dto.setId(id);
		load(con, dto);
		return dto;
	}

	/**
	 * DTOの主キーを使い、Memberより該当データを取得する。<br>
	 * 呼び出し側は、DTOを生成し、主キーだけをセットして呼び出すこと。<br>
	 * このメソッドは、主キーを含むすべてのDTOのフィールドを上書きする。<br>
	 * 該当データが存在しない場合、NotFoundExceptionをスローする。<br>
	 *
	 * @param con DBコネクション
	 * @param dto 検索結果。呼び出し前に主キーをセットしておくこと。
	 * @throws NotFoundException 該当レコードなし
	 * @throws SQLException SQLエラー
	 */
	public void load(Connection con, MemberBean dto) throws NotFoundException, SQLException {
		String sql = "SELECT * FROM Member WHERE (id = ?) ";
		PreparedStatement stmt = null;

		try {
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, dto.getId());

			singleQuery(con, stmt, dto);

		} finally {
			if (stmt != null)
				stmt.close();
		}
	}

	/**
	 * Memberの全レコードを取得しDTOのListとして返す。<br>
	 * テーブルの行数が多い場合、大量のリソースを消費するので注意。<br>
	 * (格納レコード数が少ないテーブル向き)<br>
	 *
	 * @param con DBコネクション
	 * @return Memberの全レコード
	 * @throws SQLException SQLエラー
	 */
	public List<MemberBean> loadAll(Connection con) throws SQLException {
		String sql = "SELECT * FROM Member ORDER BY id ASC ";

		List<MemberBean> searchResults = listQuery(con.prepareStatement(sql));
		return searchResults;
	}

	/**
	 * Memberテーブルに格納されているレコード件数を返す。<br>
	 * 件数は "select count(*) from Member" で取得する。<br>
	 * Memberにレコードが無い場合は0を返す。<br>
	 *
	 * @param con DBコネクション
	 * @return Memberのレコード件数
	 * @throws SQLException SQLエラー
	 */
	public int countAll(Connection con) throws SQLException {
		String sql = "SELECT count(*) FROM Member";
		PreparedStatement stmt = null;
		ResultSet result = null;
		int allRows = 0;

		try {
			stmt = con.prepareStatement(sql);
			result = stmt.executeQuery();

			if (result.next())
				allRows = result.getInt(1);
		} finally {
			if (result != null)
				result.close();
			if (stmt != null)
				stmt.close();
		}
		return allRows;
	}

	/**
	 * 検索処理のヘルパーメソッド(検索結果 １行用)<br>
	 * 該当データが無ければNotFoundExceptionをスローする。
	 *
	 * @param con  DBコネクション
	 * @param stmt 実行するSELECTを表す格納したPreparedStatement
	 * @param dto  検索結果を格納するDTO
	 * @throws NotFoundException 該当レコードなし
	 * @throws SQLException SQLエラー
	 */
	protected void singleQuery(Connection con, PreparedStatement stmt, MemberBean dto)
			throws NotFoundException, SQLException {
		ResultSet rs = null;

		try {
			rs = stmt.executeQuery();
			if (rs.next()) {
				dto.setId(rs.getInt("id"));
				dto.setName(rs.getString("name"));
				dto.setPostal(rs.getString("postal"));
				dto.setAddress(rs.getString("address"));
				dto.setTel(rs.getString("tel"));
				dto.setEmail(rs.getString("email"));
				dto.setBirthday(rs.getDate("birthday"));
				dto.setSubscribeDate(rs.getDate("subscribe_date"));
				dto.setUnsubscribeDate(rs.getDate("unsubscribe_date"));
				dto.setPassword(rs.getString("password"));
				dto.setMembership(rs.getString("membership"));

			} else {
				throw new NotFoundException("Member Object Not Found!");
			}
		} finally {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
		}
	}
	
	// **使った
	// ------------------------------------------------------------
	// 【 memberSearch.jsp 】
	// -> [検索]ボタン押下 ⇒ 名前・メールアドレスをLIKE検索
	// ------------------------------------------------------------
	public List<MemberBean> findByNameAndEmail(String name, String email, String id)
			throws DAOException {
		
		PreparedStatement pstmt = null;
		List<MemberBean> list = null;
		try {
			String sql = "SELECT * FROM member WHERE name LIKE ? AND email LIKE ? AND id LIKE ? AND unsubscribe_date IS NULL";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + name + "%");
			pstmt.setString(2, "%" + email + "%");
			pstmt.setString(3, "%" + id + "%");
			list = listQuery(pstmt);

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("レコードの取得に失敗しました。");
		} finally {
			if (pstmt != null) {
				try {
					// リソースの開放
					pstmt.close();
					this.con = Common.close(con);
				} catch (Exception e) {
					e.printStackTrace();
					throw new DAOException("リソースの開放に失敗しました。");
				}
			}
		}
		return list;
	}

	//** 使った
	/**
	 * 検索処理のヘルパーメソッド(検索結果 複数行用)<br>
	 * 該当データがない場合、空のリストを返す。
	 *
	 * @param con  DBコネクション
	 * @param stmt 実行するSELECTを表す格納したPreparedStatement
	 * @return 検索結果
	 * @throws SQLException SQLエラー
	 */
	protected List<MemberBean> listQuery(PreparedStatement stmt) throws SQLException {
//		ArrayList<MemberBean> searchResults = new ArrayList<>();
		List<MemberBean> searchResults = new ArrayList<>();
		ResultSet rs = null;

		try {
			rs = stmt.executeQuery();

			while (rs.next()) {
				MemberBean temp = createDTO();

				temp.setId(rs.getInt("id"));
				temp.setName(rs.getString("name"));
				temp.setPostal(rs.getString("postal"));
				temp.setAddress(rs.getString("address"));
				temp.setTel(rs.getString("tel"));
				temp.setEmail(rs.getString("email"));
				temp.setBirthday(rs.getDate("birthday"));
				temp.setSubscribeDate(rs.getDate("subscribe_date"));
				temp.setUnsubscribeDate(rs.getDate("unsubscribe_date"));
				temp.setPassword(rs.getString("password"));
				temp.setMembership(rs.getString("membership"));

				searchResults.add(temp);
			}

		} finally {
			if (rs != null)
				rs.close();
			// TODO 呼び出し元で本当にstmsがクローズされるか要確認
//			if (stmt != null)
//				stmt.close();
		}

		return searchResults;
	}

	/**
	 * 指定されたDTOの内容をMemberへ追加する。<br>
	 * DTOには適切な値が設定されていること。<br>
	 * このテーブルが自動代理キーを使用しない場合は、主キーを指定する必要がある。<br>
	 * 自動代理キーを使用した場合、INSERT実行前にキー値を取得しDTOにセットする。<br>
	 *
	 * @param con DBコネクション
	 * @param dto Memberへ追加する内容。自動代理キーを使用しない場合、主キー項目に適切な値を設定しておくこと。
	 * @throws NotFoundException 主キー未設定
	 * @throws SQLException SQLエラー
	 */
	public synchronized void create(Connection con, MemberBean dto) throws NotFoundException, SQLException {
		String sql = "";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		

		try {
			StringBuffer sb = new StringBuffer();
			sb.append("INSERT INTO Member( ");
			sb.append("    name, postal, address, tel, ");
			sb.append("    email, birthday, subscribe_date, unsubscribe_date, password, ");
			sb.append("    membership ");
			sb.append(") VALUES ( ");
			sb.append("    ?, ?, ?, ? ");
			sb.append("    , ?, ?, ?, ?, ? ");
			sb.append("    , ? ");
			sb.append(")");
			sql = sb.toString();

			stmt = con.prepareStatement(sql);

			stmt.setString(1, dto.getName());
			stmt.setString(2, dto.getPostal());
			stmt.setString(3, dto.getAddress());
			stmt.setString(4, dto.getTel());
			stmt.setString(5, dto.getEmail());
			stmt.setDate(6, dto.getBirthday());
			stmt.setDate(7, dto.getSubscribeDate());
			stmt.setDate(8, dto.getUnsubscribeDate());
			stmt.setString(9, dto.getPassword());
			stmt.setString(10, dto.getMembership());

			int rowcount = databaseUpdate(con, stmt);
			if (rowcount != 1) {
				throw new SQLException("PrimaryKey Error when updating DB!");
			}

		} finally {
			if (stmt != null)
				stmt.close();
		}
		
		// 自動生成された主キーを取得し、DTOへセットする。
		// これにより呼び出し側は、生成された主キーの値を知ることができる。
		sql = "SELECT MAX(id) FROM Member";

		try {
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();

			if (rs.next()) {
				dto.setId((int) rs.getLong(1));
			} else {
				throw new SQLException("Unable to find primary-key for created object!");
			}
		} finally {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
		}
	}

	/**
	 * DTOの内容でMemberを更新する。<br>
	 * 新規作成ではないので、呼び出し側が、主キーが設定する必要がある。<br>
	 * 該当するレコードがない場合(=更新失敗時)、NotFoundExceptionをスローする。<br>
	 *
	 * @param con DBコネクション
	 * @param dto Memberを更新する内容。主キー項目に適切な値を設定しておくこと。
	 * @throws NotFoundException 更新失敗(該当レコードなし)
	 * @throws SQLException SQLエラー
	 * @throws DAOException 
	 */
	public void save(MemberBean dto) throws NotFoundException, SQLException, DAOException {
		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE Member ");
		sb.append("   SET name = ?, ");
		sb.append("       postal = ?, ");
		sb.append("       address = ?, ");
		sb.append("       tel = ?, ");
		sb.append("       email = ?, ");
		sb.append("       birthday = ?, ");
		sb.append("       subscribe_date = ?, ");
		sb.append("       unsubscribe_date = ?, ");
		sb.append("       password = ?, ");
		sb.append("       membership = ? ");
		sb.append(" WHERE (id = ?)");
		String sql = sb.toString();

		PreparedStatement stmt = null;

		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, dto.getName());
			stmt.setString(2, dto.getPostal());
			stmt.setString(3, dto.getAddress());
			stmt.setString(4, dto.getTel());
			stmt.setString(5, dto.getEmail());
			stmt.setDate(6, dto.getBirthday());
			stmt.setDate(7, dto.getSubscribeDate());
			stmt.setDate(8, dto.getUnsubscribeDate());
			stmt.setString(9, dto.getPassword());
			stmt.setString(10, dto.getMembership());
			stmt.setInt(11, dto.getId());

			int rowcount = databaseUpdate(con, stmt);
			if (rowcount == 0) {
				throw new NotFoundException("Object could not be saved! (PrimaryKey not found)");
			}
			if (rowcount > 1) {
				throw new SQLException("PrimaryKey Error when updating DB! (Many objects were affected!)");
			}
		} finally {
			try {
				// リソースの開放
				if (stmt != null)
					stmt.close();
				this.con = Common.close(con);
			} catch (Exception e) {
				throw new DAOException("リソースの開放に失敗しました。");
			}
		}
	}

	/**
	 * MemberよりDTOの主キーで指定されたレコードを削除する。<br>
	 * 該当レコードがない場合(=削除失敗時)、NotFoundExceptionをスローする。<br>
	 *
	 * @param con DBコネクション
	 * @param dto 削除するレコードの主キー項目を設定したDTO
	 * @throws NotFoundException 該当レコードなし
	 * @throws SQLException SQLエラー
	 */
	public void delete(Connection con, MemberBean dto) throws NotFoundException, SQLException {
		String sql = "DELETE FROM Member WHERE (id = ?) ";
		PreparedStatement stmt = null;

		try {
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, dto.getId());

			int rowcount = databaseUpdate(con, stmt);
			if (rowcount == 0) {
				throw new NotFoundException("Object could not be deleted! (PrimaryKey not found)");
			}
			if (rowcount > 1) {
				throw new SQLException("PrimaryKey Error when updating DB! (Many objects were deleted!)");
			}
		} finally {
			if (stmt != null)
				stmt.close();
		}
	}

	/**
	 * Memberテーブルの内容を全件削除する。
	 *
	 * @param con DBコネクション
	 * @throws SQLException SQLエラー
	 */
	public void deleteAll(Connection con) throws SQLException {
		String sql = "DELETE FROM Member";
		PreparedStatement stmt = null;

		try {
			stmt = con.prepareStatement(sql);
			databaseUpdate(con, stmt);
		} finally {
			if (stmt != null)
				stmt.close();
		}
	}

	/**
	 * 更新系処理のヘルパーメソッド
	 *
	 * @param con  DBコネクション
	 * @param stmt 実行する更新系SQLを格納したPreparedStatement
	 * @return 影響を受けた行数
	 * @throws SQLException SQLエラー
	 */
	protected int databaseUpdate(Connection con, PreparedStatement stmt) throws SQLException {
		int result = stmt.executeUpdate();

		return result;
	}
	
	public MemberBean findByIdAndEmail(Connection con, int id, String password) throws NotFoundException, SQLException {

		String sql = "SELECT * FROM member WHERE id=? AND password=? AND unsubscribe_date IS NULL";
		PreparedStatement pstmt = null;
		MemberBean dto = new MemberBean();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, id);
			pstmt.setString(2, password);
			singleQuery(con, pstmt, dto);

		} catch (SQLException e) {
			throw e;

		} catch (NotFoundException e) {
			throw e;

		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return dto;
	}
	
	public MemberBean findByEmail(Connection con, String email) throws NotFoundException, SQLException {

		String sql = "SELECT * FROM member WHERE email=?";
		PreparedStatement pstmt = null;
		MemberBean dto = new MemberBean();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, email);
			singleQuery(con, pstmt, dto);

		} catch (SQLException e) {
			throw e;

		} catch (NotFoundException e) {
			throw e;

		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return dto;
	}

}
