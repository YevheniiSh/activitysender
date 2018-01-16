import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.*;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost uploadFile = new HttpPost("http://interlink.staging.taho2.interlink-ua.com/api/v1/application_activities/statistic/c7d8c0a5-7549-4f87-8e64-45325d587fcc");
        uploadFile.setHeader("X-CSRF-TOKEN", "efc20351-9e6f-4080-b3b2-5b32c82fa783");
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        File f = new File("/home/yevheniishc/meta.xml");
        builder.addBinaryBody(
                "meta",
                new FileInputStream(f),
                ContentType.APPLICATION_OCTET_STREAM,
                f.getName()
        );

        HttpEntity multipart = builder.build();
        uploadFile.setEntity(multipart);
        CloseableHttpResponse response = httpClient.execute(uploadFile);
        HttpEntity responseEntity = response.getEntity();
        String result = new BufferedReader(new InputStreamReader(responseEntity.getContent()))
                .lines().collect(Collectors.joining("\n"));
        System.out.println(result);
    }
}
