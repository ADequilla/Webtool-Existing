package com.valuequest.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.valuequest.dao.AdminDao;
import com.valuequest.entity.ViewUser;
import com.valuequest.entity.security.SecComponent;
import com.valuequest.entity.security.SecMenu;
import com.valuequest.entity.security.SecUser;
import com.valuequest.entity.security.SecUserrole;

@Repository
public class AdminDaoImpl extends BaseDao<SecUser> implements AdminDao {

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public SecUser getSecUser(String username) {
		return (SecUser) sessionFactory.getCurrentSession().createQuery("from SecUser where usrLoginname = :username")
				.setParameter("username", username).uniqueResult();
	}

	@Override
	public ViewUser getCheckStatusByName(String username) {
		return (ViewUser) sessionFactory.getCurrentSession().createQuery("from ViewUser where login = :username")
				.setParameter("username", username).uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllRights() {
		return sessionFactory.getCurrentSession().createSQLQuery("select menu_name from mfs.m_menu where menu_enabled = 1 ").list();
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getRightsByUser(SecUser userLogin) {
        return sessionFactory.getCurrentSession().createSQLQuery("select distinct m.menu_name "
        		+ "from mfs.m_menu m "
        		+ "inner join mfs.m_component c on c.menu_id = m.menu_id "
        		+ "inner join mfs.m_role_dtl rd on rd.menu_comp_id = c.menu_comp_id "
        		+ "inner join mfs.m_role r on r.role_id = rd.role_id "
        		+ "inner join mfs.t_mapping_userole ur on ur.role_id = r.role_id "
        		+ "where m.menu_enabled = 1 and ur.user_id=:userId").setParameter("userId", userLogin.getId()).list();
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<SecMenu> getAllParentMenu(){
		return sessionFactory.getCurrentSession().createQuery("from SecMenu where enabled = 1 and parent is null  order by sequence ").list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SecMenu> getChildMenuByParent(SecMenu parent){
		return sessionFactory.getCurrentSession().createQuery("from SecMenu where enabled = 1 and parent = :parent order by sequence ")
				.setParameter("parent", parent).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SecComponent> getComponentByMenu(SecMenu menu){
		return sessionFactory.getCurrentSession().createQuery("from SecComponent where menu = :menu order by name desc")
				.setParameter("menu", menu).list();
	}

	@SuppressWarnings("unchecked")
    @Override
	public List<SecUserrole> listSecUserrole(Long userId) {
        return sessionFactory.getCurrentSession().createQuery("from SecUserrole where user.id = :userId")
                .setParameter("userId", userId).list();
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getComponent(Long userId, String priviledge) {
		return sessionFactory.getCurrentSession().createSQLQuery("select distinct c.menu_comp_name "
        		+ "from mfs.m_menu m "
        		+ "inner join mfs.m_component c on c.menu_id = m.menu_id "
        		+ "inner join mfs.m_role_dtl rd on rd.menu_comp_id = c.menu_comp_id "
        		+ "inner join mfs.m_role r on r.role_id = rd.role_id "
        		+ "inner join mfs.t_mapping_userole ur on ur.role_id = r.role_id "
        		+ "where m.menu_enabled = 1 "
        		+ "and ur.user_id=:userId "
        		+ "and m.menu_name=:menu")
				.setParameter("userId", userId)
				.setParameter("menu", priviledge).list();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean getPriviledge(Long userId, String priviledge) {
		List list = sessionFactory.getCurrentSession()
			.createSQLQuery("select 1 from mfs.m_user u "
                    + "inner join mfs.t_mapping_userole ur on ur.user_id = u.user_id "
                    + "inner join mfs.m_role r on r.role_id = ur.role_id "
                    + "inner join mfs.m_role_dtl rd on rd.role_id = r.role_id "
                    + "inner join mfs.m_component c on c.menu_comp_id=rd.menu_comp_id "
                    + "inner join mfs.m_menu m on m.menu_id=c.menu_id "
                    + "where m.menu_enabled = 1 "
                    + "and m.menu_name = :menuName "
					+ "and u.user_id = :userId")
			.setParameter("menuName", priviledge).setParameter("userId", userId).list();
		
		if (list.isEmpty())
			return false;
		
		return true;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean getPriviledge(Long userId, String menu, String component) {
		List list = sessionFactory.getCurrentSession()
				.createSQLQuery("select 1 from mfs.m_user u "
	                    + "inner join mfs.t_mapping_userole ur on ur.user_id = u.user_id "
	                    + "inner join mfs.m_role r on r.role_id = ur.role_id "
	                    + "inner join mfs.m_role_dtl rd on rd.role_id = r.role_id "
	                    + "inner join mfs.m_component c on c.menu_comp_id=rd.menu_comp_id "
	                    + "inner join mfs.m_menu m on m.menu_id=c.menu_id "
	                    + "where m.menu_enabled = 1 "
	                    + "and m.menu_name = :menuName "
	                    + "and c.menu_comp_name = :compName "
						+ "and u.user_id = :userId")
				.setParameter("menuName", menu).setParameter("compName", component).setParameter("userId", userId).list();
			
			if (list.isEmpty())
				return false;
			
			return true;
	}

	@Override
	public SecUser getSecUserName(String username) {
		return (SecUser) sessionFactory.getCurrentSession().createQuery("select usrName from SecUser where usrLoginname = :username")
				.setParameter("username", username).uniqueResult();
	}
}
