package com.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.domain.Test;
import com.domapper.DoTest;

@Controller
public class TestPageController {
	/*
	 * String res = "conf.xml";
	 * 
	 * InputStream is = LoadPageController.class.getClassLoader()
	 * .getResourceAsStream(res);
	 * 
	 * SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder()
	 * .build(is);
	 * 
	 * SqlSession sqlSession = sqlSessionFactory.openSession();
	 */

	SqlSession sqlSession = InitSession.getSqlSession();

	@ResponseBody
	@RequestMapping(value = "/inserttest.do")
	public Map<String, Object> InsertTestPage(HttpServletRequest req,
			HttpServletResponse res) {
		String name = req.getParameter("name");
		String password = req.getParameter("password");
		Test test = new Test(name, password);
		Map<String, Object> json = new HashMap<String, Object>();
		String stateMent = "com.mapping.testMapper.insertTest";
		int i = DoTest.insertTest(sqlSession, stateMent, test);
		json.put("i", i);

		return json;

	}

	@ResponseBody
	@RequestMapping(value = "/uploadbyclient.do")
	public void PicClientPage(HttpServletRequest req, HttpServletResponse res) {
		try {
			req.setCharacterEncoding("UTF-8");
			boolean isMultipart = ServletFileUpload.isMultipartContent(req);
			System.out.println("isMultipartContent:" + isMultipart );
			
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			List<?> items = upload.parseRequest(req);
			Iterator<?> iter = items.iterator();
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				if (item.isFormField()) {
					// 普通文本信息处理
					String paramName = item.getFieldName();

					String paramValue = item.getString();
					System.out.println(paramName + ":" + paramValue + ":");
				} else {
					// 上传文件信息处理
					String paramName = item.getFieldName();
					String fileName = item.getName();
					String[] fileNames = fileName.split("\\.");
					String nameOfFile = fileNames[0];
					String typeOfFile = fileNames[1];
					System.out.println("paramName:"+paramName+"--fileName:" + fileName);
					//
					// String filePath = "/upload/1.jpg";
					//设置文件的保存路径
					String filePath = req.getSession().getServletContext()
							.getRealPath("/upload");
					String realPath = filePath + File.separator + nameOfFile
							+ "." + typeOfFile;
					//FileItem类内部方法
					item.write(new File(realPath));
					item.delete();
					/*
					 * 以流的形式进行数据存储
					byte[] data = item.get();
					FileOutputStream fos = new FileOutputStream(realPath);
					fos.write(data);
					fos.close();
					*/
				}
			}
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
}
