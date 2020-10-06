package javatools.poiUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestWeb {

	@RequestMapping("/test")
	public String test() {
		try {
			File file = ResourceUtils.getFile("classpath:交付物.docx");
			InputStream is = new FileInputStream(file);
			System.out.println(is);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return "this is a test";
	}
	
}
