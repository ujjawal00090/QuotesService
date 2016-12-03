package com.sjosan.utility.service.business;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.sjosan.utility.service.entity.Category;
import com.sjosan.utility.service.entity.Content;
import com.sjosan.utility.service.entity.Likes;
import com.sjosan.utility.service.entity.ResponsePayload;

public class UtilityServiceBusiness {

	@Autowired
	private SessionFactory sessionFactory;

	Logger logger = LoggerFactory.getLogger(UtilityServiceBusiness.class);

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	public ResponsePayload getCategory() {

		ResponsePayload payload = new ResponsePayload();

		Session session = sessionFactory.openSession();

		Query query = session.createQuery("from Category where display != 0");

		List<Category> list = query.list();

		for (Category c : list) {
			Query query2 = session.createQuery("from Content where categoryID=? and approved != 0");
			query2.setParameter(0, (int) c.getId());
			List<Content> contents = query2.list();
			c.setContentCount(contents.size());
			// c.setContents(contents);
		}

		payload.setCategories(list);

		return payload;
	}

	@SuppressWarnings("unchecked")
	public ResponsePayload getContent(int categoryID) {

		ResponsePayload payload = new ResponsePayload();

		Session session = sessionFactory.openSession();

		Query query = session.createQuery("from Content where categoryID=? and approved = 1");
		query.setParameter(0, categoryID);

		List<Content> list = query.list();

		Query query2 = session.createQuery("from Likes where categoryID=?");
		query2.setParameter(0, categoryID);

		List<Likes> list2 = query2.list();

		for (Content c : list) {

			List<Likes> newLikes = new ArrayList<Likes>();

			for (Likes l : list2) {
				if (l.getContentID() == c.getId()) {
					newLikes.add(l);
				}
			}
			c.setLikes(newLikes);
		}

		payload.setContents(list);

		return payload;
	}

	@SuppressWarnings("unchecked")
	public ResponsePayload getMyContent(String accountID) {

		ResponsePayload payload = new ResponsePayload();

		Session session = sessionFactory.openSession();

		Query query = session.createQuery("from Content as c where c.submittedBy like '%" + accountID + "%'");

		List<Content> list = query.list();

		for (Content c : list) {

			Query query2 = session.createQuery("from Likes where contentID=?");
			query2.setParameter(0, (int) c.getId());

			List<Likes> list2 = query2.list();
			c.setLikes(list2);
		}

		payload.setContents(list);

		return payload;
	}

	public ResponsePayload saveData() {

		ResponsePayload payload = new ResponsePayload();

		Session session = sessionFactory.openSession();
		Transaction tx = null;

		tx = session.beginTransaction();

		ArrayList<Likes> listOfFiles = new ArrayList<Likes>();

		Likes l = new Likes();
		l.setName("Age");
		l.setId(1);
		l.setUrl("https://www.dropbox.com/sh/089uxavckmtl1s7/AABGkZM37Pfgmw8hV-8pSkWCa?dl=1");
		listOfFiles.add(l);
		
		l = new Likes();
		l.setName("Inspirational");
		l.setId(1);
		l.setUrl("https://www.dropbox.com/s/hr2rj92e92r8ull/inspirational.txt?dl=1");
		listOfFiles.add(l);
		
		l = new Likes();
		l.setName("Life");
		l.setId(1);
		l.setUrl("https://www.dropbox.com/s/fxqv2igc3s9me8q/life.txt?dl=1");
		listOfFiles.add(l);
		
		l = new Likes();
		l.setId(1);
		l.setName("Love");
		l.setUrl("https://www.dropbox.com/s/5dj2nbs6hnjvdlo/love.txt?dl=1");
		listOfFiles.add(l);
		
		l = new Likes();
		l.setId(1);
		l.setName("Motivational");
		l.setUrl("https://www.dropbox.com/s/8qxquy0rsdywt0u/Motivational.txt?dl=1");
		listOfFiles.add(l);
		
		l = new Likes();
		l.setId(1);
		l.setName("Positive");
		l.setUrl("https://www.dropbox.com/s/gantdkxuhp7wsqp/positive.txt?dl=1");
		listOfFiles.add(l);
		
		l = new Likes();
		l.setId(1);
		l.setName("Success");
		l.setUrl("https://www.dropbox.com/s/s016ogpkp9qvvai/Success.txt?dl=1");
		listOfFiles.add(l);
		int count=0;
		
		String s="";
		
		try {

			for (Likes file : listOfFiles) {

				URL url = new URL(file.getUrl());
				InputStream is = url.openStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
				String line=null,str="";
	             while( (line=br.readLine()) != null) {
	                    str+=line;  
	             }
				br.close();

				String catName = file.getName();
				Category cat = new Category();
				cat.setName(catName);
				cat.setDisplay(1);
				cat.setDisplay((int) file.getId());
				session.save(cat);

				String fileContent;

				fileContent = str;

				fileContent = fileContent.replace("\u2060\u2060\u2060\u2060\u2060", "");
				fileContent = fileContent.replace("\"", "");

				String[] singleContents = fileContent.split(";;;");

				for (String singleContent : singleContents) {
					Content content = new Content();

					content.setContentText(StringEscapeUtils.escapeSql(singleContent));
					content.setCategoryID((int) cat.getId());
					content.setSubmittedBy("ADMIN");
					content.setApproved(1);
					s=StringEscapeUtils.escapeSql(singleContent);
					session.save(content);
					++count;
				}

			}

			tx.commit();

		} catch (Exception e) {
			if (tx != null)
				tx.rollback();

			logger.error("saveData", e);
			payload.setResponseStatus(false);
			payload.setResponseMessage("OOPS! Something went wrong."+count+s+e);
		} finally {
			session.close();
		}

		return payload;

	}

