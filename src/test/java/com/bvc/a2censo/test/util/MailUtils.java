package com.bvc.a2censo.test.util;

import com.bvc.a2censo.test.model.Mail;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.*;
import org.testng.annotations.Test;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

public class MailUtils {
    private static final String EMAIL_PATH = System.getProperty("dataBasePath")+"email/";
    private static final String APPLICATION_NAME = "Gmail API Java for A2censo Test";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = EMAIL_PATH + "tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(GmailScopes.GMAIL_READONLY);
    private static final String CREDENTIALS_FILE_PATH = EMAIL_PATH + "credentials.json";

    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        //InputStream in = MailUtils.class.getClassLoader().getResourceAsStream();
        File initialFile = new File(CREDENTIALS_FILE_PATH);
        InputStream in = new FileInputStream(initialFile);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    private static Message getMessagePayload(Gmail service, String user, String id) throws IOException{
        return service.users().messages().get(user,id).execute();
    }

    public static String filterMessageHeader(Message message, String key){
        List<MessagePartHeader> headers =  message.getPayload().getHeaders();
        for (MessagePartHeader header : headers){
            if (header.getName().equals(key)) {
                return header.getValue();
            }
        }
        return "";
    }

    public static String getEmailPQRS(String caso) {
        String mailResponpose = null;
        try {
            // Build a new authorized API client service.
            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                    .setApplicationName(APPLICATION_NAME)
                    .build();

            // Print the labels in the user's account.
            String user = "me";
            String query = String.format("from:(%s) subject:(SERVICIO DE CONTACTO A2CENSO - CASO %s)", "notificaciones@a2censo.com", caso);
            ListMessagesResponse listMessagesResponse = service.users().messages().list(user).setMaxResults(new Long(1)).setQ(query).execute();
            List<Message> messages = listMessagesResponse.getMessages();
            if (messages.isEmpty()) {
                System.out.println("No messages found.");
            } else {
                for (Message message : messages) {
                    message = MailUtils.getMessagePayload(service, user, message.getId());
                    mailResponpose = new Mail(MailUtils.filterMessageHeader(message, "From"), MailUtils.filterMessageHeader(message, "To"), MailUtils.filterMessageHeader(message, "Subject"), MailUtils.filterMessageHeader(message, "Date")).toString();
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            return mailResponpose;
        }
    }

}
