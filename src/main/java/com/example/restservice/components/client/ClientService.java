package com.example.restservice.components.client;

import com.example.restservice.components.dto.Person;
import com.example.restservice.components.dto.PersonStatus;
import org.springframework.boot.SpringApplication;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Scanner;

import static com.example.restservice.components.server.MyData.info;

public class ClientService {

    public static void main(String[] args) throws URISyntaxException {
        Scanner input = new Scanner(System.in);
        info();
        System.out.println();
        RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
        String uriStr="http://localhost:" + 8080 + "/persons/";

        while (true) {

            System.out.println("---------------------------------------------------------");
            System.out.println("Please select an option:");
            System.out.println("1. getAllPersons");
            System.out.println("2. getPerson by id");
            System.out.println("3. deletePerson by id");
            System.out.println("4. addPerson");
            System.out.println("5. updatePerson");
            System.out.println("6. hirePerson");
            System.out.println("7. vacatePerson");
            System.out.println("8. countPersons");
            System.out.println("9. Exit");
            System.out.println("---------------------------------------------------------");

            int choice = input.nextInt();

            switch (choice) {
                case 1 -> {
                    ResponseEntity<String> response = restTemplate.getForEntity(
                            new URI(uriStr),
                            String.class
                    );
                    System.out.println(response);
                }
                case 2 -> {
                    try {
                        System.out.print("Enter id to get person=");
                        int id = input.nextInt();
                        ResponseEntity<String> response = restTemplate.getForEntity(
                                new URI(uriStr.concat(Integer.toString(id))),
                                String.class
                        );
                        System.out.println(response);
                    }catch (HttpClientErrorException e){
                        System.out.println(e.getMessage());
                    }
                }

                case 3 -> {
                    try {
                        System.out.print("Enter id to delete person=");
                        int id = input.nextInt();
                        ResponseEntity<Boolean> response = restTemplate.exchange(
                                new URI(uriStr.concat(Integer.toString(id))),
                                HttpMethod.DELETE,
                                null,
                                Boolean.class
                        );
                        System.out.println(response);
                    }catch (HttpClientErrorException e){
                        System.out.println(e.getMessage());
                    }
                }
                case 4 -> {
                    try {
                        System.out.print("Enter name to add person=");
                        String name = input.next();
                        System.out.print("Enter age to add person=");
                        int age = input.nextInt();
                        System.out.print("Enter email to add person=");
                        String email = input.next();
                        System.out.print("Enter status to add person=");
                        String status = input.next();
                        status=status.toUpperCase();
                        PersonStatus s=PersonStatus.NOT_ACTIVE;
                        if(status.equals("ACTIVE")) s=PersonStatus.ACTIVE;
                        else if(status.equals("HIRED")) s=PersonStatus.HIRED;
                        HttpHeaders headers = new HttpHeaders();
                        headers.setContentType(MediaType.APPLICATION_JSON);
                        HttpEntity<Person> request = new HttpEntity<>(new Person(name,age,email,s), headers);
                        ResponseEntity<Person> response = restTemplate.postForEntity(
                                new URI(uriStr),
                                request,
                                Person.class
                        );
                        System.out.println(response);
                    }catch (RestClientException e){
                        System.out.println(e.getMessage());
                    }

                }
                case 5 -> {
                    try {
                        System.out.print("Enter id to update person=");
                        int id = input.nextInt();
                        System.out.print("Enter name to update person=");
                        String name = input.next();
                        System.out.print("Enter age to update person=");
                        int age = input.nextInt();
                        System.out.print("Enter email to update person=");
                        String email = input.next();
                        System.out.print("Enter status to update person=");
                        String status = input.next();
                        status=status.toUpperCase();
                        PersonStatus s=PersonStatus.NOT_ACTIVE;
                        if(status=="ACTIVE") s=PersonStatus.ACTIVE;
                        else if(status=="HIRED") s=PersonStatus.HIRED;
                        Person person=new Person(id,name,age,email,s);

                        HttpHeaders headers = new HttpHeaders();
                        headers.setContentType(MediaType.APPLICATION_JSON);


                        HttpEntity<Person> request = new HttpEntity<>(person, headers);

                        ResponseEntity<Person> response = restTemplate.exchange(
                                new URI(uriStr.concat(Integer.toString(id))),
                                HttpMethod.PUT,
                                request,
                                Person.class
                        );
                        System.out.println(response);

                    }catch (HttpClientErrorException e){
                        System.out.println(e.getMessage());
                    }
                }
                case 6 -> {
                    try {
                        System.out.print("Enter id to hire person=");
                        int id = input.nextInt();
                        HttpHeaders headers = new HttpHeaders();
                        headers.setContentType(MediaType.APPLICATION_JSON);
                        headers.set("X-HTTP-Method-Override", "PATCH");
                        ResponseEntity<String> response = restTemplate.exchange(new URI(uriStr.concat(Integer.toString(id)).concat("/hire")), HttpMethod.PATCH, null, String.class);

                        System.out.println(response);

                    }catch (HttpClientErrorException e){
                        System.out.println(e.getMessage());
                    }

                }
                case 7 -> {
                    try {
                        System.out.print("Enter id to vacate person=");
                        int id = input.nextInt();
                        HttpHeaders headers = new HttpHeaders();
                        headers.setContentType(MediaType.APPLICATION_JSON);

                        headers.set("X-HTTP-Method-Override", "PATCH");
                        ResponseEntity<String> response = restTemplate.exchange(new URI(uriStr.concat(Integer.toString(id)).concat("/vacate")), HttpMethod.PATCH, null, String.class);

                        System.out.println(response);

                    }catch (HttpClientErrorException e){
                        System.out.println(e.getMessage());
                    }
                }
                case 8 -> {
                    try {
                        ResponseEntity<String> response = restTemplate.getForEntity(
                                new URI(uriStr.concat("countAll")),
                                String.class
                        );
                        System.out.println(response);
                    }catch (HttpClientErrorException e){
                        System.out.println(e.getMessage());
                    }
                }
                case 9 -> {
                    System.out.println("Exit");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }

        }

    }

}