	public ResponsePayload addContent(Content content) {

		ResponsePayload payload = new ResponsePayload();

		Session session = sessionFactory.openSession();
		Transaction tx = null;

		tx = session.beginTransaction();

		try {
			content.setApproved(0);

			session.save(content);

			tx.commit();

			payload.setResponseMessage("Content Added. Pending Admin approval.");

		} catch (Exception e) {
			if (tx != null)
				tx.rollback();

			logger.error("addContent", e);
			payload.setResponseStatus(false);
			payload.setResponseMessage("OOPS! Something went wrong.");
		} finally {
			session.close();
		}

		return payload;

	}

	public ResponsePayload approveContent(Content content) {

		ResponsePayload payload = new ResponsePayload();

		Session session = sessionFactory.openSession();
		Transaction tx = null;

		tx = session.beginTransaction();

		try {

			if (content.getApproved() == 1) {

				Query query2 = session.createQuery("UPDATE Content set approved = " + content.getApproved()
						+ ", categoryID = " + content.getCategoryID() + " where id = " + content.getId());
				query2.executeUpdate();

				payload.setResponseMessage("Content Approved.");

			} else {
				session.saveOrUpdate(content);

				payload.setResponseMessage("Content Deleted.");
			}

			tx.commit();

		} catch (Exception e) {
			if (tx != null)
				tx.rollback();

			logger.error("addContent", e);
			payload.setResponseStatus(false);
			payload.setResponseMessage("OOPS! Something went wrong.");
		} finally {
			session.close();
		}

		return payload;

	}

	@SuppressWarnings("unchecked")
	public ResponsePayload getContentToApprove() {

		ResponsePayload payload = new ResponsePayload();

		Session session = sessionFactory.openSession();

		Query query = session.createQuery("from Content where approved = 0");

		List<Content> list = query.list();

		payload.setContents(list);

		return payload;
	}

	public ResponsePayload like(Likes like) {

		ResponsePayload payload = new ResponsePayload();

		Session session = sessionFactory.openSession();
		Transaction tx = null;

		tx = session.beginTransaction();

		try {

			session.save(like);

			tx.commit();

			payload = getContent(like.getCategoryID());

			payload.setResponseMessage("Content liked.");

		} catch (Exception e) {
			if (tx != null)
				tx.rollback();

			logger.error("like", e);
			payload.setResponseStatus(false);
			payload.setResponseMessage("OOPS! Something went wrong.");
		} finally {
			session.close();
		}

		return payload;

	}

	public ResponsePayload unlike(Likes like) {

		ResponsePayload payload = new ResponsePayload();

		Session session = sessionFactory.openSession();
		Transaction tx = null;

		tx = session.beginTransaction();

		try {

			session.delete(like);

			tx.commit();

			payload = getContent(like.getCategoryID());

			payload.setResponseMessage("Content unliked.");

		} catch (Exception e) {
			if (tx != null)
				tx.rollback();

			logger.error("unlike", e);
			payload.setResponseStatus(false);
			payload.setResponseMessage("OOPS! Something went wrong.");
		} finally {
			session.close();
		}

		return payload;

	}
}
