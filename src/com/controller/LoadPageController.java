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
			// 获得磁盘文件条目工厂。
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// 获取文件上传需要保存的路径，upload文件夹需存在。
			String path = req.getSession().getServletContext()
					.getRealPath("/upload");

			File parentFile = new File(path);

			// System.out.println(f.exists());
			// 设置暂时存放文件的存储室，这个存储室可以和最终存储文件的文件夹不同。因为当文件很大的话会占用过多内存所以设置存储室。
			factory.setRepository(parentFile);
			// 设置缓存的大小，当上传文件的容量超过缓存时，就放到暂时存储室。
			factory.setSizeThreshold(1024 * 1024);
			// 上传处理工具类（高水平API上传处理？）
			ServletFileUpload upload = new ServletFileUpload(factory);

			// 调用 parseRequest（request）方法 获得上传文件 FileItem 的集合list 可实现多文件上传。
			List<FileItem> list = (List<FileItem>) upload.parseRequest(req);
			for (FileItem item : list) {
				// 获取表单属性名字。
				String name = item.getFieldName();
				// 如果获取的表单信息是普通的文本信息。即通过页面表单形式传递来的字符串。
				if (item.isFormField()) {
					// 获取用户具体输入的字符串，
					String value = item.getString();
					req.setAttribute(name, value);
				}
				// 如果传入的是非简单字符串，而是图片，音频，视频等二进制文件。
				else {
					// 获取路径名
					String value = item.getName();
					// 取到最后一个反斜杠。
					int start = value.lastIndexOf("\\");
					// 截取上传文件的 字符串名字。+1是去掉反斜杠。
					String filename = value.substring(start + 1);

					req.setAttribute(name, filename);

					/*
					 * 第三方提供的方法直接写到文件中。 item.write(new File(path,filename));
					 */
					// 收到写到接收的文件中。
					File childFile = new File(path, filename);

					OutputStream out = new FileOutputStream(childFile);
					// System.out.println(path);
					InputStream in = item.getInputStream();

					int length = 0;
					byte[] buf = new byte[1024];
					System.out.println("获取文件总量的容量:" + item.getSize());

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
