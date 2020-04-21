import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

public class Demo {
    //爬取所需网页
    private BufferedReader bufferedReader = null;
    private BufferedWriter bufferedWriter = null;
    @Test
    public void getContent() throws URISyntaxException, IOException {
        // 获取爬取网络的地址
        URI url = new URIBuilder().setScheme("https").setHost("www.baidu.com").build();

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
        CloseableHttpResponse httpResponse = null;
        File file = new File("src/a.txt");// 指定要写入的文件
        if (!file.exists()) {// 如果文件不存在则创建
            file.createNewFile();
        }
        bufferedWriter = new BufferedWriter(new FileWriter(file));
        try {
            httpResponse = httpClient.execute(httpGet);
            HttpEntity entity = httpResponse.getEntity();
            // 获取编码信息
            InputStream is = entity.getContent();
            String line = "";
            // 转换为缓冲流，提高效率，可以按行读取
            bufferedReader = new BufferedReader(new InputStreamReader(is, "utf-8"));
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
            bufferedWriter.close();
            is.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (httpResponse != null) {
                httpResponse.close();
            }
        }
    }
}
