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
        HttpPost uploadFile = new HttpPost("http://interlink.taho2-loc.com:8080/api/v1/application_activities/statistic/auto_id"); // add auto_id
        uploadFile.setHeader("X-CSRF-TOKEN", ""); // add token
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        File f = new File("/home/yevheniishc/meta.xml");
        builder.addBinaryBody(
                "meta",
                new FileInputStream(f),
                ContentType.APPLICATION_OCTET_STREAM,
                f.getName()
        );
        File screenshotA = new File("/home/yevheniishc/screenshot_20180116-161100-A-original.png");
        builder.addBinaryBody(
                "screenshot",
                new FileInputStream(screenshotA),
                ContentType.APPLICATION_OCTET_STREAM,
                screenshotA.getName()
        );
        File screenshotB = new File("/home/yevheniishc/screenshot_20180116-161100-B-original.png");
        builder.addBinaryBody(
                "screenshot",
                new FileInputStream(screenshotB),
                ContentType.APPLICATION_OCTET_STREAM,
                screenshotB.getName()
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
