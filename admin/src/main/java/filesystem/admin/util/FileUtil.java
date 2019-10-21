package filesystem.admin.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.stereotype.Component;
import org.apache.commons.codec.binary.Base64;

@Component
public class FileUtil {
	public byte[] read(String path) {
		InputStream input = null;
		byte[] buffer = null;
		int length = 0;
		// 创建输入输出流对象
		try {
			input = new FileInputStream(path);

			// 获取文件长度
			try {
				length = input.available();

				// 创建缓存区域
				 buffer = new byte[length];
				// 将文件中的数据写入缓存数组
				input.read(buffer);
				// 将缓存数组中的数据输出到文件
				// output.write(buffer);

			} catch (IOException e) {

				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();

		} finally {
			if (input != null) {
				try {
					input.close(); // 关闭流

				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		return buffer;
	}
	public boolean  write(byte[] bytes,String path ) {
		OutputStream  output = null;
		boolean bo = false;
		// 创建输入输出流对象
		try {
			 output = new FileOutputStream(path);

			// 获取文件长度
			try {
				output.write(bytes);
				bo = true;

			} catch (IOException e) {

				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();

		} finally {
			if (output != null) {
				try {
					output.close(); // 关闭流

				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		return bo;
	}
	public String encodeStr(byte[] b){
		return Base64.encodeBase64String(b);
    }
	public byte[] decodeStr(String encodeStr){
		return Base64.decodeBase64(encodeStr);
    }
	
}
