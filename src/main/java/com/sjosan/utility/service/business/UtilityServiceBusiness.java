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
		l.setName("Bewafa");
		l.setId(1);
		l.setUrl("https://www.dropbox.com/s/mna8wlgcwvelctg/Bewafa.txt?dl=1");
		listOfFiles.add(l);
		
		l = new Likes();
		l.setName("Dard");
		l.setId(1);
		l.setUrl("https://www.dropbox.com/s/09a3fzs7gbb4amg/Dard.txt?dl=1");
		listOfFiles.add(l);
		
		l = new Likes();
		l.setName("Dosti");
		l.setId(1);
		l.setUrl("https://www.dropbox.com/s/b5a1hgd2bd44t6j/Dosti.txt?dl=1");
		listOfFiles.add(l);
		
		l = new Likes();
		l.setId(1);
		l.setName("Happy Birthday");
		l.setUrl("https://www.dropbox.com/s/ha7v5gwslpk79ks/Happy%20Birthday.txt?dl=1");
		listOfFiles.add(l);
		
		l = new Likes();
		l.setId(1);
		l.setName("Ishq");
		l.setUrl("https://www.dropbox.com/s/jkzpvnkql7kszqs/Ishq.txt?dl=1");
		listOfFiles.add(l);
		
		l = new Likes();
		l.setId(1);
		l.setName("Laugh");
		l.setUrl("https://www.dropbox.com/s/36scilxwz00hhl1/Laugh.txt?dl=1");
		listOfFiles.add(l);
		
		l = new Likes();
		l.setId(1);
		l.setName("Love");
		l.setUrl("https://www.dropbox.com/s/5icz5j2keui1ajl/Love.txt?dl=1");
		listOfFiles.add(l);
		
		l = new Likes();
		l.setId(1);
		l.setName("Other");
		l.setUrl("https://www.dropbox.com/s/nimf3v0y6u6n8is/Other.txt?dl=1");
		listOfFiles.add(l);
		
		l = new Likes();
		l.setId(1);
		l.setName("Sharabi");
		l.setId(1);
		l.setUrl("https://www.dropbox.com/s/8aokkipe9g5gzxg/Sharabi.txt?dl=1");
		listOfFiles.add(l);
		
		l = new Likes();
		l.setId(1);
		l.setName("Shubkamnya");
		l.setUrl("https://www.dropbox.com/s/w5ljocdslrzqr7c/Shubkamnya.txt?dl=1");
		listOfFiles.add(l);
		
		l = new Likes();
		l.setId(2);
		l.setName("SMS Bewafa");
		l.setUrl("https://www.dropbox.com/s/dpdfr3546b5ir5k/SMS%20Bewafa.txt?dl=1");
		listOfFiles.add(l);
		
		l = new Likes();
		l.setId(2);
		l.setName("SMS College");
		l.setUrl("https://www.dropbox.com/s/it4iagmblby96jp/SMS%20College.txt?dl=1");
		listOfFiles.add(l);
		
		l = new Likes();
		l.setId(2);
		l.setName("SMS Dosti");
		l.setUrl("https://www.dropbox.com/s/1qju2a1zl544dde/SMS%20Dosti.txt?dl=1");
		listOfFiles.add(l);
		
		l = new Likes();
		l.setId(2);
		l.setName("SMS Insipire");
		l.setUrl("https://www.dropbox.com/s/uooygfjef6jkbd0/SMS%20Insipire.txt?dl=1");
		listOfFiles.add(l);
		
		l = new Likes();
		l.setId(2);
		l.setName("SMS Intazar");
		l.setUrl("https://www.dropbox.com/s/c737wl4vdkqmut7/SMS%20Intazar.txt?dl=1");
		listOfFiles.add(l);
		
		l = new Likes();
		l.setId(2);
		l.setName("SMS Jokes");
		l.setUrl("https://www.dropbox.com/s/m6waaj01srceg9j/SMS%20Jokes.txt?dl=1");
		listOfFiles.add(l);
		
		l = new Likes();
		l.setId(2);
		l.setName("SMS Love");
		l.setUrl("https://www.dropbox.com/s/c4navdkmtouaw18/SMS%20Love.txt?dl=1");
		listOfFiles.add(l);
		
		l = new Likes();
		l.setId(2);
		l.setName("SMS Other");
		l.setUrl("https://www.dropbox.com/s/5pww0jc6f5npnso/SMS%20Other.txt?dl=1");
		listOfFiles.add(l);
		
		l = new Likes();
		l.setId(2);
		l.setName("SMS Zindagi");
		l.setUrl("https://www.dropbox.com/s/h6hir175f64j6zu/SMS%20Zindagi.txt?dl=1");
		listOfFiles.add(l);
		
		
		
		l = new Likes();
		l.setId(3);
		l.setName("Status Judai");
		l.setUrl("https://www.dropbox.com/s/g0t2r3z1u26w0ri/Status%20%20Judai.txt?dl=1");
		listOfFiles.add(l);
		
		l = new Likes();
		l.setId(3);
		l.setName("Status Attitude");
		l.setUrl("https://www.dropbox.com/s/mav1nk8aming4we/Status%20Attitude.txt?dl=1");
		listOfFiles.add(l);
		
		l = new Likes();
		l.setId(3);
		l.setName("Status Bewafa");
		l.setUrl("https://www.dropbox.com/s/iz7ypsiiosnlax4/Status%20Bewafa.txt?dl=1");
		listOfFiles.add(l);
		
		l = new Likes();
		l.setId(3);
		l.setName("Status Dard");
		l.setUrl("https://www.dropbox.com/s/ky5zmspw40pcwd4/Status%20Dard.txt?dl=1");
		listOfFiles.add(l);
		
		l = new Likes();
		l.setId(3);
		l.setName("Status Dosti");
		l.setUrl("https://www.dropbox.com/s/t2nwme7v4fc5ivb/Status%20Dosti.txt?dl=1");
		listOfFiles.add(l);
		
		l = new Likes();
		l.setId(3);
		l.setName("Status Inspire");
		l.setUrl("https://www.dropbox.com/s/rwrjln7f8usot8v/Status%20Inspire.txt?dl=1");
		listOfFiles.add(l);
		
		l = new Likes();
		l.setId(3);
		l.setName("Status Love");
		l.setUrl("https://www.dropbox.com/s/m240vdkm6j0u5lu/Status%20Love.txt?dl=1");
		listOfFiles.add(l);
		
		l = new Likes();
		l.setId(3);
		l.setName("Status Romantic");
		l.setUrl("https://www.dropbox.com/s/bjc3pyar6on7vf9/Status%20Romantic.txt?dl=1");
		listOfFiles.add(l);
		
		l = new Likes();
		l.setId(3);
		l.setName("Status Yadde");
		l.setUrl("https://www.dropbox.com/s/ec2rshggk1ayetd/Status%20Yadde.txt?dl=1");
		listOfFiles.add(l);
		
		l = new Likes();
		l.setId(3);
		l.setName("Status Zindagi");
		l.setUrl("https://www.dropbox.com/s/z18vpmn5j2b0xww/Status%20Zindagi.txt?dl=1");
		listOfFiles.add(l);

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

					session.save(content);

				}

			}

			tx.commit();

		} catch (Exception e) {
			if (tx != null)
				tx.rollback();

			logger.error("saveData", e);
			payload.setResponseStatus(false);
			payload.setResponseMessage("OOPS! Something went wrong.");
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
