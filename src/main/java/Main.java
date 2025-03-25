import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
            // Создаем RestTemplate с конвертерами
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            String url = "http://94.198.50.185:7081/api/users";


            ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
            String sessionId = responseEntity.getHeaders().getFirst("Set-Cookie");


            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Cookie", sessionId);
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);


            User user = new User(3L, "James", "Brown", (byte) 23);
            HttpEntity<User> createUser = new HttpEntity<>(user, httpHeaders);
            ResponseEntity<String> postR = restTemplate.postForEntity(url, createUser, String.class);


            User updateUser = new User(3L, "Thomas", "Shelby", (byte) 24);
            HttpEntity<User> userHttpEntity = new HttpEntity<>(updateUser, httpHeaders);
            ResponseEntity<String> responseEntity1 = restTemplate.exchange(
                    url,
                    HttpMethod.PUT,
                    userHttpEntity,
                    String.class
            );

            HttpEntity<String> delete = new HttpEntity<>(httpHeaders);
            ResponseEntity<String> deleteUser = restTemplate.exchange(
                    url + "/3",
                    HttpMethod.DELETE,
                    delete,
                    String.class
            );
            String result = postR.getBody() + responseEntity1.getBody() + deleteUser.getBody();
            System.out.println(result);
        }
    }

