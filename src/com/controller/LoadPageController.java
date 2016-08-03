package com.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
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
public class LoadPageController {

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
	@RequestMapping(value = "/insertttest.do")
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

	@RequestMapping(value = "/uploadbyconnect.do")
	public void PicPage(HttpServletRequest req, HttpServletResponse res) {
		String s=req.getParameter("name");
		System.out.println("s:"+s);
		try {
			req.setCharacterEncoding("utf-8");
			// ��ô����ļ���Ŀ������
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// ��ȡ�ļ��ϴ���Ҫ�����·����upload�ļ�������ڡ�
			String path = req.getSession().getServletContext()
					.getRealPath("/upload");

			File parentFile = new File(path);

			// System.out.println(f.exists());
			// ������ʱ����ļ��Ĵ洢�ң�����洢�ҿ��Ժ����մ洢�ļ����ļ��в�ͬ����Ϊ���ļ��ܴ�Ļ���ռ�ù����ڴ��������ô洢�ҡ�
			factory.setRepository(parentFile);
			// ���û���Ĵ�С�����ϴ��ļ���������������ʱ���ͷŵ���ʱ�洢�ҡ�
			factory.setSizeThreshold(1024 * 1024);
			// �ϴ��������ࣨ��ˮƽAPI�ϴ�������
			ServletFileUpload upload = new ServletFileUpload(factory);

			// ���� parseRequest��request������ ����ϴ��ļ� FileItem �ļ���list ��ʵ�ֶ��ļ��ϴ���
			List<FileItem> list = (List<FileItem>) upload.parseRequest(req);
			for (FileItem item : list) {
				// ��ȡ���������֡�
				String name = item.getFieldName();
				// �����ȡ�ı���Ϣ����ͨ���ı���Ϣ����ͨ��ҳ�����ʽ���������ַ�����
				if (item.isFormField()) {
					// ��ȡ�û�����������ַ�����
					String value = item.getString();
					req.setAttribute(name, value);
				}
				// ���������ǷǼ��ַ���������ͼƬ����Ƶ����Ƶ�ȶ������ļ���
				else {
					// ��ȡ·����
					String value = item.getName();
					// ȡ�����һ����б�ܡ�
					int start = value.lastIndexOf("\\");
					// ��ȡ�ϴ��ļ��� �ַ������֡�+1��ȥ����б�ܡ�
					String filename = value.substring(start + 1);

					req.setAttribute(name, filename);

					/*
					 * �������ṩ�ķ���ֱ��д���ļ��С� item.write(new File(path,filename));
					 */
					// �յ�д�����յ��ļ��С�
					File childFile = new File(path, filename);

					OutputStream out = new FileOutputStream(childFile);
					// System.out.println(path);
					InputStream in = item.getInputStream();

					int length = 0;
					byte[] buf = new byte[1024];
					System.out.println("��ȡ�ļ�����������:" + item.getSize());

					while ((length = in.read(buf)) != -1) {
						out.write(buf, 0, length);
					}
					in.close();
					out.close();

				}
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
