package tools;

import java.io.*;
import java.net.*;

import pac.Do;

import net.sf.json.JSONObject;

public class PostSth {
	public static String postJson(String strURL, String params) {
		
		try {
			URL url = new URL(strURL);// ��������
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestMethod("POST"); // ��������ʽ
			connection.setRequestProperty("Accept", "application/json"); // ���ý������ݵĸ�ʽ
			connection.setRequestProperty("Content-Type", "application/json"); // ���÷������ݵĸ�ʽ
			connection.connect();
			OutputStreamWriter out = new OutputStreamWriter(
					connection.getOutputStream(), "UTF-8"); // utf-8����
			out.append(params);
			out.flush();
			out.close();
			// ��ȡ��Ӧ
			int length = (int) connection.getContentLength();// ��ȡ����
			InputStream is = connection.getInputStream();
			if (length != -1) {
				byte[] data = new byte[length];
				byte[] temp = new byte[512];
				int readLen = 0;
				int destPos = 0;
				while ((readLen = is.read(temp)) > 0) {
					System.arraycopy(temp, 0, data, destPos, readLen);
					destPos += readLen;
				}
				String result = new String(data, "UTF-8"); // utf-8����
				return result;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "error"; // �Զ��������Ϣ
	}

	public static String send(String url, String filePath) throws IOException {
		String result = null;
		url="https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token="+url;
		File file = new File(filePath);
		if (!file.exists() || !file.isFile()) {
			throw new IOException("�ļ�������");
		}

		/**
		 * ��һ����
		 */
		URL urlObj = new URL(url);
		// ����
		HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();

		/**
		 * ���ùؼ�ֵ
		 */
		con.setRequestMethod("POST"); // ��Post��ʽ�ύ����Ĭ��get��ʽ
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(false); // post��ʽ����ʹ�û���

		// ��������ͷ��Ϣ
		con.setRequestProperty("Connection", "Keep-Alive");
		con.setRequestProperty("Charset", "UTF-8");

		// ���ñ߽�
		String BOUNDARY = "----------" + System.currentTimeMillis();
		con.setRequestProperty("Content-Type", "multipart/form-data; boundary="
				+ BOUNDARY);

		// ����������Ϣ

		// ��һ���֣�
		StringBuilder sb = new StringBuilder();
		sb.append("--"); // �����������
		sb.append(BOUNDARY);
		sb.append("\r\n");
		sb.append("Content-Disposition: form-data;name=\"file\";filename=\""
				+ file.getName() + "\"\r\n");
		sb.append("Content-Type:application/octet-stream\r\n\r\n");

		byte[] head = sb.toString().getBytes("utf-8");

		// ��������
		OutputStream out = new DataOutputStream(con.getOutputStream());
		// �����ͷ
		out.write(head);

		// �ļ����Ĳ���
		// ���ļ������ļ��ķ�ʽ ���뵽url��
		DataInputStream in = new DataInputStream(new FileInputStream(file));
		int bytes = 0;
		byte[] bufferOut = new byte[1024];
		while ((bytes = in.read(bufferOut)) != -1) {
			out.write(bufferOut, 0, bytes);
		}
		in.close();

		// ��β����
		byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// ����������ݷָ���

		out.write(foot);

		out.flush();
		out.close();

		StringBuffer buffer = new StringBuffer();
		BufferedReader reader = null;
		try {
			// ����BufferedReader����������ȡURL����Ӧ
			reader = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				// System.out.println(line);
				buffer.append(line);
			}
			if (result == null) {
				result = buffer.toString();
			}
		} catch (IOException e) {
			System.out.println("����POST��������쳣��" + e);
			e.printStackTrace();
			throw new IOException("���ݶ�ȡ�쳣");
		} finally {
			if (reader != null) {
				reader.close();
			}

		}

		try {
			return JSONObject.fromObject(result).getString("errmsg");
		} catch (Exception e) {
		}
		return JSONObject.fromObject(result).getString("url");

	}

	public static void main(String[] args) throws IOException {
		

	}
}
